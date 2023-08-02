package danny.nms.dailyreporttelegram.service;

import danny.nms.dailyreporttelegram.domain.dto.MessageInput;
import danny.nms.dailyreporttelegram.domain.entity.Message;
import danny.nms.dailyreporttelegram.exception.MessageNotFoundException;
import danny.nms.dailyreporttelegram.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {
    @Value("${bot.token}")
    private String TOKEN;
    private final MessageRepository messageRepository;
    public void sendMessage(MessageInput messageInput) {
        String receiveId = messageInput.getRecipientId();
        if (Objects.nonNull(receiveId) && receiveId.trim().length() > 0) {
            sendSingleMessage(messageInput);
            Message message = new Message();
            message.setMessage(messageInput.getContent());
            message.setSendTo(messageInput.getRecipientId());
            message.setSendDate(new Date());
            messageRepository.save(message);
        }
    }
    public void sendSingleMessage(MessageInput messageInput) {
        send(messageInput);
    }
    public void send(MessageInput messageInput) {
        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
        UriBuilder builder = UriBuilder.fromUri("https://api.telegram.org")
                .path("/{token}/sendMessage")
                .queryParam("chat_id", messageInput.getRecipientId())
                .queryParam("text", messageInput.getContent());
        HttpRequest request = HttpRequest
                .newBuilder()
                .GET().uri(builder.build("bot" + TOKEN)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }

    public Message findMessageById(Long id) {
        Optional<Message> messageOptional = messageRepository.findById(id);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            return message;
        } else {
         throw new MessageNotFoundException("Not found message");
        }
    }
}
