package internal.webserver.controller;


import internal.mqtt.service.NodeService;
import internal.model.node.Node;
import internal.model.unit.Unit;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/v1/api")
@Api(value = "Nodes Resource REST Endpoint")
public class NodeController {

    @Autowired
    private final NodeService nodeService;

    public NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @GetMapping(value = "/nodes")
    public List<Node> getAllNodes() {
        return nodeService.getAllNodes();
    }

    @GetMapping(value = "/nodes/{id}")
    public Node getNode(@PathVariable Integer id) {
        return nodeService.getNode(id);
    }

    @PostMapping(value = "/nodes/{id}/create")
    public void addNewNode(@PathVariable Integer id, @RequestParam String name, String description, @RequestBody List<Unit> units) {
        nodeService.addNewNode(id, name, description, units);
    }

    @PostMapping(value = "/nodes/{id}/update")
    public String addNewNode(@RequestParam String name, String description, @RequestBody List<Unit> units) {
        nodeService.addNewNode(name, description, units);
        return "OK";
    }
}
