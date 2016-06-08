package com.rdxer.xxlibrary.HTTPUtils.error;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by LXF on 16/6/3.
 */

public class Pre_ProcessingError extends ProcessingError {

    public Pre_ProcessingError(JSONObject response) {
        this("准备解析数据时发生异常...",response);
    }

    public Pre_ProcessingError(String detailMessage, JSONObject response) {
        super(detailMessage, response);
    }
}
