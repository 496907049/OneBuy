package com.app.onebuy.user;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.bean.OrderListData;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.http.MyHttpListener;

public class JoinNumActivity extends BasisActivity {


    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;
    @BindView(R.id.text_num)
    TextView text_num;

    private MyAdapterList mAdapter;
    private OrderListData mListBean;


    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.join_record_join_num_activty);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext,4);
        mRecyclerView.setVerticalScrollBarEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setEmptyView(findViewById(R.id.refresh_view));
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
//                mListBean.refresh();
//                getList();
            }

            @Override
            public void onLoadMore() {
                getList();
            }
        });
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingMoreEnabled(false);

        setTitle(R.string.join_number);
        setTitleLeftButton(null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mListBean = (OrderListData) getIntent().getSerializableExtra("data");
        if(mListBean == null){
            finish();
            return;
        }
        setListView();

        text_num.setText(mListBean.getOrder_number()+"");
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
//        mRecyclerView.setLoadingMoreEnabled(mListBean.hasNextPage());
        mRecyclerView.setLoadingMoreEnabled(false);
//        setEmptyView();
    }

    private void setEmptyView() {
//        if (mListBean == null || mListBean.getList().size() == 0) {
//            findViewById(R.id.refresh_view).setVisibility(View.VISIBLE);
//            findViewById(R.id.refresh_view).setOnClickListener(
//                    new View.OnClickListener() {
//                        public void onClick(View v) {
//                            mRecyclerView.refresh();
//                        }
//                    });
//        } else {
//            findViewById(R.id.refresh_view).setVisibility(View.INVISIBLE);
//        }
    }

    private void getList() {
        RequestParams params = new RequestParams();
//        showProgress();
//        params.put("parentId", channelListData.getPARENTID());
//        params.put("sortId", sortDataBean.getSortId());
//        HttpRestClient.post(Constants.URL_APP_SERVER_LIST, params, myHttpListener,
//                HTTP_LIST, AppListBean.class);

    }

    private final static int HTTP_LIST = 11;
    MyHttpListener myHttpListener = new MyHttpListener() {
        @Override
        public void onSuccess(int httpWhat, Object result) {
//            mListBean = (AppListBean) result;
//            setListView();
        }

        @Override
        public void onFailure(int httpWhat, Object result) {
            super.onFailure(httpWhat, result);
//            mListBean = new ServiceNewListBean();
            setListView();
        }

        @Override
        public void onFinish(int httpWhat) {
//            hideLoading();
            onListViewComplete();
        }
    };

    public class MyAdapterList extends RecyclerView.Adapter<MyAdapterList.ViewHolder> {

        public MyAdapterList() {

        }

        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.join_record_joinnum_grid_item, viewGroup, false);
            return new ViewHolder(view);
        }


        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            String data = mListBean.getNumber_list().get(position);
            viewHolder.text_name.setText(data);
//            viewHolder.list_item.setBackgroundResource(R.drawable.item_shadow_bluelight_selector);

        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return mListBean == null ? 0 : mListBean.getNumber_list().size();
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.list_item)
            public View list_item;
            @BindView(R.id.text_name)
            public TextView text_name;


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