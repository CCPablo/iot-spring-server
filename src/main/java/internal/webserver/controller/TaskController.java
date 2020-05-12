package internal.webserver.controller;

import internal.model.StatusType;
import internal.scheduler.action.CommuteAction;
import internal.scheduler.data.PeriodicTasks;
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
    private final PeriodicTasks periodicTasks;

    @PostMapping(value = "/task/create")
    public void addNewTask(@RequestParam String name, @RequestParam Integer nodeId, @RequestParam Integer actuatorId, @RequestParam Integer minute) {
        CommuteAction commuteAction = new CommuteAction(nodeId, actuatorId, StatusType.ON);
        periodicTasks.addNewPeriodicTask(name, commuteAction, LocalDateTime.of(2020, 4, 2, 0, minute, 0));
    }

    @PostMapping(value = "/task/remove")
    public void removeTask(@RequestParam String name) {
        periodicTasks.removePeriodicTask(name);
    }
}
