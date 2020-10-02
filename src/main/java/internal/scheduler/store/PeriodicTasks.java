package internal.scheduler.store;

import internal.scheduler.domain.TaskMapper;
import internal.scheduler.domain.entity.Action;
import internal.scheduler.domain.entity.Condition;
import internal.scheduler.domain.entity.PTask;
import internal.scheduler.task.PeriodicTask;
import internal.scheduler.task.condition.ICondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PeriodicTasks {

    private final Map<String, PeriodicTask> periodicTasks = new HashMap<>();

    public void addNewPeriodicTask(String taskName, Runnable iAction, LocalDateTime targetTime) {
        if (!periodicTasks.containsKey(taskName)) {
            periodicTasks.put(taskName, new PeriodicTask(List.of(iAction), targetTime));
        }
    }

    public void addNewPeriodicTask(String taskName, PTask task) {
        PeriodicTask addedTask = periodicTasks.put(
                taskName,
                TaskMapper.getTask(task));
    }

    public void addNewPeriodicTask(String taskName, List<Action> actions, List<Condition> conditions, LocalDateTime targetTime) {
        PeriodicTask addedTask = periodicTasks.put(
                taskName,
                new PeriodicTask(
                        TaskMapper.getActions(actions),
                        TaskMapper.getConditions(conditions),
                        targetTime));
    }

    public void addNewPeriodicTask(String taskName, Runnable iAction, ICondition iCondition, LocalDateTime targetTime) {
        if (!periodicTasks.containsKey(taskName)) {
            PeriodicTask addedTask = periodicTasks.put(taskName, new PeriodicTask(List.of(iAction), List.of(iCondition), targetTime));
        }
    }


    public void removePeriodicTask(String taskName) {
        if (periodicTasks.containsKey(taskName)) {
            PeriodicTask periodicTask = periodicTasks.get(taskName);
            periodicTask.stop();
            periodicTasks.remove(taskName, periodicTask);
        }
    }
}
