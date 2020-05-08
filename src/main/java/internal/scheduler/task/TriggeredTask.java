package internal.scheduler.task;

import internal.scheduler.condition.ICondition;
import internal.scheduler.action.IAction;
import internal.scheduler.trigger.ATrigger;

import java.util.List;

public class TriggeredTask {

    private final List<ATrigger> aTriggers;

    private final List<ICondition> iConditions;

    private final List<IAction> IActions;

    public TriggeredTask(List<ATrigger> aTriggers, List<ICondition> iConditions, List<IAction> IActions) {
        this.aTriggers = aTriggers;
        this.iConditions = iConditions;
        this.IActions = IActions;
    }

    public boolean run() {
        if (needToRun()) {
            IActions.forEach(IAction::run);
            return true;
        }
        return false;
    }

    private boolean needToRun() {
        return aTriggers.stream().anyMatch(ATrigger::test) && iConditions.stream().allMatch(ICondition::test);
    }
}
