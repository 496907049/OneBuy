package com.app.onebuy.home.goods.type;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.GoodsTypeBean;
import com.app.onebuy.bean.GoodsTypeListBean;
import com.app.onebuy.home.HomePageActivity;
import com.app.onebuy.other.AppSearchActivity;
import com.loopj.android.http.RequestParams;

import my.ActivityTool;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import q.rorbin.verticaltablayout.VerticalTabLayout;

/**
 * Created by otis on 2018/4/12.
 */

public class GoodsTypeActivity extends BasisActivity {

    @BindView(R.id.viewpager)
    MyViewPager viewpager;
    @BindView(R.id.tablayout)
    VerticalTabLayout tabLayout;

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.activity_goods_type);
        setTitle("" + getString(R.string.classification));
        setTitleRightButton(R.mipmap.search_icon, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityTool.skipActivity(mContext, AppSearchActivity.class);
            }
        });

        setTitleLeftButton(R.drawable.base_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(HomePageActivity.ALL_REFRESH);
                finish();
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        getInterfaceData();
    }

    private void getInterfaceData() {
        RequestParams params = new RequestParams();
        showProgress();
        HttpRestClient.get(Constants.URL_GOOD_TYPE, params, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {
                        GoodsTypeListBean bean = (GoodsTypeListBean) result;
                        setView(bean.getList());
                        dismissProgress();
                    }

                    @Override
                    public void onFinish(int httpWhat) {
                    }
                },
                0, GoodsTypeListBean.class);

    }

    private void setView(List<GoodsTypeBean> beanList) {
        List<String> titles = new ArrayList<>();
        for (GoodsTypeBean bean : beanList) {
            titles.add(bean.getTitle());
            mFragments.add(GoodsTypeFragment.getInstance(bean.getCategory_id()));
        }

        viewpager.setAdapter(new MyPagerAdapter(mContext.getSupportFragmentManager(), mFragments, titles));
        viewpager.setOffscreenPageLimit(titles.size() - 1);
        tabLayout.setupWithViewPager(viewpager);
    }

}
