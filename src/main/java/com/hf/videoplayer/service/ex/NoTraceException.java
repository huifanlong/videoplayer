package com.hf.videoplayer.service.ex;

public class NoTraceException extends ServiceException{
    public NoTraceException() {
    }

    public NoTraceException(String message) {
        super(message);
    }

    public NoTraceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoTraceException(Throwable cause) {
        super(cause);
    }

    public NoTraceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
