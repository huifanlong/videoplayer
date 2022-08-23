package com.hf.videoplayer.service.ex;

public class CourseVideoHasNotRegisteredException extends ServiceException{
    public CourseVideoHasNotRegisteredException() {
    }

    public CourseVideoHasNotRegisteredException(String message) {
        super(message);
    }

    public CourseVideoHasNotRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseVideoHasNotRegisteredException(Throwable cause) {
        super(cause);
    }

    public CourseVideoHasNotRegisteredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
