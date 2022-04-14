package com.hf.videoplayer.service.ex;

public class VideoNotFoundException extends ServiceException {
    public VideoNotFoundException() {
        super();
    }

    public VideoNotFoundException(String message) {
        super(message);
    }

    public VideoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public VideoNotFoundException(Throwable cause) {
        super(cause);
    }

    protected VideoNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
