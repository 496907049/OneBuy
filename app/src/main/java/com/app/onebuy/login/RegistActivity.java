package com.app.onebuy.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.basis.BasisApp;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.BasicBeanStr;
import com.app.onebuy.bean.BasisBean;
import com.app.onebuy.bean.LoginBean;
import com.app.onebuy.login.countrychoose.CountryChooseActivity;
import com.app.onebuy.login.countrychoose.CountryListDate;
import com.app.onebuy.news.NewsH5DetailActivity;
import com.app.onebuy.util.TextLengthUtil;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.OnClick;
import my.CheckUtils;
import my.DialogUtils;
import my.MD5;
import my.http.HttpRestClient;
import my.http.MyHttpListener;
import my.useful.CountDownTimerUtils;

public class RegistActivity extends BasisActivity {


    @BindView(R.id.eidt_user)
    EditText edit_acount;
    @BindView(R.id.edit_pwd)
    EditText edit_pwd;
    @BindView(R.id.edit_pwd_confirm)
    EditText edit_pwd_confirm;
    @BindView(R.id.edit_msgcode)
    EditText edit_msgcode;

    @BindView(R.id.text_msgcode)
    TextView text_msgcode;

    CountDownTimerUtils countDownTimerUtils;
    KProgressHUD mProgressDialog;


    @BindView(R.id.text_mobile_zone_num)
    TextView text_mobile_zone_num;

    CountryListDate countryListDate;

