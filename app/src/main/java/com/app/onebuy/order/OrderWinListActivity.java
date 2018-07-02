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
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.basis.BasisApp;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.BaseListBean;
import com.app.onebuy.bean.BasicBeanStr;
import com.app.onebuy.bean.OrderListBean;
import com.app.onebuy.bean.OrderListData;
import com.app.onebuy.home.goods.info.GoodsInfoActivity;
import com.app.onebuy.show.AddShowActivity;
import com.app.onebuy.user.JoinNumActivity;
import com.app.onebuy.user.address.AddressChooseActivity;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.ActivityTool;
import my.DialogUtils;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

public class OrderWinListActivity extends BasisActivity {


    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;

    private MyAdapterList mAdapter;
    private OrderListBean mListBean;

    String status_params = OrderListData.STATUS_PARAMS_ALL;

    int currentPosition = -1;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.base_xrecycler_with_top);
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

        setTitleLeftButton(null);
        setTitle(R.string.user_my_zjjl);

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
        params.put("is_win", true);
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

    private void setAddress(String period_id,String address_id) {
        RequestParams params = new RequestParams();
        params.put("period_id", period_id);
        params.put("address_id", address_id);
        showProgress();
        HttpRestClient.post(Constants.URL_ORDER_SETADDRESS, params, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {
                        DialogUtils.DialogOkMsg(mContext,((BasicBeanStr)result).getStatusInfo(),null);
                        mRecyclerView.refresh();
                    }

                    @Override
                    public void onFinish(int httpWhat) {
                        hideLoading();
                    }
                },
                0, BasicBeanStr.class);

    }

    private void confirmDelivery(String period_id) {
        RequestParams params = new RequestParams();
        params.put("period_id", period_id);
        showProgress();
        HttpRestClient.post(Constants.URL_ORDER_RECEIVED, params, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {
                        DialogUtils.DialogOkMsg(mContext,((BasicBeanStr)result).getStatusInfo(),null);
                    }

                    @Override
                    public void onFinish(int httpWhat) {
                        hideLoading();
                    }
                },
                0, BasicBeanStr.class);

    }

    final  static int REQUEST_CHOOSE = 11;
    final  static int REQUEST_DETAIL = 12;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(Activity.RESULT_OK == resultCode && requestCode == REQUEST_CHOOSE){
            String period_id = mListBean.getList().get(currentPosition).getPeriod_id();
            String  address_id = data.getStringExtra("id");
            setAddress(period_id,address_id);
            return;
        }
        if(Activity.RESULT_OK == resultCode && requestCode == REQUEST_DETAIL){
            mRecyclerView.refresh();
        }
    }


    public class MyAdapterList extends RecyclerView.Adapter<MyAdapterList.ViewHolder> {

        public MyAdapterList() {

        }

        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_winorder_list_item, viewGroup, false);
            return new ViewHolder(view);
        }


        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            OrderListData data = mListBean.getList().get(position);
            viewHolder.list_item.setTag(data);
            viewHolder.text_view_joinnum.setTag(data);
            viewHolder.text_name.setText(data.getGoods_name());
            viewHolder.text_number.setText(data.getPeriod_num()+"");
            viewHolder.text_joinpeople.setText(getResources().getString(R.string.order_order_num, data.getOrder_number() + ""));
            viewHolder.text_status.setText(data.getStatus_desc());
            BasisApp.loadImg(mContext,data.getImg_url(),viewHolder.img_photo,R.mipmap.img_item_small_default);

            switch (Integer.valueOf(data.getStatus())) {
                case OrderListData.STATUS_DOING:
                    viewHolder.view_lucky_start.setVisibility(View.GONE);
                    break;
                case OrderListData.STATUS_FINISH:
                    viewHolder.view_lucky_start.setVisibility(View.VISIBLE);
                    viewHolder.text_luckyname.setText(data.getWin_user_name());
                    break;
            }

            viewHolder.view_delivery_info.setTag(position);
