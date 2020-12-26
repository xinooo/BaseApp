package com.BaseApp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.BaseApp.Common.views.LoadingDialog;
import com.BaseApp.Common.Logger;

import java.lang.reflect.Field;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/*
 * 公共基础fragment
 */
public abstract class PushMsgTabFragment extends Fragment {
	protected final String TAG = this.getClass().getName();
	private CompositeDisposable mCompositeDisposable; //RxBus的注册事件
	private PushMsgTabFragment mParent;
	public FragmentManager mFragmentManager = null;
	public FragmentTransaction mFragmentTransaction = null;

	protected View mRootView;//根布局

	private String onlyFlag;
	private LoadingDialog mProgress;

	public String getOnlyFlag() {
		return onlyFlag;
	}

	public void setOnlyFlag(String onlyFlag) {
		this.onlyFlag = onlyFlag;
	}

	public PushMsgTabFragment() {
		super();
		mParent = null;
	}

	public PushMsgTabFragment getParent() {
		return mParent;
	}

	public void setParent(PushMsgTabFragment parent) {
		mParent = parent;
	}

	public boolean postToUIThread(Runnable runnable) {
		getActivity().runOnUiThread(runnable);
		return true;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logger.e(this.getClass().getSimpleName() + " onCreate");
		Fragment parent = getParentFragment();
		mFragmentManager = getChildFragmentManager();

		if (parent instanceof PushMsgTabFragment)
			mParent = (PushMsgTabFragment) parent;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater,container,savedInstanceState);
		Logger.e(this.getClass().getSimpleName() + " onCreateView");
		mRootView = inflater.inflate(getLayoutId(), null);
		initLayoutView();
		registerRxBus();
		initViewData();
		return mRootView;
	}

	/**
	 * RxBus注册事件管理类
	 * @param subscription
	 */
	public void bindSubscription(Disposable subscription) {
		if (this.mCompositeDisposable == null) {
			this.mCompositeDisposable = new CompositeDisposable();
		}
		this.mCompositeDisposable.add(subscription);
	}

	/**
	 * 注册RxBus相关联的监听事件
	 */
	public void registerRxBus(){}

	/**
	 *
	 * @return 返回当前界面加载的布局id
	 */
	protected abstract int getLayoutId();

	/**
	 * 初始化当前界面的控件
	 */
	protected abstract void initLayoutView();

	/**
	 * 初始化界面控件的值和条件
	 */
	protected abstract void initViewData();

	public void onAccountNotify(){}
	public void onSymbolNotify(){}
	public void onSymbolNotify(int codeId){}
	public void onOrderSuccessNotify(int notifyId, int orderId){}
	public void onOrderFailNotify(int notifyId, int resultCode){}
	public void onSendQuote() {
		//主面板根据每个版本情况发送订阅信息
	}

	/**
	 * 行情服务器监听调用的通知方法
	 *
	 * @param isConntion
	 */
	public void quoteServerNotice(Boolean isConntion) {
	}

	/**
	 * 交易服务器监听调用的通知方法
	 *
	 * @param isConntion
	 */
	public void tradeServerNotice(Boolean isConntion){

	}

	@Override
	public void onDetach() {
		super.onDetach();
		Logger.e(this.getClass().getSimpleName() + " onDetach");
		try {
			Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);

		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		Logger.e(this.getClass().getSimpleName() + " onSaveInstanceState");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Logger.e(this.getClass().getSimpleName() + " onDestroy");
		if (this.mCompositeDisposable != null) {
			this.mCompositeDisposable.clear();
		}
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		Logger.e(this.getClass().getSimpleName() + " setUserVisibleHint  "+isVisibleToUser);
		if(getActivity()==null){
			Logger.e(this.getClass().getSimpleName() + " activity  null");
		}


	}

	@Override
	public void onPause() {
		super.onPause();
		Logger.e(this.getClass().getSimpleName() + " onPause");
	}

	@Override
	public void onResume() {
		super.onResume();
		Logger.e(this.getClass().getSimpleName() + " onResume");
	}

	@Override
	public void onStart() {
		super.onStart();
		Logger.e(this.getClass().getSimpleName() + " onStart");
	}

	@Override
	public void onStop() {
		super.onStop();
		Logger.e(this.getClass().getSimpleName() + " onStop");
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		Logger.e(this.getClass().getSimpleName() + " onHiddenChanged hidden：" + hidden);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Logger.e(this.getClass().getSimpleName() + " onDestroyView ");
	}

	/**
	 * 显示加载圈
	 */
	public void showLoading() {
		if (null == mProgress || !mProgress.isShowing()) {
			mProgress = LoadingDialog.show(getContext(), "", "");
			mProgress.setCancelable(false);
		}
	}
	/**
	 * 隐藏加载圈
	 */
	public void hideLoading() {
		if (null != mProgress) {
			mProgress.dismiss();
			mProgress = null;
		}
	}
}
