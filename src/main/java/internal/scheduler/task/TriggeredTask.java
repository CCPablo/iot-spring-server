package internal.scheduler.task;

import internal.scheduler.condition.ICondition;
import internal.scheduler.action.IAction;
import internal.scheduler.trigger.ATrigger;

import java.util.List;
import java.util.UUID;

public class TriggeredTask {

    private final List<ATrigger> aTriggers;

    private final List<ICondition> iConditions;

    private final String id;

    private final List<IAction> IActions;

    private final boolean deleteOnRun;

    public TriggeredTask(List<ATrigger> aTriggers, List<ICondition> iConditions, List<IAction> IActions, boolean deleteOnRun) {
        this.aTriggers = aTriggers;
        this.iConditions = iConditions;
        this.IActions = IActions;
        this.deleteOnRun = deleteOnRun;
        this.id = UUID.randomUUID().toString();
    }

    public boolean runAndDelete() {
        if (needToRun()) {
            IActions.forEach(IAction::run);
            return deleteOnRun;
        }
        return false;
    }

    private boolean needToRun() {
        return aTriggers.stream().anyMatch(ATrigger::test) && iConditions.stream().allMatch(ICondition::test);
    }
}
