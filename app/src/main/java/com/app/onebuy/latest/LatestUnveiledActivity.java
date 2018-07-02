package com.app.onebuy.latest;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.bean.CommodityListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by otis on 2018/4/19.
 */

public class LatestUnveiledActivity extends BasisActivity implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{

    BaseQuickAdapter adapter;
    private List<CommodityListBean> beanList = new ArrayList<>();
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.rv_only)
    RecyclerView mRecyclerView;
    private int delayMillis = 1000;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.fragment_only_recycler_view);
        //创建布局管理
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new LatestUnveiledAdapter(R.layout.activity_latest_unveiled_adapter, beanList);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMoreRequested() {

    }
}
