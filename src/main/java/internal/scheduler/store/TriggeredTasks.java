package internal.scheduler.store;

import internal.scheduler.task.condition.ICondition;
import internal.scheduler.task.TriggeredTask;
import internal.scheduler.task.trigger.ATrigger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TriggeredTasks {

    private final Map<String, TriggeredTask> activeTriggeredTasks = new HashMap<>();

    public void addNewTriggeredTask(String taskName, List<ATrigger> aTriggers, List<ICondition> iConditions, List<Runnable> iActions) {
        activeTriggeredTasks.putIfAbsent(taskName, new TriggeredTask(iActions, iConditions, aTriggers, true));
    }

    public void checkTriggeredTasks() {
        activeTriggeredTasks.forEach((key, value) -> {
            if(value.runAndDelete()) {
                activeTriggeredTasks.remove(key);
            }
        });
    }
}
