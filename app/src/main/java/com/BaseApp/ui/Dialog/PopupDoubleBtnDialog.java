package com.BaseApp.ui.Dialog;

import android.app.Activity;
import android.content.Context;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.BaseApp.Common.views.BtnClickListener;
import com.BaseApp.R;
import com.BaseApp.Util.DeviceUtil;

import java.util.Timer;
import java.util.TimerTask;
/**
 * 创建一般的提示弹层的对象,带确定和取消按钮的提示弹层,可根据需要修改按钮文字和点击事件
 *
 * @author Reeta.zou
 */
public class PopupDoubleBtnDialog extends BaseDialog {

    private String mTitleText;
    private String mContentText;
    private int contentGravity = Gravity.CENTER;

    public PopupDoubleBtnDialog(Context context) {
        super(context);
    }

    /**
     * @param act
     * @param content
     * @param mBtnClickListener
     * @return
     */
    public static PopupDoubleBtnDialog newInstance(Activity act, String content, BtnClickListener mBtnClickListener) {
        return newInstance(act, "", content, "", "", mBtnClickListener);
    }


    public static PopupDoubleBtnDialog newInstance(Activity act, String title, String content, String posTitle, String negTitle, BtnClickListener mBtnClickListener) {
        PopupDoubleBtnDialog dialog = new PopupDoubleBtnDialog(act);
        dialog.mCustomViewResId = R.layout.dialog_action_content;
        if (null != mBtnClickListener) {
            dialog.setBtnClickListener(mBtnClickListener);
        }
        if (null != title && !title.equals("")) {
            dialog.mTitleText = title;
        }
        if (null != content && !"".equals(content)) {
            dialog.mContentText = content;
        }

        if (null != posTitle && !"".equals(posTitle)) {
            dialog.setPositiveBtnText(posTitle, true);
        }

        if (null != negTitle && !"".equals(negTitle)) {
            dialog.setNegativeBtnText(negTitle, true);
        }

        int padding = (DeviceUtil.instance().getScreenPixelsWidth(act) - (int) (280 * DeviceUtil.instance().getScreenDensity(act))) / 2;
        dialog.setPadding(padding, 0, padding, 0);
        mInstance = dialog;
        return dialog;
    }

    /**
     * 退出应用的弹层
     *
     * @param act
     * @return
     */
    public static PopupDoubleBtnDialog getAppExit(final Activity act) {
        PopupDoubleBtnDialog mDialog = PopupDoubleBtnDialog.newInstance(act, "是否退出平台？", new BtnClickListener() {
            @Override
            public void onBtnClick(int viewID) {
                if (viewID == R.id.action_btn_pos) {
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            act.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    }
                            });
                        }
                    }, 200);
                }
            }
        });
        mInstance = mDialog;
        return mDialog;
    }


    @Override
    public void initParam() {
        mCustomViewResId = R.layout.dialog_action_content;
        mHasPosButton = true;
        mHasNegButton = true;
    }

    @Override
    public void inflaterCustomView(View mCustomView) {
        TextView contentV = (TextView) mCustomView.findViewById(R.id.dialog_content_text);
        TextView titleV = (TextView) mCustomView.findViewById(R.id.dialog_title);
        View divider = mCustomView.findViewById(R.id.divider_view);
        if (contentV != null) {
            contentV.setText(mContentText);
            contentV.setGravity(contentGravity);
        }
        if (titleV != null) {
            if (null != mTitleText && mTitleText.length() > 0) {
                titleV.setText(mTitleText);
                titleV.setVisibility(View.VISIBLE);
                divider.setVisibility(View.VISIBLE);
            } else {
                titleV.setVisibility(View.GONE);
                divider.setVisibility(View.GONE);
            }
        }
        //不设置点击事件时使用默认的点击事件
        mBtnPos.setOnClickListener(this);
        mBtnNeg.setOnClickListener(this);
    }

}
