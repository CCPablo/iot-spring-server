package internal.scheduler.service;

import internal.scheduler.condition.ICondition;
import internal.scheduler.task.ITask;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
public class PeriodicTask {

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    private ScheduledFuture scheduledFuture;

    private final ICondition iCondition;

    private final ITask iTask;

    private LocalDateTime targetTime;

    private TemporalAmount increment;

    public PeriodicTask(ITask iTask, LocalDateTime targetTime) {
        this.iTask = iTask;
        this.targetTime = targetTime;
        this.iCondition = () -> true;
        startExecution();
    }

    public PeriodicTask(ITask iTask, ICondition iCondition, LocalDateTime targetTime) {
        this.iTask = iTask;
        this.iCondition = iCondition;
        this.targetTime = targetTime;
        startExecution();
    }

    public PeriodicTask(ITask iTask, ICondition iCondition, LocalDateTime targetTime, TemporalAmount increment) {
        this.iTask = iTask;
        this.iCondition = iCondition;
        this.targetTime = targetTime;
        this.increment = increment;
        startExecution();
    }

    public void startExecution() {
        Runnable taskWrapper = () -> {
            if (iCondition.test()) {
                iTask.run();
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

    private long computeNextDelay() {
        ZonedDateTime zonedNow = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
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
