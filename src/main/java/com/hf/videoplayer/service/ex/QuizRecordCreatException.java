package com.hf.videoplayer.service.ex;

public class QuizRecordCreatException extends ServiceException{
    public QuizRecordCreatException() {
    }

    public QuizRecordCreatException(String message) {
        super(message);
    }

    public QuizRecordCreatException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuizRecordCreatException(Throwable cause) {
        super(cause);
    }

    public QuizRecordCreatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
