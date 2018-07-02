package com.app.onebuy.user;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.BasicBeanStr;
import com.app.onebuy.bean.LoginBean;
import com.app.onebuy.util.TextLengthUtil;
import com.loopj.android.http.RequestParams;
import my.DialogUtils;
import my.MD5;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

import butterknife.BindView;


/**
 * 修改密码
 */
public class ModifyPwdActivity extends BasisActivity{

    @BindView(R.id.edit_old_pwd)
    EditText edit_old_pwd;
    @BindView(R.id.edit_new_pwd)
    EditText edit_new_pwd;
    @BindView(R.id.edit_pwd_confirm)
    EditText edit_pwd_confirm;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.user_modify_pwd_activity);
        setTitleLeftButton(null);
        setTitle(R.string.user_modify_password);
        setTitleRightText(R.string.complete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePwd();
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    private void changePwd() {
        String old = edit_old_pwd.getText().toString().trim();
        String newPwd = edit_new_pwd.getText().toString().trim();
        String confirm = edit_pwd_confirm.getText().toString().trim();
        if (TextUtils.isEmpty(old)){
            showToast(R.string.user_enter_old_password);
            return;
        }
        if (TextUtils.isEmpty(newPwd)){
            showToast(R.string.user_enter_new_password_a);
            return;
        }
        if (newPwd.equals(old)){
            showToast(R.string.user_newpassword_sure_not_same_as_old_password);
            return;
        }
        if (TextUtils.isEmpty(confirm)){
            showToast(R.string.user_password_cofirm_empty);
            return;
        }
        if (!newPwd.equals(confirm)){
            showToast(R.string.user_password_cofirm_notsame);
            return;
        }
        if(!TextLengthUtil.mixLength(old) || !TextLengthUtil.mixLength(confirm) || !TextLengthUtil.mixLength(newPwd)){
            showToast(R.string.edit_mix_length);
            return;
        }

        RequestParams params = new RequestParams();
        showProgress();
        params.put("mobile",LoginBean.getInstance().getMobile());
        params.put("password", MD5.getMD5ofStrLowercase(old));
        params.put("newpassword", MD5.getMD5ofStrLowercase(newPwd));
        HttpRestClient.post(Constants.URL_USER_MODIFY_PWD, params, myHttpListener,
                HTTP_MODIFY, BasicBeanStr.class);
    }
    private static final int HTTP_MODIFY=397;
    MyHttpListener myHttpListener = new MyHttpListener() {

        @Override
        public void onSuccess(int what, Object result) {
            // TODO Auto-generated method stub
            switch (what) {
                case HTTP_MODIFY:
                    setResult(Activity.RESULT_OK);
                    DialogUtils.DialogOKmsgFinish(mContext,((BasicBeanStr) result).getStatusInfo());
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onFailure(int httpWhat, Object result) {
            super.onFailure(httpWhat, result);
        }

        @Override
        public void onFinish(int what) {
            // TODO Auto-generated method stub
            dismissProgress();
//            onListViewComplete();
        }
    };
}