//            viewHolder.text_status.setTag(data);
            switch (data.getStatus2()){
                case OrderListData.STATUS2_NO_ADDRESS:
                    viewHolder.view_delivery_info.setVisibility(View.VISIBLE);
                    viewHolder.view_wait_receive.setVisibility(View.GONE);
                    viewHolder.view_comment.setVisibility(View.GONE);
                    viewHolder.view_delivery_info.setTag(position);
                    viewHolder.text_status.setVisibility(View.GONE);
                    break;
                case OrderListData.STATUS2_WAITING_SEND:
                    viewHolder.view_delivery_info.setVisibility(View.GONE);
                    viewHolder.view_wait_receive.setVisibility(View.GONE);
                    viewHolder.view_comment.setVisibility(View.GONE);
                    viewHolder.view_wait_receive.setTag(data);
                    viewHolder.text_status.setVisibility(View.VISIBLE);
                    viewHolder.text_status.setText(R.string.order_wait_send);
                    break;
                case OrderListData.STATUS2_WAITING_RECEIVED:
                    viewHolder.view_delivery_info.setVisibility(View.GONE);
                    viewHolder.view_wait_receive.setVisibility(View.VISIBLE);       //代收货
                    viewHolder.view_comment.setVisibility(View.GONE);
                    viewHolder.view_wait_receive.setTag(data);
                    viewHolder.text_status.setVisibility(View.GONE);
                    break;
                case OrderListData.STATUS2_TO_SHOW:
                    viewHolder.view_delivery_info.setVisibility(View.GONE);
                    viewHolder.view_wait_receive.setVisibility(View.GONE);
                    viewHolder.view_comment.setVisibility(View.VISIBLE);            //已揭晓，去晒单
                    viewHolder.view_comment.setTag(data);
                    viewHolder.text_status.setVisibility(View.VISIBLE);
                    viewHolder.text_status.setText(R.string.order_finish);
                    viewHolder.text_status.setTextColor(R.color.base_text_grey);
                    break;
                case OrderListData.STATUS2_COMPLETE:
                    viewHolder.view_delivery_info.setVisibility(View.GONE);
                    viewHolder.view_wait_receive.setVisibility(View.GONE);
                    viewHolder.view_comment.setVisibility(View.GONE);
                    viewHolder.text_status.setVisibility(View.VISIBLE);
                    viewHolder.text_status.setText(R.string.order_finish);
                    viewHolder.text_status.setTextColor(R.color.base_text_grey);
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
            @BindView(R.id.view_delivery_info)
            public View view_delivery_info;
            @BindView(R.id.view_wait_receive)
            public View view_wait_receive;
            @BindView(R.id.view_comment)
            public View view_comment;


            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }

            @OnClick(R.id.list_item)
            public void onItemClick(View v) {
                OrderListData data = (OrderListData) v.getTag();
                GoodsInfoActivity.toDetail(mContext,Integer.valueOf(data.getGoods_id()),Integer.valueOf(data.getPeriod_id()));
            }

            @OnClick(R.id.text_view_joinnum)
            public void joinNum(View v) {
                OrderListData data = (OrderListData) v.getTag();
                Intent intent = new Intent(mContext,JoinNumActivity.class);
                intent.putExtra("data",data);
                mContext.startActivity(intent);
            }

            @OnClick(R.id.view_delivery_info)
            public void deliveryInfo(View v){
                currentPosition = (int) v.getTag();
                ActivityTool.skipActivityForResult(mContext, AddressChooseActivity.class,REQUEST_CHOOSE);
            }



            @OnClick(R.id.view_wait_receive)
            public void onWaitReceiv(View v) {
                OrderListData data = (OrderListData) v.getTag();
                Intent intent = new Intent(mContext,OrderDetailDeliveryActivity.class);
                intent.putExtra("data",data);
                mContext.startActivityForResult(intent,REQUEST_DETAIL);
            }
            @OnClick(R.id.view_comment)
            public void onComment(View v) {
                OrderListData data = (OrderListData) v.getTag();
                Intent intent = new Intent(mContext,AddShowActivity.class);
                intent.putExtra("id",data.getPeriod_id());
                mContext.startActivityForResult(intent,REQUEST_DETAIL);
            }

        }
    }

}
