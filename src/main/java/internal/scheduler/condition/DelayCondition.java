package internal.scheduler.condition;

import java.time.Duration;

public class DelayCondition implements ICondition {

    private Long delayInMillis;

    private Long lastCheck;

    public DelayCondition(Integer seconds) {
        this.delayInMillis = System.currentTimeMillis() + Duration.ofSeconds(seconds).toMillis();
    }

    public void reset() {
        delayInMillis = System.currentTimeMillis() + Duration.ofSeconds(22).toMillis();
    }

    @Override
    public boolean test() {
        lastCheck = System.currentTimeMillis();
        return false;
    }
}
