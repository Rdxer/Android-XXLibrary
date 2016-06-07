package com.firstcare.dylibrary.HTTPUtils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by LXF on 16/6/3.
 */

public class ModelListRequest<T> extends DYSSRequest<List<T>> {

    public ModelListRequest(int method, String url, JSONObject requestBody, OKListener<List<T>> okListener, FailedListener failedListener, ErrorListener errorListener) {
        super(method, url, requestBody, okListener, failedListener, errorListener);
    }
    @Override
    protected List<T> parseResponseToTarget(JSONObject response) throws Exception {
        JSONArray res = (JSONArray) getTargetData(response);
        return (List<T>)res.toJavaObject(getTClass());
    }

    public Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
