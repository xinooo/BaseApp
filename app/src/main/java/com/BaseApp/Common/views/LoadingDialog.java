package com.BaseApp.Common.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.BaseApp.R;
import com.BaseApp.Common.Logger;


/**
 * 加载中。。。
 * 
 * @author Administrator
 * 
 */
public class LoadingDialog extends Dialog {

	private ProgressBar mBar;
	private TextView mMsgBox;
	protected static LoadingDialog mInstance = null;

	public static LoadingDialog  instance(){
		return mInstance;
	}

	/**
	 * 显示加载圈
	 */
	public static void showLoading(Activity act) {
		mInstance = LoadingDialog.show(act, "","");
		mInstance.setCancelable(false);
	}

	/**
	 * 隐藏加载圈
	 */
	public static void hideLoading() {
		if (null != mInstance) {
			mInstance.dismiss();
			mInstance = null;
		}
	}

	public static LoadingDialog show(Context context) {
		return show(context, null, null);
	}

	public static LoadingDialog show(Context context, CharSequence title,
                                     CharSequence message) {
		return show(context, title, message, true);
	}

	public static LoadingDialog show(Context context, CharSequence title,
                                     CharSequence message, boolean indeterminate) {
		return show(context, title, message, indeterminate, true, null);
	}

	public static LoadingDialog show(Context context, CharSequence title,
                                     CharSequence message, boolean indeterminate, boolean cancelable) {
		return show(context, title, message, indeterminate, cancelable, null);
	}

	public static LoadingDialog show(Context context, CharSequence title,
                                     CharSequence message, boolean indeterminate, boolean cancelable,
                                     OnCancelListener cancelListener) {
		boolean hasMessage = true;
		if(null == message || "".equals(message)){
			hasMessage = false;
		}
		LoadingDialog dialog = new LoadingDialog(context,hasMessage);
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setCancelable(cancelable);
		dialog.setOnCancelListener(cancelListener);
		dialog.show();

		mInstance = dialog;
		return dialog;
	}
	
	/**
	 * 默认有提示西悉尼
	 * @param context
	 */
	public LoadingDialog(Context context){
		super(context, R.style.dialog_loading_bar_no_frame);
		init(true);
	}

	/**
	 * 
	 * @param context
	 * @param hasMessage 加载圈中是否有提示信息
	 */
	public LoadingDialog(Context context, boolean hasMessage) {
		super(context, R.style.dialog_loading_bar_no_frame);

		init(hasMessage);
	}

	public void setMessage(CharSequence message) {
		mMsgBox.setVisibility(View.GONE);
	}

	public ProgressBar getProgressBar() {
		return mBar;
	}

	private void init(boolean hasMessage) {
		LayoutInflater inflater = getLayoutInflater();
		if (inflater != null) {
			View rootV = inflater
					.inflate(R.layout.dialog_progress, null, false);
			if (rootV != null) {
				// Resources res = getContext().getResources();
				mBar = (ProgressBar) rootV.findViewById(R.id.loading_progress);
				mMsgBox = (TextView) rootV.findViewById(R.id.loading_message);
				setTitle(null);
				setCancelable(true);
				setOnCancelListener(null);
				LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
				/* The next line will add the ProgressBar to the dialog. */
				addContentView(rootV, lp);
			}
		}
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
	}

	@Override
	public void dismiss() {
		mInstance = null;
		
		try {
			super.dismiss();
		} catch (IllegalArgumentException e) {
			Logger.e(e);
		}
	}
}
