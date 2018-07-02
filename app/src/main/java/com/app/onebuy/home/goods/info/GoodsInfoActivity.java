package com.app.onebuy.home.goods.info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.basis.BasisApp;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.GoodsBean;
import com.app.onebuy.bean.GoodsTypeBean;
import com.app.onebuy.home.GlideImageLoader;
import com.app.onebuy.home.HomePageActivity;
import com.app.onebuy.home.goods.info.fragment.img.ImageTextInfoFragment;
import com.app.onebuy.home.goods.info.fragment.part.PartRecordFragment;
import com.app.onebuy.home.goods.info.fragment.past.PastWinnerFragment;
import com.app.onebuy.home.goods.info.fragment.show.ShowShareFragment;
import com.app.onebuy.login.LoginActivity;
import com.app.onebuy.order.OrderSubmitActivity;
import com.app.onebuy.other.PhotoScanActivity;
import com.app.onebuy.util.ShareAndAuthorizeUtil;
import com.app.onebuy.view.roundprogressbar.RxRoundProgressBar;
import com.flyco.tablayout.SlidingTabLayout;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import my.TimeUtils;
import my.ViewFindUtils;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

/**
 * Created by otis on 2018/4/13.
 */

public class GoodsInfoActivity extends BasisActivity implements View.OnClickListener {

    @BindView(R.id.banner)
    Banner banner;      //轮播图
    @BindView(R.id.nested_scroll_view)
    NestedScrollView mNestedScrollView;      //轮播图
    @BindView(R.id.ll_doing)
    LinearLayout mLlDoing;         //包含（商品描述，价值，进度条，总人次，剩余人次）
    @BindView(R.id.tv_commodity_name_doing)
    TextView mTvCommodityNameDoing;   //商品描述
    @BindView(R.id.tv_value)
    TextView mTvValue;      //价值
    @BindView(R.id.much_peso_much_people)
    TextView muchPesoPeople;      //(1比索/1人次)
    @BindView(R.id.tv_count_down_title)
    TextView mTvCountDownTitle;      //价值
    @BindView(R.id.progress)
    RxRoundProgressBar progressBar;     //进度条
    @BindView(R.id.tv_total_number)
    TextView mTvTotalNumber;        //总需人次
    @BindView(R.id.tv_remainder)
    TextView mTvRemainder;              //剩余人次
    @BindView(R.id.ll_publish)
    LinearLayout mLlPublish;       //包含（商品描述信息，期号，状态，计算详情等）
    @BindView(R.id.tv_good_name_pub)
    TextView mTvGoodsNamePub;      //商品描述信息
    @BindView(R.id.tv_no)
    TextView mTvNo;             //期号
    @BindView(R.id.tv_status)
    TextView mTvStatus;         //揭晓状态
    @BindView(R.id.tablayout)
    SlidingTabLayout mTabLayout;   //滑动栏
    @BindView(R.id.vp)
    ViewPager mViewPage;   //滑动栏
    @BindView(R.id.count_down_view)
    CountdownView countdownView;        //倒计时控件
    @BindView(R.id.btn_join)
    Button joinBtn;                     //立即参与
    @BindView(R.id.btn_go_to_next)
    Button goToNextBtn;                 //立即前往
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appbarlayout;
    @BindView(R.id.ll_end)
    LinearLayout mLlEnd;            //已揭晓Linearlayout包含下面的 数据
    @BindView(R.id.tv_lucky_star)
    TextView mTvLuckyStar;   //幸运之星
    @BindView(R.id.tv_no_end)
    TextView mTvEndNo;   //已揭晓的期号
    @BindView(R.id.tv_people_num)
    TextView mTvPeopleNum;   //已揭晓的参与次数
    @BindView(R.id.tv_open_time)
    TextView mTvOpenTime;   //已揭晓的时间
    @BindView(R.id.tv_lucky_num)
    TextView mTvLuckyNum;   //已揭晓的幸运号码
    @BindView(R.id.tv_good_name_end)
    TextView mTvGoodName;   //商品名字
    @BindView(R.id.user_icon_end)
    RoundedImageView userIconEnd;   //商品名字
    @BindView(R.id.btn_pub_cal_info)
    Button pubCalInfoBtn;   //即将揭晓的计算详情按钮
    @BindView(R.id.btn_end_cal_info)
    Button endCalInfoBtn;   //已揭晓的计算详情按钮

    public static int refreshNumber = 64;


