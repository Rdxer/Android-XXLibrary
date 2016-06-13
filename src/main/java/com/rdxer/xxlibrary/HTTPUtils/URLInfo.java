package com.rdxer.xxlibrary.HTTPUtils;


import com.alibaba.fastjson.JSONObject;
import com.android.volley.*;
import com.rdxer.xxlibrary.HTTPUtils.Request.JSONRequest;
import com.rdxer.xxlibrary.utils.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by LXF on 16/6/8.
 */

public class URLInfo {

    private int method;
    private String URL;

    private String absURL;
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

    public String generateAbsURL(JSONRequest request, String baseURL, JSONObject jsonObject) {
        // 如果不为空 也就是已经生成了 直接返回
        if (absURL != null) {
            return absURL;
        }

        // 拼接 baseURL 如果 url 已经是完整的 则 不拼接baseURL
        if (baseURL != null && baseURL.length() > 0 && getURL().indexOf("://") == -1) {
            absURL = baseURL.trim() + getURL().trim();
        }

        // 自动拼接参数
        Set<String> keySet = jsonObject.keySet();
        for (String key : keySet){
            String targetKey = String.format("{%s}",key);
            int index = absURL.indexOf(targetKey);
            if (index != -1){
                Object o = jsonObject.get(key);
                absURL.replaceAll(targetKey,o.toString());

            }
        }

        return absURL;
    }
}
