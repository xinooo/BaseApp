package com.BaseApp.Common;

import android.app.Application;
import android.os.Handler;

import com.BaseApp.Util.ThreadPool;
import com.BaseApp.Util.ToastUtil;

import java.io.FileInputStream;
import java.io.IOException;

public class AppMain extends Application {
    private static AppMain app = null;
    private Handler mHandler;

    public AppMain() {
        super();
        app = this;
    }

    public Handler getHandler(){
        return mHandler;
    }

    public static AppMain getApp() {
        return app;
    }

    public static String getAppString(int strId){
        if(null != AppActivities.getSingleton().currentActivity()){
            return AppActivities.getSingleton().currentActivity().getString(strId);
        }
        return getApp().getString(strId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.i("AppMain","IPC NAME "+getCurrentProcessName());
        mHandler = new Handler();
        ToastUtil.init(getApplicationContext());
        ThreadPool.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                initParam();
            }
        });

    }

    /**
     * 初始化应用的一些值
     */
    public void initParam(){

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Logger.i("======onLowMemory========");
        // activity由系统打开 (是由于手机内存不够,activity在后台被系统回收,再打开时出现的现象)
        // 因为系统加载的所有的Activity不在同一个线程,所以要结束其他线程
        // AppActivities.getSingleton().popAllActivityExceptOne(null);
        // android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        /**
         * OnTrimMemory的参数是一个int数值，代表不同的内存状态： TRIM_MEMORY_COMPLETE
         * 80：内存不足，并且该进程在后台进程列表最后一个，马上就要被清理 TRIM_MEMORY_MODERATE
         * 60：内存不足，并且该进程在后台进程列表的中部。 TRIM_MEMORY_BACKGROUND 40：内存不足，并且该进程是后台进程。
         * TRIM_MEMORY_UI_HIDDEN 20：内存不足，并且该进程的UI已经不可见了。 以上4个是4.0增加
         *
         * TRIM_MEMORY_RUNNING_CRITICAL 15：内存不足(后台进程不足3个)，并且该进程优先级比较高，需要清理内存
         * TRIM_MEMORY_RUNNING_LOW 10：内存不足(后台进程不足5个)，并且该进程优先级比较高，需要清理内存
         * TRIM_MEMORY_RUNNING_MODERATE 5：内存不足(后台进程超过5个)，并且该进程优先级比较高，需要清理内存
         * 以上3个是4.1增加
         */
        Logger.i("======onTrimMemory level========" + level);
    }

    public static String getCurrentProcessName() {
        FileInputStream in = null;
        try {
            String fn = "/proc/self/cmdline";
            in = new FileInputStream(fn);
            byte[] buffer = new byte[256];
            int len = 0;
            int b;
            while ((b = in.read()) > 0 && len < buffer.length) {
                buffer[len++] = (byte) b;
            }
            if (len > 0) {
                String s = new String(buffer, 0, len, "UTF-8");
                return s;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
