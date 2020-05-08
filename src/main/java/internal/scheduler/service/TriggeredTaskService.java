package internal.scheduler.service;

import internal.scheduler.action.IAction;
import internal.scheduler.condition.ICondition;
import internal.scheduler.task.TriggeredTask;
import internal.scheduler.trigger.ITrigger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TriggeredTaskService {

    private final Map<String, TriggeredTask> activeTriggeredTasks = new HashMap<>();

    public void addNewTriggeredTask(String taskName, List<ITrigger> iTriggers, List<ICondition> iConditions, List<IAction> iActions) {
        activeTriggeredTasks.putIfAbsent(taskName, new TriggeredTask(iTriggers, iConditions, iActions));
    }

    public void checkTriggeredTasks() {
        activeTriggeredTasks.forEach((key, value) -> {
            if(value.run()) {
                activeTriggeredTasks.remove(key);
            }
        });
    }
}
