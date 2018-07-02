package com.app.onebuy.show;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
import com.app.onebuy.bean.CommentListBean;
import com.app.onebuy.bean.CommentListData;
import com.flyco.dialog.listener.OnBtnClickL;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import my.DialogUtils;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

/**
 * 晒单-我的晒单
 */
public class MyShowActivity extends BasisActivity {


    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;

    private MyShowListAdapter mAdapter;
    private CommentListBean mListBean;


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
        setTitle(R.string.user_myshows);
        setTitleLeftButton(null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mListBean = new CommentListBean();
        setListView();
        mRecyclerView.refresh();
//        getFromCache();
    }


//    private void getFromCache() {
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
            mAdapter = new MyShowListAdapter(mContext, mListBean);
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
        HttpRestClient.post(Constants.URL_COMMENT_MY_COMMENTS, params, myHttpListener,
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
            setListView();
        }

        @Override
        public void onFinish(int httpWhat) {
            hideLoading();
            onListViewComplete();
        }
    };

    public void onCommentDelete(CommentListData data){
        Resources resources = getResources();
        DialogUtils.DialogTwo(mContext, "", resources.getString(R.string.comment_delete_comment_now), resources.getString(R.string.comment_delete), resources.getString(R.string.app_cancel), new OnBtnClickL() {
            @Override
            public void onBtnClick() {

            }
        },null);
    }

     void submitDelete(String comment_id){
        RequestParams params = new RequestParams();
        showProgress();
        params.put("comment_id", comment_id);
        HttpRestClient.post(Constants.URL_COMMENT_DELETE, params, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {
                        setResult(Activity.RESULT_OK);
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


    public class MyShowListAdapter extends RecyclerView.Adapter<MyShowListAdapter.ViewHolder> {

        Activity mContext;
        CommentListBean mListBean;

        public MyShowListAdapter(Activity mContext,CommentListBean mListBean) {
            this.mContext = mContext;
            this.mListBean = mListBean;
        }
        public void setData(CommentListBean mListBean){
            this.mListBean = mListBean;
            notifyDataSetChanged();
        }

        //创建新View，被LayoutManager所调用
        @Override
        public MyShowListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_show_list_item, viewGroup, false);
            return new MyShowListAdapter.ViewHolder(view);
        }


        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(MyShowListAdapter.ViewHolder viewHolder, int position) {
            CommentListData data = mListBean.getList().get(position);
            viewHolder.text_name.setText(data.getNickname());
            viewHolder.text_time.setText(data.getDate());
            viewHolder.mTvContent.setText(data.getContent());
            viewHolder.list_item.setTag(data);
            viewHolder.deleteBtn.setTag(data);
//            viewHolder.list_item.setBackgroundResource(R.drawable.item_shadow_bluelight_selector);
            viewHolder.recyclerview.setAdapter(new ShowImgGridAdapter(mContext,data.getImages_list()));

            BasisApp.loadImgAsbitmap(mContext,data.getHead_url(),viewHolder.img_photo,R.mipmap.img_item_small_default);

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
            @BindView(R.id.text_time)
            public TextView text_time;
            @BindView(R.id.btn_delete)
            public TextView deleteBtn;
            @BindView(R.id.text_content)
            public TextView mTvContent;
            @BindView(R.id.img_photo)
            public ImageView img_photo;
            @BindView(R.id.recyclerview)
            public RecyclerView recyclerview;


            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);

                GridLayoutManager layoutManager = new GridLayoutManager(mContext,3);
                recyclerview .setLayoutManager(layoutManager);
            }

            @OnClick(R.id.btn_delete)
            public void onItemDelete(final View v) {
                final CommentListData data = (CommentListData) v.getTag();
                DialogUtils.DialogTwo(mContext, "",
                        mContext.getString(R.string.sure_delete),
                        mContext.getString(R.string.app_ok),
                        mContext.getString(R.string.app_cancel),
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                    submitDelete(""+data.getComment_id());
                            }
                        },null);


            }

            @OnClick(R.id.list_item)
            public void onItemClick(View v) {
//                    AppListData data = (AppListData) v.getTag();
//                    data.toDetail(mContext);
            }

            @OnLongClick(R.id.list_item)
            public boolean onItemLongClick(View v){
                if(mContext instanceof MyShowActivity){
                    CommentListData data = (CommentListData) v.getTag();
                    ((MyShowActivity)mContext).onCommentDelete(data);
                }
                return  true;
            }
        }
    }
}
