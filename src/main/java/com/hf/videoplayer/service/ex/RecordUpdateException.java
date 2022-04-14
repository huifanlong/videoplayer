package com.hf.videoplayer.service.ex;

public class RecordUpdateException extends ServiceException{
    public RecordUpdateException() {
        super();
    }

    public RecordUpdateException(String message) {
        super(message);
    }

    public RecordUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecordUpdateException(Throwable cause) {
        super(cause);
    }

    protected RecordUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
