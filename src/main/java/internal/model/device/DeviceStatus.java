package internal.model.device;

import internal.model.StatusType;
import lombok.Data;

@Data
public class DeviceStatus {

    private Integer id;

    private StatusType status;

}
