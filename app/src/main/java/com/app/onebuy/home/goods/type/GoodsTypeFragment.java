package com.app.onebuy.home.goods.type;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.app.onebuy.home.goods.info.GoodsInfoActivity;
import com.app.onebuy.view.roundprogressbar.RxRoundProgressBar;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.RequestParams;

import my.http.HttpRestClient;
import my.http.MyHttpListener;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("ValidFragment")
public class GoodsTypeFragment extends BasisFragment {

    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;

    private String categoryId;
    private GoodsTypeListBean mListBean;
    private MyAdapterList mAdapter;

    public static GoodsTypeFragment getInstance(String categoryId) {
        GoodsTypeFragment fragment = new GoodsTypeFragment();
        fragment.categoryId = categoryId;
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
        RequestParams params = new RequestParams();
        params.put("category_id", categoryId);
        params.put(BaseListBean.PAGE_NAME, mListBean.getCurrentPage() + 1);
        params.put(BaseListBean.PAGE_SIZE_NAME, BaseListBean.PAGE_SIZE);
        HttpRestClient.get(Constants.URL_GOOD_INDEX, params, new MyHttpListener() {
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
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_goods_item2, viewGroup, false);
            return new ViewHolder(view);
        }


        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            GoodsTypeBean data = mListBean.getList().get(position);
            viewHolder.goodsName.setText(data.getGoods_name());
            Glide.with(mContext).load(data.getImg_url()).bitmapTransform(new GlideRoundTransform(mContext, 0)).into(viewHolder.productIcon);
            viewHolder.proProgress.setText(mContext.getString(R.string.open_progress)+data.getPeriod_info().getFinish_ratio_desc());
            viewHolder.progressBar.setProgress((float) data.getPeriod_info().getFinish_ratio() * 100);
            viewHolder.listItem.setTag(data);
        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return mListBean.getList() == null ? 0 : mListBean.getList().size();
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_prod_name)
            public TextView goodsName;
            @BindView(R.id.tv_prod_progress)
            public TextView proProgress;
            @BindView(R.id.iv_product_icon)
            public ImageView productIcon;
            @BindView(R.id.progress)
            public RxRoundProgressBar progressBar;
            @BindView(R.id.list_item)
            public View listItem;


            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);

                listItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GoodsTypeBean data = (GoodsTypeBean) v.getTag();
                        GoodsInfoActivity.toDetail(mContext, Integer.valueOf(data.getGoods_id()),-1);
                    }
                });
            }
        }
    }

}