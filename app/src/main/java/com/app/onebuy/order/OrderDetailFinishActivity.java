package com.app.onebuy.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.bean.OrderListData;
import com.app.onebuy.show.AddShowActivity;

import butterknife.OnClick;

public class OrderDetailFinishActivity extends BasisActivity {


    OrderListData mDetailBean;
    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.order_detail_finish_activity);
        setTitle(R.string.order_detail);
        setTitleLeftButton(null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mDetailBean = (OrderListData) getIntent().getSerializableExtra("data");

    }

    private void save() {

    }

    @OnClick(R.id.btn_comment)
    void comment(){
        Intent intent = new Intent(mContext,AddShowActivity.class);
        intent.putExtra("id",mDetailBean.getPeriod_id());
        startActivityForResult(intent,REQUEST_DETAIL);
//        ActivityTool.skipActivity(mContext, AddShowActivity.class);
    }
    private final static int REQUEST_DETAIL = 0;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(Activity.RESULT_OK == resultCode){

        }
    }

}