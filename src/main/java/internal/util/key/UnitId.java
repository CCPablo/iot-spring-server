package internal.util.key;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnitId {

    private Integer nodeId;

    private Integer unitId;
}
