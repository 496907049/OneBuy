package com.app.onebuy.home.goods.info.fragment.past;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisFragment;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.BaseListBean;
import com.app.onebuy.bean.GoodsTypeBean;
import com.app.onebuy.bean.GoodsTypeListBean;
import com.app.onebuy.home.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

/**
 * Created by otis on 2018/4/18.
 */
@SuppressLint("ValidFragment")
public class PastWinnerFragment extends BasisFragment{

    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;

    private GoodsTypeListBean mListBean;
    private MyAdapterList mAdapter;
    private int goodsId;

    public static PastWinnerFragment getInstance(int goodsId) {
        PastWinnerFragment fragment = new PastWinnerFragment();
        fragment.goodsId = goodsId;
        return fragment;
    }

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.only_xrecyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setVerticalScrollBarEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setEmptyView(findViewById(R.id.refresh_view));
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
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
        showLoading();
        RequestParams params = new RequestParams();
        params.put("goods_id", goodsId);
        params.put(BaseListBean.PAGE_NAME, mListBean.getCurrentPage() + 1);
        params.put(BaseListBean.PAGE_SIZE_NAME, BaseListBean.PAGE_SIZE);
        HttpRestClient.get(Constants.URL_GOOD_LOTTERY_LIST, params, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {
                        GoodsTypeListBean bean = (GoodsTypeListBean) result;
                        mListBean.addListBean(bean);
                        setListView();
                    }

                    @Override
                    public void onFinish(int httpWhat) {
                        hideLoading();
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
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_past_winner_adapter, viewGroup, false);
            return new MyAdapterList.ViewHolder(view);
        }


        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(MyAdapterList.ViewHolder viewHolder, int position) {
            GoodsTypeBean data = mListBean.getList().get(position);
            if(TextUtils.isEmpty(data.getWin_user_name())){
                viewHolder.winUserName.setText(""+data.getWin_user_id());
            }else {
                viewHolder.winUserName.setText(data.getWin_user_name());
            }

            viewHolder.winNumber.setText(data.getWin_number());
            viewHolder.openTime.setText(data.getDate_time());
            viewHolder.numOfPart.setText(""+data.getWin_order_number());
            viewHolder.dateOfWinning.setText(data.getPeriod_num_desc());
            Glide.with(mContext).load(data.getHead_url()).bitmapTransform(new GlideRoundTransform(mContext, 0)).into(viewHolder.headUrl);
        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return mListBean.getList() == null ? 0 : mListBean.getList().size();
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_award_winner)
            public TextView winUserName;
            @BindView(R.id.tv_win_num)
            public TextView winNumber;
            @BindView(R.id.tv_open_time)
            public TextView openTime;
            @BindView(R.id.tv_date_of_winning)
            public TextView dateOfWinning;
            @BindView(R.id.tv_num_of_part)
            public TextView numOfPart;
            @BindView(R.id.imageView1)
            public ImageView headUrl;

            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(String messageEvent) {
        if(messageEvent.equals("PastWinnerFragment")){
            mListBean.refresh();
            getList();
        }
    }

}
