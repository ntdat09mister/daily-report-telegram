package danny.nms.dailyreporttelegram.schedule;

import danny.nms.dailyreporttelegram.domain.dto.MessageInput;
import danny.nms.dailyreporttelegram.domain.entity.IdChat;
import danny.nms.dailyreporttelegram.domain.entity.Task;
import danny.nms.dailyreporttelegram.repository.IdChatRepository;
import danny.nms.dailyreporttelegram.repository.TaskRepository;
import danny.nms.dailyreporttelegram.service.MessageService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Component
@RequiredArgsConstructor
public class ScheduledReport {
    @Value("${spring.jackson.time-zone}")
    private String TIME_ZONE;
    @Autowired
    private MessageService messageService;
    private final IdChatRepository idChatRepository;
    private final TaskRepository taskRepository;

    @Scheduled(cron = "0 30 17 * * ?", zone = "${spring.jackson.time-zone}")
    @Transactional(rollbackOn = Exception.class)
    public void scheduleReport() throws NotFoundException {
        String dayOfWeek = checkDateToSend();
        if (dayOfWeek.equalsIgnoreCase("Monday") || dayOfWeek.equalsIgnoreCase("Tuesday") ||
                dayOfWeek.equalsIgnoreCase("Wednesday") || dayOfWeek.equalsIgnoreCase("Thursday") ||
                dayOfWeek.equalsIgnoreCase("Saturday")) {
            List<Task> taskList = taskRepository.findAllWithCondition();
            for (Task task : taskList) {
                if (!task.getStatus().booleanValue()) {
                    String chatId = "";
                    String contentTask = task.getContent();
                    String dayAndMonthToday = getDayAndMonth();
                    String contentTaskSend = String.format(contentTask, dayAndMonthToday);
                    Optional<IdChat> optionalIdChat = idChatRepository.findById(task.getIdChat());
                    if (optionalIdChat.isPresent()) {
                        IdChat idChat = optionalIdChat.get();
                        chatId = idChat.getChatId();
                    } else {
                        throw new NotFoundException("Not found any id chat!");
                    }
                    MessageInput messageInput = new MessageInput(chatId, contentTaskSend);
                    messageService.sendMessage(messageInput);
                    Optional<Task> optionalTask = taskRepository.findById(task.getId());
                    Task taskSent = optionalTask.get();
                    taskSent.setStatus(true);
                    taskRepository.save(taskSent);
                    break;
                }
            }
        }
    }
    private String checkDateToSend() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        String dayOfWeek = simpleDateFormat.format(date);
        return dayOfWeek;
    }
    private String getDayAndMonth() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        String dayAndMonth = simpleDateFormat.format(date);
        return dayAndMonth;
    }

    public String getTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss EEE dd/MM/yyyy");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        String formattedDate = simpleDateFormat.format(date);
        return formattedDate;
    }
}
