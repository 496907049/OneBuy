package com.app.onebuy.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.show.AddShowActivity;

import butterknife.BindView;
import butterknife.OnClick;
import my.ActivityTool;

import static com.app.onebuy.order.OrderWinListActivity.REQUEST_DETAIL;

/**
 * Created by otis on 2018/6/12.
 */

public class OrderCompleteActivity extends BasisActivity {

    private String periodId;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.activity_order_complete);
        setTitle(R.string.order_detail);
        setTitleLeftButton(R.drawable.base_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        periodId = getIntent().getStringExtra("period_id");
        if (TextUtils.isEmpty(periodId)) {
            finish();
            return;
        }
    }

    @OnClick(R.id.btn_goto_show)
    public void gotoShow(){
        Intent intent = new Intent(mContext,AddShowActivity.class);
        intent.putExtra("id",periodId);
        mContext.startActivityForResult(intent,REQUEST_DETAIL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(Activity.RESULT_OK == resultCode && requestCode == REQUEST_DETAIL){
            setResult(RESULT_OK);
            finish();
        }
    }

}
