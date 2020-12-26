package com.BaseApp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.BaseApp.Common.views.CommonTitleBar;
import com.BaseApp.R;
import com.gwtsz.android.rxbus.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestFragment extends PushMsgTabFragment {

    @BindView(R.id.titleBar)
    CommonTitleBar titleBar;

    @BindView(R.id.tv1)
    TextView tv1;


    public static TestFragment newInstance() {
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    protected void initLayoutView() {
        ButterKnife.bind(this, mRootView);
        titleBar.setAppTitle("记事本");
        titleBar.setTitleTextColor(R.color.color_e);
        titleBar.setTitleBarBackground(R.drawable.a_view_titlebar_bg);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxBus.getInstance().postSticky("TESTTEST", "HAHAHAHA");
            }
        });
    }

    @Override
    protected void initViewData() {

    }

    @Override
    public void registerRxBus() {
        super.registerRxBus();
    }
}
