package internal.webserver.rest;

import internal.model.unit.UnitValue;
import internal.repository.implementation.UnitValueRepositoryImpl;
import internal.repository.model.ApplicationUser;
import internal.service.ValueService;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Random;

@RestController("/v1/api/value")
public class ValueController {

    private final ValueService valueService;

    public ValueController(ValueService valueService) {
        this.valueService = valueService;
    }

    @PutMapping(value = "/add")
    @ResponseStatus(HttpStatus.OK)
    public void addValue(@RequestParam Integer nodeId, @RequestParam Integer unitId, @RequestParam Long value) {
        valueService.addValue(UnitValue.builder().nodeId(nodeId).unitId(unitId).value(value).timestamp(Instant.now().toEpochMilli()).build());
    }

    @GetMapping(value = "/get")
    @ResponseStatus(HttpStatus.OK)
    public List<Pair<Long, Long>> getValues(@RequestParam Integer nodeId,
                                            @RequestParam Integer unitId,
                                            @RequestParam Long intervalDuration,
                                            @RequestParam Integer numberOfValues) {
        return valueService.getIntervalValuesArray(nodeId, unitId, intervalDuration, numberOfValues);
    }
}