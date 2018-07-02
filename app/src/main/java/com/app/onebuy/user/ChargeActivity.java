package com.app.onebuy.user;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.bean.BaseListData;
import com.app.onebuy.bean.BaseListDataListBean;
import com.loopj.android.http.RequestParams;
import my.http.MyHttpListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 充值 账户充值
 */
public class ChargeActivity extends BasisActivity {


    @BindView(R.id.recyclerview_pay)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_pay_num)
    TextView mTvPayNum;
    @BindView(R.id.edit_num)
    TextView numEdit;


    private MyAdapterList mAdapter;
    private BaseListDataListBean mListBean;

    int currentPosition = 0;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.user_charge_activity);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setVerticalScrollBarEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setEmptyView(findViewById(R.id.refresh_view));
//        mRecyclerView.setLoadingMoreEnabled(false);
        setTitle(R.string.recharge);
        setTitleLeftButton(null);
        numEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听
                Log.e("输入过程中执行该方法", "文字变化");
                if(TextUtils.isEmpty(s)){
                    mTvPayNum.setText("0");
                }else {
                    mTvPayNum.setText(""+s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // 输入前的监听
                Log.e("输入前确认执行该方法", "开始输入");

            }
        });


    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mListBean = new BaseListDataListBean();
        setListView();
//        mRecyclerView.refresh();
//        getFromCache();

        addfack();
    }

    void addfack(){
        BaseListData data;

        data = new BaseListData("","支付宝支付");
        mListBean.getList().add(data);
        data = new BaseListData("","微信支付");
        mListBean.getList().add(data);
        setListView();
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
        }
    };


    public class MyAdapterList extends RecyclerView.Adapter<MyAdapterList.ViewHolder> {

        public MyAdapterList() {

        }

        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_charge_payway_list_item, viewGroup, false);
            return new ViewHolder(view);
        }


        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
            BaseListData data = mListBean.getList().get(position);
            viewHolder.text_name.setText(data.getName());
            viewHolder.img_check.setImageResource(position == currentPosition? R.drawable.icon_checkbox_red_cheked:R.drawable.icon_checkbox_unchecked);
            viewHolder.list_item.setTag(data);
            viewHolder.list_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentPosition = position;
                    notifyDataSetChanged();
                }
            });
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
            @BindView(R.id.img_icon)
            public ImageView img_check;

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
