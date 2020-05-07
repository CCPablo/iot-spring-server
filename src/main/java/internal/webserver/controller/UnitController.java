package internal.webserver.controller;

import internal.mqtt.service.NodeService;
import internal.webserver.service.UnitControllerService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@RestController("/v1/api/actuator")
@Api(value = "Noded Resource REST Endpoint")
public class UnitController {

    private final NodeService nodeService;

    private final UnitControllerService unitControllerService;

    public UnitController(NodeService nodeService, UnitControllerService unitControllerService) {
        this.nodeService = nodeService;
        this.unitControllerService = unitControllerService;
    }

    @PostMapping(value = "/value")
    public void setActuatorValue(@RequestParam Integer nodeId, @RequestParam Integer unitId, @RequestParam Long value) {
        unitControllerService.updateActuatorValue(nodeId, unitId, value);
    }

}
