package internal.scheduler.task;

import internal.scheduler.task.condition.ICondition;
import internal.scheduler.task.trigger.ATrigger;

import java.util.List;
import java.util.UUID;

public class TriggeredTask {

    private final List<ATrigger> aTriggers;

    private final List<ICondition> iConditions;

    private final String id = UUID.randomUUID().toString();

    private final List<Runnable> IActions;

    private final boolean deleteOnRun;

    public TriggeredTask(List<Runnable> IActions, List<ICondition> iConditions, List<ATrigger> aTriggers, Boolean deleteOnRun) {
        this.aTriggers = aTriggers;
        this.iConditions = iConditions;
        this.IActions = IActions;
        this.deleteOnRun = deleteOnRun;
    }

    public boolean run() {
        if (needToRun()) {
            IActions.forEach(Runnable::run);
            return true;
        }
        return false;
    }

    public boolean runAndDelete() {
        if (needToRun()) {
            IActions.forEach(Runnable::run);
            return deleteOnRun;
        }
        return false;
    }

    private boolean needToRun() {
        return aTriggers.stream().anyMatch(ATrigger::test) && iConditions.stream().allMatch(ICondition::test);
    }
}
