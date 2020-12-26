package com.BaseApp.ui.Dialog;

/**
 * Created by reeta.zou on 2016/7/7.
 * 弹层消失后响应接口
 */
public interface DialogDismissedListener {
    /**
     * 弹层消失后响应接口回调函数
     *
     * @param dialog 弹层对象
     */
    public void onDismissed(BaseDialog dialog);
}
