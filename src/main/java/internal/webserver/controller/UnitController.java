package internal.webserver.controller;

import internal.mqtt.service.DeviceService;
import internal.webserver.service.UnitControllerService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@RestController("/v1/api/actuator")
@Api(value = "Device Resource REST Endpoint")
public class UnitController {

    private final DeviceService deviceService;

    private final UnitControllerService unitControllerService;

    public UnitController(DeviceService deviceService, UnitControllerService unitControllerService) {
        this.deviceService = deviceService;
        this.unitControllerService = unitControllerService;
    }

    @PostMapping(value = "/value")
    public void setActuatorValue(@RequestParam Integer deviceId, @RequestParam Integer unitId, @RequestParam Long value) {
        unitControllerService.updateActuatorValue(deviceId, unitId, value);
    }

}
