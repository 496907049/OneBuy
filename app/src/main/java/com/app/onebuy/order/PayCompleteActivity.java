package com.app.onebuy.order;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;

import butterknife.BindView;

/**
 * Created by otis on 2018/6/12.
 */

public class PayCompleteActivity extends BasisActivity {

    private String payType;
    private String number;

    @BindView(R.id.pay_way)
    TextView MtvPayWay;
    @BindView(R.id.pay_num)
    TextView MtvPayNum;


    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.activity_pay_complete);
        setTitle(getString(R.string.pay_complete));
        setTitleLeftButton(R.drawable.base_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setTitleRightText(R.string.complete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        payType = getIntent().getStringExtra("pay_type");
        number = getIntent().getStringExtra("number");
        if (TextUtils.isEmpty(payType) || TextUtils.isEmpty(number) ) {
            finish();
            return;
        }

        switch (payType){
            case OrderSubmitActivity.PAY_TYPE_BALANCE:
                MtvPayWay.setText(""+getString(R.string.order_pay_byaccount));
                break;
            case OrderSubmitActivity.PAY_TYPE_WECHAT:
                MtvPayWay.setText(""+getString(R.string.order_pay_bywechat));
                break;
        }


        MtvPayNum.setText(""+number);

    }
}
