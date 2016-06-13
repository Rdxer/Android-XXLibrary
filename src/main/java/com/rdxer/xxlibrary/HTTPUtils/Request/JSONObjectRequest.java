package com.rdxer.xxlibrary.HTTPUtils.Request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.rdxer.xxlibrary.HTTPUtils.URLInfo;
import com.rdxer.xxlibrary.HTTPUtils.error.Pre_ProcessingError;
import com.rdxer.xxlibrary.HTTPUtils.error.ProcessingError;
import com.rdxer.xxlibrary.HTTPUtils.error.ProcessingProcessingError;
import com.rdxer.xxlibrary.HTTPUtils.listener.ErrorListener;
import com.rdxer.xxlibrary.HTTPUtils.listener.FailedListener;
import com.rdxer.xxlibrary.HTTPUtils.listener.OKListener;
import com.rdxer.xxlibrary.utils.Log;

import java.io.UnsupportedEncodingException;

/**
 * Created by LXF on 16/6/3.
 */

public abstract class JSONObjectRequest<T> extends JSONRequest<JSONObject> {

    public JSONObjectRequest(URLInfo url, JSONObject requestBody, OKListener<T> okListener, FailedListener failedListener, ErrorListener errorListener) {
        super(url,requestBody,errorListener);
        this.okListener = okListener;
        this.failedListener = failedListener;
    }

//    public static interface OKListener<T> {
//        public void onOK(T res, JSONObject response);
//    }
//
//    public static interface FailedListener {
//        public void onFailed(ProcessingError error);
//    }
//
//    public interface ErrorListener extends Response.ErrorListener {
//        public void onErrorResponse(VolleyError error);
//    }

    private boolean isFailed = false;

    public void setFailed(boolean failed) {
        isFailed = failed;
    }

    public boolean isFailed() {
        return isFailed;
    }

    private OKListener<T> okListener;
    private FailedListener failedListener;

    private T target;

    public ProcessingError getProcessingError() {
        return processingError;
    }

    public void setProcessingError(ProcessingError processingError) {
        this.processingError = processingError;
    }

    private ProcessingError processingError;


    @Override
    protected void onFinish() {
        super.onFinish();
        okListener = null;
        failedListener = null;
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        if (isFailed == false) {
            if (okListener != null) {
                okListener.onOK(target, response);
            }
        } else {
            if (failedListener != null) {
                failedListener.onFailed(processingError);
            }
        }
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse responseBody) {
        try {
            String jsonString = new String(responseBody.data,
                    HttpHeaderParser.parseCharset(responseBody.headers, PROTOCOL_CHARSET));
            JSONObject response = JSON.parseObject(jsonString);

            try {
                target = parseResponse(response);
                this.setFailed(false);
            } catch (Exception e) {
                Log.wtf(e);
                e.printStackTrace();
                this.setFailed(true);
                try {
                    if (e instanceof ProcessingError) {
                        this.setProcessingError((ProcessingError) e);
                        parseProcessingError(processingError);
                    } else {
                        this.setProcessingError(new ProcessingError(response));
                        parseProcessingError(processingError);
                    }
                } catch (Exception e1) {
                    Log.wtf(e1);
                    e1.printStackTrace();
                    this.setProcessingError(new ProcessingProcessingError(response,this.getProcessingError()));
                    this.getProcessingError().setMessage("服务器数据异常..");
                    Log.e("parseNetworkResponse:","解析响应中'异常'数据失败...");
                }
            }

            return Response.success(response,
                    HttpHeaderParser.parseCacheHeaders(responseBody));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    /**
     * 解析 响应
     */
    protected T parseResponse(JSONObject response) throws Exception {
        if (pre_process(response) == false) {
            this.setFailed(true);
            throw new Pre_ProcessingError(response);
        } else {
            return parseResponseToTarget(response);
        }
    }

    /**
     * 解析 response to target
     */
    protected abstract T parseResponseToTarget(JSONObject response) throws Exception;

    /**
     * 前置处理 用于判断数据是否是正确的
     */
    protected abstract boolean pre_process(JSONObject response) throws Exception;

    /**
     * 解析 response 到 错误中
     */
    protected abstract void parseProcessingError(ProcessingError error) throws Exception;

}
