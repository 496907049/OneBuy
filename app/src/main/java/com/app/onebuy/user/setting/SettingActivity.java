package com.app.onebuy.user.setting;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.basis.BasisApp;
import com.app.onebuy.bean.LoginBean;
import com.app.onebuy.bean.UpdateBean;
import com.app.onebuy.news.NewsH5DetailActivity;
import com.app.onebuy.util.glide.GlideCatchUtil;
import com.bumptech.glide.Glide;
import com.flyco.dialog.listener.OnBtnClickL;

import butterknife.BindView;
import butterknife.OnClick;
import my.DialogUtils;

/***
 * 设置
 * **/
public class SettingActivity extends BasisActivity {

    @BindView(R.id.btn_logout)
    Button btn_logout;
    @BindView(R.id.text_cache)
    TextView text_cache;

    @Override
    public void initViews() {
        // TODO Auto-generated method stub
        super.initViews();
        setContentView(R.layout.setting_activity);
        setTitle(getString(R.string.app_setting));
        setTitleLeftButton(null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.initData(savedInstanceState);
//		UserBean userBean = (UserBean) ObjectCacheUtils
//				.readObj(UserBean.FILE_USER);

        if (LoginBean.isLogin()) { // 登入的页面
            btn_logout.setVisibility(View.VISIBLE);
        } else {
            btn_logout.setVisibility(View.GONE);
        }

        setCacheView();
    }

    void setCacheView(){
        text_cache.setText(GlideCatchUtil.getInstance().getCacheSize());
    }


    @OnClick(R.id.view_clear)
    void clear() {
        showLoading();
        Glide.get(mContext).clearMemory();
        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... strings) {
                GlideCatchUtil.getInstance().cleanCacheDisk();
                GlideCatchUtil.getInstance().clearCacheDiskSelf();
                GlideCatchUtil.getInstance().clearCacheMemory();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                hideLoading();
                showToast(getString(R.string.setting_clear_cache_success));
                setCacheView();
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }.execute("");
    }

    @OnClick(R.id.view_aboutus)
    void aboutUs() {
        NewsH5DetailActivity.toDetail(mContext, "about_us", true);
    }

    @OnClick(R.id.view_feedback)
    void feedback() {
//        ActivityTool.skipActivity(mContext, FeedBackActivity.class);
    }

    @OnClick(R.id.view_version)
    void version() {
        NewsH5DetailActivity.toDetail(mContext, "release_notes", true);
    }


    @OnClick(R.id.view_update)
    void update() {
        UpdateBean.check(mContext, true);
    }

    @OnClick(R.id.btn_logout)
    void logout() {
        DialogUtils.DialogTwo(mContext, "", getString(R.string.sure_user_logout), getString(R.string.user_logout), getString(R.string.app_cancel), new OnBtnClickL() {
            @Override
            public void onBtnClick() {
				LoginBean.logout(mContext);
//				btn_logout.setVisibility(View.GONE);
                finish();
            }
        }, null);
    }
}
