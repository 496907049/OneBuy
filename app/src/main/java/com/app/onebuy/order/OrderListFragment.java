package com.app.onebuy.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisApp;
import com.app.onebuy.basis.BasisFragment;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.BaseListBean;
import com.app.onebuy.bean.OrderListBean;
import com.app.onebuy.bean.OrderListData;
import com.app.onebuy.home.goods.info.GoodsInfoActivity;
import com.app.onebuy.user.JoinNumActivity;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

public class OrderListFragment extends BasisFragment {

    public static OrderListFragment newInstance(String status) {
        OrderListFragment fragment = new OrderListFragment();
        fragment.status_params = status;
        return fragment;
    }

    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;

    private MyAdapterList mAdapter;
    private OrderListBean mListBean;

    String status_params = OrderListData.STATUS_PARAMS_ALL;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.base_xrecycler_with_empty);
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

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mListBean = new OrderListBean();
        setListView();
        mRecyclerView.refresh();
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
//        setEmptyView();
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
        params.put("status", status_params);
        params.put(BaseListBean.PAGE_NAME, mListBean.getNextPage());
        params.put(BaseListBean.PAGE_SIZE_NAME, BaseListBean.PAGE_SIZE);
        HttpRestClient.post(Constants.URL_ORDER_INDEX, params, myHttpListener,
                HTTP_LIST, OrderListBean.class);

    }

    private final static int HTTP_LIST = 11;
    MyHttpListener myHttpListener = new MyHttpListener() {
        @Override
        public void onSuccess(int httpWhat, Object result) {
            OrderListBean bean = (OrderListBean) result;
            mListBean.addListBean(bean);
            setListView();
        }

        @Override
        public void onFailure(int httpWhat, Object result) {
            super.onFailure(httpWhat, result);
            setListView();
        }

        @Override
        public void onFinish(int httpWhat) {
            hideLoading();
            onListViewComplete();
        }
    };

    private final static int REQUEST_DETAIL = 0;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK == resultCode) {
            mRecyclerView.refresh();
        }
    }

    public class MyAdapterList extends RecyclerView.Adapter<MyAdapterList.ViewHolder> {

        public MyAdapterList() {

        }

        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.join_records_list_item, viewGroup, false);
            return new ViewHolder(view);
        }


        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            OrderListData data = mListBean.getList().get(position);
            viewHolder.list_item.setTag(data);
            viewHolder.text_view_joinnum.setTag(data);
            viewHolder.text_name.setText(data.getGoods_name());
            viewHolder.text_number.setText(data.getPeriod_num() + "");
            viewHolder.text_joinpeople.setText(getResources().getString(R.string.order_order_num, data.getOrder_number() + ""));
            viewHolder.text_status.setText(data.getStatus_desc());
            BasisApp.loadImg(mContext, data.getImg_url(), viewHolder.img_photo, R.mipmap.img_item_small_default);
            switch (Integer.valueOf(data.getStatus())) {
                case OrderListData.STATUS_DOING:
                    viewHolder.view_lucky_start.setVisibility(View.GONE);
                    break;
                case OrderListData.STATUS_FINISH:

                    viewHolder.view_lucky_start.setVisibility(View.VISIBLE);
                    viewHolder.text_luckyname.setText(data.getWin_user_name());
                    break;
                case OrderListData.STATUS_COMING:
                    viewHolder.view_lucky_start.setVisibility(View.GONE);
                    viewHolder.text_luckyname.setVisibility(View.GONE);
                    break;
            }

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
            @BindView(R.id.text_number)
            public TextView text_number;
            @BindView(R.id.text_joinpeople)
            public TextView text_joinpeople;
            @BindView(R.id.text_status)
            public TextView text_status;
            @BindView(R.id.text_view_joinnum)
            public TextView text_view_joinnum;
            @BindView(R.id.text_luckyname)
            public TextView text_luckyname;
            @BindView(R.id.view_lucky_start)
            public View view_lucky_start;
            @BindView(R.id.img_photo)
            public ImageView img_photo;


            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }

            @OnClick(R.id.list_item)
            public void onItemClick(View v) {
                OrderListData data = (OrderListData) v.getTag();
                GoodsInfoActivity.toDetail(mContext, Integer.valueOf(data.getGoods_id()), Integer.valueOf(data.getPeriod_id()));
//                Intent intent = new Intent(mContext,OrderDetailFinishActivity.class);
//                intent.putExtra("data",data);
//                mContext.startActivityForResult(intent,REQUEST_DETAIL);
            }

            @OnClick(R.id.text_view_joinnum)
            public void joinNum(View v) {
                OrderListData data = (OrderListData) v.getTag();
                Intent intent = new Intent(mContext, JoinNumActivity.class);
                intent.putExtra("data", data);
                mContext.startActivity(intent);
            }
        }
    }

}
