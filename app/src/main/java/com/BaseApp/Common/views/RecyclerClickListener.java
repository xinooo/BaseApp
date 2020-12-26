package com.BaseApp.Common.views;

import com.BaseApp.Common.model.DataItemDetail;

/*
 * RecyclerView点击事件回调接口
 */
public interface RecyclerClickListener {
    /**
     * 列表界面点击回调函数
     * @param position
     */
    void onClick(int position, DataItemDetail itemDetail);
}
