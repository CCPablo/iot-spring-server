package internal.mqtt.listener.exception;

public class JsonProcessingException extends UnsupportedOperationException {

    public JsonProcessingException() {
        super();
    }

    public JsonProcessingException(String message) {
        super(message);
    }

    public JsonProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
