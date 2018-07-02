package com.app.onebuy.other;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.basis.BasisApp;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.BaseListBean;
import com.app.onebuy.bean.GoodsTypeBean;
import com.app.onebuy.bean.GoodsTypeListBean;
import com.app.onebuy.home.HomePageActivity;
import com.app.onebuy.home.goods.info.GoodsInfoActivity;
import com.app.onebuy.view.DividerItemDecoration;
import com.app.onebuy.view.roundprogressbar.RxRoundProgressBar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.RequestParams;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.MySharedPreferences;
import my.ViewUtils;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

public class AppSearchActivity extends BasisActivity {

    @BindView(R.id.recyclerview_dothings)
    XRecyclerView mRecyclerView;
    @BindView(R.id.refresh_text_empty)
    TextView mTvRefreshTvEmpty;

    private MyAdapterList mAdapter;
    private GoodsTypeListBean mListBean;

    @BindView(R.id.edit_search)
    EditText edit_search;
    @BindView(R.id.flow_layout)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.nsv_flow_and_rv)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.img_search_cancel)
    ImageView mIvSearchCancel;
    @BindView(R.id.ll_rv)
    LinearLayout mLlRv;

    @BindView(R.id.rv_history)
    RecyclerView mRvHistory;     //历史
    BaseQuickAdapter historyAdapter;
    MySharedPreferences sharedPreferences;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.dothings_search_activity);
        mRecyclerView.setVerticalScrollBarEnabled(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.BOTH_SET, 5, getResources().getColor(R.color.home_base_gray)));
        //垂直+水平分割线
        mRecyclerView.setEmptyView(findViewById(R.id.refresh_view));
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mListBean.refresh();
                toSearch();
            }

            @Override
            public void onLoadMore() {
                toSearch();
            }
        });
        mRecyclerView.setPullRefreshEnabled(true);

        setTitle("搜索应用");

        edit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    search();
                }
                return true;
            }
        });
        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edit_search.getText().toString().trim().length() > 0) {
                    mIvSearchCancel.setVisibility(View.VISIBLE);
                } else {
                    mIvSearchCancel.setVisibility(View.GONE);
                }
            }
        });

        sharedPreferences = new MySharedPreferences(this);
        historyAdapter = new SearchHistoryAdapter(R.layout.search_history_item_adaptert, sharedPreferences.getInfoSearchHistory(), sharedPreferences);
        mRvHistory.setLayoutManager(new LinearLayoutManager(this));
        mRvHistory.setAdapter(historyAdapter);
        mRvHistory.setNestedScrollingEnabled(false);
        historyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                edit_search.setText("" + adapter.getItem(position).toString());
                toSearch();
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mListBean = new GoodsTypeListBean();
        setListView();
        getHotSearchWords();
        mTvRefreshTvEmpty.setText(""+getString(R.string.no_result));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewUtils.hideInput(mContext, edit_search);
    }

    @OnClick(R.id.tv_search)
    void search() {
        mListBean.refresh();
        toSearch();
    }

    @OnClick(R.id.img_search_cancel)
    void cancel() {
        edit_search.setText("");
    }

    @OnClick(R.id.base_btn_back)
    void goback() {
        setResult(HomePageActivity.ALL_REFRESH);
        finish();
    }

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
    }

    private void getHotSearchWords() {
        RequestParams params = new RequestParams();
        HttpRestClient.get(Constants.URL_KEY_WORDS, params, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {
                        GoodsTypeListBean bean = (GoodsTypeListBean) result;
                        ArrayList<String> titleList = new ArrayList<String>();
                        for (GoodsTypeBean a : bean.getList()) {
                            titleList.add(a.getTitle());
                        }
                        setFlowLayout(titleList);
                    }

                    private void setFlowLayout(final ArrayList<String> mVals) {

                        mFlowLayout.setAdapter(new TagAdapter<String>(mVals) {
                            @Override
                            public View getView(FlowLayout parent, int position, String s) {
                                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.flow_layout_tv, mFlowLayout, false);
                                tv.setText(s);
                                return tv;
                            }
                        });

                        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                            @Override
                            public boolean onTagClick(View view, int position, FlowLayout parent) {
                                //  标签点击事件
                                String a = mVals.get(position);
                                edit_search.setText("" + a);
                                toSearch();
                                return true;
                            }
                        });
                    }

                    @Override
                    public void onFinish(int httpWhat) {
                        hideLoading();
                    }
                },
                0, GoodsTypeListBean.class);
    }

    private void toSearch() {
        showLoading();
        String keyWord = edit_search.getText().toString().trim();
        mNestedScrollView.setVisibility(View.GONE);
        mLlRv.setVisibility(View.VISIBLE);
        RequestParams params = new RequestParams();
        if (!TextUtils.isEmpty(keyWord)) {
            sharedPreferences.saveInfoSearchHistory("" + keyWord);
            params.put("keyword", keyWord);
        }
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

    private final static int REQUEST_ADD = 11;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_ADD == requestCode & Activity.RESULT_OK == resultCode) {
            mRecyclerView.refresh();
        }
    }

    public class MyAdapterList extends RecyclerView.Adapter<MyAdapterList.ViewHolder> {

        public MyAdapterList() {

        }

        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_goods_item, viewGroup, false);
            return new ViewHolder(view);
        }


        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            GoodsTypeBean data = mListBean.getList().get(position);
            viewHolder.goodsName.setText(data.getGoods_name());
            viewHolder.progressNum.setText(data.getPeriod_info().getFinish_ratio_desc());
            viewHolder.progressBar.setProgress((float) data.getPeriod_info().getFinish_ratio() * 100);
            BasisApp.loadImg(mContext, data.getImg_url(), viewHolder.goodsIcon,R.mipmap.img_item_small_default);
            viewHolder.list_item.setTag(data);
        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return mListBean.getList() == null ? 0 : mListBean.getList().size();
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.list_item)
            public View list_item;
            @BindView(R.id.iv_product_icon)
            public ImageView goodsIcon;
            @BindView(R.id.tv_prod_name)
            public TextView goodsName;
            @BindView(R.id.tv_prod_progress)
            public TextView progressNum;
            @BindView(R.id.progress)
            public RxRoundProgressBar progressBar;

            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }

            @OnClick(R.id.list_item)
            public void onItemClick(View v) {
                GoodsTypeBean data = (GoodsTypeBean) v.getTag();
//                GoodsInfoActivity.toDetail(mContext, data.getGoods_id(), data.getPeriod_id());
                GoodsInfoActivity.toDetail(mContext, data.getGoods_id(), -1);
            }
        }
    }
}
