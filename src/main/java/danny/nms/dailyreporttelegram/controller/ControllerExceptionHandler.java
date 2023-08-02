package danny.nms.dailyreporttelegram.controller;

import danny.nms.dailyreporttelegram.exception.MessageNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler extends BaseController{
    @ExceptionHandler({ MessageNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleMessageNotFoundException(MessageNotFoundException ex) {
        return failedResponse(HttpStatus.NOT_FOUND.value() + "",
                String.format("%s: %s",ex.getClass().getSimpleName(), ex.getMessage()));
    }
}
