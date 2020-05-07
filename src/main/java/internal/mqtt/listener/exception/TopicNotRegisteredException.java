package internal.mqtt.listener.exception;

public class TopicNotRegisteredException extends UnsupportedOperationException {

    public TopicNotRegisteredException() {
        super();
    }

    public TopicNotRegisteredException(String message) {
        super(message);
    }

    public TopicNotRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }
}
