package com.cy.springboot.util;

import java.io.Serializable;

/**
 * Json格式数据进行响应
 */
public class JsonResult<T> implements Serializable {
    /**状态码*/
    private Integer state;
    /**描述信息*/
    private String message;
    /**数据*/
    private T data;

    public JsonResult() {
    }


    public JsonResult(Integer state) {
        this.state = state;
    }

    public JsonResult(Throwable e) {
        this.message = e.getMessage();
    }

    public JsonResult(Integer state, T data) {
        this.state = state;
        this.data = data;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
