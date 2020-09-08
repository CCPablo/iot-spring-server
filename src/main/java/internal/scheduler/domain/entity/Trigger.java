package internal.scheduler.domain.entity;

import internal.scheduler.task.trigger.TriggerType;
import lombok.Data;

@Data
public class Trigger {

    private TriggerType type;

    private Integer nodeId;

    private Integer unitId;

    private Long highLimit;

    private Long lowLimit;
}
