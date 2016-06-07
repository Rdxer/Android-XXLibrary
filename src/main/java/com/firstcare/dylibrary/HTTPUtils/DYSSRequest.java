package com.firstcare.dylibrary.HTTPUtils;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.firstcare.dylibrary.Encrypt.DYEncryptionTools;
import com.firstcare.dylibrary.OtherUtils.AppUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LXF on 16/6/3.
 */

public class DYSSRequest<T> extends JSONObjectRequest<T> {

    public final static String key_data = "data";
    public final static String key_errcode = "errcode";
    public final static String key_errmsg = "errmsg";

    public DYSSRequest(int method, String url, JSONObject requestBody, OKListener<T> okListener, FailedListener failedListener, ErrorListener errorListener) {
        super(method, url, requestBody, okListener, failedListener, errorListener);
    }

    protected Object getTargetData(JSONObject response) throws Exception {
        return response.get(key_data);
    }

    @Override
    protected T parseResponseToTarget(JSONObject response) throws Exception {
        return (T) getTargetData(response);
    }

    @Override
    protected boolean pre_process(JSONObject response) throws Exception {
        if (response.getInteger(key_errcode) == 0) {
            return true;
        }
        return false;
    }

    @Override
    protected void parseProcessingError(ProcessingError error) throws Exception {
        error.setErrcode(error.getResponse().getInteger(key_errcode));
        error.setMessage(error.getResponse().getString(key_errmsg));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put("Device-Time", getTimestamp() + "");
        headers.put("App-Name", AppUtils.getAppName(this.getContext()));
        headers.put("App-Version", AppUtils.getVersionName(this.getContext()));
        headers.put("Device-Type", "Android");
        headers.put("System-Name", "Android");
        headers.put("System-Version", android.os.Build.VERSION.RELEASE);
//                TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//                String szImei = TelephonyMgr.getDeviceId();
        headers.put("Device-Identifier", "asdasdadasdasdasdasdasdas");
        headers.put("User-Id", "0");
        headers.put("range", "range");
        headers.put("Content-Type", "application/json");
        headers.put("Hmac", DYEncryptionTools.generateHmacAuthCode(
                headers.get("Device-Identifier"),
                getTimestamp() + "",
                headers.get("User-Id"),
                null
        ));
        return headers;
    }
}
