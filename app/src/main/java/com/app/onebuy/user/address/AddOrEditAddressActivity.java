package com.app.onebuy.user.address;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.EditText;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.AddressListData;
import com.app.onebuy.bean.BasicBeanStr;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.OnClick;
import my.DialogUtils;
import my.http.HttpRestClient;
import my.http.MyHttpListener;


/**
 * 增加或者编辑地址
 */
public class AddOrEditAddressActivity extends BasisActivity{

    @BindView(R.id.edit_name)
    EditText edit_name;
    @BindView(R.id.edit_mobile)
    EditText edit_mobile;
    @BindView(R.id.edit_detail_address)
    EditText edit_detail_address;

    AddressListData mListData;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.address_addaddress_actvity);
        setTitleLeftButton(null);
        setTitle(R.string.add_receiver);
        edit_mobile.setFilters(new InputFilter[]{filter});
    }

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

        mListData = (AddressListData) getIntent().getSerializableExtra("data");
        if(mListData != null){
            edit_name.setText(mListData.getRealname());
            edit_mobile.setText(mListData.getMobile());
            edit_detail_address.setText(mListData.getAddress());
        }
    }

    @OnClick(R.id.btn_ok)
     void addAddress() {
        String name = edit_name.getText().toString().trim();
        String mobile = edit_mobile.getText().toString().trim();
        String detailaddress = edit_detail_address.getText().toString().trim();
        if (TextUtils.isEmpty(name)){
            showToast(R.string.address_enter_delivery_name);
            return;
        }
        if (TextUtils.isEmpty(mobile)){
            showToast(R.string.address_enter_delivery_mobile);
            return;
        }
        if (TextUtils.isEmpty(detailaddress)){
            showToast(getString(R.string.please_edit)+" "+ getString(R.string.address_enter_detail_address));
            return;
        }
        RequestParams params = new RequestParams();
        showProgress();
        params.put("realname", name);
        params.put("mobile", mobile);
        params.put("area_ids", "3431");
        params.put("address", detailaddress);
        String url = Constants.URL_ADDRESS_ADD;
        if(mListData !=null){
            url = Constants.URL_ADDRESS_MODIFY;
            params.put("address_id", mListData.getAddress_id());
        }
        HttpRestClient.post(url, params, myHttpListener,
                HTTP_MODIFY, BasicBeanStr.class);
    }
    private static final int HTTP_MODIFY = 11;
    MyHttpListener myHttpListener = new MyHttpListener() {

        @Override
        public void onSuccess(int what, Object result) {
            // TODO Auto-generated method stub
            setResult(Activity.RESULT_OK);
            DialogUtils.DialogOKmsgFinish(mContext,((BasicBeanStr)result).getStatusInfo());
        }

        @Override
        public void onFailure(int httpWhat, Object result) {
            super.onFailure(httpWhat, result);
        }

        @Override
        public void onFinish(int what) {
            // TODO Auto-generated method stub
            dismissProgress();
        }
    };
}
