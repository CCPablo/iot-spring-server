package internal.webserver.controller;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController("/v1/api/values")
@Api(value = "Values REST Endpoint")
public class ValuesController {

    private final SEEEmitters emitters;

    public ValuesController(SEEEmitters emitters){
        this.emitters = emitters;
    }

    @GetMapping(path="/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream()  throws IOException {
        SseEmitter emitter = new SseEmitter();
        emitters.add(emitter);
        return emitter;
    }

}
