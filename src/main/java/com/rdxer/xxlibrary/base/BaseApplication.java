package com.rdxer.xxlibrary.base;

import android.app.Application;

import com.litesuits.orm.LiteOrm;
import com.rdxer.xxlibrary.utils.HTTPUtils;

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

    @Override
    public void onCreate() {
        super.onCreate();
        shared = this;
    }
}