    private MyPagerAdapter mAdapter;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();

    private int goodsId;
    private int periodId;
    private GoodsTypeBean goodsTypeBean;
    private String[] fragmentName = {"PartRecordFragment", "ImageTextInfoFragment", "PastWinnerFragment", "ShowShareFragment"};

    public static void toDetail(Activity context, int goods_id, int period_id) {
        Intent intent = new Intent(context, GoodsInfoActivity.class);
        intent.putExtra("goods_id", goods_id);
        intent.putExtra("period_id", period_id);
//        context.startActivity(intent);
        context.startActivityForResult(intent, HomePageActivity.ITEM_REFRESH);

    }

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.activity_goods_info);

        setTitle("" + getString(R.string.commodity_info));
        setTitleLeftButton(R.drawable.base_icon_back_white, this);
        setTitleRightButton(R.mipmap.share_icon, this);

        goodsId = getIntent().getIntExtra("goods_id", -1);
        periodId = getIntent().getIntExtra("period_id", -1);
        if (goodsId == -1) {
            finish();
            return;
        }

        //设置 NestedScrollView 的内容是否拉伸填充整个视图，
        //这个设置是必须的，否者我们在里面设置的ViewPager会不可见
        mNestedScrollView.setFillViewport(true);
        joinBtn.setOnClickListener(this);

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
                getList();
                String a = fragmentName[mTabLayout.getCurrentTab()];
                EventBus.getDefault().post(a);
            }
        });

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        getList();
    }

    //读取接口
    private void getList() {
        RequestParams params = new RequestParams();
        params.put("goods_id", goodsId);
        if (periodId != -1) {
            params.put("period_id", periodId);
        }
        HttpRestClient.get(Constants.URL_GOOD_DETAIL, params, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {
                        GoodsTypeBean bean = (GoodsTypeBean) result;
                        initViewPage(bean.getPeriod_info());
                        goodsTypeBean = bean;
                        setBannerView(goodsTypeBean.getImg_list());
                        switch (goodsTypeBean.getPeriod_info().getStatus()) {
                            case GoodsTypeBean.DOING:
                                initDoingView(goodsTypeBean);
                                break;
                            case GoodsTypeBean.PUBLISH:
                                initPublishView(goodsTypeBean);
                                break;
                            case GoodsTypeBean.END:
                                initEndView(goodsTypeBean);
                                break;
                        }
                    }

                    @Override
                    public void onFinish(int httpWhat) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                },
                0, GoodsTypeBean.class);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.base_btn_back:
                setResult(HomePageActivity.ITEM_REFRESH);
                finish();
                break;
            case R.id.base_btn_right:
                ShareAndAuthorizeUtil.showBroadView(GoodsInfoActivity.this);
                break;
            case R.id.btn_join:
                if (!LoginActivity.toLoginIfNotLogin(mContext, 0)) return;
                Intent intent = new Intent(mContext, OrderSubmitActivity.class);
                intent.putExtra("data", goodsTypeBean);
                this.startActivityForResult(intent,refreshNumber);
                break;
        }
    }

    /*
        *轮播图
         */
    void setBannerView(final List<String> listImgs) {
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
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                PhotoScanActivity.toImgScanActivityt(mContext, (ArrayList<String>) listImgs, position);
            }
        });
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    /**
     * 设置时间控件的剩余时间
     *
     * @param time 直接将秒放进去
     */
    void setCountdownView(long time) {
        if (time > 0) {
            countdownView.setVisibility(View.VISIBLE);
            countdownView.start(time*1000);
            countdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                @Override
                public void onEnd(CountdownView cv) {
                    timeEnd();
                }
            });
        } else {
            timeEnd();
        }
    }

    /**
     * 时间控件的时间结束方法
     */
    void timeEnd() {
        mTvCountDownTitle.setText("" + getString(R.string.next_e_gou));
        joinBtn.setVisibility(View.GONE);
        goToNextBtn.setVisibility(View.VISIBLE);
        countdownView.setVisibility(View.GONE);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

    }

    /**
     * 如果  状态是进行时  索要展示的界面
     */
    private void initDoingView(GoodsTypeBean bean) {
        if(bean.getPeriod_info().getLottery_type() == bean.ME){
            mTvCountDownTitle.setText(Html.fromHtml(getString(R.string.this_remainder)+"<font color='#F7B32D'>"+bean.getPeriod_info().getSurplus_num()+"</font>"));
        }else  if(bean.getLottery_type() == bean.DD){
            setCountdownView(goodsTypeBean.getPeriod_info().getSurplus_time());
        }
        joinBtn.setVisibility(View.VISIBLE);
        progressBar.setProgress((float) (bean.getPeriod_info().getFinish_ratio() * 100));
        mLlDoing.setVisibility(View.VISIBLE);
        mTvCommodityNameDoing.setText("" + bean.getGoods_name());
        mTvValue.setText(": ₱" + bean.getAmount());
        muchPesoPeople.setText(""+bean.getPrice());
        mTvTotalNumber.setText(getString(R.string.total_need) + bean.getPeriod_info().getLimit_num()+getString(R.string.people));
        mTvRemainder.setText(getString(R.string.surplus) + bean.getPeriod_info().getSurplus_num()+getString(R.string.people));
    }

    /**
     * 如果  状态是 即将揭晓  所要展示的界面
     */
    private void initPublishView(final GoodsTypeBean bean) {
        mLlPublish.setVisibility(View.VISIBLE);
        mTvGoodsNamePub.setText("" + bean.getGoods_name());
        mTvNo.setText("" + getString(R.string.no) + bean.getPeriod_info().getPeriod_num());
        mTvStatus.setText("" + getString(R.string.status) + bean.getPeriod_info().getStatus_desc());
        pubCalInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculationInfoActivity.toDetail(mContext, bean.getPeriod_info().getPeriod_id());
            }
        });
        timeEnd();
    }

    /**
     * 如果  状态是 已揭晓  所要展示的界面
     */
    private void initEndView(final GoodsTypeBean bean) {
        mLlEnd.setVisibility(View.VISIBLE);
        mTvGoodName.setText(""+bean.getGoods_name());
        mTvLuckyStar.setText("" + bean.getPeriod_info().getWin_user_name());
        mTvEndNo.setText("" + bean.getPeriod_info().getPeriod_num());
        mTvLuckyNum.setText("" + bean.getPeriod_info().getWin_number());
        mTvPeopleNum.setText("" + bean.getPeriod_info().getWin_order_number());
//        mTvOpenTime.setText("" + TimeUtils.getTimeLongToStrByFormat(bean.getPeriod_info().getFinish_time()*1000, "yyyy-MM-dd HH:mm:ss"));
        mTvOpenTime.setText("" +bean.getPeriod_info().getWin_datetime() );
        BasisApp.loadImg(mContext, bean.getPeriod_info().getWin_head_url(), userIconEnd, R.mipmap.img_item_small_default);
        endCalInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculationInfoActivity.toDetail(mContext, bean.getPeriod_info().getPeriod_id());
            }
        });
        timeEnd();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        EventBus.getDefault().register(this);
        Log.d("logD", "onCreate");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.d("logD", "onDestroy");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == refreshNumber){
            getList();
            String a = fragmentName[mTabLayout.getCurrentTab()];
            EventBus.getDefault().post(a);
        }
    }

    /**
     * 点击《立即前往》按钮
     */
    @OnClick(R.id.btn_go_to_next)
    public void goToNext() {
        GoodsInfoActivity.toDetail(mContext, goodsTypeBean.getGoods_id(), -1);
    }

    private void initViewPage(GoodsBean bean) {
//        titles.clear();
//        mFragments.clear();
        if(mFragments .size() == 0 ){
            Collections.addAll(titles, getString(R.string.user_cyjl), getString(R.string.img_txt_info), getString(R.string.lottery), getString(R.string.share));
            mFragments.add(PartRecordFragment.getInstance(bean.getPeriod_id()));
            mFragments.add(ImageTextInfoFragment.getInstance(bean.getGoods_id()));
            mFragments.add(PastWinnerFragment.getInstance(bean.getGoods_id()));
            mFragments.add(ShowShareFragment.getInstance(bean.getGoods_id()));

            View decorView = getWindow().getDecorView();
            mViewPage = ViewFindUtils.find(decorView, R.id.vp);
            mAdapter = new MyPagerAdapter(getSupportFragmentManager());
            mViewPage.setAdapter(mAdapter);
            mViewPage.setOffscreenPageLimit(titles.size());
            mTabLayout = ViewFindUtils.find(decorView, R.id.tablayout);
            mTabLayout.setViewPager(mViewPage);
        }else{
           //获取各个fragment刷新
        }

    }

}
