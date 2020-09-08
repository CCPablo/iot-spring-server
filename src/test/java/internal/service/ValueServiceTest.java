package internal.service;

import internal.model.unit.UnitValue;
import internal.repository.implementation.UnitValueRepositoryImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static internal.service.ValueServiceTest.TestData.getUnitValues;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValueServiceTest {

    @Mock
    UnitValueRepositoryImpl unitValueRepository;

    @InjectMocks
    ValueService valueService;

    @Test
    void getIntervalValues() {
        PeriodData periodData = new PeriodData(1000L, 5);
        when(unitValueRepository.findByIdSinceDate(eq(1),eq(1), anyLong())).thenReturn(getUnitValues(periodData.getEndOfPeriod(), 50, 100L));

        List<Pair<Long, Long>> intervalValuesArray = valueService.getIntervalValuesArray(1,1, new PeriodData(1000L, 5));

    }

    interface TestData {
        static List<UnitValue> getUnitValues(Long endOfPeriod, Integer numberOfValues, Long intervalTime) {
            List<UnitValue> unitValues = new ArrayList<>();
            for( int i = 0; i<numberOfValues; i++) {
                unitValues.add(getUnitValue((long)i + 100L, endOfPeriod - intervalTime*i));
            }
            return unitValues;
        }

        static UnitValue getUnitValue(Long value, Long timestamp) {
            return UnitValue.builder().nodeId(1).unitId(1).value(value).timestamp(timestamp).build();
        }
    }
}