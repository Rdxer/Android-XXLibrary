package com.rdxer.xxlibrary.proxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.litesuits.orm.LiteOrm;
import com.rdxer.xxlibrary.base.BaseApplication;
import com.rdxer.xxlibrary.bean.BaseModel;
import com.rdxer.xxlibrary.utils.Log;

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
        return JSON.toJSONString(this, true);
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
     *
     * @param modelJSONString 模型的json字符穿
     */
    public BaseModelProxy(String modelJSONString) {
        super();
        setModelJSON(modelJSONString);
    }


    ///************************************************
    ///                生成 模型代理数组
    ///************************************************

    /**
     * 生成模型代理数组
     * @param text 模型数据的json字符串
     * @param modelProxyClazz 模型代理的类
     * @param <T> 约束的泛型
     * @return 模型代理数组
     */
    public static <T extends BaseModelProxy> List<T> generateModelProxyList(String text, Class<T> modelProxyClazz) {
        T t = null;
        try {
            t = modelProxyClazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (t == null) {
            Log.e("解析失败...此类型不正常...:" + modelProxyClazz);
            return new ArrayList<T>();
        }

        List modelList = JSON.parseArray(text, t.getModelClass());

        return generateModelProxyList(modelList, modelProxyClazz);
    }

    /**
     * 生成模型代理数组
     * @param jsonArray jsonArray
     * @param modelProxyClazz modelProxy class
     * @param <T> 约束泛型
     * @return 模型数组
     */
    public static <T extends BaseModelProxy> List<T> generateModelProxyList(JSONArray jsonArray, Class<T> modelProxyClazz) {
        T t = null;
        try {
            t = modelProxyClazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (t == null) {
            Log.e("解析失败...此类型不正常...:" + modelProxyClazz);
            return new ArrayList<T>();
        }
        List modelList = new ArrayList();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            modelList.add(jsonObject.toJavaObject(t.getModelClass()));
        }
        return generateModelProxyList(modelList, modelProxyClazz);
    }

    /**
     * 生成模型代理数组
     * @param modelList 模型数组
     * @param modelProxyClazz 模型代理类型
     * @return 模型代理结果
     */
    public static <TM extends BaseModelProxy<T2>, T2 extends BaseModel> List<TM> generateModelProxyList(List<T2> modelList, Class<TM> modelProxyClazz) {
        List<TM> mpList = new ArrayList<TM>();
        try {
            for (T2 model : modelList) {
                TM t = null;
                t = modelProxyClazz.newInstance();
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

    ///************************************************
    ///                生成 模型数组
    ///************************************************

    /**
     * 模型代理List转成模型列表
     *
     * @param mpList 模型代理list
     * @return modelList模型了list
     */
    public static <ModelT extends BaseModel, ModelProxyT extends BaseModelProxy<ModelT>> List<ModelT> generateModelList(List<ModelProxyT> mpList) {
        List<ModelT> modelTList = new ArrayList();
        if (mpList == null) {
            return modelTList;
        }

        for (ModelProxyT mp : mpList) {
            modelTList.add(mp.getModel());
        }

        return modelTList;
    }

    ///************************************************
    ///                     数据库
    ///************************************************

    public static LiteOrm getDB(){
        return BaseApplication.getShared().getDb();
    }

    /**
     * 保存到数据库
     */
    public long saveToDB(){
        return getDB().save(getModel());
    }

    /**
     * 保存到数据库
     * @param list 模型代理列表
     * @return
     */
    public static <T extends BaseModelProxy> long saveToDB(List<T> list){
        List<BaseModel> modelList = generateModelList(list);
        return getDB().save(modelList);
    }

    /**
     * 从数据库删除
     */
    public long deleteToDB(){
        return getDB().delete(getModel());
    }

    /**
     * 从数据库删除
     * @param list 模型代理列表
     * @return
     */
    public static <T extends BaseModelProxy> long deleteFromDB(List<T> list){
        List<BaseModel> modelList = generateModelList(list);
        return getDB().delete(modelList);
    }

    /**
     * 从数据库删除此表
     * @return
     */
    public static <T extends BaseModel> long deleteFromDB(Class<T> clazz){
        return getDB().delete(clazz);
    }

    /**
     * 从数据库读取数据
     * @return
     */
    public static  <ModelT extends BaseModel, ModelProxyT extends BaseModelProxy<ModelT>> List<ModelProxyT> getDataFromDB(Class<ModelT> modelClass,Class<ModelProxyT> modelProxyTClass){
        List<ModelT> modelTList = getDB().query(modelClass);
        List<ModelProxyT> modelProxyTList = generateModelProxyList(modelTList,modelProxyTClass);
        return modelProxyTList;
    }



}
