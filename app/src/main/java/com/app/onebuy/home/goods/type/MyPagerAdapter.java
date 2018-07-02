package com.app.onebuy.home.goods.type;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * Created by otis on 2018/4/16.
 */

public class MyPagerAdapter extends FragmentPagerAdapter implements TabAdapter {

    private ArrayList<Fragment> mFragments;
    private List<String> mTitles;
    private FragmentManager fragmentManager;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public MyPagerAdapter(FragmentManager fm,ArrayList<Fragment> mFragments, List<String> mTitles) {
        super(fm);
        this.mFragments = mFragments;
        this.mTitles = mTitles;
        this.fragmentManager = fm;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        fragment = mFragments.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("id",""+position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public ITabView.TabBadge getBadge(int position) {
        //            if (position == 5) return new TabView.TabBadge.Builder().setBadgeNumber(666)
//                    .setExactMode(true)
//                    .setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
//                        @Override
//                        public void onDragStateChanged(int dragState, Badge badge, View targetView) {
//                        }
//                    }).build();
        return null;
    }

    @Override
    public ITabView.TabIcon getIcon(int i) {
        return null;
    }

    @Override
    public ITabView.TabTitle getTitle(int position) {
        return new TabView.TabTitle.Builder()
                .setContent(mTitles.get(position))
                .setTextColor(Color.RED,Color.BLACK)
                .build();
    }

    @Override
    public int getBackground(int i) {
        return 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment)super.instantiateItem(container,position);
        fragmentManager.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        Fragment fragment = mFragments.get(position);
        fragmentManager.beginTransaction().hide(fragment).commit();
    }

}
