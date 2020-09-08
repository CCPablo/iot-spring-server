package internal.scheduler.task.condition;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class DayOfWeekCondition implements ICondition{

    List<Boolean> daysEnabled;

    public DayOfWeekCondition(List<Boolean> days) {
        this.daysEnabled = days;
    }

    @Override
    public boolean test() {
        DayOfWeek currentDay = DayOfWeek.from(LocalDate.now());
        return daysEnabled.get(currentDay.getValue()-1);
    }
}
