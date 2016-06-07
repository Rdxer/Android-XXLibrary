package com.firstcare.dylibrary.bean;

import com.alibaba.fastjson.JSON;

/**
 * 模型基类
 * Created by LXF on 16/6/1.
 */
public class BaseModel {

    public BaseModel(){}

    @Override
    public String toString() {
        return super.toString()+this.toJSON(true);
    }

    /**
     * 将JavaBean序列化为带格式的JSON文本
     * @param prettyFormat
     * @return
     */
    public String toJSON(boolean prettyFormat){
        return JSON.toJSONString(this,prettyFormat);
    }
}

