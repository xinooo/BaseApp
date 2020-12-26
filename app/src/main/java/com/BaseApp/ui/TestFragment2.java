package com.BaseApp.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.BaseApp.Common.Logger;
import com.BaseApp.Common.views.CommonTitleBar;
import com.BaseApp.R;
import com.gwtsz.android.rxbus.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class TestFragment2 extends PushMsgTabFragment {

    @BindView(R.id.titleBar)
    CommonTitleBar titleBar;

    @BindView(R.id.tv1)
    TextView tv1;


    public static TestFragment2 newInstance() {
        TestFragment2 fragment = new TestFragment2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test2;
    }

    @Override
    protected void initLayoutView() {
        ButterKnife.bind(this, mRootView);
        titleBar.setAppTitle("记事本");
        titleBar.setTitleTextColor(R.color.color_e);
        titleBar.setTitleBarBackground(R.drawable.a_view_titlebar_bg);
    }

    @Override
    protected void initViewData() {

    }

    @Override
    public void registerRxBus() {
        super.registerRxBus();
        Disposable messageRegister = RxBus.getInstance().registertoObservableSticky("TESTTEST", String.class).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Logger.e(s);
                    }
                });

        bindSubscription(messageRegister);
    }
}
