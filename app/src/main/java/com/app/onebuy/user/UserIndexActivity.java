package com.app.onebuy.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.app.onebuy.R;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.LoginBean;
import com.app.onebuy.bean.UserAccountInfoBean;
import com.app.onebuy.bean.UserPhotoUploadBean;
import com.app.onebuy.home.HomeBaseActivity;
import com.app.onebuy.login.LoginActivity;
import com.app.onebuy.order.OrderBaseActivity;
import com.app.onebuy.order.OrderWinListActivity;
import com.app.onebuy.show.MyShowActivity;
import com.app.onebuy.user.address.AddressManageActivity;
import com.app.onebuy.user.setting.SettingActivity;
import com.app.onebuy.view.PopLanguageSetting;
import com.bumptech.glide.Glide;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import bc.app.jpush.uitool.ShareBoard;
import bc.app.jpush.uitool.ShareBoardlistener;
import bc.app.jpush.uitool.SnsPlatform;
import butterknife.BindView;
import butterknife.OnClick;
import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatActionListener;
import cn.jiguang.share.android.api.Platform;
import cn.jiguang.share.android.api.ShareParams;
import my.ActivityTool;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

public class UserIndexActivity extends HomeBaseActivity {


    @BindView(R.id.text_nologin)
    TextView text_nologin;
    @BindView(R.id.text_acount_left)
    TextView text_acount_left;
    @BindView(R.id.text_name)
    TextView text_name;
    @BindView(R.id.view_login)
    View view_login;
    @BindView(R.id.img_photo)
    RoundedImageView img_photo;


