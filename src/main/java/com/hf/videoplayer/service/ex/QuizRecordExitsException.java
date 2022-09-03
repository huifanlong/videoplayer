package com.hf.videoplayer.service.ex;

public class QuizRecordExitsException extends ServiceException{
    public QuizRecordExitsException() {
    }

    public QuizRecordExitsException(String message) {
        super(message);
    }

    public QuizRecordExitsException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuizRecordExitsException(Throwable cause) {
        super(cause);
    }

    public QuizRecordExitsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
