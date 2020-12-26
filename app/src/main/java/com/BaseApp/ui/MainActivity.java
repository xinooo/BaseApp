package com.BaseApp.ui;

import android.os.Bundle;
import android.view.KeyEvent;

import com.BaseApp.Common.Logger;
import com.BaseApp.Common.model.ConfigType;
import com.BaseApp.Common.model.DataItemDetail;
import com.BaseApp.Common.views.MainBottomTabView;
import com.BaseApp.Common.views.RecyclerClickListener;
import com.BaseApp.R;
import com.BaseApp.Util.FragmentsManagerUtil;
import com.BaseApp.ui.Dialog.PopupDoubleBtnDialog;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottom_tab)
    MainBottomTabView mHomeTabView = null; // 底部tab控件

    private TestFragment testFragment;
    private TestFragment2 testFragment2;
    private PopupDoubleBtnDialog mExitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initLayoutView() {
        mCurrentTag = ConfigType.TAB_1;
    }

    @Override
    protected void initViewData() {
        homeTabClickListener();
        setTabFragment(mCurrentTag, "");
    }

    public void homeTabClickListener() {
        mHomeTabView.setRecyclerClickListener(new RecyclerClickListener() {
            @Override
            public void onClick(int position, DataItemDetail itemDetail) {

                if (itemDetail.getString(ConfigType.CONFIG_TYPE_KEY_TAG).equals(ConfigType.TAB_1)) {
                    showTestFragment();
                }else if (itemDetail.getString(ConfigType.CONFIG_TYPE_KEY_TAG).equals(ConfigType.TAB_2)) {
                    showTestFragment2();
                }

                mHomeTabView.setSelectPositon(position);
            }
        });
    }

    /**
     * 根据当前tag显示fragment
     */
    public void setTabFragment(String tag, String subTag) {
        setTabFragment(tag, subTag, 0);
    }

    /**
     * 根据当前tag显示fragment
     */
    public void setTabFragment(String tag, String subTag, int findPostion) {
        mCurrentTag = tag;
        if (mCurrentTag.equals(ConfigType.TAB_1)) {
            showTestFragment();
        }else if (mCurrentTag.equals(ConfigType.TAB_2)){
            showTestFragment2();
        }
    }

    /**
     * 删除所有的fragment
     */
    public void removeAllFragment(boolean isAll, boolean isDelQuote) {
        mFragmentTransaction = mFragmentManager.beginTransaction();


        if (null != testFragment) {
            mFragmentTransaction.remove(testFragment);
            FragmentsManagerUtil.instance().removeFragmentbyTag(this, ConfigType.TAB_1);
            testFragment = null;
        }
        if (null != testFragment2) {
            mFragmentTransaction.remove(testFragment2);
            FragmentsManagerUtil.instance().removeFragmentbyTag(this, ConfigType.TAB_2);
            testFragment2 = null;
        }


        mFragmentTransaction.commitAllowingStateLoss();
    }


    /**
     * 删除所有的fragment,除嵌套首页
     */
    public void removeFragment() {
        mFragmentTransaction = mFragmentManager.beginTransaction();

        if (null != testFragment) {
            mFragmentTransaction.remove(testFragment);
            FragmentsManagerUtil.instance().removeFragmentbyTag(this, ConfigType.TAB_1);
            testFragment = null;
        }
        if (null != testFragment2) {
            mFragmentTransaction.remove(testFragment2);
            FragmentsManagerUtil.instance().removeFragmentbyTag(this, ConfigType.TAB_2);
            testFragment2 = null;
        }

        mFragmentTransaction.commitAllowingStateLoss();
    }


    public void showTestFragment() {
        mCurrentTag = ConfigType.TAB_1;
        testFragment = (TestFragment) FragmentsManagerUtil.instance().getFragmentbyTag(MainActivity.this, mCurrentTag);
        if (null == testFragment) {
            testFragment = TestFragment.newInstance();
            FragmentsManagerUtil.instance().addPushMsgTabFragment(MainActivity.this, testFragment, mCurrentTag);
        }
        showFragment(testFragment);
    }

    public void showTestFragment2() {
        mCurrentTag = ConfigType.TAB_2;
        testFragment2 = (TestFragment2) FragmentsManagerUtil.instance().getFragmentbyTag(MainActivity.this, mCurrentTag);
        if (null == testFragment2) {
            testFragment2 = TestFragment2.newInstance();
            FragmentsManagerUtil.instance().addPushMsgTabFragment(MainActivity.this, testFragment2, mCurrentTag);
        }
        showFragment(testFragment2);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Logger.e("onKeyDown = " + keyCode);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showExitDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showExitDialog() {
        if (mExitDialog == null)
            mExitDialog = PopupDoubleBtnDialog.getAppExit(this);

        if (!mExitDialog.isShowing())
            mExitDialog.show();
    }

}
