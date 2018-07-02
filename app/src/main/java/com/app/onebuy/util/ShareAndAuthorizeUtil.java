package com.app.onebuy.util;

import android.app.Activity;
import android.os.Message;

import com.app.onebuy.basis.BasisApp;
import com.bumptech.glide.gifdecoder.GifHeaderParser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import bc.app.jpush.uitool.ShareBoard;
import bc.app.jpush.uitool.ShareBoardlistener;
import bc.app.jpush.uitool.SnsPlatform;
import cn.jiguang.share.android.api.AuthListener;
import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatActionListener;
import cn.jiguang.share.android.api.Platform;
import cn.jiguang.share.android.api.ShareParams;
import cn.jiguang.share.android.model.AccessTokenInfo;
import cn.jiguang.share.android.model.BaseResponseInfo;
import cn.jiguang.share.android.model.UserInfo;
import cn.jiguang.share.android.utils.Logger;

import static android.R.attr.action;
import static com.app.onebuy.basis.BasisApp.showToast;
import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by otis on 2018/5/25.
 */

public class ShareAndAuthorizeUtil {

    public static ShareBoard mShareBoard;
    static int mAction = Platform.ACTION_SHARE;

    public static void showBroadView(Activity activity) {
        if (mShareBoard == null) {
            mShareBoard = new ShareBoard(activity);
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
        try {
            mShareBoard.show();
        }catch (Exception e){
            mShareBoard.close();
            mShareBoard = null;
        }
    }

    public static ShareBoardlistener mShareBoardlistener = new ShareBoardlistener() {
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

    public static PlatActionListener mShareListener = new PlatActionListener() {
        @Override
        public void onComplete(Platform platform, int action, HashMap<String, Object> data) {
            BasisApp.showToast("分享成功");
        }

        @Override
        public void onError(Platform platform, int action, int errorCode, Throwable error) {
            BasisApp.showToast("分享失败");
        }

        @Override
        public void onCancel(Platform platform, int action) {
            BasisApp.showToast("分享取消");
        }
    };




    //  第三方授权
    public static boolean toAuthorize(String platform) {
        final boolean[] authorize = new boolean[1];
        JShareInterface.authorize(platform, new AuthListener() {
            @Override
            public void onComplete(Platform platform, int i, BaseResponseInfo data) {
                Logger.dd(GifHeaderParser.TAG, "onComplete:" + platform + ",action:" + action + ",data:" + data);
                String toastMsg = null;
                switch (action) {
                    case Platform.ACTION_AUTHORIZING:
                        if (data instanceof AccessTokenInfo) {        //授权信息
                            String token = ((AccessTokenInfo) data).getToken();// token
                            long expiration = ((AccessTokenInfo) data).getExpiresIn();// token有效时间，时间戳
                            String refresh_token = ((AccessTokenInfo) data).getRefeshToken();//refresh_token
                            String openid = ((AccessTokenInfo) data).getOpenid();//openid
                            //授权原始数据，开发者可自行处理
                            String originData = data.getOriginData();
                            toastMsg = "授权成功:" + data.toString();
                            Logger.dd(GifHeaderParser.TAG, "openid:" + openid + ",token:" + token + ",expiration:" + expiration + ",refresh_token:" + refresh_token);
                            Logger.dd(GifHeaderParser.TAG, "originData:" + originData);
                            authorize[0] = true;
                        }
                        break;
                }
            }

            @Override
            public void onError(Platform platform, int i, int i1, Throwable throwable) {
                String toastMsg = null;
                switch (action) {
                    case Platform.ACTION_AUTHORIZING:
                        toastMsg = "授权失败";
                        authorize[0] = false;
                        break;
                }
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Logger.dd(GifHeaderParser.TAG, "onCancel:" + platform + ",action:" + action);
                String toastMsg = null;
                switch (action) {
                    case Platform.ACTION_AUTHORIZING:
                        toastMsg = "取消授权";
                        authorize[0] = true;
                        break;
                }
            }
        });
        return authorize[0];
    }

    //  第三方授权
    public static void removeAuthorize(String platform) {
        JShareInterface.removeAuthorize(platform, new AuthListener() {
            @Override
            public void onComplete(Platform platform, int i, BaseResponseInfo data) {
                Logger.dd(GifHeaderParser.TAG, "onComplete:" + platform + ",action:" + action + ",data:" + data);
                String toastMsg = null;
                switch (action) {
                    case Platform.ACTION_REMOVE_AUTHORIZING:
                        toastMsg = "删除授权成功";
                        break;
                }
            }

            @Override
            public void onError(Platform platform, int i, int i1, Throwable error) {
                Logger.dd(GifHeaderParser.TAG, "onError:" + platform + ",action:" + action + ",error:" + error);
                String toastMsg = null;
                switch (action) {
                    case Platform.ACTION_REMOVE_AUTHORIZING:
                        toastMsg = "删除授权失败";
                        break;
                }
            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
    }

    public static void getUserInfo(String platform) {
        JShareInterface.getUserInfo(platform, new AuthListener() {
            @Override
            public void onComplete(Platform platform, int i, BaseResponseInfo data) {
                Logger.dd(GifHeaderParser.TAG, "onComplete:" + platform + ",action:" + action + ",data:" + data);
                String toastMsg = null;
                switch (action) {
                    case Platform.ACTION_USER_INFO:
                        if (data instanceof UserInfo) {      //第三方个人信息
                            String openid = ((UserInfo) data).getOpenid();  //openid
                            String name = ((UserInfo) data).getName();  //昵称
                            String imageUrl = ((UserInfo) data).getImageUrl();  //头像url
                            int gender = ((UserInfo) data).getGender();//性别, 1表示男性；2表示女性
                            //个人信息原始数据，开发者可自行处理
                            String originData = data.getOriginData();
                            toastMsg = "获取个人信息成功:" + data.toString();
                            Logger.dd(GifHeaderParser.TAG, "openid:" + openid + ",name:" + name + ",gender:" + gender + ",imageUrl:" + imageUrl);
                            Logger.dd(GifHeaderParser.TAG, "originData:" + originData);
                        }
                        break;
                }
            }

            @Override
            public void onError(Platform platform, int i, int i1, Throwable error) {
                Logger.dd(GifHeaderParser.TAG, "onError:" + platform + ",action:" + action + ",error:" + error);
                String toastMsg = null;
                switch (action) {
                    case Platform.ACTION_USER_INFO:
                        toastMsg = "获取个人信息失败";
                        break;
                }
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Logger.dd(GifHeaderParser.TAG, "onCancel:" + platform + ",action:" + action);
                String toastMsg = null;
                switch (action) {
                    case Platform.ACTION_USER_INFO:
                        toastMsg = "取消获取个人信息";
                        break;
                }
            }
        });
    }


}
