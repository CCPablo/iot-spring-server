package internal.service;

import internal.model.unit.UnitValue;
import internal.repository.implementation.UnitValueRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class ValueService {

    private final Clock clock;

    private final UnitValueRepositoryImpl unitValueRepository;

    private final Map<Integer, Map<Integer, LinkedList<UnitValue>>> cachedValues = new ConcurrentHashMap<>();

    public ValueService(Clock clock, UnitValueRepositoryImpl unitValueRepository) {
        this.clock = clock;
        this.unitValueRepository = unitValueRepository;
    }

    public void addValue(UnitValue unitValue) {
        unitValueRepository.addUnitValue(unitValue);
    }

    public long getMeanValue(Integer nodeId, Integer unitId) {
        Map<Integer, LinkedList<UnitValue>> valuesOfNode = cachedValues.get(nodeId);
        if (valuesOfNode.isEmpty() || !isUnitCached(unitId, valuesOfNode)) {
            return -1;
        } else {
            Queue<UnitValue> unitValues = cachedValues.get(nodeId).get(unitId);
            return !unitValues.isEmpty() ? (long) unitValues.stream().mapToLong(UnitValue::getValue).average().orElse(-1) : -1;
        }
    }

    public List<Pair<Long, Long>> getIntervalValuesArray(Integer nodeId, Integer unitId, PeriodData periodData) {
        List<UnitValue> allValuesWithinPeriod = unitValueRepository.findByIdSinceDate(nodeId, unitId, periodData.getOriginOfPeriod());
        List<List<Long>> intervalsWithAllValues = getIntervalsWithAllValues(allValuesWithinPeriod, periodData);
        return getIntervalValuesArray(intervalsWithAllValues, periodData);
    }

    private List<List<Long>> getIntervalsWithAllValues(List<UnitValue> allValues, PeriodData periodData) {
        List<List<Long>> intervalsWithAllValues = getEmptyDynamicMatrix(periodData.getNumberOfIntervals());
        allValues.forEach(value -> addToRightInterval(value, intervalsWithAllValues, periodData));
        return intervalsWithAllValues;
    }

    private List<List<Long>> getEmptyDynamicMatrix(Integer numberOfIntervals) {
        List<List<Long>> matrix = new ArrayList<>();
        for(int i = 0; i < numberOfIntervals; i++) {
            matrix.add(new ArrayList<>());
        }
        return matrix;
    }

    private void addToRightInterval(UnitValue value, List<List<Long>> intervalArray, PeriodData periodData) {
        if(periodData.includesTimeValue(value.getTimestamp())) {
            intervalArray
                    .get(periodData.getIntervalIndex(value.getTimestamp()))
                    .add(value.getValue());
        }
    }

    private List<Pair<Long, Long>> getIntervalValuesArray(List<List<Long>> intervalsWithAllValues, PeriodData periodData) {
        List<Pair<Long, Long>> intervalValuesArray = new ArrayList<>();
        for(int i = 0; i < periodData.getNumberOfIntervals(); i++) {
            intervalValuesArray.add(Pair.of(
                    periodData.getIntervalTimestamp(i),
                    getMeanOfValues(intervalsWithAllValues, intervalValuesArray, i)));
        }
        return intervalValuesArray;
    }

    private long getMeanOfValues(List<List<Long>> intervalsWithAllValues, List<Pair<Long, Long>> intervalValuesArray, int intervalIndex) {
        List<Long> valuesOfInterval = intervalsWithAllValues.get(intervalIndex);
        return intervalIsEmpty(valuesOfInterval, intervalIndex) ?
                intervalValuesArray.get(intervalIndex - 1).getSecond() :
                getIntervalMean(valuesOfInterval);
    }

    private boolean intervalIsEmpty(List<Long> valuesOfInterval, int intervalIndex) {
        return valuesOfInterval.isEmpty() && intervalIndex != 0;
    }

    private long getIntervalMean(List<Long> values) {
        long sum = 0L;
        if(!values.isEmpty()) {
            for (Long value : values) {
                sum += value;
            }
            return sum / values.size();
        }
        return sum;
    }

    private boolean isUnitCached(Integer unitId, Map<Integer, LinkedList<UnitValue>> valuesOfNode) {
        return valuesOfNode.keySet().stream().anyMatch(unitId::equals);
    }

}
