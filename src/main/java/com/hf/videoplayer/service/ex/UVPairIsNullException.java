package com.hf.videoplayer.service.ex;

public class UVPairIsNullException extends ServiceException{
    public UVPairIsNullException() {
    }

    public UVPairIsNullException(String message) {
        super(message);
    }

    public UVPairIsNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public UVPairIsNullException(Throwable cause) {
        super(cause);
    }

    public UVPairIsNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
