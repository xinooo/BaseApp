package com.BaseApp.Common.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.BaseApp.Common.AppActivities;
import com.BaseApp.Common.Logger;
import com.BaseApp.Common.Tint.TintImageTextView;
import com.BaseApp.R;
import com.BaseApp.Util.CommonUtils;
import com.BaseApp.Util.DeviceUtil;

/*
 * 公共顶部title自定义控件，带返回按钮
 */
public class CommonTitleBar extends LinearLayout implements OnClickListener {
    private View mSystemTitleBar;
    private View mAppTitleBar;
    private View mContainer;
    public TextView mTitleView; // 中间文案
    private ImageView mIvTitleLogo;//中间Logo
    public TintImageTextView mLeftBtn; // 左边按钮
    private TintImageTextView mLeftBtn2;//左边按钮2
    public ToggleSwitchView mSwitchView; // 中间切换按钮
    public TintImageTextView mRightBtn; // 右边按钮
    public TintImageTextView mRightBtn2; // 右边按钮
    public View mDividerView;
    private BtnClickListener mCallback;// 左按钮点击事件监听器
    private TextView tv_im_count;
    private TextView tv_im_count2;
    public View right_line;
    public View right_line2;
    private Context mcontext;

    public CommonTitleBar(Context context) {
        super(context);
        initView(context);
    }

    public CommonTitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public CommonTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void initView(Context act) {
        mcontext = act;
        View mView = LayoutInflater.from(act).inflate(R.layout.common_title_bar, this, true);
        mSystemTitleBar = mView.findViewById(R.id.system_title_bar);
        mAppTitleBar = mView.findViewById(R.id.app_title_bar);
        mContainer = mView.findViewById(R.id.title_bar);
        mTitleView = (TextView) mView.findViewById(R.id.app_title);
        mIvTitleLogo = (ImageView) mView.findViewById(R.id.iv_title_logo);
        mDividerView = mView.findViewById(R.id.title_divider);
        mLeftBtn = (TintImageTextView) mView.findViewById(R.id.title_left_btn);
        mLeftBtn.setColorValue(ContextCompat.getColor(getContext(), R.color.colorAccent),ContextCompat.getColor(getContext(), R.color.color_808080));
        mLeftBtn2 = (TintImageTextView) mView.findViewById(R.id.title_left_btn2);
        mSwitchView = (ToggleSwitchView) mView.findViewById(R.id.main_tab_title);
        mLeftBtn.setSelected(false);
        mRightBtn = (TintImageTextView) mView.findViewById(R.id.title_right_btn);
        mRightBtn2 = (TintImageTextView) mView.findViewById(R.id.title_right_btn2);
        right_line = mView.findViewById(R.id.title_right_line);
        right_line2 = mView.findViewById(R.id.title_right_line2);
        mLeftBtn.setOnClickListener(this);
        mLeftBtn2.setOnClickListener(this);
        mRightBtn.setOnClickListener(this);
        mRightBtn2.setOnClickListener(this);
        tv_im_count=(TextView)mView.findViewById(R.id.tv_im_count);
        tv_im_count2=(TextView)mView.findViewById(R.id.tv_im_count2);
    }

    public void enable(boolean enable) {
        if (mContainer != null)
            mContainer.setVisibility(enable ? View.VISIBLE : View.GONE);
    }

