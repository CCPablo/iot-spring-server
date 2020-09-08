package internal.scheduler.task.condition;

public class True implements ICondition {
    @Override
    public boolean test() {
        return true;
    }
}
