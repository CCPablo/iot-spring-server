package internal.mqtt.publisher;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GatewayConnectMsg {

    private Boolean connected;
}