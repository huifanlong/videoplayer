package com.hf.videoplayer.service.ex;

public class VideoWatchingTimeRecordNotFoundException extends ServiceException{
    public VideoWatchingTimeRecordNotFoundException() {
    }

    public VideoWatchingTimeRecordNotFoundException(String message) {
        super(message);
    }

    public VideoWatchingTimeRecordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public VideoWatchingTimeRecordNotFoundException(Throwable cause) {
        super(cause);
    }

    public VideoWatchingTimeRecordNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
