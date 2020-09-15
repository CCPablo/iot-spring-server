package internal.scheduler.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.List;

@Data
public class TTask {

    private String id;

    private String taskName;

    private Integer unitId;

    private Integer nodeId;

    private List<Action> actionList;

    private List<Condition> conditionList;

    private List<Trigger> triggerList;

    private LocalDateTime targetTime;

    private TemporalAmount increment;
}
