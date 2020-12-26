package com.BaseApp.ui;

import android.Manifest;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.BaseApp.Common.AppActivities;
import com.BaseApp.Common.views.CommonTitleBar;
import com.BaseApp.R;
import com.BaseApp.Util.FragmentsManagerUtil;
import com.BaseApp.Common.views.LoadingDialog;
import com.BaseApp.Common.Logger;
import com.BaseApp.Util.PermissionUtil;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity extends AppCompatActivity {

    protected final String TAG = this.getClass().getName();
    protected FragmentManager mFragmentManager = null;
    protected FragmentTransaction mFragmentTransaction = null;
    public String mCurrentTag = "";
    protected PushMsgTabFragment mCurrentFragment = null;
    public CommonTitleBar mTitleBar = null;

    /**
     * 添加等待框提示
     */
    private LoadingDialog mProgress;

    private CompositeDisposable mCompositeDisposable;
    public PermissionUtil mAccessStoragePermission;
    protected int extendActLayout=-1;//子activity调用setContentView


    @Override
    protected void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTransparentBar(true);
        if(getLayoutView()!=extendActLayout){
            setContentView(getLayoutView());
        }
        ButterKnife.bind(this);
        mTitleBar = (CommonTitleBar) findViewById(R.id.common_title_bar);

        Logger.e("class on create " + this.getClass().getSimpleName());
        mFragmentManager = this.getSupportFragmentManager();
        FragmentsManagerUtil.instance().createStackByActivity(this);

        AppActivities.getSingleton().pushActivity(this);
        requestPermission(null);
    }

    public void registerRxBus() {

    }

    /**
     * @return 返回当前界面加载的布局id
     */
    protected abstract int getLayoutView();

    /**
     * 初始化当前界面的控件
     */
    protected abstract void initLayoutView();

    /**
     * 初始化界面控件的值和条件
     */
    protected abstract void initViewData();

    /**
     * 权限申请
     */
    public void requestPermission(final PermissionUtil.OnRequestPermissionResult result) {
        // 初始化 文件读写敏感权限，复制指标数据库
        mAccessStoragePermission = new PermissionUtil();
        mAccessStoragePermission.requestPermission(BaseActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE, 1, new PermissionUtil.OnRequestPermissionResult() {
            @Override
            public void onGranted() {
                if (null != result) {
                    result.onGranted();
                } else {
                    registerRxBus();
                    initLayoutView();
                    initViewData();
                }
            }

            @Override
            public void onRefused() {
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mAccessStoragePermission != null && requestCode == 1) {
            mAccessStoragePermission.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    /**
     * 显示加载圈
     */
    public void showLoading() {
        if (null == mProgress || !mProgress.isShowing()) {
            mProgress = LoadingDialog.show(this, "", "");
            mProgress.setCancelable(false);
        }
    }
    public void setLoadingCancelable(boolean cancelable){
        if(mProgress!=null){
            mProgress.setCancelable(cancelable);
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


    /**
     * 显示指定的fragment
     *
     * @param mBaseFragment
     */
    public void showFragment(PushMsgTabFragment mBaseFragment) {
        if (mFragmentManager == null) {
            mFragmentManager = this.getSupportFragmentManager();
        }
        mFragmentTransaction = mFragmentManager.beginTransaction();

        // 若当前fragment不存在则添加
        if (null == mFragmentManager.findFragmentByTag(mCurrentTag)) {
            mFragmentTransaction.add(R.id.contentFragment, mBaseFragment, mCurrentTag);
        }
        if (null != mCurrentFragment) {
            mFragmentTransaction.hide(mCurrentFragment);
        }
        mFragmentTransaction.show(mBaseFragment);
        mFragmentTransaction.commitAllowingStateLoss();
        mCurrentFragment = mBaseFragment;
        FragmentsManagerUtil.instance().setCurrentFragment(this, mCurrentFragment);
    }

    /**
     * RxBus注册事件管理类
     *
     * @param subscription
     */
    public void bindSubscription(Disposable subscription) {
        if (this.mCompositeDisposable == null) {
            this.mCompositeDisposable = new CompositeDisposable();
        }
        this.mCompositeDisposable.add(subscription);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.e("class on onResume " + this.getClass().getSimpleName());
        // 屏幕常亮设置
        /*
        if (GTConfig.instance().getBooleanValue(GTConfig.PREF_KEEP_SCREEN, false)) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.e("class on onStop " + this.getClass().getSimpleName());
    }

    @Override
    public void finish() {
        super.finish();
        Logger.e("class on finish " + this.getClass().getSimpleName());
        hideSoft();
        /* Pop Activity off stack */
        AppActivities.getSingleton().popActivity(this);
        if (this.mCompositeDisposable != null) {
            this.mCompositeDisposable.clear();
        }
        FragmentsManagerUtil.instance().removeAllPushMsgTabFragment(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.e("class on onDestroy " + this.getClass().getSimpleName());
        if (this.mCompositeDisposable != null) {
            this.mCompositeDisposable.clear();
        }

        if (null != mFragmentManager) {
            mFragmentManager = null;
        }

        if (null != mFragmentTransaction) {
            mFragmentTransaction = null;
        }
        if (null != LoadingDialog.instance() && LoadingDialog.instance().getOwnerActivity() != null && !LoadingDialog.instance().getOwnerActivity().isFinishing()) {
            LoadingDialog.instance().dismiss();
        }
        AppActivities.getSingleton().popActivity(this);
        FragmentsManagerUtil.instance().removeAllPushMsgTabFragment(this);
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoft() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getWindow().getDecorView();
        if (null != view) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
        }
    }

    /**
     * 设置透明状态栏
     * isTransparent 标识有些界面从状态栏处开始绘制ui
     */
    public void setTransparentBar(boolean isTransparent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //状态栏文字颜色设置为深色
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(option);
            if (isTransparent) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            if (isTransparent) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }
}
