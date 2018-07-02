package com.app.onebuy.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.InviteHasPresentBean;
import com.app.onebuy.home.goods.info.GoodsInfoActivity;
import com.app.onebuy.news.NewsH5DetailActivity;
import com.app.onebuy.other.DialogShare;
import com.app.onebuy.util.ShareAndAuthorizeUtil;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.OnClick;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

/**
 * Created by otis on 2018/6/7.
 */

public class InviteHasPresentActivity extends BasisActivity{

    @BindView(R.id.text_shareget)
    TextView mTvShareGet;
    @BindView(R.id.text_people)
    TextView mTvPeople;
    @BindView(R.id.text_intergral)
    TextView mTvIntergral;

    InviteHasPresentBean inviteHasPresentBean;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.user_invite_now_activity);

        setTitle(mContext.getString(R.string.invite_invite_has_present));
        setTitleLeftButton(R.drawable.base_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setTitleRightButton(R.mipmap.share_icon, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ShareAndAuthorizeUtil.showBroadView(InviteHasPresentActivity.this);
                toH5Activity();
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        getList();
    }

    private void getList(){
        RequestParams params = new RequestParams();
        HttpRestClient.get(Constants.URL_RECOMMEND_INDEX, params, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {
                        inviteHasPresentBean = (InviteHasPresentBean) result;
                        setView(inviteHasPresentBean);
                    }

                    @Override
                    public void onFinish(int httpWhat) {
                    }
                },
                0, InviteHasPresentBean.class);
    }

    private void setView(InviteHasPresentBean bean){
        mTvShareGet.setText(""+bean.getReward_price()+mContext.getString(R.string.unit_money));
        mTvPeople.setText(""+bean.getSuccess_num());
        mTvIntergral.setText(""+bean.getReward_price()+mContext.getString(R.string.unit_money));
    }

    private void toH5Activity(){
        NewsH5DetailActivity.toDetail(mContext,"recommended_rule",true);
    }

    @OnClick(R.id.view_list)
    void rewardlist(){
        Intent intent = new Intent(mContext,InviteRewardListActivity.class);
        intent.putExtra("data",inviteHasPresentBean);
        startActivity(intent);
//        ActivityTool.skipActivity(mContext,InviteRewardListActivity.class);
    }
    @OnClick(R.id.btn_ok)
    void share(){
        ShareAndAuthorizeUtil.showBroadView(InviteHasPresentActivity.this);
//        new DialogShare(mContext,inviteHasPresentBean).show();
    }

}
