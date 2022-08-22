package com.hf.videoplayer.service.ex;

public class QuestionsNotFoundException extends ServiceException{
    public QuestionsNotFoundException() {
    }

    public QuestionsNotFoundException(String message) {
        super(message);
    }

    public QuestionsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuestionsNotFoundException(Throwable cause) {
        super(cause);
    }

    public QuestionsNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
