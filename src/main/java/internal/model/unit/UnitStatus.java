package internal.model.unit;

import internal.model.StatusType;
import lombok.Data;

@Data
public class UnitStatus {

    private Integer nodeId;

    private Integer id;

    private StatusType status;
}
