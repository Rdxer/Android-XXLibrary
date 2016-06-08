package com.rdxer.xxlibrary.OtherUtils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * 跟App相关的辅助类
 * Created by LXF on 16/6/2.
 */
public class AppUtils {

    private AppUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");

    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param verApp app 版本 比如:4.1.0 5
     * @param ver    需要比较的版本 比如:4.1
     * @return =0 相等, >0 APP版本大, <0比较版本大
     */
    public static int compareAppVersion(String verApp, String ver) {
        String[] verAppArray = verApp.split("\\.");
        String[] verArray = ver.split("\\.");
        int i = 0;
        for (; i < (verAppArray.length > verArray.length ? verArray.length : verAppArray.length); i++) {
            String verAppStr = verAppArray[i];
            String verStr = verArray[i];
            if (Integer.valueOf(verAppStr) - Integer.valueOf(verStr) != 0) {
                return Integer.valueOf(verAppStr) - Integer.valueOf(verStr);
            }
        }
        if (verAppArray.length == verArray.length) {
            return 0;
        }
        String[] array = verAppArray.length > verArray.length ? verAppArray : verArray;
        int value = 0;
        for (; i < array.length; i++) {
            String str = array[i];
            value += Integer.valueOf(str);
        }
        return value * (verAppArray == array ? 1 : -1);
    }
}