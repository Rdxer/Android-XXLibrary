package com.rdxer.xxlibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.rdxer.xxlibrary.HTTPUtils.Request.JSONObjectRequest;
import com.rdxer.xxlibrary.HTTPUtils.Request.JSONRequest;

/**
 * Created by LXF on 16/6/1.
 */
public abstract class HTTPUtils {

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private Context context;
    private String baseURL;

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public HTTPUtils(Context context) {
        this.context = context;

        requestQueue = getRequestQueue();
        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * 添加请求到执行队列 并且取消 之前添加的请求
     * @param request 需要添加的请求
     */
    public <T> void addToRequestQueueCancelLast(final Request<T> request) {
        processRequest(request);
        if (request instanceof JSONRequest) {
            requestQueue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    return request.getTag() == request.getTag();
                }
            });
        }
        getRequestQueue().add(request);
    }

    /**
     * 添加请求到执行队列
     */
    public <T> void addToRequestQueue(final Request<T> request) {
        processRequest(request);
        getRequestQueue().add(request);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    /**
     * 处理请求
     * @param request 请求
     */
    public void processRequest(Request request){
        if (request instanceof JSONRequest) {
            ((JSONObjectRequest) request).setBaseURL(getBaseURL());
            ((JSONObjectRequest) request).setContext(this.context);
        }
    }

}