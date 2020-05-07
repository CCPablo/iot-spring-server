package internal.scheduler.periodic;

import internal.scheduler.condition.ICondition;
import internal.scheduler.task.ITask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class PeriodicTaskService {

    private Map<String, PeriodicTask> activePeriodicTasks = new HashMap<>();

    public void addNewPeriodicTask(String taskName, ITask iTask, LocalDateTime targetTime) {
        if (!activePeriodicTasks.containsKey(taskName)) {
            activePeriodicTasks.put(taskName, new PeriodicTask(List.of(iTask), targetTime));
        }
    }

    public void addNewPeriodicTask(String taskName, ITask iTask, ICondition iCondition, LocalDateTime targetTime) {
        if (!activePeriodicTasks.containsKey(taskName)) {
            activePeriodicTasks.put(taskName, new PeriodicTask(List.of(iTask), List.of(iCondition), targetTime));
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
