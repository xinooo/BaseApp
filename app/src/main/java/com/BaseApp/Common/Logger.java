package com.BaseApp.Common;

import android.util.Log;

/**
 * 日志答应控制类
 */
public class Logger {
    private static int LOG_LEVEL = 6;  //>6 打印所有日志
    public static int ERROR = 1;
    public static int WARN = 2;
    public static int INFO = 3;
    public static int DEBUG = 4;
    public static int VERBOS = 5;
    public static String TAG = "gw.com.android";

    public static void e(String tag, String msg){
        if(LOG_LEVEL>ERROR){
            System.out.println("线程id = " + android.os.Process.myTid());
            Log.e(tag, msg);
        }
    }

    /**
     * 打印出错信息的 StackTrace 信息
     *
     * ( 若调试开关关闭，则不打印 )
     *
     */
    public static void e(String tag , Throwable e) {
        if (null != e && LOG_LEVEL>ERROR ) {
            if (e instanceof Throwable) {
                e.printStackTrace();
            }
        }
    }

    public static void w(String tag, String msg){
        if(LOG_LEVEL>WARN) {
            System.out.println("线程id = " + android.os.Process.myTid());
            Log.w(tag, msg);
        }
    }
    public static void i(String tag, String msg){
        if(LOG_LEVEL>INFO) {
            System.out.println("线程id = " + android.os.Process.myTid());
            Log.i(tag, msg);
        }
    }
    public static void d(String tag, String msg){
        if(LOG_LEVEL>DEBUG) {
            System.out.println("线程id = " + android.os.Process.myTid());
            Log.d(tag, msg);
        }
    }
    public static void v(String tag, String msg){
        if(LOG_LEVEL>VERBOS) {
            System.out.println("线程id = " + android.os.Process.myTid());
            Log.v(tag, msg);
        }
    }

    public static void disableLogger(){
        LOG_LEVEL = 0;
    }

    public static void e(Throwable e) {
        e(TAG,e);
    }
    public static void e(String msg){
            e(TAG, msg);
    }
    public static void w(String msg){
        w(TAG, msg);
    }
    public static void i(String msg){
        i(TAG, msg);
    }
    public static void d(String msg){
        d(TAG, msg);
    }
    public static void v(String msg){
        v(TAG, msg);
    }
}
