package com.firstcare.dylibrary.HTTPUtils;

import com.alibaba.fastjson.JSONObject;
import com.firstcare.dylibrary.bean.BaseModel;

import java.lang.reflect.ParameterizedType;

/**
 * Created by LXF on 16/6/3.
 */

public class ModelRequest<T extends BaseModel> extends DYSSRequest<T>{

    public ModelRequest(int method, String url, JSONObject requestBody, OKListener<T> okListener, FailedListener failedListener, ErrorListener errorListener) {
        super(method, url, requestBody, okListener, failedListener, errorListener);
    }

    @Override
    protected T parseResponseToTarget(JSONObject response) throws Exception {
        JSONObject jsonObject = (JSONObject) getTargetData(response);
        return jsonObject.toJavaObject(getTClass());
    }

    public Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
