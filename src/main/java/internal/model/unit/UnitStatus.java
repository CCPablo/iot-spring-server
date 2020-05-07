package internal.model.unit;

import internal.model.StatusType;
import lombok.Data;

@Data
public class UnitStatus {

    private Integer deviceId;

    private Integer id;

    private StatusType status;
}
