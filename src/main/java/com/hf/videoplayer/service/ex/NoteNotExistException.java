package com.hf.videoplayer.service.ex;

public class NoteNotExistException extends ServiceException{
    public NoteNotExistException() {
        super();
    }

    public NoteNotExistException(String message) {
        super(message);
    }

    public NoteNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoteNotExistException(Throwable cause) {
        super(cause);
    }

    protected NoteNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
