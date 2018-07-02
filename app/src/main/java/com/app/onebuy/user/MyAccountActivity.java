package com.app.onebuy.user;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.basis.BasisApp;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.LoginBean;
import com.app.onebuy.bean.UserAccountInfoBean;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.OnClick;
import my.ActivityTool;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

public class MyAccountActivity extends BasisActivity {

    @BindView(R.id.text_money)
    TextView text_money;
    @BindView(R.id.img_photo)
    ImageView img_photo;

    UserAccountInfoBean userAccountInfoBean;
    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.user_my_account_activity);
        setTitle(R.string.user_myaccount);
        setTitleLeftButton(null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setView();
//        getInfo();
    }



    private void getInfo() {
        RequestParams params = new RequestParams();
        showProgress();
        HttpRestClient.post(Constants.URL_USER_ACCOUNT_INFO, params, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {
                        setView();
                    }

                    @Override
                    public void onFinish(int httpWhat) {
                        hideLoading();
                    }
                },
                0, UserAccountInfoBean.class);

    }

    void setView(){
        LoginBean loginBean = LoginBean.getInstance();
        BasisApp.loadImgAsbitmap(mContext,loginBean.getHead_url(),img_photo,R.drawable.user_icon_default);
//        if(userAccountInfoBean == null)return;
        text_money.setText(loginBean.getAccount_info().getUsable_account());
    }

    @OnClick(R.id.view_charge_history)
    void chargehistory() {
        ActivityTool.skipActivity(mContext, ChargeHistoryActivity.class);
    }

    @OnClick(R.id.view_spend_history)
    void spendhistory() {
        ActivityTool.skipActivity(mContext, SpendHistoryActivity.class);
    }

    @OnClick(R.id.btn_charge)
    void charge() {
        ActivityTool.skipActivity(mContext, ChargeActivity.class);
    }
}
