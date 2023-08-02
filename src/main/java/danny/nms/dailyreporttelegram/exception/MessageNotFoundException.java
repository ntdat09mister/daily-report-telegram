package danny.nms.dailyreporttelegram.exception;

public class MessageNotFoundException extends RuntimeException{
    public MessageNotFoundException() {
    }

    public MessageNotFoundException(String message) {
        super(message);
    }
}
