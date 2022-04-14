package com.hf.videoplayer.service.ex;
/*
用户名被占用异常
 */
public class UsernameDuplicatedException extends ServiceException{
    /*
   Alt+insert键可以选择需要override的方法，选择前五个
    */
    public UsernameDuplicatedException() {
        super();
    }

    public UsernameDuplicatedException(String message) {
        super(message);
    }

    public UsernameDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameDuplicatedException(Throwable cause) {
        super(cause);
    }

    protected UsernameDuplicatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
