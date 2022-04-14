package com.hf.videoplayer.service.ex;

public class LikeCreateException extends ServiceException{
    public LikeCreateException() {
        super();
    }

    public LikeCreateException(String message) {
        super(message);
    }

    public LikeCreateException(String message, Throwable cause) {
        super(message, cause);
    }

    public LikeCreateException(Throwable cause) {
        super(cause);
    }

    protected LikeCreateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
