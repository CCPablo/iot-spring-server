package internal.webserver.rest;

import internal.scheduler.store.PeriodicTasks;
import internal.scheduler.task.action.SetValueAction;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController("/api/v1/task")
@AllArgsConstructor
@Api(value = "Tasks REST Endpoint")
public class TaskController {

    @Autowired
    private final PeriodicTasks periodicTasks;

    @PostMapping(value = "/create")
    public void addNewTask(@RequestParam String name, @RequestParam Integer nodeId, @RequestParam Integer actuatorId, @RequestParam Long value, @RequestParam Integer seconds) {
        SetValueAction setValueAction = new SetValueAction(nodeId, actuatorId, value);
        periodicTasks.addNewPeriodicTask(name, setValueAction, LocalDateTime.now().plusSeconds(seconds));
    }

    @PostMapping(value = "/remove")
    public void removeTask(@RequestParam String name) {
        periodicTasks.removePeriodicTask(name);
    }
}
