package com.BaseApp.ui.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewStub;

import androidx.fragment.app.FragmentActivity;

import com.BaseApp.Common.Tint.TintTextView;
import com.BaseApp.Common.views.BtnClickListener;
import com.BaseApp.R;

/**
 * 自定义弹层类
 * @author Administrator
 *
 */
public abstract class BaseDialog extends Dialog implements View.OnClickListener{
	
	protected FragmentActivity mOwnerAct;
	protected int mCustomViewResId = 0; // 布局视图id
	protected Rect mPadding = null;
	protected boolean mHasPosButton = true;// 是否有确定按钮
	protected boolean mHasNegButton = true;// 是否有取消按钮
	
	protected ViewStub mCustomViewContainer = null; // content视图
	protected View divider = null;
	protected View mBtnLayout = null;// 按钮布局
	protected View dialog_layout=null;
	protected TintTextView mBtnPos = null;// 确定按钮
	protected TintTextView mBtnNeg = null;// 取消按钮
	protected String mBtnPosText = null;// 确定按钮文案
	protected String mBtnNegText = null;// 取消按钮文案
	protected BtnClickListener mBtnClickListener = null; // 按钮响应事件
	protected DialogPreDismissListener mPreDismissListener = null; // 弹层消失时响应事件
	protected DialogDismissedListener mDimissedListener = null;// 弹层消失后响应事件
	protected static BaseDialog mInstance = null;
	private int PosButtonColor=0,NegButtonColor=0;

	public static BaseDialog  instance(){
		return mInstance;
	}

	public int getPosButtonColor() {
		return PosButtonColor;
	}

	public int getNegButtonColor() {
		return NegButtonColor;
	}

	public void setPosButtonColor(int posButtonColor) {
		PosButtonColor = posButtonColor;
	}

	public void setNegButtonColor(int negButtonColor) {
		NegButtonColor = negButtonColor;
	}

	/**
	 * 默认有提示信息
	 * @param context
	 */
	public BaseDialog(Context context){
		super(context, R.style.dialog_loading_bar_no_frame);
		mOwnerAct = (FragmentActivity) context;
		initParam();
	}

	/**
	 * 初始化界面元素
	 */
	public abstract void initParam();
	
	/**
	 * 初始化界面视图
	 * @param mCustomView
	 */
	public abstract void inflaterCustomView(View mCustomView);
	
	private void initView() {
		LayoutInflater inflater = getLayoutInflater();
		if (inflater != null) {
			View rootView = inflater.inflate(R.layout.dialog_action, null, false);
			
			if (mPadding != null) {
				rootView.setPadding(mPadding.left, mPadding.top, mPadding.right,mPadding.bottom);
			}
			dialog_layout=rootView.findViewById(R.id.dialog_layout);
			divider = rootView.findViewById(R.id.divider);
			mBtnLayout = rootView.findViewById(R.id.action_btn_bar);
			mBtnPos = (TintTextView) rootView.findViewById(R.id.action_btn_pos);
			if (mHasPosButton) {
				mBtnPos.setClickable(true);
				mBtnPos.setEnabled(true);
				mBtnPos.setOnClickListener(this);
				if (mBtnPosText != null)
					mBtnPos.setText(mBtnPosText);
			} else {
				mBtnPos.setVisibility(View.GONE);
				divider.setVisibility(View.GONE);
			}

			mBtnNeg = (TintTextView) rootView.findViewById(R.id.action_btn_neg);
			if (mHasNegButton) {
				mBtnNeg.setClickable(true);
				mBtnNeg.setEnabled(true);
				mBtnNeg.setOnClickListener(this);
				if (mBtnNegText != null)
					mBtnNeg.setText(mBtnNegText);
			} else {
				mBtnNeg.setVisibility(View.GONE);
				divider.setVisibility(View.GONE);
			}
			if(getNegButtonColor()!=0){
				mBtnNeg.setColorValue(getNegButtonColor(),getNegButtonColor());
			}
			if(getPosButtonColor()!=0){
				mBtnPos.setColorValue(getPosButtonColor(),getPosButtonColor());
			}
			mCustomViewContainer = (ViewStub) rootView.findViewById(R.id.action_content);
			if(mCustomViewResId != 0 && mCustomViewContainer != null){
				mCustomViewContainer.setLayoutResource(mCustomViewResId);
				View mCustomView = mCustomViewContainer.inflate();
				inflaterCustomView(mCustomView);
			}
			
			LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			/* The next line will add the ProgressBar to the dialog. */
			addContentView(rootView, lp);
		}
	}
	
	@Override
	public void show() {
		if(null != mOwnerAct && !mOwnerAct.isFinishing()){
			initView();
			super.show();
		}
	}
	
	@Override
	public void dismiss() {
		
		mInstance = null;
		
		if (mPreDismissListener != null){
			mPreDismissListener.onPreDismiss(this);
		}

		super.dismiss();

		if (mDimissedListener != null){
			mDimissedListener.onDismissed(this);
		}
	}

	@Override
	public void onClick(View v) {
		if (v == mBtnPos) {
			if(null != mBtnClickListener){
				mBtnClickListener.onBtnClick(R.id.action_btn_pos);
			}
			dismiss();
		}else{
			if(null != mBtnClickListener){
				mBtnClickListener.onBtnClick(R.id.action_btn_neg);
			}
			dismiss();
		} 
	}
	
	/**
	 * 设置弹层类的左边间距
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @return
	 */
	public BaseDialog setPadding(int left, int top, int right, int bottom) {
		mPadding = new Rect(left, top, right, bottom);
		return this;
	}

	/**
	 * 设置弹层类的左边间距均为0
	 * 
	 * @return
	 */
	public BaseDialog setZeroPadding() {
		mPadding = new Rect(0, 0, 0, 0);
		return this;
	}
	
	/**
	 * 设置左边按钮的文字和显示状态
	 * @param text
	 * @param visible
	 */
	public void setPositiveBtnText(String text, boolean visible) {
		mBtnPosText = text;
		mHasPosButton = visible;
	}

	/**
	 * 设置右边按钮的文字和显示状态
	 * @param text
	 * @param visible
	 */
	public void setNegativeBtnText(String text, boolean visible) {
		mBtnNegText = text;
		mHasNegButton = visible;
	}
	public void setBtnClickListener(BtnClickListener l) {
		mBtnClickListener = l;
	}

	public BaseDialog setOnDismissedListener(DialogDismissedListener l) {
		mDimissedListener = l;
		return this;
	}

	public DialogPreDismissListener getOnPreDismissListener() {
		return mPreDismissListener;
	}

	public BaseDialog setOnPreDismissListener(DialogPreDismissListener l) {
		mPreDismissListener = l;
		return this;
	}

	public DialogDismissedListener getOnDimissedListener() {
		return mDimissedListener;
	}



}
