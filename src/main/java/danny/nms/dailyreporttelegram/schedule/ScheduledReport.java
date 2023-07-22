package danny.nms.dailyreporttelegram.schedule;

import danny.nms.dailyreporttelegram.domain.dto.MessageInput;
import danny.nms.dailyreporttelegram.domain.entity.Task;
import danny.nms.dailyreporttelegram.repository.TaskRepository;
import danny.nms.dailyreporttelegram.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ScheduledReport {
    @Autowired
    private MessageService messageService;
    private final TaskRepository taskRepository;

//    @Scheduled(fixedRate = 2000)
    @Scheduled(cron = "0 00 18 * * ?")
    public void scheduleReport() {
        String dayOfWeek = checkDateToSend();
        if (dayOfWeek.equalsIgnoreCase("Monday") || dayOfWeek.equalsIgnoreCase("Tuesday") ||
                dayOfWeek.equalsIgnoreCase("Wednesday") || dayOfWeek.equalsIgnoreCase("Thursday") ||
                dayOfWeek.equalsIgnoreCase("Saturday")) {
            List<Task> taskList = taskRepository.findAll();
            for (Task task : taskList) {
                if (!task.getStatus().booleanValue()) {
                    MessageInput messageInput = new MessageInput("6175557658",task.getContent());
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
        String dayOfWeek = simpleDateFormat.format(date);
        return dayOfWeek;
    }
}
