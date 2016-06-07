package com.firstcare.dylibrary.HTTPUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by LXF on 16/6/3.
 */

public class ProcessingError extends Exception{
    private JSONObject response;
    private int errcode;
    private String message;

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public void setMessage(String message) {
        this.message = message;

    }

    public ProcessingError(JSONObject response) {
        this("请求成功...然而处理数据时...发生错误...",response);
    }

    public ProcessingError(String detailMessage, JSONObject response) {
        super(detailMessage);
        this.response = response;
    }

    public JSONObject getResponse() {
        return response;
    }

    public int getErrcode() {
        return errcode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
