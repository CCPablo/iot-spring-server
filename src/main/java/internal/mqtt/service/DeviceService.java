package internal.mqtt.service;

import internal.model.device.Device;
import internal.model.StatusType;
import internal.model.unit.Unit;
import internal.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.System.currentTimeMillis;

@Service
public class DeviceService {

    final private DeviceRepository deviceRepository;

    private final Map<Integer, Device> cachedDevices = new ConcurrentHashMap<>();

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @PostConstruct
    public void init() {
        deviceRepository.findAll().forEach((device) -> cachedDevices.putIfAbsent(device.getId(), device));
    }

    public void addNewDevice(Device device) {
        cachedDevices.putIfAbsent(device.getId(), device);
    }

    public void addNewDevice(Integer deviceId, StatusType statusType, String name, String description,
                             Long connectedSince, List<Unit> units) {
        Device device = getDeviceObject(deviceId, statusType, name, description, connectedSince, units);
        cachedDevices.putIfAbsent(device.getId(), device);
    }

    public void addNewDevice(String name, String description, List<Unit> units) {
    }

    public void addNewDevice(Integer deviceId, String name, String description, List<Unit> units) {
        Device device = getDeviceObject(deviceId, StatusType.OFF, name, description, currentTimeMillis(), units);

        cachedDevices.putIfAbsent(deviceId, device);
    }

    public List<Device> getAllDevices() {
        return new ArrayList<>(cachedDevices.values());
    }

    public void updateDevice(Device device) {
        cachedDevices.computeIfPresent(device.getId(), (integer, device1) -> device1 = device);
        deviceRepository.updateDevice(device);
    }

    public boolean isUnitPresent(Integer deviceId, Integer unitId) {
        return cachedDevices.containsKey(deviceId) && cachedDevices.get(deviceId).getUnits().stream().anyMatch(unit -> unitId.equals(unit.getId()));
    }

    public Device getDevice(Integer id) {
        return cachedDevices.getOrDefault(id, deviceRepository.findDeviceById(id));
    }

    public boolean isDevicePresent(Integer deviceId) {
        return cachedDevices.containsKey(deviceId);
    }

    public boolean isDeviceActive(Integer deviceId) {
        return cachedDevices.getOrDefault(deviceId, Device.builder().status(StatusType.OFF).build()).getStatus().equals(StatusType.ON);
    }

    private Device getDeviceObject(Integer deviceId, StatusType statusType, String name, String description,
                                   Long connectedSince, List<Unit> units) {
        return Device.builder()
                .status(statusType)
                .name(name)
                .description(description)
                .units(units)
                .id(deviceId)
                .build();
    }
}
