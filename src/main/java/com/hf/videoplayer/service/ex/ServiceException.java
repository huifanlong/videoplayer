package com.hf.videoplayer.service.ex;

/*
业务层基异常 throw new ServiceException()
 */
public class ServiceException extends RuntimeException {
    /*
    Alt+insert键可以选择需要override的方法，选择前五个
     */
    public ServiceException() {
        super();
    }
//常用
    public ServiceException(String message) {
        super(message);
    }
//常用
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
