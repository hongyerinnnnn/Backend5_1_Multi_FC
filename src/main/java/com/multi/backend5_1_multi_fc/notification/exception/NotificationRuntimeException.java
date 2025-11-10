package com.multi.backend5_1_multi_fc.notification.exception;

public class NotificationException extends RuntimeException {
    public NotificationException(String message) {
        super(message);
    }
    public NotificationException(String message, Throwable cause) {
        super(message, cause);
    }
}

public class NotificationNotFoundException extends NotificationException{
    public NotificationNotFoundException(Long id) {
        super(String.format("Notification with id %s not found", id));
    }
}
