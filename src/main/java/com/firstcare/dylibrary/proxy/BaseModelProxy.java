package com.firstcare.dylibrary.proxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.firstcare.dylibrary.bean.BaseModel;
import com.firstcare.dylibrary.utils.Log;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;


/**
 * 代理基类
 * Created by LXF on 16/6/1.
 */
public abstract class BaseModelProxy<T extends BaseModel> {
    private T model;

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    //////////////////////
    //      构造

    /**
     * 默认构造函数
     */
    public BaseModelProxy() {

    }

    /**
     * @param model 模型
     */
    public BaseModelProxy(T model) {
        this.setModel(model);
    }

    /**
     * @param object JSONObject
     */
    public BaseModelProxy(JSONObject object) {
        this.setModel(object.toJavaObject(getModelClass()));
    }
    /**
     * 构造模型代理实例
     * @param modelJSONString 模型的json字符穿
     */
    public BaseModelProxy(String modelJSONString) {
        super();
        setModelJSON(modelJSONString);
    }

    /**
     *
     * @param text
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T extends BaseModelProxy> List<T> mpListWithJSON(String text, Class<T> clazz) {
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (t == null) {
            Log.e("解析失败...此类型不正常...:" + clazz);
            return new ArrayList<T>();
        }

        List modelList = JSON.parseArray(text, t.getModelClass());

        return mpListWithModelList(modelList,clazz);
    }

    public static <T extends BaseModelProxy> List<T> mpListWithJSONArray(JSONArray jsonArray, Class<T> clazz) {
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (t == null) {
            Log.e("解析失败...此类型不正常...:" + clazz);
            return new ArrayList<T>();
        }
        List modelList = new ArrayList();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            modelList.add(jsonObject.toJavaObject(t.getModelClass()));
        }
        return mpListWithModelList(modelList,clazz);
    }

    public static <TM extends BaseModelProxy<T2>, T2 extends BaseModel> List<TM> mpListWithModelList(List<T2> modelList, Class<TM> clazz) {
        List<TM> mpList = new ArrayList<TM>();
        try {
            for (T2 model : modelList) {
                TM t = null;
                t = clazz.newInstance();
                t.setModel(model);
                mpList.add(t);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return mpList;
    }

    /**
     * 初始化
     *
     * @param modelJSONString 模型字符串
     */
    public void setModelJSON(String modelJSONString) {
        T model = JSON.parseObject(modelJSONString, getModelClass());
        setModel(model);
    }


    /**
     * @return 当前类 代理的模型类
     */
    @JSONField(serialize = false)
    public Class<T> getModelClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }


    @Override
    public String toString() {
        return JSON.toJSONString(this,true);
    }
}
