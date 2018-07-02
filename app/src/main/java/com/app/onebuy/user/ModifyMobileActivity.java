package com.app.onebuy.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;
import android.widget.TextView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.BasicBeanStr;
import com.app.onebuy.bean.LoginBean;
import com.app.onebuy.login.countrychoose.CountryChooseActivity;
import com.app.onebuy.login.countrychoose.CountryListDate;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.OnClick;
import my.CheckUtils;
import my.DialogUtils;
import my.MD5;
import my.http.HttpRestClient;
import my.http.MyHttpListener;
import my.useful.CountDownTimerUtils;

public class ModifyMobileActivity extends BasisActivity {


    @BindView(R.id.edit_mobile)
    EditText edit_mobile;
    @BindView(R.id.text_msgcode)
    TextView text_msgcode;
    @BindView(R.id.edit_msgcode)
    EditText edit_msgcode;
    @BindView(R.id.edit_pwd)
    EditText edit_pwd;

    CountDownTimerUtils countDownTimerUtils;
    String mobile;

    @BindView(R.id.text_mobile_zone_num)
    TextView text_mobile_zone_num;

    CountryListDate countryListDate;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.user_modify_mobile_activity);

        setTitleLeftButton(null);
        setTitle(R.string.info_modify_mobile);
        edit_mobile.setFilters(new InputFilter[]{filter});
    }
    //禁止输入换行  和 空格
    private InputFilter filter=new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if(source.equals(" ")||source.toString().contentEquals("\n"))return "";
            else return null;
        }
    };

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setDetailView();

        countryListDate = CountryListDate.getFromCache();
        setCountryView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(countDownTimerUtils !=null)countDownTimerUtils.cancel();
    }


    private void setDetailView() {

    }


    private final static int HTTP_MODIFY = 11;
    private final static int HTTP_USERINFO = 12;
    private final static int HTTP_SEND_MSGCODE = 13;
    private final static int HTTP_VERTIFY_MSGCODE = 14;

    MyHttpListener myHttpListener = new MyHttpListener() {
        @Override
        public void onSuccess(int httpWhat, Object result) {
            switch (httpWhat) {
                case HTTP_MODIFY:
                    BasicBeanStr beanF = (BasicBeanStr) result;
                    LoginBean beanLogin = LoginBean.getInstance();
                    String mobile = edit_mobile.getText().toString();
                    beanLogin.setMobile(mobile);
//                    beanLogin.setRegMobile(mobile);
//                    beanLogin.setTransactorMobile(mobile);
                    beanLogin.save();
                    setResult(Activity.RESULT_OK);
                    DialogUtils.DialogOKmsgFinish(mContext,((BasicBeanStr) result).getStatusInfo());
                    break;
                case HTTP_USERINFO:
//                    loginSucceed((LoginBean) result);
                    break;
                case HTTP_SEND_MSGCODE:
//                    showToast(((BasicBeanHC)result).getStatusInfo());
                    countDownTimerUtils = new CountDownTimerUtils(text_msgcode,60000,1000);
                    countDownTimerUtils.start();
                    break;
                case HTTP_VERTIFY_MSGCODE:
//                    postModify();
                    break;
            }
        }

        @Override
        public void onFinish(int httpWhat) {
            dismissProgress();
        }
    };

//    @OnClick(R.id.btn_logout)
//    void logout() {
//        CustomDialog.Alert(mContext, "", "是否退出登录", "退出", "取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                LoginBean.logout(mContext);
//                ((UserBaseActivity) getActivity()).checkLogin();
//            }
//        }, null);
//
//    }

//    void getUserInfo(LoginFirst beanF) {
//        RequestParams params = new RequestParams();
//        showProgress();
//        params.put("type", "1");
//        params.put("name", beanF.getLoginId());
//        params.put("selkind", "1");
//        params.put("selobjtype", "1");
//        HttpRestClient.get(Constants.URL_USER_INFOBYUSER, params, myHttpListener,
//                HTTP_USERINFO, LoginBean.class);
//    }


    @OnClick(R.id.text_msgcode)
    void sendCode() {
        String mobile = edit_mobile.getText().toString().trim();
        if (!CheckUtils.isMobileNO(mobile)) {
            showToast("请输入正确的手机号码");
            return;
        }
        RequestParams params = new RequestParams();
        showProgress();
        params.put("mobile", mobile);
        params.put("type", "change_mobile");
        params.put("mobile_prefix", countryListDate.getTel_code() );
        HttpRestClient.post(Constants.URL_USER_MSGCODE, params, myHttpListener,
                HTTP_SEND_MSGCODE, BasicBeanStr.class);
    }


    @OnClick(R.id.btn_ok)
     void post() {
         mobile = edit_mobile.getText().toString().trim();
        String msgCode = edit_msgcode.getText().toString().trim();
        String pwd = edit_pwd.getText().toString().trim();
        if (!CheckUtils.isMobileNO(mobile)){
            showToast(R.string.login_hint_mobile);
            return;
        }
        if(CheckUtils.isStrEmpty(pwd)){
            showToast(R.string.user_password_hint);
            return;
        }
        if(CheckUtils.isStrEmpty(msgCode)){
            showToast(R.string.user_code_hint);
            return;
        }
//        vertifyMsgcode(msgCode);

        RequestParams params = new RequestParams();
        showProgress();
        params.put("mobile", mobile);
        params.put("password", MD5.getMD5ofStrLowercase(pwd));
        params.put("smscode", msgCode);
        HttpRestClient.post(Constants.URL_USER_MODIFY_MOBILE, params, myHttpListener,
                HTTP_MODIFY, BasicBeanStr.class);

    }


    void vertifyMsgcode(String msgCode) {
        RequestParams params = new RequestParams();
        showProgress();
        params.put("mobile", mobile);
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
