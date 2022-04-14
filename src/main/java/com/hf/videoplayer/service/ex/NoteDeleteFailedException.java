package com.hf.videoplayer.service.ex;

public class NoteDeleteFailedException extends ServiceException{
    public NoteDeleteFailedException() {
        super();
    }

    public NoteDeleteFailedException(String message) {
        super(message);
    }

    public NoteDeleteFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoteDeleteFailedException(Throwable cause) {
        super(cause);
    }

    protected NoteDeleteFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