    @Override
    public void initViews() {
        setDefautTrans(false);
        super.initViews();
        setContentView(R.layout.login_regist_activity);
        setTitle("");
        setTitleLeftButton(null);
        setTitleBg(R.color.transparent);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        check_rule.setSelected(isRuleCheck);

        countryListDate = CountryListDate.getFromCache();
        setCountryView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimerUtils != null) countDownTimerUtils.cancel();
    }

    private final static int HTTP_REGIST = 11;
    private final static int HTTP_SEND_MSGCODE = 12;
    private final static int HTTP_VERTIFY_MSGCODE = 13;
    MyHttpListener myHttpListener = new MyHttpListener() {
        @Override
        public void onSuccess(int httpWhat, Object result) {
            switch (httpWhat) {
                case HTTP_REGIST:
                    loginSucceed((LoginBean) result);
//                    setResult(Activity.RESULT_OK);
//                    DialogUtils.DialogOKmsgFinish(mContext,((BasicBeanStr) result).getStatusInfo());
                    break;
                case HTTP_SEND_MSGCODE:
                    showToast(((BasisBean) result).getStatusInfo());
                    countDownTimerUtils = new CountDownTimerUtils(text_msgcode, 60000, 1000);
                    countDownTimerUtils.start();
                    dismissProgress();
                    break;
                case HTTP_VERTIFY_MSGCODE:
                    postRegist();
                    break;
            }
        }

        @Override
        public boolean onHttpFailure(int httpWhat, Throwable arg3) {
            dismissProgress();
            return super.onHttpFailure(httpWhat, arg3);
        }

        @Override
        public void onFailure(int httpWhat, Object result) {
            super.onFailure(httpWhat, result);
            dismissProgress();
        }

        @Override
        public void onFinish(int httpWhat) {
//            dismissProgress();
//            mProgressDialog.dismiss();
        }
    };

    String account, pwd;

    @OnClick(R.id.btn_ok)
    void checkAndPost() {
        account = edit_acount.getText().toString().trim();
        pwd = edit_pwd.getText().toString().trim();
        String pwd_confirm = edit_pwd_confirm.getText().toString().trim();
        String msgcode = edit_msgcode.getText().toString().trim();

        if (CheckUtils.isStrEmpty(account)) {
            showToast(""+ getString(R.string.login_hint_mobile));
            return;
        }

        if (CheckUtils.isStrEmpty(pwd)) {
            showToast(""+getString(R.string.login_hint_pwd));
            return;
        }
        if (CheckUtils.isStrEmpty(pwd_confirm)) {
            showToast(""+getString(R.string.user_password_cofirm_empty));
            return;
        }
        if(!TextLengthUtil.mixLength(pwd) || !TextLengthUtil.mixLength(pwd_confirm)){
            showToast(getString(R.string.edit_mix_length));
            return;
        }
        if (!pwd.equals(pwd_confirm)) {
            showToast(""+getString(R.string.two_pw_difference));
            return;
        }
        if (CheckUtils.isStrEmpty(msgcode)) {
            showToast(""+getString(R.string.edit_sms_num));
            return;
        }

        if (!isRuleCheck) {
            showToast(""+mContext.getString(R.string.degree_hc));
            return;
        }

//        vertifyMsgcode(msgcode);
        postRegist();
    }

    @OnClick(R.id.text_msgcode)
    void sendCode() {
        String mobile = edit_acount.getText().toString().trim();
        if (!CheckUtils.isMobileNO(mobile)) {
            showToast(""+getString(R.string.user_mobile_error));
            return;
        }
        RequestParams params = new RequestParams();
        showProgress();
        params.put("mobile", mobile);
        params.put("type", "register");
        params.put("mobile_prefix", countryListDate.getTel_code() );
        HttpRestClient.post(Constants.URL_USER_MSGCODE, params, myHttpListener,
                HTTP_SEND_MSGCODE, BasicBeanStr.class);
    }

    void vertifyMsgcode(String msgCode) {
        RequestParams params = new RequestParams();
        showProgress();
//        params.put("mobile", mobile);
        params.put("code", msgCode);
//        HttpRestClient.post(Constants.URL_MSGCODE_VERTIFY, params, myHttpListener,
//                HTTP_VERTIFY_MSGCODE, BasicBeanHC.class);

    }

    void postRegist() {

        RequestParams params = new RequestParams();
        showProgress();
//        mProgressDialog.show();
        params.put("mobile", account);
        params.put("password", MD5.getMD5ofStrLowercase(pwd));
        params.put("smscode", edit_msgcode.getText().toString());
        params.put("mobile_prefix", countryListDate.getTel_code() );
        HttpRestClient.get(Constants.URL_USER_REGIST, params, myHttpListener,
                HTTP_REGIST, LoginBean.class);
    }

    private void loginSucceed(LoginBean bean) {
//        mSp.putString("personalUser",mEdtUser.getText().toString().trim());
//        mSp.putString("personalPass",mEdtPass.getText().toString().trim());
//        mSp.putString("personalData",bean.getData().getData());
        bean.save();
//        mSp.putIsLogined(true);
//        finish();
        setResult(Activity.RESULT_OK);
        DialogUtils.DialogOKmsgFinish(mContext, bean.getStatusInfo());

    }

    @OnClick(R.id.text_regist_rule)
    void onBtnRegistRule() {
//        Intent intent = new Intent(mContext,RegistRuleActivity.class);
//        startActivity(intent);
    }

    @BindView(R.id.check_rule)
    ImageView check_rule;

    boolean isRuleCheck = true;

    @OnClick(R.id.check_rule)
    void onRuleCheckClick() {
        isRuleCheck = !isRuleCheck;
        check_rule.setSelected(isRuleCheck);
    }

    @OnClick(R.id.text_regist_rule)
    void registRuleClick() {
        NewsH5DetailActivity.toDetail(mContext,"service_agreement",true);
//        showProgress();
//        RequestParams params = new RequestParams();
//        params.put("module","service_agreement");
//        HttpRestClient.get(Constants.URL_NEWS_ARTICLEPAGE, params, new MyHttpListener() {
//                    @Override
//                    public void onSuccess(int httpWhat, Object result) {
//                        NewsListData bean = (NewsListData) result;
//                        NewsH5DetailActivity.toDetail(mContext,bean.getTitle(),bean.getContent());
//                    }
//
//                    @Override
//                    public void onFinish(int httpWhat) {
//                        dismissProgress();
//                    }
//                }, 0, NewsListData.class);
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
