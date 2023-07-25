package danny.nms.dailyreporttelegram.controller;

import danny.nms.dailyreporttelegram.domain.dto.MessageInput;
import danny.nms.dailyreporttelegram.schedule.ScheduledReport;
import danny.nms.dailyreporttelegram.service.MessageService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {
    private final MessageService messageService;
    @Autowired
    private ScheduledReport scheduledReport;
    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody MessageInput messageInput) {
        messageService.sendMessage(messageInput);
    }
    @GetMapping("/active")
    public void activeMessage() throws NotFoundException {
        scheduledReport.scheduleReport();
    }
    @GetMapping("/getTime")
    public String getTime() {
        return scheduledReport.getTime();
    }
}
