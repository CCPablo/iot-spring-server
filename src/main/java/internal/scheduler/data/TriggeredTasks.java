package internal.scheduler.data;

import internal.scheduler.action.IAction;
import internal.scheduler.condition.ICondition;
import internal.scheduler.task.TriggeredTask;
import internal.scheduler.trigger.ATrigger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TriggeredTasks {

    private final Map<String, TriggeredTask> activeTriggeredTasks = new HashMap<>();

    public void addNewTriggeredTask(String taskName, List<ATrigger> aTriggers, List<ICondition> iConditions, List<IAction> iActions) {
        activeTriggeredTasks.putIfAbsent(taskName, new TriggeredTask(aTriggers, iConditions, iActions, true));
    }

    public void checkTriggeredTasks() {
        activeTriggeredTasks.forEach((key, value) -> {
            if(value.runAndDelete()) {
                activeTriggeredTasks.remove(key);
            }
        });
    }
}
