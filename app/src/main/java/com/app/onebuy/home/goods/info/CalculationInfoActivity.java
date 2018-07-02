package com.app.onebuy.home.goods.info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.CalInfoBean;
import com.app.onebuy.bean.Top100PeriodNumber;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.loopj.android.http.RequestParams;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

/**
 * Created by otis on 2018/5/21.
 */

public class CalculationInfoActivity extends BasisActivity {

    @BindView(R.id.tv_win_init_number)
    TextView mTvWinInitNumber;
    @BindView(R.id.tv_win_a_number)
    TextView mTvWinANumber;
    @BindView(R.id.tv_win_num)
    TextView mTvWinNumber;
    @BindView(R.id.tv_win_num2)
    TextView mTvWinNumber2;
    @BindView(R.id.tv_remainder)
    TextView mTvRemainder;
    @BindView(R.id.tv_open)
    TextView mTvOpen;
    @BindView(R.id.tv_win_order_num)
    TextView mTvWinOrderNum;
    @BindView(R.id.tv_win_a_number2)
    TextView mTvWinANumber2;
    @BindView(R.id.tv_win_init_number2)
    TextView mTvWinInitNumber2;
    @BindView(R.id.rv_open)
    RecyclerView mRecyclerView;
    BaseQuickAdapter adapter;
    private int periodId;
    private boolean isOpen = true;


    public static void toDetail(Context context,int period_id){
        Intent intent = new Intent(context,CalculationInfoActivity.class);
        intent.putExtra("period_id",period_id);
        context.startActivity(intent);
    }

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.activity_calculation_info);
        setTitle(""+getString(R.string.calculation_details));

        setTitleLeftButton(R.drawable.base_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        periodId =  getIntent().getIntExtra("period_id",-1);
        if (periodId == -1) {
            finish();
            return;
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        getList();
    }

    private void getList() {
        showProgress();
        RequestParams params = new RequestParams();
        params.put("period_id",periodId);
        HttpRestClient.get(Constants.URL_GOOD_PERIOD_INFO, params, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {
                        CalInfoBean.setCalInfoBean((CalInfoBean) result);
                        setView(CalInfoBean.getCalInfoBean());
                    }

                    @Override
                    public void onFinish(int httpWhat) {
                        dismissProgress();
                    }
                },
                0, CalInfoBean.class);
    }

    private void setView(CalInfoBean bean){
        mTvWinInitNumber.setText(""+bean.getWin_init_number());
        mTvWinInitNumber2.setText(""+bean.getWin_init_number());
        mTvWinANumber.setText(""+bean.getWin_a_number());
        mTvWinANumber2.setText(""+bean.getWin_a_number());
        mTvWinNumber.setText(""+bean.getWin_number());
        mTvWinNumber2.setText(""+bean.getWin_number());
        mTvRemainder.setText(""+bean.getWin_remainder());
        mTvWinOrderNum.setText(""+bean.getWin_order_number());
    }

    @OnClick(R.id.tv_open)
    public void openOnclick(){
        if(adapter == null){
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));    //一条数据
            List<Top100PeriodNumber> list =  CalInfoBean.getCalInfoBean().getTop100_period_number();
            list.add(0,null);
            adapter = new CalculationAdapter(R.layout.activity_calculation_adapter, list);
            adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setNestedScrollingEnabled(false);
        }
        if(isOpen){
            mRecyclerView.setVisibility(View.VISIBLE);
            mTvOpen.setText(""+getString(R.string.take_up));
        }else {
            mTvOpen.setText(""+getString(R.string.spread));
            mRecyclerView.setVisibility(View.GONE);
        }
        isOpen = !isOpen;
    }

}
