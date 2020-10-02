package internal.scheduler.domain.entity;

import internal.scheduler.task.action.ActionType;
import lombok.Data;

@Data
public class Action {
    Integer nodeId;

    Integer actuatorId;

    ActionType actionType;

    Long value;

    Long delayMillis;
}
