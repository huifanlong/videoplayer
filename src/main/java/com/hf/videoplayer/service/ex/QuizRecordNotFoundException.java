package com.hf.videoplayer.service.ex;

public class QuizRecordNotFoundException extends ServiceException{
    public QuizRecordNotFoundException() {
    }

    public QuizRecordNotFoundException(String message) {
        super(message);
    }

    public QuizRecordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuizRecordNotFoundException(Throwable cause) {
        super(cause);
    }

    public QuizRecordNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
