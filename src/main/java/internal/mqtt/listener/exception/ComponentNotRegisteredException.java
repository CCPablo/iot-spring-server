package internal.mqtt.listener.exception;

public class ComponentNotRegisteredException extends UnsupportedOperationException {

    public ComponentNotRegisteredException() {
        super();
    }

    public ComponentNotRegisteredException(String message) {
        super(message);
    }

    public ComponentNotRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }
}
