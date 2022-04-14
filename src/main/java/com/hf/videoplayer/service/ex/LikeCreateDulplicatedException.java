package com.hf.videoplayer.service.ex;

public class LikeCreateDulplicatedException extends ServiceException{
    public LikeCreateDulplicatedException() {
        super();
    }

    public LikeCreateDulplicatedException(String message) {
        super(message);
    }

    public LikeCreateDulplicatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LikeCreateDulplicatedException(Throwable cause) {
        super(cause);
    }

    protected LikeCreateDulplicatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
