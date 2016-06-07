package com.firstcare.dylibrary.utils;


/**
 * Log用
 * @author LXF 09-15 2014
 */

public class Log {
    /**
     * Tag
     */
    public static String TAG = "LOG_TAG";
    /**
     * 打印能够显示的级别
     */
    public static int LOG_LEVEL = android.util.Log.VERBOSE;
    /**
     * 是否需要打印
     */
    public static boolean IS_DEBUG = true;

    private Log() {
    }

    public static void DEBUG(Object... objs) {
        println(android.util.Log.ERROR, TAG, objs);
    }

    /**
     * 详细的
     *
     * @param objs
     */
    public static void v(Object... objs) {
        println(android.util.Log.VERBOSE, TAG, objs);
    }

    /**
     * 调试
     *
     * @param objs
     */
    public static void d(Object... objs) {
        println(android.util.Log.DEBUG, TAG, objs);
    }

    public static void i(Object... objs) {
        println(android.util.Log.INFO, TAG, objs);
    }

    /**
     * 警告
     */
    public static void w(Object... objs) {
        println(android.util.Log.WARN, TAG, objs);
    }

    /**
     * 错误
     */
    public static void e(Object... objs) {
        println(android.util.Log.ERROR, TAG, objs);
    }

    public static void  wtf(Object... objs) {
        println(android.util.Log.ERROR, TAG, objs);
    }

    private static void println(int level, String tag, Object... objs) {
        if (isPrint(level))
            return;
        String msg = "";
        msg += getFunctionName();
        msg += "MSG: " + "\n" + getMsgForObjs(objs);
        android.util.Log.println(level, tag, msg);
    }

    private static boolean isPrint(int level) {
        boolean b = false;
        b = level < LOG_LEVEL || !IS_DEBUG;
        return b;
    }

    private static String getMsgForObjs(Object[] objs) {
        String msg = "";
        for (int n = 0; n < objs.length; n++) {
            msg += objs[n] == null ? " [" + (n + 1) + "=null] " : " "
                    + objs[n].toString() + " ";
            msg += "\t\n";
        }
        return msg;
    }

    /**
     * Get The Current Function Name 获取当前的函数名
     *
     * @return
     */
    private static String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null) {
            return "[null]";
        }
        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }
            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }
            if (st.getClassName().equals(Log.class.getName())) {
                continue;
            }
            return String
                    .format("FROM[Thread(%s),Path:%s.%s(...){L:%s}]", Thread
                            .currentThread().getName(), st.getClassName(), st
                            .getMethodName(), st.getLineNumber());
        }
        return " error [null]";
    }
}