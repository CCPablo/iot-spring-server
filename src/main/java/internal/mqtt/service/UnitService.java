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

    private final DeviceService deviceService;

    private Map<Integer, Map<Integer, UnitValue>> cachedValues = new ConcurrentHashMap<>();

    public UnitService(UnitValueRepositoryImpl unitValueRepository, DeviceService deviceService) {
        this.unitValueRepository = unitValueRepository;
        this.deviceService = deviceService;
    }

    public void addValue(UnitValue value) {
        if(!deviceService.isUnitPresent(value.getDeviceId(), value.getUnitId())) {
            GatewayClient.publishMessage(new GatewayConnectMsg(true), TopicsToPub.GATEWAY_CONNECT_TOPIC);
            throw new ComponentNotRegisteredException();
        }

        if(unitValuePresent(value.getDeviceId(), value.getUnitId())) {
            UnitValue currentValue = cachedValues.get(value.getDeviceId()).get(value.getUnitId());
            if(currentValue.getValue().equals(value.getValue())) {
                return;
            }
            currentValue.setValue(value.getValue());
            currentValue.setTimestamp(value.getTimestamp());
            unitValueRepository.addUnitValue(currentValue);
        } else if(devicePresent(value.getDeviceId())){
            cachedValues.get(value.getDeviceId()).put(value.getUnitId(), value);
            unitValueRepository.addUnitValue(value);
        } else {
            cachedValues.put(value.getDeviceId(), new HashMap<>(Map.of(value.getUnitId(), value)));
            unitValueRepository.addUnitValue(value);
        }
    }

    private boolean unitValuePresent(Integer deviceId, Integer unitId) {
        return devicePresent(deviceId) && cachedValues.get(deviceId).keySet().stream().anyMatch(unitId::equals);
    }

    private boolean devicePresent(Integer deviceId) {
        return cachedValues.containsKey(deviceId);
    }
}
