package com.rdxer.xxlibrary.HTTPUtils;


import com.alibaba.fastjson.JSONObject;
import com.android.volley.*;
import com.rdxer.xxlibrary.HTTPUtils.Request.JSONRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LXF on 16/6/8.
 */

public class URLInfo {

    private int method;
    private String URL;
//  private String[] keys;

    public URLInfo(int method, String URL) {
        this.method = method;
        this.URL = URL;
    }

//
//    public URLInfo(Request.Method method, String URL,String ...keys) {
//        this.method = method;
//        this.URL = URL;
//        this.keys = keys;
//    }


    public String getURL() {
        return URL;
    }
    public int getMethod() {
        return method;
    }

    public String generateAbsURL(JSONRequest request,String baseURL, JSONObject jsonObject){
        String absURL = null;
        if (baseURL != null && baseURL.length() > 0 && getURL().indexOf("://") == -1){
            absURL = baseURL.trim() + getURL().trim();
        }
        return absURL;
    }
}
