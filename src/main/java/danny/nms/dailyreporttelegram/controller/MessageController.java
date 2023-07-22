package danny.nms.dailyreporttelegram.controller;

import danny.nms.dailyreporttelegram.domain.dto.MessageInput;
import danny.nms.dailyreporttelegram.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("message")
public class MessageController {
    private final MessageService messageService;
    @PostMapping("sendMessage")
    public void sendMessage(@RequestBody MessageInput messageInput) {
        messageService.sendMessage(messageInput);
    }
}
