package com.rdxer.xxlibrary.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.rdxer.xxlibrary.utils.Log;


/**
 * Created by LXF on 16/6/15.
 */

public abstract class BaseActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getIntent().getExtras().getString(KEY_Activity_Parames_Cache);

    }

    @Override
    protected void onDestroy() {
        // del
        Log.e(getClass() + "onDestroy");
        getApp().removeParamsFromJumpCache(getKEY_Activity_Parames());
        super.onDestroy();
    }

    //********************************************
    //                常用的方法
    //********************************************

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

    /**
     * @return 全局单例 application
     */
    public BaseApplication getApp() {
        return BaseApplication.getShared();
    }

    //********************************************
    //                跳转的参数处理
    //********************************************
    // 随机的一个 key
    public static final String KEY_Activity_Parames_Cache = "Activity_Parames_Cache_9f080b39-f811-4da1-b7aa-a2fdc66dd5c3";

    /**
     * 携带参数跳转
     *
     * @param intent  意图
     * @param parames 参数随意类型,
     */
    public void startActivity(Intent intent, Object parames) {
        String key = getApp().putParamsToJumpCache(parames);
        intent.putExtra(KEY_Activity_Parames_Cache, key);
        super.startActivity(intent);
    }

    private boolean isGetKey = false;
    private String KEY_Activity_Parames = null;

    public String getKEY_Activity_Parames() {
        if (isGetKey == false && getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey(KEY_Activity_Parames_Cache)) {
            KEY_Activity_Parames = getIntent().getExtras().getString(KEY_Activity_Parames_Cache);
            isGetKey = true;
        }
        return KEY_Activity_Parames;
    }

    private Object activityParames = null;

    public <T> T getActivityParames() {
        if (activityParames == null && getKEY_Activity_Parames() != null) {
            activityParames = getApp().getParamsFromJumpCache(getKEY_Activity_Parames());
        }
        return (T) activityParames;
    }


    //********************************************
    //            startActivityForResult
    //                  反向传值处理
    //********************************************
    // 逆向传值key
    public static final String KEY_Activity_Parames_Cache_Reverse = "Activity_Parames_Cache_Reverse_9f080b39-f811-4da1-b7aa-a2fdc66dd5c3";

    public void startActivityForResult(Intent intent, int requestCode, Object parames) {
        String key = getApp().putParamsToJumpCache(parames);
        intent.putExtra(KEY_Activity_Parames_Cache, key);
        startActivityForResult(intent, requestCode);
    }

    public void setResult(int resultCode, Object reverseParames) {
        Intent intent = new Intent();
        String key = getApp().putParamsToReverseCache(reverseParames);
        intent.putExtra(KEY_Activity_Parames_Cache_Reverse, key);
        setResult(resultCode, intent);
    }

    public void setResult(int resultCode, Intent data, Object reverseParames) {
        String key = getApp().putParamsToReverseCache(reverseParames);
        data.putExtra(KEY_Activity_Parames_Cache_Reverse, key);
        setResult(resultCode,data);
    }

    /**
     * 一个  intent 只能取一次,,,
     * @param data
     * @param <T>
     * @return
     */
    public <T> T getReverseParames(Intent data){
        if (data != null && data.getExtras() != null && data.getExtras().containsKey(KEY_Activity_Parames_Cache_Reverse)){
            String key = data.getExtras().getString(KEY_Activity_Parames_Cache_Reverse);
            return getApp().removeParamsFromReverseCache(key);
        }
        return null;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onActivityResult(requestCode,resultCode,data,getReverseParames(data));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data, Object reverseParames) {

    }


}
