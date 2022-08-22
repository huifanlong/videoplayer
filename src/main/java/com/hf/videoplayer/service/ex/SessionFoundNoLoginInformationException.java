package com.hf.videoplayer.service.ex;

public class SessionFoundNoLoginInformationException extends RuntimeException{
    public SessionFoundNoLoginInformationException() {
    }

    public SessionFoundNoLoginInformationException(String message) {
        super(message);
    }

    public SessionFoundNoLoginInformationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionFoundNoLoginInformationException(Throwable cause) {
        super(cause);
    }

    public SessionFoundNoLoginInformationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
