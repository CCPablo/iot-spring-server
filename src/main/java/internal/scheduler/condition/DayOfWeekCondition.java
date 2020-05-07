package internal.scheduler.condition;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class DayOfWeekCondition implements ICondition{

    ArrayList<Boolean> daysEnabled;

    public DayOfWeekCondition(ArrayList<Boolean> days) {
        this.daysEnabled = days;
    }

    @Override
    public boolean test() {
        DayOfWeek currentDay = DayOfWeek.from(LocalDate.now());
        return daysEnabled.get(currentDay.getValue()-1);
    }
}
