package internal.service;

import internal.model.unit.UnitMeasure;
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

    private final Map<Integer, Map<Integer, LinkedList<UnitMeasure>>> cachedValues = new ConcurrentHashMap<>();

    public ActuatorService(UnitValueRepositoryImpl unitValueRepository, NodeService nodeService) {
        this.unitValueRepository = unitValueRepository;
        this.nodeService = nodeService;
    }

    public long getMeanValue(Integer nodeId, Integer unitId) {
        Map<Integer, LinkedList<UnitMeasure>> valuesOfNode = cachedValues.get(nodeId);
        if (valuesOfNode.isEmpty() || !isUnitCached(unitId, valuesOfNode)) {
            return -1;
        } else {
            Queue<UnitMeasure> unitMeasures = cachedValues.get(nodeId).get(unitId);
            return !unitMeasures.isEmpty() ? (long) unitMeasures.stream().mapToLong(UnitMeasure::getValue).average().orElse(-1) : -1;
        }
    }

    private boolean isUnitCached(Integer unitId, Map<Integer, LinkedList<UnitMeasure>> valuesOfNode) {
        return valuesOfNode.keySet().stream().anyMatch(unitId::equals);
    }
}
