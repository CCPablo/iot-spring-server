package internal.scheduler.domain;

import internal.scheduler.domain.entity.*;
import internal.scheduler.task.PeriodicTask;
import internal.scheduler.task.TriggeredTask;
import internal.scheduler.task.action.ActionType;
import internal.scheduler.task.action.SetValueAction;
import internal.scheduler.task.condition.ConditionType;
import internal.scheduler.task.condition.DayOfWeekCondition;
import internal.scheduler.task.condition.ICondition;
import internal.scheduler.task.trigger.ATrigger;
import internal.scheduler.task.trigger.SensorTrigger;
import internal.scheduler.task.trigger.TriggerType;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.List;

public class TaskMapper {

    public static List<Runnable> getActions (List<Action> actions) {
        List<Runnable> javaActions = new ArrayList<>();
        actions.forEach( action -> {
            if (action.getActionType().equals(ActionType.SET_VALUE)) {
                javaActions.add(new SetValueAction(action.getNodeId(), action.getActuatorId(), action.getValue()));
            }
        });
        return javaActions;
    }

    public static List<ICondition> getConditions (List<Condition> actions) {
        List<ICondition> javaConditions = new ArrayList<>();
        actions.forEach( condition -> {
            if(condition.getType().equals(ConditionType.DAY_OF_WEEK)) {
                javaConditions.add(new DayOfWeekCondition(condition.getDaysEnabled()));
            }
        });
        return javaConditions;
    }

    public static List<ATrigger> getTriggers (List<Trigger> triggers) {
        List<ATrigger> javaTriggers = new ArrayList<>();
        triggers.forEach( trigger -> {
            if(trigger.getType().equals(TriggerType.SENSOR)) {
                javaTriggers.add(new SensorTrigger(trigger.getNodeId(), trigger.getUnitId(), trigger.getHighLimit(), trigger.getLowLimit()));
            }
        });
        return javaTriggers;
    }

    public static PeriodicTask getTask (PTask periodicTask) {
        return new PeriodicTask(
                getActions(periodicTask.getActionList()),
                getConditions(periodicTask.getConditionList()),
                LocalDateTime.ofInstant(Instant.ofEpochSecond(periodicTask.getTargetTime()), ZoneId.systemDefault()),
                Duration.ofSeconds(periodicTask.getIncrement()));
    }


    public static TriggeredTask getTask (TTask triggeredTask) {
        return new TriggeredTask(
                getActions(triggeredTask.getActionList()),
                getConditions(triggeredTask.getConditionList()),
                getTriggers(triggeredTask.getTriggerList()),
                false);
    }
}
