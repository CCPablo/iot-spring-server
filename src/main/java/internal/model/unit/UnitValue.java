package internal.model.unit;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "unitValues")
public class UnitValue {

    private Integer nodeId;

    private Integer unitId;

    private Long value;

    @Indexed
    private Long timestamp;

}
