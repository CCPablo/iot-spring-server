package internal.scheduler.task;

import internal.scheduler.task.condition.ICondition;
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

    private ScheduledFuture<?> scheduledFuture;

    private final List<ICondition> conditions;

    private final List<Runnable> actions;

    private final String id = UUID.randomUUID().toString();

    private LocalDateTime targetTime;

    private TemporalAmount increment;

    public PeriodicTask(List<Runnable> actions, LocalDateTime targetTime) {
        this.actions = actions;
        this.targetTime = targetTime;
        this.conditions = List.of(() -> true);
    }

    public PeriodicTask(List<Runnable> actions, List<ICondition> conditions, LocalDateTime targetTime) {
        this.actions = actions;
        this.conditions = conditions;
        this.targetTime = targetTime;
    }

    public PeriodicTask(List<Runnable> actions, List<ICondition> conditions, LocalDateTime targetTime, TemporalAmount increment) {
        this.actions = actions;
        this.conditions = conditions;
        this.targetTime = targetTime;
        this.increment = increment;
    }

    @PostConstruct
    public void startExecutionAndStore() {
        Runnable taskWrapper = () -> {
            if (conditions.stream().allMatch(ICondition::test)) {
                actions.forEach(Runnable::run);
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
            if (conditions.stream().allMatch(ICondition::test)) {
                actions.forEach(Runnable::run);
            }
            startExecution();
        };
        scheduledFuture = executorService.schedule(taskWrapper, computeNextDelay(), TimeUnit.SECONDS);
    }

    private long computeNextDelay() {
        final ZonedDateTime zonedNow = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
        ZonedDateTime zonedTargetTime = targetTime.atZone(ZoneId.systemDefault()).truncatedTo(ChronoUnit.SECONDS);
        long secondsToTarget = Duration.between(zonedNow, zonedTargetTime).toSeconds();
        if (secondsToTarget > 0) {
            targetTime = zonedTargetTime.toLocalDateTime();
        } else {
            targetTime = zonedTargetTime.plus(increment).toLocalDateTime();
        }

        log.info(String.format("PTask scheduled for %s", zonedTargetTime.toString()));
        return secondsToTarget + 1;
    }
}
