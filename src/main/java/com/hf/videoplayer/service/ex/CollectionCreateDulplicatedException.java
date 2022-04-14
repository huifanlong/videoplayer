package com.hf.videoplayer.service.ex;

public class CollectionCreateDulplicatedException extends ServiceException{
    public CollectionCreateDulplicatedException() {
        super();
    }

    public CollectionCreateDulplicatedException(String message) {
        super(message);
    }

    public CollectionCreateDulplicatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CollectionCreateDulplicatedException(Throwable cause) {
        super(cause);
    }

    protected CollectionCreateDulplicatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
