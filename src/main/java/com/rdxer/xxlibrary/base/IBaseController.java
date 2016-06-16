package com.rdxer.xxlibrary.base;

import android.os.Bundle;

/**
 * Created by LXF on 16/6/15.
 */

public interface IBaseController {
    int getContentViewId(Bundle savedInstanceState);
    void loadViews(Bundle savedInstanceState);
    void viewDidLoad(Bundle savedInstanceState);
}
