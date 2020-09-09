package internal.webserver.rest;

import internal.model.unit.UnitMeasure;
import internal.service.PeriodData;
import internal.service.ValueService;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/values")
public class ValueController {

    private final ValueService valueService;

    public ValueController(ValueService valueService) {
        this.valueService = valueService;
    }

    @PutMapping(value = "/add")
    @ResponseStatus(HttpStatus.OK)
    public void addValue(@RequestParam Integer nodeId, @RequestParam Integer unitId, @RequestParam Long value) {
        valueService.addValue(UnitMeasure.builder().nodeId(nodeId).unitId(unitId).value(value).timestamp(Instant.now().toEpochMilli()).build());
    }

    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public List<Pair<Long, Long>> getValues(@RequestParam Integer nodeId,
                                            @RequestParam Integer unitId,
                                            @RequestParam Long intervalDuration,
                                            @RequestParam Integer numberOfValues) {
        return valueService.getIntervalValuesArray(nodeId, unitId, new PeriodData(intervalDuration, numberOfValues));
    }

    @GetMapping(value = "/units")
    @ResponseStatus(HttpStatus.OK)
    public List<List<Pair<Long, Long>>> getArraysValues(@RequestParam List<String> unitKeys,
                                            @RequestParam Long intervalDuration,
                                            @RequestParam Integer numberOfValues) {

        return valueService.getIntervalValuesArrays(unitKeys, new PeriodData(intervalDuration, numberOfValues));
    }
}
