package com.BaseApp.Util;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/*
 * 用于判断第二次点击时是否太快，默认500ms只能点击一次，快速点击第二次不响应事件
 * @author Administrator
 */
public class CommonUtils {
	private static long lastClickTime = 0;
	private static long minTimeInterval = 500;

	public synchronized static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long duration = time - lastClickTime;
		lastClickTime = System.currentTimeMillis();
		if (duration > 0 && duration < minTimeInterval)
			return true;
		return false;
	}
	public synchronized static boolean isFasterDoubleClick() {
		long time = System.currentTimeMillis();
		long duration = time - lastClickTime;
		lastClickTime = System.currentTimeMillis();
		if (duration > 0 && duration < minTimeInterval+500)
			return true;
		return false;
	}

	/**
	 * 隐藏软键盘
	 * @param activity
     */
	public static  void hideSoft(Activity activity){
		if(null == activity){
			return;
		}
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		// 隐藏软键盘
		if(null != activity.getWindow().getDecorView().getWindowToken()){
			imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
		}
	}
}
