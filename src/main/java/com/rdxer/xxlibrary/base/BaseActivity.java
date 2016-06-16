package com.rdxer.xxlibrary.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.rdxer.xxlibrary.ViewInject.ViewUtils;


/**
 * Created by LXF on 16/6/15.
 */

public abstract class BaseActivity extends Activity implements IBaseController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int  contentViewId = getContentViewId(savedInstanceState);
        if (contentViewId != 0){
            setContentView(contentViewId);
        }
        viewInject(null);
        loadViews(savedInstanceState);
        viewDidLoad(savedInstanceState);
    }

    @Override
    public void viewInject(View view) {
        ViewUtils.inject(this);
    }

    @Override
    public void loadViews(Bundle savedInstanceState) {

    }
}
