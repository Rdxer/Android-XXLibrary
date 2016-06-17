package com.rdxer.xxlibrary.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;



/**
 * Created by LXF on 16/6/15.
 */

public abstract class BaseActivity extends Activity {


    /**
     * 在子线程运行,
     * Activity.runOnUiThread()
     *
     * @param action
     */
    public void runOnChildThread(Runnable action) {
        Thread thread = new Thread(action);
        thread.start();
    }

}
