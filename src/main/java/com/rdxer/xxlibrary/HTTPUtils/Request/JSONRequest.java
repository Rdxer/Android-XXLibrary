package com.rdxer.xxlibrary.HTTPUtils.Request;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.rdxer.xxlibrary.HTTPUtils.URLInfo;
import com.rdxer.xxlibrary.utils.Log;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Map;

/**
 * Created by LXF on 16/6/3.
 */

public abstract class JSONRequest<T> extends Request<T> {

    /** Default charset for JSON request. */
    protected static final String PROTOCOL_CHARSET = "utf-8";

    /** Content type for request. */
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);

    private boolean preprocessed = false;

    /**
     * 处理参数调用的方法
     * 如果需要加密参数等等的可以在这里
     * 只允许执行一次 子类中不能主动调用
     */
    protected JSONObject preprocessRequestBody(JSONObject requestBody) throws Exception{
        return requestBody;
    }

    public JSONObject getRequestBody() {
        if (preprocessed == false){
            try {
                requestBody = preprocessRequestBody(requestBody);
            } catch (Exception e) {
                e.printStackTrace();
                Log.wtf("参数处理时发生异常....",requestBody.toJSONString());
            }
            preprocessed = true;
        }
        return requestBody;
    }
    public void setRequestBody(JSONObject requestBody) {
        this.requestBody = requestBody;
    }

    private JSONObject requestBody;

    private long timestamp;
    private Context context;
    private String baseURL;

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    private Map<String, String> headers;
    private URLInfo targetUrl;

    public URLInfo getTargetUrl() {
        return targetUrl;
    }

    /**
     * Returns a list of extra HTTP headers to go along with this request. Can
     * throw {@link AuthFailureError} as authentication may be required to
     * provide these values.
     * @throws AuthFailureError In the event of auth failure
     */
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (this.headers == null){
            return Collections.emptyMap();
        }
        return this.headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Context getContext() {
        return context;
    }
    public void setContext(Context context) {
        this.context = context;
    }

    public long getTimestamp() {
        if (timestamp == 0){
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    public <TT extends JSONRequest<T>> TT setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return (TT)this;
    }

    public JSONRequest(URLInfo url, JSONObject requestBody,
                       Response.ErrorListener errorListener) {
        super(url.getMethod(), url.getURL(), errorListener);
        this.targetUrl = url;
        this.requestBody = requestBody;
    }

    @Override
    abstract protected Response<T> parseNetworkResponse(NetworkResponse response);

    @Override
    public String getUrl() {
        return this.targetUrl.generateAbsURL(this,getBaseURL(),getRequestBody());
    }
    @Override
    public String getOriginUrl() {
        return getUrl();
    }
    @Override
    public String getCacheKey() {
        return getMethod() + ":" + getUrl();
    }

    @Override
    public String getPostBodyContentType() {
        return getBodyContentType();
    }

    /**
     * @deprecated Use {@link #getBody()}.
     */
    @Override
    public byte[] getPostBody() {
        return getBody();
    }

    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    @Override
    public byte[] getBody() {
        try {
            if (getRequestBody() == null || getRequestBody().size() == 0){
                return null;
            }
            String bodyStr = getRequestBodyString(getRequestBody());
            return bodyStr.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    getRequestBody(), PROTOCOL_CHARSET);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            VolleyLog.wtf("序列化参数时发生错误 %s %s",
                    getRequestBody(), PROTOCOL_CHARSET);
            return null;
        }
    }

    protected String getRequestBodyString(JSONObject requestBody) throws Exception{
        return requestBody.toJSONString();
    }

}
