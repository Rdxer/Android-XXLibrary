package com.rdxer.xxlibrary.HTTPUtils.listener;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by LXF on 16/6/8.
 */

public interface OKListener<T> {
    void onOK(T res, JSONObject response);
}