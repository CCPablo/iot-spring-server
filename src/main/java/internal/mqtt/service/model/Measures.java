package internal.mqtt.service.model;

import internal.model.unit.UnitValue;
import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
@Builder
public class Measures {

    private Integer nodeId;

    Map<Integer, Set<UnitValue>> measures;
}