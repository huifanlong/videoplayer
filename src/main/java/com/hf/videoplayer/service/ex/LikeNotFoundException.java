package com.hf.videoplayer.service.ex;

public class LikeNotFoundException extends ServiceException{
    public LikeNotFoundException() {
        super();
    }

    public LikeNotFoundException(String message) {
        super(message);
    }

    public LikeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LikeNotFoundException(Throwable cause) {
        super(cause);
    }

    protected LikeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
