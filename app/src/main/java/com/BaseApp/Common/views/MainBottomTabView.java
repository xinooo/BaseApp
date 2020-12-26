package com.BaseApp.Common.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.BaseApp.R;
import com.BaseApp.Common.model.DataItemDetail;
import com.BaseApp.Common.model.DataItemResult;
import com.BaseApp.Common.Tint.TintImageTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * 首页底部自定义View
 */
public class MainBottomTabView extends RelativeLayout {

    @BindView(R.id.list_view)
    RecyclerView listView;

    private ButtomTabAdapter mAdapter;
    private DataItemResult mResult;
    private ConfigMenuDeal mMenuDeal;

    private LayoutInflater inflater;
    private View layout;
    private RecyclerClickListener mClickListener = null; //homeTab切换点击监听回调接对象
    private int mSelectPosition = 0;

    public MainBottomTabView(Context context) {
        super(context);
        initView(context);
    }

    public MainBottomTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MainBottomTabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    /**
     * 初始化底部导航控件
     *
     * @author yuye.zou
     */
    private void initView(Context context) {
        if (!this.isInEditMode()) {
            inflater = LayoutInflater.from(context);
            layout = inflater.inflate(R.layout.commom_main_bottom_bar, this, true);
            ButterKnife.bind(this, layout);

            mMenuDeal = new ConfigMenuDeal();
            mResult = mMenuDeal.getButtomTabTypeList();
            mAdapter = new ButtomTabAdapter(context, listView, mResult);
            // 线性布局管理器
            GridLayoutManager layoutManager = new GridLayoutManager(context, mAdapter.getItemCount());
            // 设置布局管理器
            listView.setLayoutManager(layoutManager);
            listView.setAdapter(mAdapter);
        }
    }

    /**
     * 改变选中项
     *
     * @param title
     */
    public DataItemDetail setSelectStr(String title) {
        DataItemDetail item = new DataItemDetail();
        for (int i = 0; i < mResult.getDataCount(); i++) {
            if (title.equals(mResult.getItem(i).getString("key"))) {
                item = mResult.getItem(i);
                mSelectPosition = i;
                break;
            }
        }
        mAdapter.notifyDataSetChanged();
        return item;
    }

    /**
     * 改变选中项
     *
     * @param positon
     */
    public void setSelectPositon(int positon) {
        mSelectPosition = positon;
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 刷新界面
     */
    public void refreshData() {
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 设置tab切换的点击监听事件
     *
     * @param onHomeTabClick
     * @author yuye.zou
     */
    public void setRecyclerClickListener(RecyclerClickListener onHomeTabClick) {
        mClickListener = onHomeTabClick;
    }

    /**
     * Created by reeta.zou on 2017/3/18.
     * 底部布局适配器
     */
    public class ButtomTabAdapter extends RecylerListAdapter {

        private DataItemResult mList;
        private DataItemDetail mItem;

        /**
         * 初始化界面数据
         *
         * @param context
         */
        public ButtomTabAdapter(Context context, RecyclerView mView, DataItemResult list) {
            super(context, mView);
            mList = list;
        }

        @Override
        public void refreshData() {
            notifyDataChanged();
        }

        @Override
        public int getItemCount() {
            if (null != mList) {
                return mList.getDataCount();
            }
            return 0;
        }

        public DataItemDetail getItem(int position) {
            if (position < 0 || position >= mList.getDataCount()) {
                return null;
            }

            if (null != mList && null != mList.getItem(position)) {
                return mList.getItem(position);
            }

            return null;
        }

        public void updateViews(RecyclerView.ViewHolder viewholder, int position) {
            mItem = getItem(position);
            ListItemView mItemView = (ListItemView) viewholder;
            if (null != mItem) {
                String key = mItem.getString("key");
                //未登录交易tab特殊处理
                mItemView.btnV.setVisibility(VISIBLE);
                if (position == mSelectPosition) {
                    mItemView.btnV.setSelected(true);
                } else {
                    mItemView.btnV.setSelected(false);
                }
                mItemView.btnV.setText(mItem.getString("title"));
                if (!TextUtils.isEmpty(mItem.getString("iconUrl"))) {
                    Glide.with(getContext()).load(mItem.getString("iconUrl"))
                            .placeholder(R.mipmap.a_me_default)
                            .error(R.mipmap.a_me_default)
                            .into(mItemView.btnV.getImageView());
                } else if (mItem.getInt("iconID") == 0) {
                    mItemView.btnV.setImageResource(R.mipmap.a_me_default);
                } else {
                    mItemView.btnV.setImageResource(mItemView.btnV.isSelected() ? mItem.getInt("iconSelectID") : mItem.getInt("iconID"));
                }
                mItemView.itemView.setTag(position);
            }
        }

        @Override
        public void updateViews(RecyclerView.ViewHolder viewholder, List payloads) {

        }

        @Override
        public int getLayoutID() {
            return R.layout.commom_main_bottom_item;
        }

        @Override
        public RecyclerView.ViewHolder getViewHolder(View v, int type) {
            return new ListItemView(v);
        }

        public class ListItemView extends RecyclerView.ViewHolder {
            /**
             * 产品名称
             */
            @BindView(R.id.radio_button)
            public TintImageTextView btnV;

            public ListItemView(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            @OnClick(R.id.item_layout)
            void onClick() {
                int position = (int) itemView.getTag();
                if (null != mClickListener) {
                    DataItemDetail dataItemDetail = getItem(position);
                    if (dataItemDetail != null) {
                        mClickListener.onClick(position, dataItemDetail);
                    }
                }
            }
        }
    }

}
