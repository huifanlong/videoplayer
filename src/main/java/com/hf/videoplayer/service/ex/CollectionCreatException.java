package com.hf.videoplayer.service.ex;

public class CollectionCreatException extends ServiceException{
    public CollectionCreatException() {
        super();
    }

    public CollectionCreatException(String message) {
        super(message);
    }

    public CollectionCreatException(String message, Throwable cause) {
        super(message, cause);
    }

    public CollectionCreatException(Throwable cause) {
        super(cause);
    }

    protected CollectionCreatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
