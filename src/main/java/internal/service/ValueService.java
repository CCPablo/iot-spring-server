package internal.service;

import internal.model.unit.UnitValue;
import internal.repository.implementation.UnitValueRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
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

    public List<Pair<Long, Long>> getIntervalValuesArray(Integer nodeId, Integer unitId, Long intervalDuration, Integer numberOfIntervals) {
        List<List<Long>> intervalFullValuesArray = getIntervalFullValuesArray(nodeId, unitId, intervalDuration, numberOfIntervals, clock.instant().toEpochMilli());
        return getIntervalValuesArray(intervalFullValuesArray, intervalDuration, numberOfIntervals);
    }

    private List<List<Long>> getIntervalFullValuesArray(Integer nodeId, Integer unitId, Long intervalDuration, Integer numberOfIntervals, Long currentMillis) {
        List<List<Long>> intervalFullValuesArray = new ArrayList<>();
        for(int i = 0; i < numberOfIntervals; i++) {
            intervalFullValuesArray.add(new ArrayList<>());
        }
        unitValueRepository.findByIdSinceDate(nodeId, unitId, currentMillis - intervalDuration*numberOfIntervals)
                .forEach(value -> addToArray(value, intervalFullValuesArray, intervalDuration, numberOfIntervals, currentMillis));

        return intervalFullValuesArray;
    }

    private void addToArray(UnitValue value, List<List<Long>> intervalArray, Long intervalDuration, Integer numberOfIntervals, Long currentMillis) {
        if(currentMillis - intervalDuration*numberOfIntervals <= value.getTimestamp() && value.getTimestamp() < currentMillis) {
            intervalArray
                    .get(getIntervalArrayIndex((double) currentMillis, (double) value.getTimestamp(), (double) intervalDuration, (double) numberOfIntervals))
                    .add(value.getValue());
        }
    }

    private Integer getIntervalArrayIndex(double currentMillis, double timestamp, double intervalDuration, double numberOfIntervals) {
        return (int) ((intervalDuration*numberOfIntervals - (currentMillis - timestamp)) / (intervalDuration));
    }

    private List<Pair<Long, Long>> getIntervalValuesArray(List<List<Long>> fullIntervalArray, Long intervalDuration, Integer numberOfIntervals) {
        List<Pair<Long, Long>> intervalValuesArray = new ArrayList<>();
        for(int i = 0; i < numberOfIntervals; i++) {
            intervalValuesArray.add(Pair.of(
                    getIntervalTimestamp(i, intervalDuration, numberOfIntervals),
                    fullIntervalArray.get(i).isEmpty() && i!=0 ? intervalValuesArray.get(i-1).getSecond(): getIntervalMean(fullIntervalArray.get(i))));
        }
        return intervalValuesArray;
    }

    private Long getIntervalTimestamp(Integer index, Long intervalDuration, Integer numberOfIntervals) {
        return clock.instant().toEpochMilli() - intervalDuration*numberOfIntervals + intervalDuration*index;
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
