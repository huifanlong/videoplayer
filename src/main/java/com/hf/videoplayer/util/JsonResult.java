package com.hf.videoplayer.util;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Json格式进行响应,所有controller的方法返回类型都是JsonResult<>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class JsonResult<E> implements Serializable {
    /**状态码*/
    private Integer state;

    /**提示信息*/
    private String message;

    /** 数据 因为类型不确定 所以使用的是泛型*/
    private E data;

    public JsonResult() {
    }

    public JsonResult(Integer state) {
        this.state = state;
    }

    public JsonResult(Throwable e) {
        this.message = e.getMessage();
    }

    public JsonResult(Integer state, E data) {
        this.state = state;
        this.data = data;
    }

    public JsonResult(Integer state, String message) {
        this.state = state;
        this.message = message;
    }

    public JsonResult(Integer state, String message, E data) {
        this.state = state;
        this.message = message;
        this.data = data;
    }
}
