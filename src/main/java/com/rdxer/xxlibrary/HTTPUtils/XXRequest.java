package com.rdxer.xxlibrary.HTTPUtils;

import com.alibaba.fastjson.JSONObject;
import com.rdxer.xxlibrary.HTTPUtils.Request.JSONObjectRequest;
import com.rdxer.xxlibrary.HTTPUtils.error.ProcessingError;
import com.rdxer.xxlibrary.HTTPUtils.listener.ErrorListener;
import com.rdxer.xxlibrary.HTTPUtils.listener.FailedListener;
import com.rdxer.xxlibrary.HTTPUtils.listener.OKListener;

/**
 * Created by LXF on 16/6/3.
 */

public class XXRequest<T> extends JSONObjectRequest<T> {

    private String key_data = "data";
    private String key_errcode = "errcode";
    private String key_errmsg = "errmsg";


    public String getKey_data() {
        return key_data;
    }

    public void setKey_data(String key_data) {
        this.key_data = key_data;
    }

    public String getKey_errcode() {
        return key_errcode;
    }

    public void setKey_errcode(String key_errcode) {
        this.key_errcode = key_errcode;
    }

    public String getKey_errmsg() {
        return key_errmsg;
    }

    public void setKey_errmsg(String key_errmsg) {
        this.key_errmsg = key_errmsg;
    }

    public XXRequest(URLInfo url, JSONObject requestBody, OKListener<T> okListener, FailedListener failedListener, ErrorListener errorListener) {
        super(url, requestBody, okListener, failedListener, errorListener);
    }

    /**
     * 获取 response 中需要解析的jsonObject 或者 jsonArray
     * @param response 响应
     * @return 目标对象
     * @throws Exception
     */
    protected Object getTargetData(JSONObject response) throws Exception {
        return response.get(this.getKey_data());
    }

    @Override
    protected T parseResponseToTarget(JSONObject response) throws Exception {
        return (T) getTargetData(response);
    }

    @Override
    protected boolean pre_process(JSONObject response) throws Exception {
        if (response.getInteger(this.getKey_errcode()) == 0) {
            return true;
        }
        return false;
    }

    @Override
    protected void parseProcessingError(ProcessingError error) throws Exception {
        error.setErrcode(error.getResponse().getInteger(getKey_errcode()));
        error.setMessage(error.getResponse().getString(getKey_errmsg()));
    }
}
