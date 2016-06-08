package com.rdxer.xxlibrary.HTTPUtils.listener;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by LXF on 16/6/8.
 */

public interface ErrorListener extends Response.ErrorListener {
    void onErrorResponse(VolleyError error);
}
