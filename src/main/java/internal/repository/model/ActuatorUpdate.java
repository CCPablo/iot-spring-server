package internal.repository.model;

import internal.model.StatusType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("actuatorUpdates")
public class ActuatorUpdate {

    private Integer deviceId;

    private Integer actuatorId;

    private StatusType actuatorStatus;

    private Long value;

    private Long timeStamp;
}
