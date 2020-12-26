package com.BookKeeping.note;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BaseApp.Common.AppMain;
import com.BaseApp.Common.views.CommonTitleBar;
import com.BaseApp.R;
import com.BaseApp.Util.ImageUtil;
import com.BaseApp.Util.PermissionUtil;
import com.BaseApp.Util.ToastUtil;
import com.BaseApp.greendao.DBService;
import com.BaseApp.greendao.NoteDataHelper;
import com.BaseApp.ui.BaseActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.gwtsz.android.rxbus.RxBus;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import butterknife.BindView;

public class NewNoteActivity extends BaseActivity {

    @BindView(R.id.titleBar)
    CommonTitleBar titleBar;

    @BindView(R.id.tv_date)
    TextView tv_date;

    @BindView(R.id.et_note)
    EditText et_note;

    @BindView(R.id.iv_photo)
    ImageView iv_photo;

    @BindView(R.id.photo_layout)
    RelativeLayout photo_layout;

    @BindView(R.id.delete_photo)
    ImageView delete_photo;

    @BindView(R.id.tv_clear)
    TextView tv_clear;

    @BindView(R.id.tv_photo)
    TextView tv_photo;

    @BindView(R.id.tv_submit)
    TextView tv_submit;

    private String takePhotoPath, note, date;
    private long _id;
    private int REQUEST_CAMERS = 0;
    private PermissionUtil mAccessCameraPermission;
    public int REQUEST_PERMISSION_ACCESS_CAMERA = 5;
    private DBService dbService = new DBService();

    @Override
    protected int getLayoutView() {
        return R.layout.activity_newnote;
    }

    @Override
    protected void initLayoutView() {
        dbService.init(this);
        titleBar.setAppTitle(getString(R.string.note));
        titleBar.setTitleTextColor(R.color.color_e);
        titleBar.setTitleBarBackground(R.drawable.a_view_titlebar_bg);
        titleBar.setLeftResource(R.mipmap.arrow_icon, 0);
        titleBar.setLeftBtnColorValue(0,0);
        photo_layout.setVisibility(View.GONE);

        delete_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotoPath = "";
                photo_layout.setVisibility(View.GONE);
            }
        });
        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteDataHelper.delete(dbService,date);
                ToastUtil.showMessageOnCenter(getString(R.string.note_delete));
                RxBus.getInstance().post("RefreshNoteData", true);
                finish();
            }
        });
        tv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCameraPermission();
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showMessageOnCenter(getString(R.string.note_save_success));
                if (NoteDataHelper.exsit(dbService,date)){
                    NoteDataHelper.alter(dbService,_id,date,et_note.getText().toString(),takePhotoPath);
                }else {
                    NoteDataHelper.save(dbService,date,et_note.getText().toString(),takePhotoPath);
                }
                RxBus.getInstance().post("RefreshNoteData", true);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == REQUEST_CAMERS){
                if (TextUtils.isEmpty(takePhotoPath)) {
                    return;
                }
                showIMG(takePhotoPath);
                photo_layout.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    protected void initViewData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        _id = bundle.getLong("ID");
        date = bundle.getString("DATE");
        note = bundle.getString("NOTE");
        takePhotoPath = bundle.getString("PHOTO_PATH");

        tv_date.setText(date.substring(0,10));
        et_note.setText(note);
        if (!TextUtils.isEmpty(takePhotoPath)){
            showIMG(takePhotoPath);
            photo_layout.setVisibility(View.VISIBLE);
        }
    }

    public void showIMG(String path){
        Glide.with(AppMain.getApp()).load(path).asBitmap().centerCrop().into(new BitmapImageViewTarget(iv_photo) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(AppMain.getApp().getResources(), resource);
                circularBitmapDrawable.setCornerRadius(15);
                iv_photo.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    private void requestCameraPermission() {
        mAccessCameraPermission = new PermissionUtil();
        mAccessCameraPermission.requestPermission(this, Manifest.permission.CAMERA, REQUEST_PERMISSION_ACCESS_CAMERA, new PermissionUtil.OnRequestPermissionResult() {
            @Override
            public void onGranted() {
                launchCamera();
            }

            @Override
            public void onRefused() {
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mAccessCameraPermission != null) {
            mAccessCameraPermission.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void launchCamera() {
        try {
            takePhotoPath = ImageUtil.getNewPhotoPath();
            Intent mSourceIntent = ImageUtil.takeBigPicture(this, takePhotoPath);
            startActivityForResult(mSourceIntent, REQUEST_CAMERS);
        } catch (Exception ignore) {
        }
    }
}
