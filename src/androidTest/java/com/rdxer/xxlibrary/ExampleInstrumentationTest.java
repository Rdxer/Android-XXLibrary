package com.rdxer.xxlibrary;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rdxer.xxlibrary.utils.HTTPUtils;
import com.rdxer.xxlibrary.utils.Log;

import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentationTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        testhttp(appContext);
        assertEquals("com.rdxer.xxlibrary.test", appContext.getPackageName());
    }

    private void testhttp(Context context) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://www.baidu.com",
                new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                //打印请求返回结果
                Log.e(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("volleyerror", "erro2");
            }
        });
        //将StringRequest对象添加进RequestQueue请求队列中
    }
}