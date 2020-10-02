package internal.webserver.see;

import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/sse")
@Api(value = "Values REST Endpoint")
public class ValuesSEEController {

    private final SEEEmitters emitters;

    public ValuesSEEController(SEEEmitters emitters){
        this.emitters = emitters;
    }

    @GetMapping(path="/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream()  throws IOException {
        SseEmitter emitter = new SseEmitter();
        emitters.add(emitter);
        return emitter;
    }

}
