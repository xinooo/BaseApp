package com.BookKeeping.note;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.BaseApp.Common.views.BtnClickListener;
import com.BaseApp.Common.views.CommonTitleBar;
import com.BaseApp.R;
import com.BaseApp.greendao.DBService;
import com.BaseApp.greendao.NoteDataHelper;
import com.BaseApp.ui.PushMsgTabFragment;
import com.gwtsz.android.rxbus.RxBus;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class ANoteFragment extends PushMsgTabFragment {


    @BindView(R.id.titleBar)
    CommonTitleBar titleBar;

    @BindView(R.id.note_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_add)
    TextView tv_add;

    private DBService dbService = new DBService();
    private List<NoteBean> notelist;
    private NoteAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_a_note;
    }

    public static ANoteFragment newInstance() {
        return new ANoteFragment();
    }

    @Override
    protected void initLayoutView() {
        ButterKnife.bind(this, mRootView);
        dbService.init(getContext());
        titleBar.setAppTitle("记事本");
        titleBar.setTitleTextColor(R.color.color_e);
        titleBar.setTitleBarBackground(R.drawable.a_view_titlebar_bg);
        titleBar.setRightResource(R.mipmap.create_icon, 0);
        titleBar.setRighttBtnColorValue(0,0);
        titleBar.getLeftBtn().setVisibility(View.GONE);

        titleBar.setBtnClickListener(new BtnClickListener() {
            @Override
            public void onBtnClick(int viewID) {
                switch (viewID) {
                    case R.id.title_right_btn:
                        newNote();
                        break;
                }
            }
        });
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newNote();
            }
        });
    }

    private void newNote(){
        String now = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        Bundle bundle = new Bundle();
        bundle.putLong("ID", 0);
        bundle.putString("DATE", now);
        bundle.putString("NOTE", "");
        bundle.putString("PHOTO_PATH", "");
        Intent intent = new Intent(getActivity(), NewNoteActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void initViewData() {
        notelist = NoteDataHelper.getNoteData(dbService);
        Collections.reverse(notelist);
        adapter = new NoteAdapter(getContext(), notelist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setVisibility(notelist.size() != 0? View.VISIBLE : View.GONE);
    }

    public void registerRxBus(){
        Disposable messageRegister = RxBus.getInstance().register("RefreshNoteData", Boolean.class).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>(){

                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Boolean isRefresh) throws Exception {
                        dbService.getDaoSession().clear();
                        initViewData();
                    }
                });

        bindSubscription(messageRegister);
    }
}
