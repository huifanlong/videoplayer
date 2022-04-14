package com.hf.videoplayer.service.ex;

public class CollectionNotFoundException extends ServiceException{
    public CollectionNotFoundException() {
        super();
    }

    public CollectionNotFoundException(String message) {
        super(message);
    }

    public CollectionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CollectionNotFoundException(Throwable cause) {
        super(cause);
    }

    protected CollectionNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