    private void setViewVisibility(View v, boolean visible) {
        v.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
    public void setTv_im_count(int count){
        if(count<=0){
            tv_im_count.setVisibility(GONE);
            tv_im_count2.setVisibility(GONE);
            return;
        }
        if(count>99){
            tv_im_count.setVisibility(GONE);
            tv_im_count2.setVisibility(VISIBLE);
        }else {
            tv_im_count.setText(count+"");
            tv_im_count.setVisibility(VISIBLE);
            tv_im_count2.setVisibility(GONE);
            if(count>9){
                tv_im_count.getLayoutParams().width= LayoutParams.WRAP_CONTENT;
                tv_im_count.setBackgroundResource(R.drawable.mine_red_bg3);
            }else {
                tv_im_count.getLayoutParams().width= DeviceUtil.instance().dip2px(15,getContext());
                tv_im_count.setBackgroundResource(R.drawable.mine_red_bg2);
            }
        }

    }

    /**
     * 设置分割线显示隐藏。默认是显示状态
     *
     * @param visible
     */
    public void setDividerView(boolean visible) {
        setViewVisibility(mDividerView, visible);
    }

    /**
     * 设置左右切换按钮的显示与隐藏
     *
     * @param isVisibility
     */
    public void mSwitchViewVisibility(int isVisibility) {
        mSwitchView.setVisibility(isVisibility);
    }

    /**
     * 设置中间文案的显示与隐藏
     *
     * @param isVisibility
     */
    public void mTitleViewVisibility(int isVisibility) {
        mTitleView.setVisibility(isVisibility);
    }

    /**
     * 给右边按钮设置文案
     *
     * @param titleID
     */
    public void setTabRightResource(int titleID) {
        mSwitchView.setRightResource(titleID);
    }

    /**
     * 给左边按钮设置文案
     *
     * @param titleID
     */
    public void setTabLeftResource(int titleID) {
        mSwitchView.setLeftResource(titleID);
    }

    /**
     * 给右边按钮设置文案
     *
     * @param title
     */
    public void setTabRightResource(String title) {
        mSwitchView.setRightResource(title);
    }

    /**
     * 给左边按钮设置文案
     *
     * @param title
     */
    public void setTabLeftResource(String title) {
        mSwitchView.setLeftResource(title);
    }

    /**
     * 设置中间title文案
     *
     * @param appTitleID 中间title文案ID
     */
    public void setAppTitle(int appTitleID) {
        setAppTitle(mcontext.getString(appTitleID));
    }

    /**
     * 设置中间title文案
     *
     * @param appTitle 中间title文案
     */
    public void setAppTitle(CharSequence appTitle) {
        mIvTitleLogo.setVisibility(View.GONE);
        mTitleView.setVisibility(View.VISIBLE);
        mTitleView.setText(appTitle);
    }

    /**
     * 给左边按钮的文字
     *
     * @param titleID
     */
    public void setLeftResource(int titleID) {
        //		if (0 != titleID) {
        //			mLeftBtn.setText(AppMain.getAppString(titleID));
        //		}
        mLeftBtn.setText("");
    }
    public void setLeftValue(CharSequence appTitle) {
        mLeftBtn.setText((String) appTitle);
    }

    /**
     * 给左边按钮的文字
     *
     * @param title
     */
    public void setLeftResource(String title) {
        //		mLeftBtn.setText(title);
        mLeftBtn.setText("");
    }
    public void setLeftTextColor(int normal, int select) {
        if (null != mLeftBtn) {
            mLeftBtn.setColorValue(normal, select);
            mLeftBtn.setVisibility(View.VISIBLE);
        }
    }
    /**
     * 给右边按钮的文字
     *
     * @param title
     */
    public void setRightResource(String title) {
        mRightBtn.setText(title);
    }

    /**
     * 给左边按钮设置图片和文案
     *
     * @param resourceID
     * @param titleID
     */
    public void setLeftResource(int resourceID, int titleID) {
        if (0 != resourceID) {
            mLeftBtn.setImageResource(resourceID);
        } else {
            mLeftBtn.setImageResource(0);
        }
        if (0 != titleID) {
            mLeftBtn.setText(mcontext.getString(titleID));
        } else {
            mLeftBtn.setText("");
        }
    }

    /**
     * 给左边按钮2设置图片和文案
     *
     * @param resourceID
     * @param titleID
     */
    public void setLeftResource2(int resourceID, int titleID) {
        if (0 != resourceID) {
            mLeftBtn2.setImageResource(resourceID);
        } else {
            mLeftBtn2.setImageResource(0);
        }
        if (0 != titleID) {
            mLeftBtn2.setText(mcontext.getString(titleID));
        } else {
            mLeftBtn2.setText("");
        }
        mLeftBtn2.setVisibility(View.VISIBLE);
    }

    /**
     * 给右边按钮设置图片和文案
     *
     * @param resourceID
     * @param titleID
     */
    public void setRightResource(int resourceID, int titleID) {
        if (0 != resourceID) {
            mRightBtn.setImageResource(resourceID);
        }
        if (0 != titleID) {
            mRightBtn.setText(mcontext.getString(titleID));
        }

        mRightBtn.setVisibility(View.VISIBLE);
    }

    /**
     * 设置状态下的颜色
     *
     * @param normal
     * @param select
     */
    public void setRightTextColor(int normal, int select) {
        if (null != mRightBtn) {
            mRightBtn.setColorValue(normal, select);
            mRightBtn.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 给右边按钮设置图片和文案
     *
     * @param resourceID
     * @param titleID
     */
    public void setRightResource2(int resourceID, int titleID) {
        if (0 != resourceID) {
            mRightBtn2.setImageResource(resourceID);
        }
        if (0 != titleID) {
            mRightBtn2.setText(mcontext.getString(titleID));
        }

        mRightBtn2.setVisibility(View.VISIBLE);
    }

    /**
     * 给右边按钮设置资源
     *
     * @param resourceID
     */
    public void setRightResource(int resourceID) {
        if (resourceID > 0) {
            mRightBtn.setText(mcontext.getString(resourceID));
        } else {
            mRightBtn.setText("返回");
        }
        mRightBtn.setVisibility(View.VISIBLE);
    }

    /**
     * 设置左边按钮显示状态
     *
     * @param visible 是否显示
     */
    public void setLeftButtonVisible(boolean visible) {
        setViewVisibility(mLeftBtn, visible);
    }

    /**
     * 设置左边按钮显示状态
     *
     * @param visible 是否显示
     */
    public void setLeftButton2Visible(boolean visible) {
        setViewVisibility(mLeftBtn2, visible);
    }
    /**
     * 设置右边按钮显示状态
     *
     * @param visible 是否显示
     */
    public void setRightButton(boolean visible) {
        setViewVisibility(mRightBtn, visible);
    }

    /**
     * 设置右边按钮显示状态
     *
     * @param visible 是否显示
     */
    public void setRightButton2(boolean visible) {
        setViewVisibility(mRightBtn2, visible);
    }

    /**
     * 设置按钮点击监听事件
     *
     * @param mCallback
     */
    public void setBtnClickListener(BtnClickListener mCallback) {
        this.mCallback = mCallback;
    }

    public void setLeftBtnColorValue(int normal, int selector) {
        if (mLeftBtn == null)
            return;
        mLeftBtn.setColorValue(normal, selector);
    }
    public void setLeftBtn2ColorValue(int normal, int selector) {
        if (mLeftBtn2 == null)
            return;
        mLeftBtn2.setColorValue(normal, selector);
    }

    public void setRighttBtnColorValue(int normal, int selector) {
        if (mRightBtn == null)
            return;
        mRightBtn.setColorValue(normal, selector);
    }

    public TintImageTextView getLeftBtn() {
        return mLeftBtn;
    }

    public TintImageTextView getRightBtn() {
        return mRightBtn;
    }

    public TintImageTextView getRightBtn2() {
        return mRightBtn2;
    }

    public void onNightMode(boolean isNight) {
        if (isNight) {
            mSystemTitleBar.setBackgroundColor(getResources().getColor(R.color.color_night_bg));
            mAppTitleBar.setBackgroundColor(getResources().getColor(R.color.color_night_bg));
            mTitleView.setTextColor(getResources().getColor(R.color.color_f));
            mDividerView.setBackgroundColor(getResources().getColor(R.color.color_night_bg_dark));
            mTitleView.setTextColor(getResources().getColor(R.color.color_f));
            mLeftBtn.setColorValue(ContextCompat.getColor(getContext(), R.color.color_e),ContextCompat.getColor(getContext(), R.color.color_808080));
        } else {
            mSystemTitleBar.setBackgroundColor(getResources().getColor(R.color.color_nav_bg));
            mAppTitleBar.setBackgroundColor(getResources().getColor(R.color.color_nav_bg));
            mTitleView.setTextColor(getResources().getColor(R.color.color_nav_title));
            mDividerView.setBackgroundColor(getResources().getColor(R.color.color_d));
            mTitleView.setTextColor(getResources().getColor(R.color.color_b));
            mLeftBtn.setColorValue(ContextCompat.getColor(getContext(), R.color.colorAccent),ContextCompat.getColor(getContext(), R.color.color_808080));
        }
        //夜间模式
//        mLeftBtn.onNightMode(isNight);
        mLeftBtn2.onNightMode(isNight);
        mRightBtn.onNightMode(isNight);
        mRightBtn2.onNightMode(isNight);
    }

    public void setTitleBarBlack() {
        mAppTitleBar.setBackgroundColor(getResources().getColor(R.color.color_1F1F1F));
        mSystemTitleBar.setBackgroundColor(getResources().getColor(R.color.color_1F1F1F));
        mDividerView.setBackgroundColor(getResources().getColor(R.color.color_1F1F1F));
    }

    public void setTitleBarBackground(int bgID) {
        mAppTitleBar.setBackgroundResource(bgID);
        mSystemTitleBar.setBackgroundResource(bgID);
        mDividerView.setBackgroundResource(bgID);
    }

    public void setTitleTextColor(int colorId) {
        mTitleView.setTextColor(getResources().getColor(colorId));
    }

    @Override
    public void onClick(View v) {
        if (CommonUtils.isFastDoubleClick())
            return;

        switch (v.getId()) {
            case R.id.title_left_btn:
                if (null != mCallback) {
                    mCallback.onBtnClick(v.getId());
                } else {
                    Activity currentActivity = AppActivities.getSingleton().currentActivity();
                   /* if (currentActivity != null && currentActivity instanceof LoginActivity) {
                        currentActivity.finish();
                    }*/
//                    if (AppActivities.getSingleton().isStackActivity(UserHelpActivity.class.getSimpleName()) && !AppActivities.getSingleton().isStackActivity(MainActivity.class.getSimpleName())) {
//                        ActivityManager.showMainTab(currentActivity, ConfigType.TAB_HOME_TAG, 0);
//                        return;
//                    }
                    Logger.e(String.valueOf(getContext()));
                    ((Activity) getContext()).finish();
                }
                break;
            case R.id.title_left_btn2: {
                if (null != mCallback) {
                    mCallback.onBtnClick(v.getId());
                }
                break;
            }
            case R.id.title_right_btn:
                if (null != mCallback) {
                    mCallback.onBtnClick(v.getId());
                }
                break;
            case R.id.title_right_btn2:
                if (null != mCallback) {
                    mCallback.onBtnClick(v.getId());
                }
                break;
            default:
                break;
        }

    }

    public ToggleSwitchView getSwitchView() {
        return mSwitchView;
    }
}
