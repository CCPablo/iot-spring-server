package internal.scheduler.task;

import internal.scheduler.condition.ICondition;
import internal.scheduler.action.IAction;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
public class PeriodicTask {

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    private ScheduledFuture scheduledFuture;

    private final List<ICondition> iConditions;

    private final List<IAction> IActions;

    private LocalDateTime targetTime;

    private TemporalAmount increment;

    private final String id;

    public PeriodicTask(List<IAction> IActions, LocalDateTime targetTime) {
        this.IActions = IActions;
        this.targetTime = targetTime;
        this.id = UUID.randomUUID().toString();
        this.iConditions = List.of(() -> true);
    }

    public PeriodicTask(List<IAction> IActions, List<ICondition> iConditions, LocalDateTime targetTime) {
        this.IActions = IActions;
        this.iConditions = iConditions;
        this.targetTime = targetTime;
        this.id = UUID.randomUUID().toString();
    }

    public PeriodicTask(List<IAction> IActions, List<ICondition> iConditions, LocalDateTime targetTime, TemporalAmount increment) {
        this.IActions = IActions;
        this.iConditions = iConditions;
        this.targetTime = targetTime;
        this.increment = increment;
        this.id = UUID.randomUUID().toString();
    }

    @PostConstruct
    public void startExecutionAndStore() {
        Runnable taskWrapper = () -> {
            if (iConditions.stream().allMatch(ICondition::test)) {
                IActions.forEach(IAction::run);
            }
            startExecution();
        };
        scheduledFuture = executorService.schedule(taskWrapper, computeNextDelay(), TimeUnit.SECONDS);
    }

    public void updateExecutionTime(LocalDateTime newTargetTime) {
        if (scheduledFuture.cancel(true)) {
            targetTime = newTargetTime;
            startExecution();
        }
    }

    public void stop() {
        executorService.shutdownNow();
        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
            log.info(PeriodicTask.class.getName());
        }
    }

    private void startExecution() {
        Runnable taskWrapper = () -> {
            if (iConditions.stream().allMatch(ICondition::test)) {
                IActions.forEach(IAction::run);
            }
            startExecution();
        };
        scheduledFuture = executorService.schedule(taskWrapper, computeNextDelay(), TimeUnit.SECONDS);
    }

    private long computeNextDelay() {
        final ZonedDateTime zonedNow = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
        ZonedDateTime zonedNextTarget =
                zonedNow.withHour(targetTime.getHour()).withMinute(targetTime.getMinute()).withSecond(targetTime.getSecond())
                        .truncatedTo(ChronoUnit.SECONDS);
        if (zonedNow.compareTo(zonedNextTarget) >= 0) {
            zonedNextTarget = zonedNextTarget.plus(increment);
        }
        targetTime = zonedNextTarget.toLocalDateTime();
        Duration duration = Duration.between(zonedNow, zonedNextTarget);
        log.info(String.format("Task scheduled for %s", zonedNextTarget.toString()));
        return duration.getSeconds() + 1;
    }
}
