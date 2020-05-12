package internal.scheduler.data;

import internal.scheduler.condition.ICondition;
import internal.scheduler.action.IAction;
import internal.scheduler.task.PeriodicTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PeriodicTasks {

    private final Map<String, PeriodicTask> activePeriodicTasks = new HashMap<>();

    public void addNewPeriodicTask(String taskName, IAction iAction, LocalDateTime targetTime) {
        if (!activePeriodicTasks.containsKey(taskName)) {
            activePeriodicTasks.put(taskName, new PeriodicTask(List.of(iAction), targetTime));
        }
    }

    public void addNewPeriodicTask(String taskName, IAction iAction, ICondition iCondition, LocalDateTime targetTime) {
        if (!activePeriodicTasks.containsKey(taskName)) {
            activePeriodicTasks.put(taskName, new PeriodicTask(List.of(iAction), List.of(iCondition), targetTime));
        }
    }

    public void removePeriodicTask(String taskName) {
        if (activePeriodicTasks.containsKey(taskName)) {
            PeriodicTask periodicTask = activePeriodicTasks.get(taskName);
            periodicTask.stop();
            activePeriodicTasks.remove(taskName, periodicTask);
        }
    }
}
