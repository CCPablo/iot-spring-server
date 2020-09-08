package internal.scheduler.domain.entity;

import lombok.Data;

import java.util.List;

@Data
public class PTask {

    private String taskName;

    private Integer unitId;

    private Integer nodeId;

    private List<Action> actionList;

    private List<Condition> conditionList;

    private Long targetTime;

    private Long increment;
}
