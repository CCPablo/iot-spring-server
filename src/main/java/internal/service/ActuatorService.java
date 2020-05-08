package internal.service;

import internal.model.unit.UnitValue;
import internal.repository.implementation.UnitValueRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class ActuatorService {
    private final UnitValueRepositoryImpl unitValueRepository;

    private final NodeService nodeService;

    private final Map<Integer, Map<Integer, LinkedList<UnitValue>>> cachedValues = new ConcurrentHashMap<>();

    public ActuatorService(UnitValueRepositoryImpl unitValueRepository, NodeService nodeService) {
        this.unitValueRepository = unitValueRepository;
        this.nodeService = nodeService;
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

    private boolean isUnitCached(Integer unitId, Map<Integer, LinkedList<UnitValue>> valuesOfNode) {
        return valuesOfNode.keySet().stream().anyMatch(unitId::equals);
    }
}
