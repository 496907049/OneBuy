package com.app.onebuy.home.goods.info.fragment.part;

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
//ParticipationRecordFragment
@SuppressLint("ValidFragment")
public class PartRecordFragment extends BasisFragment  {

    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;
    private GoodsTypeListBean mListBean;
    private MyAdapterList mAdapter;
    private int periodId;

    public static PartRecordFragment getInstance(int periodId) {
        PartRecordFragment fragment = new PartRecordFragment();
        fragment.periodId = periodId;
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
        params.put("period_id", periodId);
        params.put(BaseListBean.PAGE_NAME, mListBean.getCurrentPage() + 1);
        params.put(BaseListBean.PAGE_SIZE_NAME, BaseListBean.PAGE_SIZE);
        HttpRestClient.get(Constants.URL_GOOD_ORDER_LIST, params, new MyHttpListener() {
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
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_part_record_adapter, viewGroup, false);
            return new MyAdapterList.ViewHolder(view);
        }


        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(MyAdapterList.ViewHolder viewHolder, int position) {
            GoodsTypeBean data = mListBean.getList().get(position);
            if(TextUtils.isEmpty(data.getNickname())){
                viewHolder.winerName.setText(getString(R.string.nick)+data.getUser_id());
            }else {
                viewHolder.winerName.setText(data.getNickname());
            }

            viewHolder.openTime.setText(data.getDate_time());
            viewHolder.frequency.setText(data.getNumber()+getString(R.string.frequency));
            Glide.with(mContext).load(data.getHead_url()).bitmapTransform(new GlideRoundTransform(mContext, 0)).into(viewHolder.headUrl);

        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return mListBean.getList() == null ? 0 : mListBean.getList().size();
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_winer_name)
            public TextView winerName;
            @BindView(R.id.tv_time)
            public TextView openTime;
            @BindView(R.id.tv_frequency)
            public TextView frequency;
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
        if(messageEvent.equals("PartRecordFragment")){
            mListBean.refresh();
            getList();
        }
    }


}
