package com.app.onebuy.order;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.basis.BasisApp;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.GoodsTypeBean;
import com.app.onebuy.bean.UserAccountInfoBean;
import com.app.onebuy.bean.UserChargeHistoryListData;
import com.app.onebuy.home.goods.info.GoodsInfoActivity;
import com.app.onebuy.user.MyAccountActivity;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.OnClick;
import my.ActivityTool;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

public class OrderSubmitActivity extends BasisActivity {


    @BindView(R.id.img_photo)
    ImageView img_photo;
    @BindView(R.id.text_goodsname)
    TextView text_goodsname;
    @BindView(R.id.text_leftchance)
    TextView text_leftchance;
    @BindView(R.id.text_order_num)
    TextView text_order_num;
    @BindView(R.id.text_price)
    TextView text_price;
    @BindView(R.id.text_paybyleft_des)
    TextView text_paybyleft_des;
    @BindView(R.id.view_paybyleft)
    View view_paybyleft;
    @BindView(R.id.view_paybywechat)
    View view_paybywechat;

    UserAccountInfoBean userAccountInfoBean;
    GoodsTypeBean goodsTypeBean;

    public static final String PAY_TYPE_BALANCE = "balance_pay", PAY_TYPE_WECHAT = "wechat";
    String pay_type = PAY_TYPE_BALANCE;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.order_submit_activity);
        setTitle(R.string.order_submit_title);
        setTitleLeftButton(R.drawable.base_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(GoodsInfoActivity.refreshNumber);
                finish();
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        goodsTypeBean = (GoodsTypeBean) getIntent().getSerializableExtra("data");
        if (goodsTypeBean == null) {
            finish();
            return;
        }
        setView();
        getAccountInfo();
    }


    private void getAccountInfo() {
        RequestParams params = new RequestParams();
        showProgress();
        HttpRestClient.post(Constants.URL_USER_ACCOUNT_INFO, params, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {
                        userAccountInfoBean = (UserAccountInfoBean) result;
                        setView();
                    }

                    @Override
                    public void onFinish(int httpWhat) {
                        hideLoading();
                    }
                },
                0, UserAccountInfoBean.class);

    }

    void setView() {
        BasisApp.loadImg(mContext, goodsTypeBean.getImg_url(), img_photo,R.mipmap.img_item_small_default);
        text_goodsname.setText(goodsTypeBean.getGoods_name());
        text_leftchance.setText(getResources().getString(R.string.order_goods_left_changce, goodsTypeBean.getLeftNum()));
        setOrderNumView();
        setPayTypeView();
        if (userAccountInfoBean == null) return;
        text_paybyleft_des.setText(getResources().getString(R.string.order_pay_byleftdes, (userAccountInfoBean.getUsable_account())+getString(R.string.unit_money)));

    }

    void setOrderNumView() {
        text_price.setText(goodsTypeBean.getTotalPrice()+getString(R.string.unit_money));
        text_order_num.setText(goodsTypeBean.getOrder_num() + "");
    }

    void setPayTypeView() {
        if (pay_type.equals(PAY_TYPE_BALANCE)) {
            view_paybyleft.setSelected(true);
            view_paybywechat.setSelected(false);
        } else {
            view_paybyleft.setSelected(false);
            view_paybywechat.setSelected(true);
        }
    }

    @OnClick(R.id.img_plus)
    public void onPlusClick() {
        goodsTypeBean.plusNum();
        setOrderNumView();
    }

    @OnClick(R.id.img_minus)
    public void onMinusClick() {
        goodsTypeBean.minusNum();
        setOrderNumView();
    }

    @OnClick(R.id.btn_five)
    public void btnFive() {
        goodsTypeBean.setOrderNum(5);
        setOrderNumView();
    }

    @OnClick(R.id.btn_ten)
    public void btnTen() {
        goodsTypeBean.setOrderNum(10);
        setOrderNumView();
    }

    @OnClick(R.id.btn_twenty)
    public void btnTwenty() {
        goodsTypeBean.setOrderNum(20);
        setOrderNumView();
    }

    @OnClick(R.id.btn_fifty)
    public void btnFifty() {
        goodsTypeBean.setOrderNum(50);
        setOrderNumView();
    }

    @OnClick(R.id.view_paybyleft)
    public void paybyleft() {
        pay_type = PAY_TYPE_BALANCE;
        setPayTypeView();
    }

    @OnClick(R.id.view_paybywechat)
    public void paybywechat() {
        pay_type = PAY_TYPE_WECHAT;
        setPayTypeView();
    }

    @OnClick(R.id.btn_charge)
    public void toCharge() {
        ActivityTool.skipActivity(mContext, MyAccountActivity.class);
    }

    @OnClick(R.id.btn_ok)
    public void checkAndSubmit() {

        RequestParams params = new RequestParams();
        params.put("period_id", goodsTypeBean.getPeriod_id());
        params.put("number", goodsTypeBean.getOrder_num());
        params.put("pay_type", pay_type);
        showProgress();
        HttpRestClient.post(Constants.URL_ORDER_SUBMIT, params, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {
//                        DialogUtils.DialogOKmsgFinish(mContext, ((OrderListData) result).getStatusInfo());
                        UserChargeHistoryListData bean = (UserChargeHistoryListData) result;
                        showToast(bean.getStatusInfo());
                        Bundle bundle = new Bundle();
                        bundle.putString("pay_type",bean.getPay_type());
                        bundle.putString("number",text_price.getText().toString().trim());
                        ActivityTool.skipActivity(mContext,PayCompleteActivity.class,bundle);
                        finish();
                    }

                    @Override
                    public void onFinish(int httpWhat) {
                        hideLoading();
                    }
                },
                0, UserChargeHistoryListData.class);
    }
}
