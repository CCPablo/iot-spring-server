package internal.service;

import internal.model.unit.UnitValue;
import internal.mqtt.GatewayClient;
import internal.mqtt.listener.exception.ComponentNotRegisteredException;
import internal.mqtt.publisher.GatewayConnectMsg;
import internal.mqtt.topic.TopicsToPub;
import internal.repository.implementation.UnitValueRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SensorService {

    private final UnitValueRepositoryImpl unitValueRepository;

    private final NodeService nodeService;

    private final Map<Integer, Map<Integer, LinkedList<UnitValue>>> cachedValues = new ConcurrentHashMap<>();

    public SensorService(UnitValueRepositoryImpl unitValueRepository, NodeService nodeService) {
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

    public void addValue(UnitValue value) {
        if (!nodeService.isUnitPresent(value.getNodeId(), value.getUnitId())) {
            GatewayClient.publishMessage(new GatewayConnectMsg(true), TopicsToPub.GATEWAY_CONNECT_TOPIC);
            throw new ComponentNotRegisteredException();
        }

        Map<Integer, LinkedList<UnitValue>> valuesOfNode = cachedValues.get(value.getNodeId());

        if (valuesOfNode.isEmpty()) {
            LinkedList<UnitValue> newList = new LinkedList<>();
            newList.add(value);
            cachedValues.put(value.getNodeId(), new HashMap<>(Map.of(value.getUnitId(), newList)));
            unitValueRepository.addUnitValue(value);
        } else if (!isUnitCached(value.getUnitId(), valuesOfNode)) {
            LinkedList<UnitValue> newList = new LinkedList<>();
            newList.add(value);
            cachedValues.get(value.getNodeId()).put(value.getUnitId(), newList);
            unitValueRepository.addUnitValue(value);
        } else {
            UnitValue currentValue = cachedValues.get(value.getNodeId()).get(value.getUnitId()).getLast();
            if (currentValue.getValue().equals(value.getValue())) {
                return;
            }
            currentValue.setValue(value.getValue());
            currentValue.setTimestamp(value.getTimestamp());
            unitValueRepository.addUnitValue(currentValue);
        }
    }

    private boolean isUnitCached(Integer unitId, Map<Integer, LinkedList<UnitValue>> valuesOfNode) {
        return valuesOfNode.keySet().stream().anyMatch(unitId::equals);
    }
}
