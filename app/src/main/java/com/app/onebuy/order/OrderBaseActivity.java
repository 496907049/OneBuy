package com.app.onebuy.order;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.basis.MyViewPagerAdapter;
import com.app.onebuy.bean.OrderListData;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;

public class OrderBaseActivity extends BasisActivity {

    @BindView(R.id.tablayout)
    CommonTabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    MyViewPagerAdapter myViewPagerAdapter;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    public void initViews() {
        setDefautTrans(false);
        setContentView(R.layout.user_join_records_activity);
        super.initViews();
        setTitle(R.string.user_cyjl);
        setTitleLeftButton(null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setView();
    }

    private  void setView(){

        Fragment[] fms = new Fragment[3];
        fms[0] = OrderListFragment.newInstance(OrderListData.STATUS_PARAMS_ALL);
        fms[1] = OrderListFragment.newInstance(OrderListData.STATUS_PARAMS_DOING);
        fms[2] = OrderListFragment.newInstance(OrderListData.STATUS_PARAMS_FINISH);
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(),fms);
        viewpager.setAdapter(myViewPagerAdapter);
        viewpager.setOffscreenPageLimit(3);
        mTabEntities = new ArrayList<>();
        mTabEntities.add(new TabEntity(getString(R.string.all),0,0));
        mTabEntities.add(new TabEntity(getString(R.string.doing),0,0));
        mTabEntities.add(new TabEntity(getString(R.string.unveiled),0,0));

        tablayout.setTabData(mTabEntities);

        tablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tablayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewpager.setCurrentItem(0);
    }

    public class TabEntity implements CustomTabEntity {
        public String title;
        public int selectedIcon;
        public int unSelectedIcon;

        public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
            this.title = title;
            this.selectedIcon = selectedIcon;
            this.unSelectedIcon = unSelectedIcon;
        }

        @Override
        public String getTabTitle() {
            return title;
        }

        @Override
        public int getTabSelectedIcon() {
            return selectedIcon;
        }

        @Override
        public int getTabUnselectedIcon() {
            return unSelectedIcon;
        }
    }

}
