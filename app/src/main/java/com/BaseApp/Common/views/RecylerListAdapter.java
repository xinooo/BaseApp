package com.BaseApp.Common.views;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.BaseApp.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/*
 * 列表提供适配器
 */
public abstract class RecylerListAdapter extends RecyclerView.Adapter{
	/** 产品布局加载器 **/
	protected LayoutInflater mInflater;
	/** 上下文 **/
	protected BaseActivity mOwnerAct;

	protected Resources mRes;
	/**记录按钮是否可以点击标识*/
	protected boolean isClick;
	/**当前列表控件对象*/
	protected RecyclerView mListV;

    /**标识当前界面是否正在刷新*/
	protected boolean mIsRefreshing = false;

	/**
	 * 初始化界面数据
	 *
	 * @param context
	 */
	public RecylerListAdapter(Context context, RecyclerView mView) {
		mOwnerAct = (BaseActivity) context;
		mInflater = LayoutInflater.from(context);
		mRes = context.getResources();
		mIsRefreshing = false;
		isClick = false;
		mListV = mView;
	}

	/**
	 * 报价来了刷新界面
	 * @param codeId
     */
	public void refreshPrice(int codeId){

	}

	/**
	 * 报价来了刷新界面,主要是刷新持仓列表使用
	 * @param codeId
	 */
	public void refreshPrice(ArrayList<Integer> codeId){

	}

	/**
	 * 单个订单更新界面
	 * @param orderId
     */
	public void refreshOrder(int orderId){

	}

	/**
	 * 刷新界面所有数据
	 */
	public abstract void refreshData();

	/**
	 * 列表刷新
	 * @param viewholder
	 * @param position
     */
	public abstract void updateViews(RecyclerView.ViewHolder viewholder, int position);

	/**
	 * 部分刷新
	 * @param viewholder
	 * @param payloads
     */
	public abstract void updateViews(RecyclerView.ViewHolder viewholder, List payloads);

	public abstract int getLayoutID();

	public abstract RecyclerView.ViewHolder getViewHolder(View v, int type);

	protected void notifyDataChanged(){
		if(!mIsRefreshing){
			notifyDataSetChanged();
		}
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		updateViews(holder,position);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
		mIsRefreshing = true;
		if(payloads.isEmpty()){
			onBindViewHolder(holder, position);
		}else{
			updateViews(holder,payloads);
		}

		mIsRefreshing = false;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup convertView, int type) {
		 View v = mInflater.inflate(getLayoutID(), convertView, false);
	     return getViewHolder(v, type);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
