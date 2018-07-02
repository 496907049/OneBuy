package com.app.onebuy.basis;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.HashMap;

/**
 * @作者 suncco
 * @备注
 */
public class MyViewPagerAdapter extends FragmentStatePagerAdapter {

	Fragment[] mListBean;
	HashMap<Integer, Fragment> mFragments;
//	private String[] titles;

	public MyViewPagerAdapter(FragmentManager fm, Fragment[] list) {
		super(fm);
		this.mListBean = list;
		mFragments = new HashMap<Integer, Fragment>();
//		this.titles = titles;
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		Fragment f = mListBean[position];
		mFragments.put(position, f);
		return f;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		super.destroyItem(container, position, object);
		mFragments.remove(position);
	}

	public Fragment getFragment(int position) {
		return mFragments.get(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListBean == null ? 0 : mListBean.length;
	}

//	@Override
//	public CharSequence getPageTitle(int position) {
//		return titles[position];
//	}

}
