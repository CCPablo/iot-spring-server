package internal.repository.model;

import java.time.LocalDateTime;
import java.util.List;

public class Task {

    private String id;

    private List<Trigger> triggers;

    private List<Condition> conditions;

    private LocalDateTime targetTime;

    private Boolean deleteOnRun;
}
