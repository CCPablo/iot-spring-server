package internal.webserver.see;

import internal.mqtt.mapper.JsonMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class SEEEmitters {

    private static final List<SseEmitter> emitters = Collections.synchronizedList(new ArrayList<>());

    public void add(SseEmitter emitter) throws IOException {
        emitters.add(emitter);

        emitter.onCompletion(() -> {
            emitters.remove(emitter);
        });
        emitter.onTimeout(() -> {
            emitter.complete();
            emitters.remove(emitter);
        });
    }

    static public void send(Object obj) {
        List<SseEmitter> failedEmitters = new ArrayList<>();

        synchronized (emitters) {
            emitters.forEach(emitter -> {
                try {
                    emitter.send(Objects.requireNonNull(JsonMapper.getSerializedObject(obj)));
                } catch (Exception e) {
                    emitter.completeWithError(e);
                    failedEmitters.add(emitter);
                }
            });
        }

        emitters.removeAll(failedEmitters);
    }

    @Scheduled(fixedRate = 5000L)
    void sendEvents() {
        SEEMessage message = SEEMessage.builder().integer(333).build();
        send(message);
    }
}
