package internal.mqtt.publisher;

import lombok.Data;

@Data
public class GatewayConnectMsg {

    private Boolean connected;

    public GatewayConnectMsg(boolean connected) {
        this.connected = connected;
    }
}