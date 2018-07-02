package com.app.onebuy.user.address;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.AddressListBean;
import com.app.onebuy.bean.AddressListData;
import com.app.onebuy.bean.BasicBeanStr;
import com.flyco.dialog.listener.OnBtnClickL;
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
/**
 * 地址管理
 */
public class AddressManageActivity extends BasisActivity {


    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;

    private MyAdapterList mAdapter;
    private AddressListBean mListBean;

    int currentPosition = -1;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.address_choose_activity);
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
        setTitle(R.string.user_address);
        setTitleLeftButton(null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mListBean = new AddressListBean();
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
//        params.put("parentId", channelListData.getPARENTID());
//        params.put("sortId", sortDataBean.getSortId());
        HttpRestClient.get(Constants.URL_ADDRESS_MYADDRESS, params, myHttpListener,
                HTTP_LIST, AddressListBean.class);

    }

    private final static int HTTP_LIST = 11;
    MyHttpListener myHttpListener = new MyHttpListener() {
        @Override
        public void onSuccess(int httpWhat, Object result) {
            AddressListBean listBean = (AddressListBean) result;
            mListBean.addListBean(listBean);
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

    private final static int REQUEST_ADD = 11;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(Activity.RESULT_OK == resultCode){
            mRecyclerView.refresh();
        }
    }

    @OnClick(R.id.btn_add)
    void addAddress(){
        ActivityTool.skipActivityForResult(mContext,AddOrEditAddressActivity.class,REQUEST_ADD);

    }

    public class MyAdapterList extends RecyclerView.Adapter<MyAdapterList.ViewHolder> {

        public MyAdapterList() {

        }

        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.address_manage_list_item, viewGroup, false);
            return new ViewHolder(view);
        }


        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            AddressListData data = mListBean.getList().get(position);
            viewHolder.text_name.setText(data.getRealname());
            viewHolder.text_mobile.setText(data.getMobile());
            viewHolder.text_address.setText(data.getFull_address());
            viewHolder.list_item.setTag(data);
            viewHolder.view_edit.setTag(position);
            viewHolder.view_delete.setTag(position);

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
            @BindView(R.id.text_mobile)
            public TextView text_mobile;
            @BindView(R.id.text_address)
            public TextView text_address;
            @BindView(R.id.view_edit)
            public View view_edit;
            @BindView(R.id.view_delete)
            public View view_delete;


            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }

            @OnClick(R.id.list_item)
            public void onItemClick(View v) {
//                    AppListData data = (AppListData) v.getTag();
//                    data.toDetail(mContext);
            }
            @OnClick(R.id.view_edit)
            public void edit(View v) {
//                    AppListData data = (AppListData) v.getTag();
//                    data.toDetail(mContext);
                currentPosition = (int) v.getTag();
                Intent intent = new Intent(mContext,AddOrEditAddressActivity.class);
                intent.putExtra("data",mListBean.getList().get(currentPosition));
                mContext.startActivityForResult(intent,REQUEST_ADD);

//                ActivityTool.skipActivity(mContext,AddOrEditAddressActivity.class);
            }
            @OnClick(R.id.view_delete)
            public void delete(View v) {
                currentPosition = (int) v.getTag();
                DialogUtils.DialogTwo(mContext, "",
                        mContext.getString(R.string.address_delete_address_now),
                        mContext.getString(R.string.address_delete_now),
                        mContext.getString(R.string.app_cancel),
                        new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        submitDelete(mListBean.getList().get(currentPosition).getAddress_id());
                    }
                },null);
            }
        }
    }

    void submitDelete(String address_id){
        RequestParams params = new RequestParams();
        showProgress();
//        params.put("parentId", channelListData.getPARENTID());
        params.put("address_id",address_id);
        HttpRestClient.post(Constants.URL_ADDRESS_DELETE, params, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {
                        showToast(((BasicBeanStr)result).getStatusInfo());
                        mRecyclerView.refresh();
                    }

                    @Override
                    public void onFinish(int httpWhat) {
                        hideLoading();
                    }
                },
                0, BasicBeanStr.class);
    }

}
