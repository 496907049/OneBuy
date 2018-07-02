package com.app.onebuy.home;

import android.os.Bundle;
import android.view.View;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.gyf.barlibrary.ImmersionBar;


/**
 * tab中activity基础类
 */

public class HomeBaseActivity extends BasisActivity {

    HomeTabActivity homeTabActivity;

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        homeTabActivity = (HomeTabActivity) getParent();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        checkTitleBarHeight();
    }

    private void checkTitleBarHeight() {
        ImmersionBar mImmersionBar =getImmersionBar();
        View titleview = findViewById(R.id.base_title_view);
        if(titleview == null)return;
//        mImmersionBar.titleBarMarginTop(titleview);
        mImmersionBar.titleBar(titleview);
//        int statusBarHeight = SystemParamsUtils.getStatusBarHeight(mContext);
//        LogUtil.i("statusBarHeight----》"+statusBarHeight);
//        if(statusBarHeight == 0)return;
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) titleview.getLayoutParams();
//        params.topMargin = statusBarHeight;
//        titleview.setLayoutParams(params);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        homeTabActivity.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        return super.onKeyDown(keyCode, event);
//    }
}
