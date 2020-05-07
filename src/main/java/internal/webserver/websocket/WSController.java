package internal.webserver.websocket;

/*
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class WSController {

    private final SimpMessagingTemplate template;

    public WSController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/data")
    public void onReceivedMessage(String message) throws Exception {
        template.convertAndSend("/data", message);
    }


    @MessageExceptionHandler
    public String handleException(Throwable exception) {
        template.convertAndSend("/errors", exception.getMessage());
        return exception.getMessage();
    }
}

 */