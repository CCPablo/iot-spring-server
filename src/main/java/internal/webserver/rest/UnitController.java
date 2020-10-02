package internal.webserver.rest;

import internal.service.NodeService;
import internal.webserver.service.UnitControllerService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/unit")
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
