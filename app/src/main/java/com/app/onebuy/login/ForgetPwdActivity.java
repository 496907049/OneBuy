package com.app.onebuy.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.BasicBeanStr;
import com.app.onebuy.bean.LoginBean;
import com.app.onebuy.login.countrychoose.CountryChooseActivity;
import com.app.onebuy.login.countrychoose.CountryListDate;
import com.app.onebuy.util.TextLengthUtil;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.OnClick;
import my.CheckUtils;
import my.DialogUtils;
import my.MD5;
import my.http.HttpRestClient;
import my.http.MyHttpListener;
import my.useful.CountDownTimerUtils;


public class ForgetPwdActivity extends BasisActivity {

    @BindView(R.id.edit_pwd)
    EditText edit_pwd;
    @BindView(R.id.edit_pwd_confirm)
    EditText edit_pwd_confirm;
    @BindView(R.id.eidt_user)
    EditText edit_acount;
    @BindView(R.id.edit_msgcode)
    EditText edit_msgcode;
    @BindView(R.id.text_msgcode)
    TextView text_msgcode;

    CountDownTimerUtils countDownTimerUtils;

    @BindView(R.id.text_mobile_zone_num)
    TextView text_mobile_zone_num;

    CountryListDate countryListDate;


    public static void toForgetPwdActivity(Activity context, int logintype) {
        Intent intent = new Intent(context,ForgetPwdActivity.class);
        intent.putExtra("logintype",logintype);
        context.startActivity(intent);
    }

    @Override
    public void initViews() {
        setDefautTrans(false);
        super.initViews();
        setContentView(R.layout.login_forgetpwd_activity);
        setTitleLeftButton(null);
        setTitle(R.string.user_forget_password);
        setTitleBg(R.color.transparent);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);


       countryListDate = CountryListDate.getFromCache();
        setCountryView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(countDownTimerUtils !=null)countDownTimerUtils.cancel();
    }
    private final static int HTTP_MODIFY = 11;
    private final static int HTTP_SEND_MSGCODE = 12;
    private final static int HTTP_VERTIFY_MSGCODE = 13;
    private final static int HTTP_GET_USERINFO = 14;
    MyHttpListener myHttpListener = new MyHttpListener() {
        @Override
        public void onSuccess(int httpWhat, Object result) {
            switch (httpWhat) {
                case HTTP_MODIFY:
//                    loginSucceed((LoginBean) result);
                    setResult(Activity.RESULT_OK);
                    DialogUtils.DialogOKmsgFinish(mContext,((BasicBeanStr)result).getStatusInfo());
                    break;
                case HTTP_SEND_MSGCODE:
                    showToast(((BasicBeanStr)result).getStatusInfo());
                    countDownTimerUtils = new CountDownTimerUtils(text_msgcode,60000,1000);
                    countDownTimerUtils.start();
                    break;
                case HTTP_VERTIFY_MSGCODE:
//                    post();
                    break;
                case HTTP_GET_USERINFO:
                    LoginBean beanLogin = (LoginBean) result;
                    if (beanLogin == null) {
                        return;
                    }
//                    oldpwd = beanLogin.getAccountPwd();
//                    if(logintype == LoginBean.TYPE_USER){
//                        mobile = beanLogin.getMobile();
//                    }else{
//                        mobile = beanLogin.getTransactorMobile();
//                    }

//                    sendCodeReal(mobile);
                    break;
            }
        }

        @Override
        public void onFinish(int httpWhat) {
            dismissProgress();
        }

        @Override
        public void onFailure(int httpWhat, Object result) {
            super.onFailure(httpWhat, result);
        }
    };

    @OnClick(R.id.btn_ok)
    void checkAndPost() {
        String account = edit_acount.getText().toString().trim();
        String pwd = edit_pwd.getText().toString().trim();
        String pwd_confirm = edit_pwd_confirm.getText().toString().trim();
        String msgcode = edit_msgcode.getText().toString().trim();

        if (CheckUtils.isStrEmpty(account)) {
            showToast(""+mContext.getString(R.string.login_hint_mobile));

            return;
        }

        if (CheckUtils.isStrEmpty(msgcode)) {
            showToast(""+mContext.getString(R.string.edit_sms_num));

            return;
        }

        if (CheckUtils.isStrEmpty(pwd)) {
            showToast(""+mContext.getString(R.string.edit_new_pwd));
            return;
        }
        if (CheckUtils.isStrEmpty(pwd_confirm)) {
            showToast(""+mContext.getString(R.string.user_password_cofirm_empty));
            return;
        }

        if(!TextLengthUtil.mixLength(pwd)  ||  !TextLengthUtil.mixLength(pwd_confirm)){
            showToast(R.string.edit_mix_length);
            return;
        }


        if (!pwd.equals(pwd_confirm)) {
            showToast(""+mContext.getString(R.string.two_pw_difference));
            return;
        }

//        vertifyMsgcode(msgcode);
        RequestParams params = new RequestParams();
        showProgress();
        params.put("mobile",account);
        params.put("password", MD5.getMD5ofStrLowercase(pwd));
        params.put("smscode",msgcode);
        params.put("mobile_prefix", countryListDate.getTel_code() );
        HttpRestClient.post(Constants.URL_USER_FORGET_PWD, params, myHttpListener,
                HTTP_MODIFY, BasicBeanStr.class);
    }

    @OnClick(R.id.text_msgcode)
    void sendCode() {
        String account = edit_acount.getText().toString().trim();
        if (CheckUtils.isStrEmpty(account)) {
            showToast(""+mContext.getString(R.string.login_hint_mobile));
            return;
        }
        RequestParams params = new RequestParams();
        showProgress();

        params.put("mobile", account);
        params.put("type", "forget_password");
        params.put("mobile_prefix", countryListDate.getTel_code() );
        HttpRestClient.post(Constants.URL_USER_MSGCODE, params, myHttpListener,
                HTTP_SEND_MSGCODE, BasicBeanStr.class);
    }

    void sendCodeReal(String mobile) {
        if(TextUtils.isEmpty(mobile)){
            showToast(""+mContext.getString(R.string.no_bind_phone_num));
            return;
        }
        RequestParams params = new RequestParams();
        showProgress();
        params.put("mobile", mobile);
//        HttpRestClient.post(Constants.URL_MSGCODE_SEND, params, myHttpListener,
//                HTTP_SEND_MSGCODE, BasicBeanHC.class);
    }

    void vertifyMsgcode(String msgCode) {
        RequestParams params = new RequestParams();
        showProgress();
//        params.put("mobile", mobile);
        params.put("code", msgCode);
//        HttpRestClient.post(Constants.URL_MSGCODE_VERTIFY, params, myHttpListener,
//                HTTP_VERTIFY_MSGCODE, BasicBeanHC.class);

    }

    @OnClick(R.id.view_country)
    void countryClick() {
        Intent intent = new Intent(mContext, CountryChooseActivity.class);
        startActivityForResult(intent, REQUEST_COUNTRY);
    }

    void setCountryView(){
        text_mobile_zone_num.setText(countryListDate.getTel_code());
    }

    private final int REQUEST_COUNTRY = 15;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_COUNTRY:
                if (resultCode == Activity.RESULT_OK) {
                    countryListDate = (CountryListDate) data.getSerializableExtra("data");
                    setCountryView();
                }
                break;
            default:
                break;
        }
    }
}
