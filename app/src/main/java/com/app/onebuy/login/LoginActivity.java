package com.app.onebuy.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.LoginBean;
import com.app.onebuy.home.HomeTabActivity;
import com.app.onebuy.login.countrychoose.CountryChooseActivity;
import com.app.onebuy.login.countrychoose.CountryListDate;
import com.app.onebuy.util.TextLengthUtil;
import com.flyco.dialog.listener.OnBtnClickL;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.OnClick;
import my.DialogUtils;
import my.MD5;
import my.MySharedPreferences;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

public class LoginActivity extends BasisActivity {



    @BindView(R.id.eidt_user)
    EditText edit_user;
    @BindView(R.id.edit_pwd)
    EditText edit_pwd;

    @BindView(R.id.text_mobile_zone_num)
    TextView text_mobile_zone_num;

    CountryListDate countryListDate;

    public static void ToLogin(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivityForResult(intent, requestCode);

    }

    public static boolean toLoginIfNotLogin(Activity context, int requestCode) {
        if (!LoginBean.isLogin()) {
            ToLogin(context, requestCode);
            return false;
        }
        return true;

    }
    public static boolean toLoginIfNotLoginDialog(final Activity context, final int requestCode) {
        if (!LoginBean.isLogin()) {
            DialogUtils.DialogTwo(context,"",context.getResources().getString(R.string.app_not_login_hint),context.getResources().getString(R.string.app_login_now),context.getResources().getString(R.string.app_cancel), new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    ToLogin(context, requestCode);
                }
            },null);

            return false;
        }
        return true;

    }

    public static void ToLogin(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);

    }


    public static void toLoadingAllClear(Context mContext) {
        LoginBean.logout();
        Intent intent = new Intent(mContext, HomeTabActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
//				| Intent.FLAG_ACTIVITY_NEW_TASK);
//		intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
//        mContext.finish();
    }

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.login_activity);
        setTitle("");
        setTitleBg(R.color.base_color_red);
        setTitleRightButton(R.drawable.icon_close_white, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        setTitleLeftButton(null);
        edit_user.setFilters(new InputFilter[]{filter});
    }

    //禁止输入换行  和   空格
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

        edit_user.setText("13695049660");
//        edit_user.setText("15880214362");
        edit_pwd.setText("123456");


        CountryListDate dateCountry = CountryListDate.getFromCache();
        if(dateCountry == null){
            countryListDate = new CountryListDate();
            countryListDate.setId("189");
            countryListDate.setEn_name("China");
            countryListDate.setCn_name("中国");
            countryListDate.setTel_code("86");
            countryListDate.save();
        }else{
            countryListDate = dateCountry;
        }

        setCountryView();
    }


    public static final int REQUEST_REGISTER = 11;
    private final int REQUEST_FOGET = 13;
    private final int REQUEST_COUNTRY = 15;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_FOGET:
                if (resultCode == Activity.RESULT_OK) {
                }
                break;
            case  REQUEST_REGISTER:
                if (resultCode == Activity.RESULT_OK) {
                    setResult(Activity.RESULT_OK);
                    finish();
                }
                break;
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

    @OnClick(R.id.img_close)
    void close() {
        finish();
    }

    @OnClick(R.id.btn_login)
    void login() {
        doLogin();
    }


    @OnClick(R.id.text_regist)
    void regist() {
        Intent intent = new Intent(this, RegistActivity.class);
        startActivityForResult(intent, REQUEST_REGISTER);
    }

    @OnClick(R.id.text_forgetpwd)
    void fogetpwd() {
        Intent intent = new Intent(this, ForgetPwdActivity.class);
        startActivityForResult(intent, REQUEST_FOGET);
    }


    String userName, password;

    void doLogin() {

        userName = edit_user.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            showToast(R.string.user_user_empty);
            return;
        }


            password = edit_pwd.getText().toString().trim();
            if (TextUtils.isEmpty(password)) {
                showToast(R.string.user_password_empty);
                return;
            }

            if(!TextLengthUtil.mixLength(password)){
                showToast(R.string.edit_mix_length);
                return;
            }

            RequestParams params = new RequestParams();
            params.put("mobile", userName);
            params.put("password", MD5.getMD5ofStrLowercase(password) );
            params.put("mobile_prefix", countryListDate.getTel_code() );
            showLoading();
            HttpRestClient.post(Constants.URL_USER_LOGIN, params, myHttpListener, HTTP_LOGIN, LoginBean.class);
//
    }

    static final int HTTP_LOGIN = 12;
    static final int HTTP_SMS = 13;
    MyHttpListener myHttpListener = new MyHttpListener() {
        @Override
        public void onSuccess(int httpWhat, Object result) {
            switch (httpWhat) {
                case HTTP_LOGIN:
                    LoginBean loginBean = (LoginBean) result;
                    onLoginSuccess(loginBean);
                    break;
//                case HTTP_SMS:
////                    showToast("短信验证码获取成功");
//                    showToast(((BasisBean) result).getResultData());
//                    new CountDownTimerUtils(btn_msgcode).start();
//                    break;
            }
        }

        @Override
        public void onFailure(int httpWhat, Object result) {
                super.onFailure(httpWhat, result);
        }

        @Override
        public void onFinish(int httpWhat) {
            dismissProgress();
        }
    };

    protected void onLoginSuccess(LoginBean loginBean) {
        showToast("登录成功");
        setResult(Activity.RESULT_OK);
        loginBean.save();
        MySharedPreferences mSp = new MySharedPreferences(this);
        mSp.putUser(userName);
        if (!TextUtils.isEmpty(password)) {
            mSp.putPassword(password);
        }
        finish();
    }

    private void onResultOk() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @OnClick(R.id.view_country)
    void countryClick() {
        Intent intent = new Intent(mContext, CountryChooseActivity.class);
        startActivityForResult(intent, REQUEST_COUNTRY);
    }

    void setCountryView(){
        text_mobile_zone_num.setText(countryListDate.getTel_code());
    }
}
