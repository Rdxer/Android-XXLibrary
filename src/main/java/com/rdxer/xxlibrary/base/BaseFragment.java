package com.rdxer.xxlibrary.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rdxer.xxlibrary.ViewInject.ViewUtils;
import com.rdxer.xxlibrary.ViewInject.view.annotation.ContentView;

/**
 * Created by LXF on 16/6/13.
 */

public abstract class BaseFragment extends Fragment implements IBaseController{

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int contentViewId = getContentViewId(savedInstanceState);
        if (contentViewId == 0){
            Class<?> handlerType = this.getClass();
            ContentView contentView = handlerType.getAnnotation(ContentView.class);
            contentViewId = contentView.value();
        }
        View rootView = inflater.inflate(contentViewId , container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        viewInject(view);
        loadViews(savedInstanceState);
        viewDidLoad(savedInstanceState);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void viewInject(View view) {
        ViewUtils.inject(this,view);
    }

    @Override
    public void loadViews(Bundle savedInstanceState) {

    }

    @Override
    public int getContentViewId(Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}