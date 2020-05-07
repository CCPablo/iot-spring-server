package internal.scheduler.trigger;

import lombok.Data;

@Data
public class Trigger {

    private String unitId;

    private TriggerType triggerType;
}
