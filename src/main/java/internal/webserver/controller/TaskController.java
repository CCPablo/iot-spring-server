package internal.webserver.controller;

import internal.model.StatusType;
import internal.scheduler.task.CommuteTask;
import internal.scheduler.service.PeriodicTaskService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController("/v1/api/task")
@AllArgsConstructor
@Api(value = "Tasks REST Endpoint")
public class TaskController {

    @Autowired
    private final PeriodicTaskService periodicTaskService;

    @PostMapping(value = "/task/create")
    public void addNewTask(@RequestParam String name, @RequestParam Integer deviceId, @RequestParam Integer actuatorId, @RequestParam Integer minute) {
        CommuteTask commuteTask = new CommuteTask(deviceId, actuatorId, StatusType.ON);
        periodicTaskService.addNewPeriodicTask(name, commuteTask, LocalDateTime.of(2020, 4, 2, 0, minute, 0));
    }

    @PostMapping(value = "/task/remove")
    public void removeTask(@RequestParam String name) {
        periodicTaskService.removePeriodicTask(name);
    }
}
