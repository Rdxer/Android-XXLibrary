package com.rdxer.xxlibrary.HTTPUtils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rdxer.xxlibrary.HTTPUtils.listener.ErrorListener;
import com.rdxer.xxlibrary.HTTPUtils.listener.FailedListener;
import com.rdxer.xxlibrary.HTTPUtils.listener.OKListener;
import com.rdxer.xxlibrary.bean.BaseModel;
import com.rdxer.xxlibrary.proxy.BaseModelProxy;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LXF on 16/6/3.
 */

public class ModelListRequest<T> extends XXRequest<List<T>> {


    public ModelListRequest(URLInfo url, JSONObject requestBody, OKListener<List<T>> okListener, FailedListener failedListener, ErrorListener errorListener) {
        super(url, requestBody, okListener, failedListener, errorListener);
    }

    @Override
    protected List<T> parseResponseToTarget(JSONObject response) throws Exception {
        JSONArray res = (JSONArray) getTargetData(response);

        List<T> list = new ArrayList<T>(res.size());
        boolean isProxyClass = BaseModelProxy.class.isAssignableFrom(getTClass());

        for (int i = 0 ; i < res.size() ; i++){
            JSONObject r = res.getJSONObject(i);
            T t;
            if (isProxyClass){
                t = getTClass().newInstance();
                BaseModelProxy modelProxy = (BaseModelProxy) t;
                BaseModel model = (BaseModel) r.toJavaObject(modelProxy.getModelClass());
                modelProxy.setModel(model);
            }
            else{
                t = r.toJavaObject(getTClass());
            }
            list.add(t);
        }
        return list;
    }

    public Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
