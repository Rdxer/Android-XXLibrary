package com.rdxer.xxlibrary.HTTPUtils;

import com.alibaba.fastjson.JSONObject;
import com.rdxer.xxlibrary.HTTPUtils.listener.ErrorListener;
import com.rdxer.xxlibrary.HTTPUtils.listener.FailedListener;
import com.rdxer.xxlibrary.HTTPUtils.listener.OKListener;
import com.rdxer.xxlibrary.bean.BaseModel;

import java.lang.reflect.ParameterizedType;

/**
 * Created by LXF on 16/6/3.
 */

public class ModelRequest<T extends BaseModel> extends XXRequest<T> {


    public ModelRequest(URLInfo url, JSONObject requestBody, OKListener<T> okListener, FailedListener failedListener, ErrorListener errorListener) {
        super(url, requestBody, okListener, failedListener, errorListener);
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
