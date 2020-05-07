package internal.mqtt.service;

import internal.model.unit.UnitValue;
import internal.mqtt.GatewayClient;
import internal.mqtt.listener.exception.ComponentNotRegisteredException;
import internal.mqtt.publisher.GatewayConnectMsg;
import internal.mqtt.topic.TopicsToPub;
import internal.repository.implementation.UnitValueRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UnitService {
    private final UnitValueRepositoryImpl unitValueRepository;

    private final NodeService nodeService;

    private final Map<Integer, Map<Integer, UnitValue>> cachedValues = new ConcurrentHashMap<>();

    public UnitService(UnitValueRepositoryImpl unitValueRepository, NodeService nodeService) {
        this.unitValueRepository = unitValueRepository;
        this.nodeService = nodeService;
    }

    public void addValue(UnitValue value) {
        if (!nodeService.isUnitPresent(value.getNodeId(), value.getUnitId())) {
            GatewayClient.publishMessage(new GatewayConnectMsg(true), TopicsToPub.GATEWAY_CONNECT_TOPIC);
            throw new ComponentNotRegisteredException();
        }

        if (unitValuePresent(value.getNodeId(), value.getUnitId())) {
            UnitValue currentValue = cachedValues.get(value.getNodeId()).get(value.getUnitId());
            if (currentValue.getValue().equals(value.getValue())) {
                return;
            }
            currentValue.setValue(value.getValue());
            currentValue.setTimestamp(value.getTimestamp());
            unitValueRepository.addUnitValue(currentValue);
        } else if (nodePresent(value.getNodeId())) {
            cachedValues.get(value.getNodeId()).put(value.getUnitId(), value);
            unitValueRepository.addUnitValue(value);
        } else {
            cachedValues.put(value.getNodeId(), new HashMap<>(Map.of(value.getUnitId(), value)));
            unitValueRepository.addUnitValue(value);
        }
    }

    private boolean unitValuePresent(Integer nodeId, Integer unitId) {
        return nodePresent(nodeId) && cachedValues.get(nodeId).keySet().stream().anyMatch(unitId::equals);
    }

    private boolean nodePresent(Integer nodeId) {
        return cachedValues.containsKey(nodeId);
    }
}
