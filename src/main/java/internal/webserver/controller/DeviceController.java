package internal.webserver.controller;


import internal.mqtt.service.DeviceService;
import internal.model.device.Device;
import internal.model.unit.Unit;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController("/v1/api")
@Api(value = "Device Resource REST Endpoint")
public class DeviceController {

    @Autowired
    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping(value = "/devices")
    public List<Device> getAllDevices() {
        return deviceService.getAllDevices();
    }

    @GetMapping(value = "/device/{id}")
    public Device getDevice(@PathVariable Integer id) {
        return deviceService.getDevice(id);
    }

    @PostMapping(value = "/device/{id}/create")
    public void addNewDevice(@PathVariable Integer id, @RequestParam String name, String description, @RequestBody List<Unit> units) {
        deviceService.addNewDevice(id, name, description, units);
    }

    @PostMapping(value = "/device/{id}/update")
    public String addNewDevice(@RequestParam String name, String description, @RequestBody List<Unit> units) {
        deviceService.addNewDevice(name, description, units);
        return "OK";
    }
}
