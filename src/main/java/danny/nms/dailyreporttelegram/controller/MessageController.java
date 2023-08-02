package danny.nms.dailyreporttelegram.controller;

import danny.nms.dailyreporttelegram.domain.dto.MessageInput;
import danny.nms.dailyreporttelegram.domain.entity.Message;
import danny.nms.dailyreporttelegram.payload.CustomResponse;
import danny.nms.dailyreporttelegram.schedule.ScheduledReport;
import danny.nms.dailyreporttelegram.service.MessageService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController extends BaseController{
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
    @GetMapping("/{id}")
    public ResponseEntity<?> findMessageById(@PathVariable Long id) {
        return successResponse(messageService.findMessageById(id));
//        return ResponseEntity.ok(new CustomResponse<>(HttpStatus.OK.value(), messageService.findMessageById(id)));
    }
    @GetMapping("/getTime")
    public String getTime() {
        return scheduledReport.getTime();
    }
}
