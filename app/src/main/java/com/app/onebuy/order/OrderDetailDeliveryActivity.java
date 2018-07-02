package com.app.onebuy.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.AddressListData;
import com.app.onebuy.bean.BasicBeanStr;
import com.app.onebuy.bean.LoginBean;
import com.app.onebuy.bean.OrderListData;
import com.flyco.dialog.listener.OnBtnClickL;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.OnClick;
import my.ActivityTool;
import my.DialogUtils;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

import static com.app.onebuy.order.OrderWinListActivity.REQUEST_DETAIL;

public class OrderDetailDeliveryActivity extends BasisActivity {

    @BindView(R.id.text_delivery_company)
    TextView mTvDeliCompany;
    @BindView(R.id.text_delivery_num)
    TextView mTvDeliNum;
    @BindView(R.id.text_name)
    TextView winnerName;
    @BindView(R.id.text_mobile)
    TextView winnerMobile;
    @BindView(R.id.text_address)
    TextView mTvAddress;

    OrderListData mDetailBean;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.order_detail_delivery_activity);
        setTitle(R.string.order_detail);
        setTitleLeftButton(null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mDetailBean = (OrderListData) getIntent().getSerializableExtra("data");
        if(mDetailBean == null){
            finish();
            return;
        }
        mTvDeliCompany.setText(""+mDetailBean.getExpress_type_desc());
        mTvDeliNum.setText(getString(R.string.delivery_num)+mDetailBean.getExpress_number());
        winnerName.setText(""+mDetailBean.getWin_user_name());

        getAddress();
    }

    private void getAddress(){
            RequestParams params = new RequestParams();
            params.put("address_id", mDetailBean.getAddress_id());
            showProgress();
            HttpRestClient.post(Constants.URL_ADDRESS_INFO, params, new MyHttpListener() {
                        @Override
                        public void onSuccess(int httpWhat, Object result) {
                            AddressListData bean = (AddressListData) result;
                            mTvAddress.setText(""+bean.getFull_address());
                        }

                        @Override
                        public void onFinish(int httpWhat) {
                            hideLoading();
                        }
                    },
                    0, AddressListData.class);

    }


    @OnClick(R.id.btn_ok)
    public void submit() {
        DialogUtils.DialogTwo(mContext, "", getString(R.string.sure_receiver), getString(R.string.app_ok), getString(R.string.app_cancel), new OnBtnClickL() {
            @Override
            public void onBtnClick() {

                RequestParams params = new RequestParams();
                params.add("period_id", mDetailBean.getPeriod_id());
                showProgress();
                HttpRestClient.post(Constants.URL_ORDER_RECEIVED, params, new MyHttpListener() {
                            @Override
                            public void onSuccess(int httpWhat, Object result) {
//                                setResult(Activity.RESULT_OK);
//                                DialogUtils.DialogOKmsgFinish(mContext,((BasicBeanStr)result).getStatusInfo());
                                Intent intent = new Intent(mContext,OrderCompleteActivity.class);
                                intent.putExtra("period_id",mDetailBean.getPeriod_id());
                                mContext.startActivityForResult(intent,REQUEST_DETAIL);
                            }

                            @Override
                            public void onFinish(int httpWhat) {
                                hideLoading();
                            }
                        },
                        0, BasicBeanStr.class);
            }
        }, null);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(Activity.RESULT_OK == resultCode && requestCode == REQUEST_DETAIL){
            setResult(RESULT_OK);
            finish();
        }
    }
}
