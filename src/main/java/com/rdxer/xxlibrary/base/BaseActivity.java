package com.rdxer.xxlibrary.base;

import android.app.Activity;
import android.os.Bundle;


/**
 * Created by LXF on 16/6/15.
 */

public abstract class BaseActivity extends Activity implements IBaseController {

//    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId(savedInstanceState));
//        unbinder = ButterKnife.bind(this);
        loadViews(savedInstanceState);
        viewDidLoad(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unbinder.unbind();
    }
}
