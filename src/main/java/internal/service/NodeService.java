package internal.service;

import internal.model.node.Node;
import internal.model.StatusType;
import internal.model.unit.Unit;
import internal.repository.NodeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NodeService {

    private final NodeRepository nodeRepository;

    private final Map<Integer, Node> cachedNodes = new ConcurrentHashMap<>();

    public NodeService(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @PostConstruct
    public void init() {
        nodeRepository.findAll().forEach((node) -> cachedNodes.putIfAbsent(node.getId(), node));
    }

    public void addNewNode(Node node) {
        cachedNodes.putIfAbsent(node.getId(), node);
    }

    public void addNewNode(Integer nodeId, StatusType statusType, String name, String description, List<Unit> units) {
        Node node = Node(nodeId, statusType, name, description, units);
        cachedNodes.putIfAbsent(node.getId(), node);
    }

    public void addNewNode(String name, String description, List<Unit> units) {
    }

    public void addNewNode(Integer nodeId, String name, String description, List<Unit> units) {
        Node node = Node(nodeId, StatusType.OFF, name, description,  units);

        cachedNodes.putIfAbsent(nodeId, node);
    }

    public List<Node> getAllNodes() {
        return new ArrayList<>(cachedNodes.values());
    }

    public void updateNode(Node node) {
        cachedNodes.computeIfPresent(node.getId(), (integer, node1) -> node1 = node);
        nodeRepository.updateNode(node);
    }

    public boolean isUnitPresent(Integer nodeId, Integer unitId) {
        Node node = getNode(nodeId);
        return node != null && node.getUnits().stream().anyMatch(unit -> unitId.equals(unit.getId()));
    }

    public Node getNode(Integer id) {
        return cachedNodes.getOrDefault(id, nodeRepository.findNodeById(id));
    }

    public boolean isNodePresent(Integer nodeId) {
        return cachedNodes.containsKey(nodeId);
    }

    public boolean isNodeActive(Integer nodeId) {
        return cachedNodes.getOrDefault(nodeId, Node.builder().status(StatusType.OFF).build()).getStatus().equals(StatusType.ON);
    }

    private Node Node(Integer nodeId, StatusType statusType, String name, String description, List<Unit> units) {
        return Node.builder()
                .status(statusType)
                .name(name)
                .description(description)
                .units(units)
                .id(nodeId)
                .build();
    }
}
