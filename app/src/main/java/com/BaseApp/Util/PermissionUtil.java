package com.BaseApp.Util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

/**
 * 对于涉及到危险权限的操作：
 * 一 是检查有无该权限，如有，直接执行操作; 如果没有， 则一是检查权限是否被用户禁用并进行提示， 二是进行权限请求，  并在 Activity 或 Fragment中 重写onRequestPermissionsResult 对权限请求的结果进行处理
 * <p>
 * 用法：
 * 每个该类的实例仅用于一次权限请求， 1. 调用 requestPermission(Activity activity, String permission, int requestCode, OnRequestPermissionResult result) 请求权限，
 * 2. 在 Activity的 回调方法 public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)中 调用 本类的回调 public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
 * 3. OnRequestPermissionResult 用于处理 请求结果。如果应用本来具有权限或者用户授权，都会走onGranted方法，否则走onRefused()方法
 * <p>
 */

public class PermissionUtil {

    private int mRequestCode = -1;
    //private String mPermission = "";
    private OnRequestPermissionResult mResult = null;

    /**
     * 是否具有该权限
     */
    private boolean hasPermission(Context context, String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission(Activity activity, String permission, int requestCode, OnRequestPermissionResult result) {
        mRequestCode = requestCode;
        //this.mPermission = permission;
        this.mResult = result;
        if (!hasPermission(activity, permission)) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
        } else {
            if (mResult != null) {
                mResult.onGranted();
            }
        }

    }

    public void requestPermission(Fragment fragment, String permission, int requestCode, OnRequestPermissionResult result) {
        if (fragment == null || fragment.getActivity() == null) {
            return;
        }
        mRequestCode = requestCode;
        //this.mPermission = permission;
        this.mResult = result;
        if (!hasPermission(fragment.getActivity(), permission)) {
            fragment.requestPermissions(new String[]{permission}, requestCode);
        } else {
            if (mResult != null) {
                mResult.onGranted();
            }
        }

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mResult == null || permissions.length <= 0) {
            return;
        }
        if (requestCode == mRequestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mResult.onGranted();
            } else {
                mResult.onRefused();
            }
        }
    }

    public interface OnRequestPermissionResult {
        void onGranted();

        void onRefused();
    }
}
