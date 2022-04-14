package com.hf.videoplayer.service.ex;

public class NoteCreateFailedException extends ServiceException{
    public NoteCreateFailedException() {
        super();
    }

    public NoteCreateFailedException(String message) {
        super(message);
    }

    public NoteCreateFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoteCreateFailedException(Throwable cause) {
        super(cause);
    }

    protected NoteCreateFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
