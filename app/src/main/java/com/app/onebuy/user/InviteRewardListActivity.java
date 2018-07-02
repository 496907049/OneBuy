package com.app.onebuy.user;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.basis.BasisApp;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.BaseListBean;
import com.app.onebuy.bean.IntegralDetailListBean;
import com.app.onebuy.bean.IntegralDetailListData;
import com.app.onebuy.bean.InviteHasPresentBean;
import com.app.onebuy.bean.LoginBean;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

/***
 * 邀请奖励记录  邀请有礼-邀请有礼
 * **/
public class InviteRewardListActivity extends BasisActivity {


    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;
    @BindView(R.id.text_project_all)
    TextView text_project_all;
    @BindView(R.id.text_intergral_all)
    TextView text_intergral_all;

    private MyAdapterList mAdapter;
    private IntegralDetailListBean mListBean;

    InviteHasPresentBean inviteHasPresentBean;
    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.user_invite_intergral_detail);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setVerticalScrollBarEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setEmptyView(findViewById(R.id.refresh_view));
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
//        mRecyclerView.setLoadingMoreEnabled(false);
        setTitle(getString(R.string.invite_invite_rewrard));
        setTitleLeftButton(null);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mListBean = new IntegralDetailListBean();
        setListView();
        mRecyclerView.refresh();
        inviteHasPresentBean = (InviteHasPresentBean) getIntent().getSerializableExtra("data");
        text_project_all.setText(inviteHasPresentBean.getSuccess_num()+getString(R.string.people_unit));
        text_intergral_all.setText(inviteHasPresentBean.getReward_price()+getString(R.string.unit_money));
//        getFromCache();

    }

//    private void getFromCache() {
//
//        DothingsListBean beanCaceh = (DothingsListBean) DothingsListBean.getObjFromCache(id);
//        if (beanCaceh != null) {
//            mListBean = beanCaceh;
//            setListView();
//        } else {
//            mListBean = new ServiceNewListBean();
//            setListView();
//            getList();
//        }
//    }

    private void setListView() {
        if (mAdapter == null) {
            mAdapter = new MyAdapterList();
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }


    }

    private void onListViewComplete() {
        mRecyclerView.refreshComplete();
        mRecyclerView.loadMoreComplete();
        mRecyclerView.setLoadingMoreEnabled(mListBean.hasNextPage());
//        mRecyclerView.setLoadingMoreEnabled(false);
        setEmptyView();
    }

    private void setEmptyView() {
        if (mListBean == null || mListBean.getList().size() == 0) {
            findViewById(R.id.refresh_view).setVisibility(View.VISIBLE);
            findViewById(R.id.refresh_view).setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View v) {
                            mRecyclerView.refresh();
                        }
                    });
        } else {
            findViewById(R.id.refresh_view).setVisibility(View.INVISIBLE);
        }
    }

    private void getList() {
        RequestParams params = new RequestParams();
        showProgress();
        params.put("token", LoginBean.getUserToken());
        params.put(BaseListBean.PAGE_NAME, mListBean.getNextPage());
        params.put(BaseListBean.PAGE_SIZE_NAME, BaseListBean.PAGE_SIZE);
        HttpRestClient.get(Constants.URL_RECOMMEND_REWARD_LIST, params, myHttpListener,
                HTTP_LIST, IntegralDetailListBean.class);

    }

    private final static int HTTP_LIST = 11;
    MyHttpListener myHttpListener = new MyHttpListener() {
        @Override
        public void onSuccess(int httpWhat, Object result) {
//            IntegralInviteRewardBase beanBase = (IntegralInviteRewardBase) result;
            IntegralDetailListBean listBean = (IntegralDetailListBean) result;
            mListBean.addListBean(listBean);
            setListView();
        }

        @Override
        public void onFailure(int httpWhat, Object result) {
            super.onFailure(httpWhat, result);
//            mListBean = new ServiceNewListBean();
            setListView();
        }

        @Override
        public void onFinish(int httpWhat) {
            hideLoading();
            onListViewComplete();
        }
    };

    public class MyAdapterList extends RecyclerView.Adapter<MyAdapterList.ViewHolder> {

        public MyAdapterList() {

        }

        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_integral_reward_detail_list_item, viewGroup, false);
            return new ViewHolder(view);
        }


        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            IntegralDetailListData data = mListBean.getList().get(position);
            viewHolder.text_name.setText(data.getFull_mobile());
            viewHolder.text_des.setText(data.getRecommended_type());
            viewHolder.text_change.setText("+"+data.getTo_reward_amount());
            viewHolder.text_time.setText(data.getReward_time_desc());
            BasisApp.loadImgAsbitmap(mContext,data.getHead_url(),viewHolder.img_photo,R.mipmap.img_item_small_default);

            viewHolder.list_item.setTag(data);
//            viewHolder.list_item.setBackgroundResource(R.drawable.item_shadow_bluelight_selector);

        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return mListBean == null ? 0 : mListBean.getList().size();
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.list_item)
            public View list_item;
            @BindView(R.id.text_name)
            public TextView text_name;
            @BindView(R.id.text_des)
            public TextView text_des;
            @BindView(R.id.text_time)
            public TextView text_time;
            @BindView(R.id.text_change)
            public TextView text_change;
            @BindView(R.id.img_photo)
            public ImageView img_photo;



            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }

            @OnClick(R.id.list_item)
            public void onItemClick(View v) {
//                    AppListData data = (AppListData) v.getTag();
//                    data.toDetail(mContext);
            }
        }
    }

}