    @Override
    public void initViews() {
        setContentView(R.layout.user_index_activity);
        super.initViews();
        setTitleRightButton(R.drawable.icon_setting, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityTool.skipActivity(mContext, SettingActivity.class);
//                showBroadView();
            }
        });

        setTitleBg(R.color.transparent);
        setTitle("");
        setTitleLeftText(R.string.setting_change_language, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popLanguage(view);
            }
        });

    }

    private ShareBoard mShareBoard;
    private int mAction = Platform.ACTION_SHARE;

    private void showBroadView() {
        if (mShareBoard == null) {
            mShareBoard = new ShareBoard(this);
            List<String> platforms = JShareInterface.getPlatformList();
            if (platforms != null) {
                Iterator var2 = platforms.iterator();
                while (var2.hasNext()) {
                    String temp = (String) var2.next();
                    SnsPlatform snsPlatform = ShareBoard.createSnsPlatform(temp);
                    mShareBoard.addPlatform(snsPlatform);
                }
            }
            mShareBoard.setShareboardclickCallback(mShareBoardlistener);
        }
        mShareBoard.show();
    }

    private ShareBoardlistener mShareBoardlistener = new ShareBoardlistener() {
        @Override
        public void onclick(SnsPlatform snsPlatform, String platform) {

            switch (mAction) {
                case Platform.ACTION_SHARE:
                    //这里以分享链接为例
                    ShareParams shareParams = new ShareParams();
                    shareParams.setShareType(Platform.SHARE_WEBPAGE);
                    shareParams.setTitle("标题");
                    shareParams.setText("文本");
                    shareParams.setShareType(Platform.SHARE_WEBPAGE);
                    shareParams.setUrl("http://www.baidu.com");
//                    shareParams.setImagePath("图片");
                    JShareInterface.share(platform, shareParams, mShareListener);
                    break;
                default:
                    break;
            }
        }
    };

    private PlatActionListener mShareListener = new PlatActionListener() {
        @Override
        public void onComplete(Platform platform, int action, HashMap<String, Object> data) {
            showToast("分享成功");
        }

        @Override
        public void onError(Platform platform, int action, int errorCode, Throwable error) {
            showToast("分享失败");
        }

        @Override
        public void onCancel(Platform platform, int action) {
            showToast("分享取消");
        }
    };

    void popLanguage(View view) {
        PopLanguageSetting mQuickCustomPopup = new PopLanguageSetting(mContext);
        mQuickCustomPopup
                .showAnim(null)
                .dismissAnim(null)
                .dimEnabled(true)
                .anchorView(view)
                .offset(2, 0)
                .dimEnabled(false)
                .gravity(Gravity.BOTTOM)
                .show();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LoginBean.isLogin()){
            getList();
        }else {
            setUserView();
        }
    }

    private void getList(){
        RequestParams params = new RequestParams();
        showProgress();
        HttpRestClient.get(Constants.URL_USER_USERINFO, params, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {
                        LoginBean loginBean = (LoginBean) result;
                        loginBean.save();
                        setUserView();
                    }

                    @Override
                    public void onFinish(int httpWhat) {
                        hideLoading();
//                        if (!LoginActivity.toLoginIfNotLogin(mContext, REQUEST_LOGIN)) return;
                    }

                    @Override
                    public void onFailure(int httpWhat, Object result) {
                        super.onFailure(httpWhat, result);
                    }
                },
                0, LoginBean.class);
    }


    private void setUserView() {
        if (LoginBean.isLogin()) {
            text_nologin.setVisibility(View.GONE);
            view_login.setVisibility(View.VISIBLE);
            LoginBean loginBean = LoginBean.getInstance();
            if (TextUtils.isEmpty(loginBean.getNickname())) {
                text_name.setText(loginBean.getMobile());
            } else {
                text_name.setText(loginBean.getNickname());
            }
            text_acount_left.setText(loginBean.getAccount_info().getUsable_account());
            Glide.with(mContext).load(loginBean.getHead_url()).asBitmap().placeholder(R.drawable.user_icon_default).into(img_photo);
//            BasisApp.loadImg(loginBean.getSso_head_url(),img_photo);
        } else {
            text_nologin.setVisibility(View.VISIBLE);
            view_login.setVisibility(View.GONE);
            img_photo.setImageResource(R.drawable.user_icon_default);
            text_acount_left.setText("0");
        }
    }

    @OnClick(R.id.view_user)
    void onUserClick() {

        if (!LoginActivity.toLoginIfNotLogin(mContext, REQUEST_LOGIN)) return;
        ActivityTool.skipActivity(mContext, UserInfoActivity.class);
    }

    @OnClick(R.id.btn_charge)
    void charge() {
        if (!LoginActivity.toLoginIfNotLogin(mContext, REQUEST_LOGIN)) return;
        ActivityTool.skipActivity(mContext, ChargeActivity.class);
    }

    @OnClick(R.id.view_cyjl)
    void joinRecords() {

        if (!LoginActivity.toLoginIfNotLogin(mContext, REQUEST_LOGIN)) return;
        ActivityTool.skipActivity(mContext, OrderBaseActivity.class);
    }

    @OnClick(R.id.view_zjjl)
    void myLucky() {

        if (!LoginActivity.toLoginIfNotLogin(mContext, REQUEST_LOGIN)) return;
        ActivityTool.skipActivity(mContext, OrderWinListActivity.class);
    }

    @OnClick(R.id.view_address)
    void address() {

        if (!LoginActivity.toLoginIfNotLogin(mContext, REQUEST_LOGIN)) return;
        ActivityTool.skipActivity(mContext, AddressManageActivity.class);
    }

    @OnClick(R.id.view_myaccount)
    void myaccount() {

        if (!LoginActivity.toLoginIfNotLogin(mContext, REQUEST_LOGIN)) return;
        ActivityTool.skipActivity(mContext, MyAccountActivity.class);
    }

    @OnClick(R.id.view_myshows)
    void myshows() {
        if (!LoginActivity.toLoginIfNotLogin(mContext, REQUEST_LOGIN)) return;
        ActivityTool.skipActivity(mContext, MyShowActivity.class);
    }

    @OnClick(R.id.view_invite_has_present)
    void inviteHasPresent() {
        if (!LoginActivity.toLoginIfNotLogin(mContext, REQUEST_LOGIN)) return;
        ActivityTool.skipActivity(mContext, InviteHasPresentActivity.class);
    }

    final int REQUEST_LOGIN = 11;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
