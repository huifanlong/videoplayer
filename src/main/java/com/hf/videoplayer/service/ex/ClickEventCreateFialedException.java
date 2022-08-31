package com.hf.videoplayer.service.ex;

public class ClickEventCreateFialedException extends ServiceException{
    public ClickEventCreateFialedException() {
    }

    public ClickEventCreateFialedException(String message) {
        super(message);
    }

    public ClickEventCreateFialedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClickEventCreateFialedException(Throwable cause) {
        super(cause);
    }

    public ClickEventCreateFialedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
