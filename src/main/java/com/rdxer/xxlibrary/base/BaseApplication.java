package com.rdxer.xxlibrary.base;

import android.app.Application;

import com.litesuits.orm.LiteOrm;
import com.rdxer.xxlibrary.utils.HTTPUtils;

import java.util.Map;
import java.util.Stack;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by LXF on 16/6/20.
 */
public class BaseApplication extends Application {

    // -----------
    //    全局单例
    private static BaseApplication shared;
    public static BaseApplication getShared() {
        return shared;
    }

    // 网络
    private HTTPUtils api;
    public HTTPUtils getApi() {
        return api;
    }
    public void setApi(HTTPUtils api) {
        this.api = api;
    }

    // 数据库
    private LiteOrm db = null;
    public LiteOrm getDb() {
        return db;
    }
    public void setDb(LiteOrm db) {
        this.db = db;
    }


    //********************************************
    //               正向传值处理
    //********************************************
    // 页面跳转的参数缓存
    private Map<String,Object> jumpCache;
    public synchronized Map<String, Object> getJumpCache() {
        if (jumpCache == null) {
            jumpCache = new ConcurrentHashMap<>();
        }
        return jumpCache;
    }

    /**
     * 页面参数 缓存put
     * @param params 页面参数
     * @return 缓存的key
     */
    public synchronized String putParamsToJumpCache(Object params){
        String uuid = UUID.randomUUID().toString();
        getJumpCache().put(uuid,params);
        return uuid;
    }

    /**
     * 获取缓存
     * @param key 添加时候返回的可以
     * @return 添加时候的缓存
     */
    public synchronized <T>  T getParamsFromJumpCache(String key){
        T t = (T) getJumpCache().get(key);
        return t;
    }

    /**
     * 删除缓存 使用 key
     * @param key key
     * @return 获取删除的缓存
     */
    public synchronized <T>  T removeParamsFromJumpCache(String key){
        if (key == null) {
            return null;
        }
        T t = (T) getJumpCache().remove(key);
        return t;
    }

    //********************************************
    //               反向传值处理 Reverse
    //********************************************
    // 页面跳转的参数缓存
    private Map<String,Object> reverseCache;

    public synchronized Map<String, Object> getReverseCache() {
        if (reverseCache == null) {
            reverseCache = new ConcurrentHashMap<>();
        }
        return reverseCache;
    }

    /**
     * 逆向传值 缓存
     * @param params 逆向传值
     * @return 缓存的key
     */
    public synchronized String putParamsToReverseCache(Object params){
        String uuid = UUID.randomUUID().toString();
        getReverseCache().put(uuid,params);
        return uuid;
    }
    /**
     * 删除缓存 使用 key
     * @param key key
     * @return 获取删除的缓存
     */
    public synchronized <T>  T removeParamsFromReverseCache(String key){
        T t = (T) getReverseCache().remove(key);
        return t;
    }


    //********************************************
    //                生命周期
    //********************************************
    @Override
    public void onCreate() {
        super.onCreate();
        shared = this;
    }
}
