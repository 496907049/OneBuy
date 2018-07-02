package com.app.onebuy.show;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.app.onebuy.R;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.BaseListBean;
import com.app.onebuy.bean.CommentListBean;
import com.app.onebuy.home.HomeBaseActivity;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

/**
 * 晒单首页
 */
public class ShowIndexActivity extends HomeBaseActivity {


    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;

    private ShowListAdapter mAdapter;
    private CommentListBean mListBean;


    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.show_index_activity);

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
        setTitle(R.string.home_shows);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mListBean = new CommentListBean();
        setListView();
        mRecyclerView.refresh();
//        getFromCache();

//        addfack();
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
            mAdapter = new ShowListAdapter(mContext, mListBean);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setData(mListBean);
//            mAdapter.notifyDataSetChanged();
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
        params.put(BaseListBean.PAGE_NAME, mListBean.getNextPage());
        params.put(BaseListBean.PAGE_SIZE_NAME, BaseListBean.PAGE_SIZE);
        HttpRestClient.post(Constants.URL_COMMENT_INDEX, params, myHttpListener,
                HTTP_LIST, CommentListBean.class);

    }

    private final static int HTTP_LIST = 11;
    MyHttpListener myHttpListener = new MyHttpListener() {
        @Override
        public void onSuccess(int httpWhat, Object result) {
            CommentListBean  bean = (CommentListBean) result;
            mListBean.addListBean(bean);
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

}
