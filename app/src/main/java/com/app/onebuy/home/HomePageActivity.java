package com.app.onebuy.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.onebuy.R;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.BaseListBean;
import com.app.onebuy.bean.GoodsTypeBean;
import com.app.onebuy.bean.GoodsTypeListBean;
import com.app.onebuy.bean.HomePageListBean;
import com.app.onebuy.home.goods.type.GoodsTypeActivity;
import com.app.onebuy.home.goods.info.GoodsInfoActivity;
import com.app.onebuy.login.LoginActivity;
import com.app.onebuy.news.NewsListActivity;
import com.app.onebuy.other.AppSearchActivity;
import com.app.onebuy.other.WebViewX5Activity;
import com.app.onebuy.user.ChargeActivity;
import com.app.onebuy.view.DividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.loopj.android.http.RequestParams;
import com.sunfusheng.marqueeview.MarqueeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import my.ActivityTool;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

/**
 * Created by otis on 2018/4/11.
 */

public class HomePageActivity extends HomeBaseActivity {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.marqueeView)
    MarqueeView marqueeView;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_goods_type)
    LinearLayout mLlGoodsType;
    @BindView(R.id.ll_recharge)
    LinearLayout mLlRecharge;
    @BindView(R.id.ll_help_circle)
    LinearLayout mLlHelpCircle;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appbarlayout;

    BaseQuickAdapter adapter;
    private GoodsTypeListBean mListBean = new GoodsTypeListBean();
    private boolean isRefresh = false;

    private int refreshPosition;
    public static final int ALL_REFRESH = 90;
    public static final int ITEM_REFRESH = 89;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_home_page);
        super.initViews();
        setTitle("" + getString(R.string.home_page_titile));
        setTitleRightButton(R.mipmap.search_icon, new View.OnClickListener() {
            @Override
            public void onClick(View v) {ActivityTool.skipActivityForResult(mContext, AppSearchActivity.class,ALL_REFRESH);}
        });

        appbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    mSwipeRefreshLayout.setEnabled(true);
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mSwipeRefreshLayout.setEnabled(false);
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mSwipeRefreshLayout.setEnabled(false);
                }
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(R.color.base_blue, R.color.base_text_green);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mListBean.refresh();
                getList();
            }
        });
        initRecyclerView();
    }

    void initRecyclerView() {
        adapter = new GoodsAdapter(R.layout.adapter_goods_item, mListBean.getList());
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isRefresh = false;
                getGoodsList();
            }
        }, mRecyclerView);
        adapter.setEmptyView(R.layout.base_refresh_view, (ViewGroup) mRecyclerView.getParent());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                refreshPosition = position;
                List<GoodsTypeBean> list= adapter.getData();
                GoodsInfoActivity.toDetail(mContext, Integer.valueOf( list.get(position).getGoods_id()),Integer.valueOf(-1));
            }
        });
        mRecyclerView.setFocusable(false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.BOTH_SET, 10, getResources().getColor(R.color.home_base_gray)));
        //垂直+水平分割线
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Log.d("HomePageActivity", "initData");
        getList();
    }

    private void setBannerView(final List<com.app.onebuy.bean.Banner> bannerList) {
        ArrayList<String> listImgs = new ArrayList<>();
        for (com.app.onebuy.bean.Banner bean : bannerList) {
            listImgs.add(bean.getImages_url());
        }
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(listImgs);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(null);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                String url = bannerList.get(position).getOpen_content();
                if(!TextUtils.isEmpty(url)) WebViewX5Activity.toWebView(mContext,url,bannerList.get(position).getTitle());
            }
        });

    }

    private void setMarqueeView(List<String> marqueeList) {
        // 在代码里设置自己的动画
        marqueeView.startWithList(marqueeList, R.anim.anim_bottom_in, R.anim.anim_top_out);
    }

    private void getList() {
        showProgress();
        RequestParams params = new RequestParams();
        HttpRestClient.get(Constants.URL_HOME_PAGE, params, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {
                        HomePageListBean bean = (HomePageListBean) result;
                        setBannerView(bean.getBanner());
                        setMarqueeView(bean.getLottery_list());
                        isRefresh = true;
                        getGoodsList();
                    }

                    @Override
                    public void onFinish(int httpWhat) {
                    }
                },
                0, HomePageListBean.class);
    }

    private void getGoodsList() {
        RequestParams params = new RequestParams();
        params.put(BaseListBean.PAGE_NAME, mListBean.getCurrentPage()+1);
        params.put(BaseListBean.PAGE_SIZE_NAME, BaseListBean.PAGE_SIZE);
        HttpRestClient.get(Constants.URL_GOOD_LIST, params, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {
                        mListBean = (GoodsTypeListBean) result;
                        setData(mListBean.getList());
                    }

                    @Override
                    public void onFinish(int httpWhat) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        dismissProgress();
                    }
                },
                0, GoodsTypeListBean.class);
    }

    private void setData(List data) {
        int a =adapter.getData().size();
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            adapter.setNewData(data);
        } else {
            if (size > 0) {
                adapter.addData(data);
            }
        }
        if (size < BaseListBean.PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
//            adapter.loadMoreEnd(isRefresh);
            adapter.setEnableLoadMore(false);
//            Toast.makeText(this, "" + getString(R.string.loading_done), Toast.LENGTH_SHORT).show();    //没有更多数据提示
        } else {
            adapter.loadMoreComplete();
        }
    }

    @OnClick(R.id.ll_help_circle)
    void helpcenter() {
        NewsListActivity.toNewsList(mContext, NewsListActivity.MARK_HELP);
    }

    @OnClick(R.id.ll_goods_type)
    public void goodsType(View view) {
        ActivityTool.skipActivityForResult(mContext,GoodsTypeActivity.class,ALL_REFRESH);
    }

    final int REQUEST_LOGIN = 11;

    @OnClick(R.id.ll_recharge)
    public void recharge(View view) {
        if (!LoginActivity.toLoginIfNotLogin(mContext, REQUEST_LOGIN)) return;
        ActivityTool.skipActivity(mContext, ChargeActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ALL_REFRESH:
                mListBean.refresh();
                getList();
                break;
            case ITEM_REFRESH:
                adapter.notifyItemChanged(refreshPosition);
                break;
        }
    }
}
