package com.app.onebuy.publish;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.onebuy.R;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.BaseListBean;
import com.app.onebuy.bean.GoodsTypeBean;
import com.app.onebuy.bean.GoodsTypeListBean;
import com.app.onebuy.home.GlideRoundTransform;
import com.app.onebuy.home.HomeBaseActivity;
import com.app.onebuy.home.goods.info.GoodsInfoActivity;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

/**
 * Created by otis on 2018/5/16.
 */

public class PublishActivity extends HomeBaseActivity {

    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;
    private GoodsTypeListBean mListBean;
    private MyAdapterList mAdapter;


    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.activity_publish);

        setTitle(""+getString(R.string.home_new));

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setVerticalScrollBarEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setEmptyView(findViewById(R.id.refresh_view));
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mListBean.refresh();
                getList();
            }

            @Override
            public void onLoadMore() {
                getList();
            }
        });
        mRecyclerView.setPullRefreshEnabled(true);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mListBean = new GoodsTypeListBean();
        setListView();
        getList();

    }

    private void getList() {
        showProgress();
        RequestParams params = new RequestParams();
        params.put(BaseListBean.PAGE_NAME, mListBean.getCurrentPage() + 1);
        params.put(BaseListBean.PAGE_SIZE_NAME, BaseListBean.PAGE_SIZE);
        HttpRestClient.get(Constants.URL_GOOD_LOTTERY, params, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {
                        GoodsTypeListBean bean = (GoodsTypeListBean) result;
                        mListBean.addListBean(bean);
                        setListView();
                    }

                    @Override
                    public void onFinish(int httpWhat) {
                        dismissProgress();
                        onListViewComplete();
                    }
                },
                0, GoodsTypeListBean.class);
    }

    private void onListViewComplete() {
        mRecyclerView.refreshComplete();
        mRecyclerView.loadMoreComplete();
        mRecyclerView.setLoadingMoreEnabled(mListBean.hasNextPage());
    }

    private void setListView() {
        if (mAdapter == null) {
            mAdapter = new MyAdapterList();
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    public class MyAdapterList extends RecyclerView.Adapter<MyAdapterList.ViewHolder> {

        public MyAdapterList() {

        }

        //创建新View，被LayoutManager所调用
        @Override
        public MyAdapterList.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_publish_adapter, viewGroup, false);
            return new MyAdapterList.ViewHolder(view);
        }


        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(MyAdapterList.ViewHolder viewHolder, int position) {
            GoodsTypeBean data = mListBean.getList().get(position);
            viewHolder.date.setText("【"+data.getPeriod_num()+mContext.getString(R.string.stage)+"】"+data.getGoods_name());
            Glide.with(mContext).load(data.getImg_url()).bitmapTransform(new GlideRoundTransform(mContext, 0)).into(viewHolder.headIcon);

            switch (data.getStatus()){
                case GoodsTypeBean.PUBLISH:
                    viewHolder.mLlWinner.setVisibility(View.GONE);
                    viewHolder.mLlCountDown.setVisibility(View.VISIBLE);
                    setCountdownView(data.getSurplus_time(),viewHolder.countdownView);
                    break;
                case GoodsTypeBean.END:
                    viewHolder.mLlWinner.setVisibility(View.VISIBLE);
                    viewHolder.mLlCountDown.setVisibility(View.GONE);
                    viewHolder.winerName.setTextColor(getResources().getColor(R.color.text_gray));
                    if(TextUtils.isEmpty(data.getWin_user_name())){
                        viewHolder.winerName.setText(""+data.getWin_user_id());
                    }else {
                        viewHolder.winerName.setText(data.getWin_user_name());
                    }
                    viewHolder.openTime.setText(data.getLottery_time_desc());
                    viewHolder.winNum.setText(""+data.getWin_number());
                    break;
            }




            viewHolder.mCardView.setTag(data);
        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return mListBean.getList() == null ? 0 : mListBean.getList().size();
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_date_of_winning)
            public TextView date;
            @BindView(R.id.tv_winer_name)
            public TextView winerName;
            @BindView(R.id.tv_open_time)
            public TextView openTime;
            @BindView(R.id.tv_win_num)
            public TextView winNum;
            @BindView(R.id.iv_head_icon)
            public ImageView headIcon;
            @BindView(R.id.card_view)
            public View mCardView;
            @BindView(R.id.rl_count_down)
            LinearLayout mLlCountDown;        //倒计时控件的  父控件
            @BindView(R.id.ll_winner)
            LinearLayout mLlWinner;        //获奖者信息  父控件
            @BindView(R.id.count_down_view)
            CountdownView countdownView;        //倒计时控件

            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
                mCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GoodsTypeBean data = (GoodsTypeBean) v.getTag();
                        GoodsInfoActivity.toDetail(mContext,
                                Integer.valueOf(data.getGoods_id()),
                                Integer.valueOf(data.getPeriod_id()));
                    }
                });
            }
        }
    }

    /**
     * 设置时间控件的剩余时间
     *
     * @param time 直接将秒放进去
     */
    void setCountdownView(long time,CountdownView countdownView) {
            countdownView.start(time*1000);
    }

}
