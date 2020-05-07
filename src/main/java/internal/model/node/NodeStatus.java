package internal.model.node;

import internal.model.StatusType;
import lombok.Data;

@Data
public class NodeStatus {

    private Integer id;

    private StatusType status;

}
