package internal.mqtt.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Slf4j
public class JsonMapper {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @PostConstruct
    void init() {
        /*
        JSON_MAPPER.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
            @Override
            public JsonPOJOBuilder.Value findPOJOBuilderConfig(AnnotatedClass ac) {
                if (ac.hasAnnotation(JsonPOJOBuilder.class)) {
                    return super.findPOJOBuilderConfig(ac);
                }
                return new JsonPOJOBuilder.Value("build", "");
            }
        });
         */

        JSON_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T getDeserializedMessage(MqttMessage message, Class<T> clazz) {
        try {
            return JSON_MAPPER.readValue(message.getPayload(), clazz);
        } catch (IOException ex) {
            log.error("Error deserializing  " + clazz.getName() + ". Exception: " + ex.toString());
            return null;
        }
    }

    public static <T> T getDeserializedHttpRequest(String message, Class<T> clazz) {
        try {
            return JSON_MAPPER.readValue(message, clazz);
        } catch (IOException ex) {
            log.error("Error deserializing  " + clazz.getName() + ". Exception: " + ex.toString());
            return null;
        }
    }

    public static <T> String getSerializedObject(T t) {
        try {
            return JSON_MAPPER.writeValueAsString(t);
        } catch (IOException ex) {
            log.error("Error serializing " + ex.toString());
            return null;
        }
    }


}
