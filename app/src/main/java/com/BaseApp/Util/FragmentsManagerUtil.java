package com.BaseApp.Util;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.BaseApp.ui.PushMsgTabFragment;

import java.util.HashMap;


/*
 * Fragments 管理类
 */
public class FragmentsManagerUtil {
	private final HashMap<String, HashMap<String, PushMsgTabFragment>> fragmentStack= new HashMap();
	private static FragmentsManagerUtil instance = null;

	public synchronized static FragmentsManagerUtil instance(){
		if (instance == null){
			instance = new FragmentsManagerUtil();
		}
		return instance;
	}
	
	/**
	 * 根据activity创建关注PushMsgTabFragment集合对象
	 * 
	 * @param context
	 */
	public void createStackByActivity(Activity context) {
		String key = context.getClass().getSimpleName()+context.hashCode();
		if (!fragmentStack.containsKey(key)) {
			fragmentStack.put(key, new HashMap<String, PushMsgTabFragment>());
		}
	}

	/**
	 * 添加PushMsgTabFragment到相应Activity的集合
	 * 
	 * @param context
	 */
	public void addPushMsgTabFragment(Activity context, PushMsgTabFragment PushMsgTabFragment, String tag) {
		String key = context.getClass().getSimpleName()+context.hashCode();
		if (fragmentStack.containsKey(key)) {
			HashMap<String, PushMsgTabFragment> fragments = fragmentStack.get(key);
			if (null != fragments) {
				fragments.put(tag, PushMsgTabFragment);
			}
		}
	}
    
    /**
     * 根据指定tag移除PushMsgTabFragment对象
     * @param context
     * @param tag
     * @return
     */
    public boolean removeFragmentbyTag(Activity context, String tag){
		String key = context.getClass().getSimpleName()+context.hashCode();
    	if (fragmentStack.containsKey(key)) {
			HashMap<String, PushMsgTabFragment> fragments = fragmentStack.get(key);
			if (null != fragments && fragments.containsKey(tag)) {
				fragments.remove(tag);
				return true;
			}
		}
    	return false;
    }

	/**
	 * 根据指定tag获取PushMsgTabFragment对象
	 * @param context
	 * @param tag
	 * @return
	 */
	public PushMsgTabFragment getFragmentbyTag(Activity context, String tag){
		PushMsgTabFragment mFragment = null;
		String key = context.getClass().getSimpleName()+context.hashCode();
		if (fragmentStack.containsKey(key)) {
			HashMap<String, PushMsgTabFragment> fragments = fragmentStack.get(key);
			if (null != fragments && fragments.containsKey(tag)) {
				mFragment = fragments.get(tag);
			}
		}

		return mFragment;
	}
	
	public int getFragmentSize(Activity context){
		String key = context.getClass().getSimpleName()+context.hashCode();
		if (fragmentStack.containsKey(key)) {
			HashMap<String, PushMsgTabFragment> fragments = fragmentStack.get(key);
			if (null != fragments) {
				return fragments.size();
			}
		}
		
		return 0;
	}
	
	 /**
     * 移除对象activity中的所有PushMsgTabFragment
     * @param context
     */
    public void removeAllPushMsgTabFragment(Activity context){
		String key = context.getClass().getSimpleName()+context.hashCode();
    	if (fragmentStack.containsKey(key)) {
			HashMap<String, PushMsgTabFragment> fragments = fragmentStack.get(key);
			fragments.clear();
		}
    }
	
	/**
	 * 设置当前活动Fragment
	 * 
	 * @param PushMsgTabFragment
	 */
	public void setCurrentFragment(Activity context, PushMsgTabFragment PushMsgTabFragment) {
		String key = context.getClass().getSimpleName()+context.hashCode();
		if (fragmentStack.containsKey(key)) {
			HashMap<String, PushMsgTabFragment> fragments = fragmentStack.get(key);
			if (null != fragments) {
				fragments.put("currentFragment", PushMsgTabFragment);
			}
		}
	}
	
	/**
	 * 获取当前Fragment
	 * @return
	 */
	public PushMsgTabFragment getCurrentFragment(Activity context){
		String key = context.getClass().getSimpleName()+context.hashCode();
		PushMsgTabFragment mFragment = null;
    	if (fragmentStack.containsKey(key)) {
			HashMap<String, PushMsgTabFragment> fragments = fragmentStack.get(key);
			if (null != fragments && fragments.containsKey("currentFragment")) {
				mFragment = fragments.get("currentFragment");
			}
		}
    	return mFragment;
	}
	
	/**
	 * 移除系统中的指定的Fragment
	 * @param tag
	 */
	public void removeSysFragment(String tag, FragmentManager fm){
		if(null == fm){
			return;
		}
		FragmentTransaction ft = fm.beginTransaction();
		Fragment fragment = fm.findFragmentByTag(tag);
		if(null != fragment){
			ft.remove(fragment);
		}
		ft.commitAllowingStateLoss();
	}

}
