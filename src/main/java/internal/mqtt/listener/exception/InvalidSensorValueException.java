package internal.mqtt.listener.exception;

public class InvalidSensorValueException extends UnsupportedOperationException {

    public InvalidSensorValueException() {
        super();
    }

    public InvalidSensorValueException(String message) {
        super(message);
    }

    public InvalidSensorValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
