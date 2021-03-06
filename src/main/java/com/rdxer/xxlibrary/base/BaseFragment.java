package com.rdxer.xxlibrary.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * Created by LXF on 16/6/13.
 */

public abstract class BaseFragment extends Fragment {
    /** Fragment当前状态是否可见 */
    protected boolean isVisible;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }
    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }
    /**
     * 不可见
     */
    protected void onInvisible() {
    }
    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();
    
    @Override
    public Context getContext() {
        return getActivity();
    }

    protected static final String Key_args_MainFragment = "Key_args_MainFragment";

    public static BaseFragment newInstance(BaseFragment fragment,JSONObject parames) {
        Bundle args = new Bundle();
        args.putString(Key_args_MainFragment, parames.toJSONString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JSONObject parames = getParames();
        initField(parames);
        onCreate(savedInstanceState, parames);
    }
    public void onCreate(@Nullable Bundle savedInstanceState,JSONObject parames) {

    }
    protected void initField(JSONObject parames){
        if (parames == null || parames.size() == 0) {
            return;
        }

    }

    public JSONObject getParames(){
        if (getArguments() != null && getArguments().getString(Key_args_MainFragment) != null) {
            return JSON.parseObject(getArguments().getString(Key_args_MainFragment));
        }
        return null;
    }

}
