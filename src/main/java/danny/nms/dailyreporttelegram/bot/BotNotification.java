package danny.nms.dailyreporttelegram.bot;

import danny.nms.dailyreporttelegram.domain.entity.IdChat;
import danny.nms.dailyreporttelegram.repository.IdChatRepository;
import danny.nms.dailyreporttelegram.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@Configuration
@Component
@RequiredArgsConstructor
public class BotNotification extends TelegramLongPollingBot {
    private final IdChatRepository idChatRepository;
    @Value("${bot.name}")
    private String BOT_NAME;
    @Value("${bot.token}")
    private String BOT_TOKEN;
    @Autowired
    MessageService messageService;
    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (update.hasMessage()) {
                Message message = update.getMessage();
                String messageText = message.getText();
                Long chatId = message.getChatId();
                String chatName;
                Chat chat = message.getChat();
                IdChat idChat = new IdChat();
                if (chat.isUserChat()) {
                    idChat.setType(true);
                    String firstName = chat.getFirstName();
                    String lastName = chat.getLastName();
                    chatName = firstName + " " + lastName;
                    System.out.println("User Chat");
                } else if (chat.isGroupChat()) {
                    chatName = chat.getTitle();
                    System.out.println("Group Chat");
                    idChat.setType(false);
                } else {
                    chatName = "Unknown";
                    System.out.println("Unknown Chat");
                    idChat.setType(null);
                }

                idChat.setName(chatName);
                idChat.setChatId(String.valueOf(chatId));
                IdChat idChat1 = idChatRepository.findByChatId(String.valueOf(chatId));
                if (idChat1 == null) {
                    idChatRepository.save(idChat);
                }
                System.out.println("Received message: " + messageText);
                System.out.println("Chat ID: " + chatId);
                System.out.println("Chat Name: " + chatName);

            }
            Message message = update.getMessage();
            String command = message.getText();
            String chatId = message.getChatId().toString();
            String response;
            if ("/start".equals(command)) {
                response = "Từ giờ xin phép spam thông báo :3";
            } else if ("/wtf".equals(command)) {
                response = "Con lợn này :/";
            } else if ("/who".equals(command)) {
                response = "From Đạt đẹp trai";
            } else {
                response = "Nhập một trong 3 options start - wtf - who thôi tại mới test 3 cái cmd đấy";
            }
            sendMessage(chatId, response);
        }
    }
    private void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
