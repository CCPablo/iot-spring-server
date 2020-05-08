package internal.scheduler.task;

import internal.scheduler.condition.ICondition;
import internal.scheduler.action.IAction;
import internal.scheduler.trigger.ITrigger;

import java.util.List;

public class TriggeredTask {

    private final List<ITrigger> iTriggers;

    private final List<ICondition> iConditions;

    private final List<IAction> IActions;

    public TriggeredTask(List<ITrigger> iTriggers, List<ICondition> iConditions, List<IAction> IActions) {
        this.iTriggers = iTriggers;
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
        return iTriggers.stream().anyMatch(ITrigger::test) && iConditions.stream().allMatch(ICondition::test);
    }
}
