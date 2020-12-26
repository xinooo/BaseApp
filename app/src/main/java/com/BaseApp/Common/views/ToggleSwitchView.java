package com.BaseApp.Common.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.BaseApp.Common.Tint.TintTextView;
import com.BaseApp.R;
import com.BaseApp.Util.CommonUtils;

/*
 * Tab切换，包含左右两个
 * @author Administrator
 */
public class ToggleSwitchView extends LinearLayout implements OnClickListener {

	private TintTextView mLeftBtn;
	private TintTextView mRightBtn;
	private ImageView mLeftIcon;
	private ImageView mRightIcon;
	private BtnClickListener mListener;
	private int mCheckedButton;
	private Context cxt;

	public ToggleSwitchView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public ToggleSwitchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ToggleSwitchView(Context context) {
		super(context);
		init(context);
	}

	/**
	 * 初始化控件信息
	 * @param ctx
	 */
	private void init(Context ctx) {
		setOrientation(LinearLayout.HORIZONTAL);
		cxt = ctx;
		View view = LayoutInflater.from(ctx).inflate(R.layout.commom_main_title_top_tab, this, true);
		mLeftBtn = (TintTextView) view.findViewById(R.id.main_top_left_tab);
		mLeftBtn.setOnClickListener(this);
		mLeftBtn.setSelected(true);

		mRightBtn = (TintTextView) view.findViewById(R.id.main_top_right_tab);
		mRightBtn.setOnClickListener(this);

		mLeftIcon = (ImageView) view.findViewById(R.id.left_hot_view);
		mRightIcon = (ImageView) view.findViewById(R.id.right_hot_view);
		mLeftIcon.setVisibility(GONE);
		mListener = null;
		mCheckedButton = R.id.main_top_left_tab;
		checkedButton(mCheckedButton);
	}

	/**
	 * 给右边按钮设置文案
	 * @param titleID
	 */
	public void setRightResource(int titleID) {
		if (titleID == 0) {
			mRightBtn.setVisibility(View.GONE);
		} else {
			mRightBtn.setText(cxt.getString(titleID));
			mRightBtn.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 给左边按钮设置文案
	 * @param titleID
	 */
	public void setLeftResource(int titleID) {
		if (0 == titleID) {
			mLeftBtn.setText("");
			mLeftBtn.setVisibility(View.VISIBLE);
		} else {
			mLeftBtn.setText(cxt.getString(titleID));
			mLeftBtn.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 将title背景设置成透明的
	 */
	public void setBgTransparent() {
		mLeftBtn.setBackgroundColor(cxt.getColor(R.color.transparent));
		mRightBtn.setBackgroundColor(cxt.getColor(R.color.transparent));
		mLeftBtn.setClickable(false);
		mRightBtn.setClickable(false);
	}

	/**
	 * 给右边按钮设置文案
	 * @param title
	 */
	public void setRightResource(String title) {
		if (null == title || title.equals("")) {
			mRightBtn.setVisibility(View.GONE);
		} else {
			mRightBtn.setText(title);
			mRightBtn.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 给左边按钮设置文案
	 * @param title
	 */
	public void setLeftResource(String title) {
		if (null == title || title.equals("")) {
			mLeftBtn.setVisibility(View.GONE);
		} else {
			mLeftBtn.setText(title);
			mLeftBtn.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 显示或隐藏左边新功能图标
	 * @param isVisible
	 */
	public void showLeftIcon(int isVisible){
		if(null != mLeftIcon){
			mLeftIcon.setVisibility(isVisible);
		}
	}

	/**
	 * 显示或隐藏右边新功能图标
	 * @param isVisible
	 */
	public void showRightIcon(int isVisible){
		if(null != mRightIcon){
			mRightIcon.setVisibility(isVisible);
		}
	}

	/**
	 * 设置左右按钮的点击监听事件
	 * @param l
	 */
	public void setBtnClickListener(BtnClickListener l) {
		mListener = l;
	}

	@Override
	public void setFocusable(boolean focusable) {
		mLeftBtn.setFocusable(focusable);
		mRightBtn.setFocusable(focusable);
		super.setFocusable(focusable);
	}

	/**
	 * 改变左右按钮的状态，主要用于改变按钮的背景
	 * @param btnIndex
	 */
	public void checkedButton(int btnIndex) {
		mCheckedButton = btnIndex;
		if (btnIndex == R.id.main_top_left_tab) {
			mLeftBtn.setSelected(true);
			mRightBtn.setSelected(false);
		} else if (btnIndex == R.id.main_top_right_tab) {
			mRightBtn.setSelected(true);
			mLeftBtn.setSelected(false);
		}
	}

	@Override
	public void onClick(View v) {
		if (CommonUtils.isFastDoubleClick())
			return;

		if (v == mLeftBtn && mCheckedButton != R.id.main_top_left_tab) {
			checkedButton(R.id.main_top_left_tab);
			if (mListener != null) {
				mListener.onBtnClick(R.id.main_top_left_tab);
			}
		} else if (v == mRightBtn && mCheckedButton != R.id.main_top_right_tab) {
			checkedButton(R.id.main_top_right_tab);
			if (mListener != null) {
				mListener.onBtnClick(R.id.main_top_right_tab);
			}
		}
	}
}