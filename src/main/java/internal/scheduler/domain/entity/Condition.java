package internal.scheduler.domain.entity;

import internal.scheduler.task.condition.ConditionType;
import lombok.Data;

import java.util.List;

@Data
public class Condition {

    private ConditionType type;

    private Long delayInMillis;

    private Long lastCheck;

    private List<Boolean> daysEnabled;
    //private ArrayList<Boolean> daysEnabled;
}
