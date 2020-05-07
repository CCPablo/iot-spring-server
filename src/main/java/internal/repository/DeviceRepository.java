package internal.repository;

import internal.model.device.Device;

import java.util.List;

public interface DeviceRepository {

    List<Device> findAll();

    void deleteAll();

    void saveDevice(Device device);

    Device findDeviceById(Integer deviceId);

    long updateDevice(Device device);

    void deleteDeviceById(Integer deviceId);
}
