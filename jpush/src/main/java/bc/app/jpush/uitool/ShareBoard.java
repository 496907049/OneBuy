package bc.app.jpush.uitool;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.jiguang.share.android.api.ShareParams;
import cn.jiguang.share.facebook.Facebook;
import cn.jiguang.share.facebook.messenger.FbMessenger;
import cn.jiguang.share.qqmodel.QQ;
import cn.jiguang.share.qqmodel.QZone;
import cn.jiguang.share.twitter.Twitter;
import cn.jiguang.share.wechat.Wechat;
import cn.jiguang.share.wechat.WechatFavorite;
import cn.jiguang.share.wechat.WechatMoments;
import cn.jiguang.share.weibo.SinaWeibo;
import cn.jiguang.share.weibo.SinaWeiboMessage;

/**
 * Created by cloud on 17/1/13.
 */

public class ShareBoard {
    private String mFrom = null;
    private ShareBoardlistener boardlistener = null;
    private Activity activity;
    private List<SnsPlatform> platformlist = new ArrayList();
    private List<ShareParams> contentlist = new ArrayList();
    private View showatView = null;
    private ShareBoardView mShareBoardView;
    private ShareBoardConfig mConfig = null;

    public ShareBoard(Activity activity) {
        if (activity != null) {
            this.activity = (Activity) (new WeakReference(activity)).get();
        }

    }

    public ShareBoard(Activity activity, ShareBoardConfig config) {
        mConfig = config;
        if (activity != null) {
            this.activity = (Activity) (new WeakReference(activity)).get();
        }

    }

    public String getFrom() {
        return this.mFrom;
    }


    public ShareBoard setShareboardclickCallback(ShareBoardlistener listener) {
        this.boardlistener = listener;
        return this;
    }

    public ShareBoard setPlatformList(List<SnsPlatform> list) {
        if(list != null) {
            this.platformlist.clear();
            Iterator var2 = list.iterator();
            while (var2.hasNext()) {
                SnsPlatform temp = (SnsPlatform) var2.next();
                addPlatform(temp);
            }
        }
        return this;
    }

    public ShareBoard addPlatform(SnsPlatform snsPlatform) {
        if (snsPlatform != null && !platformlist.contains(snsPlatform)) {
            this.platformlist.add(snsPlatform);
        }
        return this;
    }

    public ShareBoard clearPlatform(){
        this.platformlist.clear();
        return this;
    }


    public ShareBoard addButton(String showword, String Keyword, String icon, String Grayicon) {
        this.platformlist.add(createSnsPlatform(showword, Keyword, icon, Grayicon, 0));
        return this;
    }

    public static SnsPlatform createSnsPlatform(String showword, String Keyword, String icon, String Grayicon, int indext) {
        SnsPlatform var5 = new SnsPlatform(Keyword);
        var5.mShowWord = showword;
        var5.mIcon = icon;
        var5.mGrayIcon = Grayicon;
        var5.mIndex = indext;
        return var5;
    }

    public static SnsPlatform createSnsPlatform(String platformName) {
        String mShowWord = platformName;
        String mIcon = "";
        String mGrayIcon = "";
        String mKeyword = platformName;
        if (Wechat.Name.equals(platformName)) {
            mIcon = "jiguang_socialize_wechat";
            mGrayIcon = "jiguang_socialize_wechat";
            mShowWord = "jiguang_socialize_text_weixin_key";
        } else if (WechatMoments.Name.equals(platformName)) {
            mIcon = "jiguang_socialize_wxcircle";
            mGrayIcon = "jiguang_socialize_wxcircle";
            mShowWord = "jiguang_socialize_text_weixin_circle_key";

        } else if (WechatFavorite.Name.equals(platformName)) {
            mIcon = "jiguang_socialize_wxfavorite";
            mGrayIcon = "jiguang_socialize_wxfavorite";
            mShowWord = "jiguang_socialize_text_weixin_favorite_key";

        } else if (SinaWeibo.Name.equals(platformName)) {
            mIcon = "jiguang_socialize_sina";
            mGrayIcon = "jiguang_socialize_sina";
            mShowWord = "jiguang_socialize_text_sina_key";
        } else if (SinaWeiboMessage.Name.equals(platformName)) {
            mIcon = "jiguang_socialize_sina";
            mGrayIcon = "jiguang_socialize_sina";
            mShowWord = "jiguang_socialize_text_sina_msg_key";
        } else if (QQ.Name.equals(platformName)) {
            mIcon = "jiguang_socialize_qq";
            mGrayIcon = "jiguang_socialize_qq";
            mShowWord = "jiguang_socialize_text_qq_key";

        } else if (QZone.Name.equals(platformName)) {
            mIcon = "jiguang_socialize_qzone";
            mGrayIcon = "jiguang_socialize_qzone";
            mShowWord = "jiguang_socialize_text_qq_zone_key";
        } else if (Facebook.Name.equals(platformName)) {
            mIcon = "jiguang_socialize_facebook";
            mGrayIcon = "jiguang_socialize_facebook";
            mShowWord = "jiguang_socialize_text_facebook_key";
        } else if (FbMessenger.Name.equals(platformName)) {
            mIcon = "jiguang_socialize_messenger";
            mGrayIcon = "jiguang_socialize_messenger";
            mShowWord = "jiguang_socialize_text_messenger_key";
        }else if (Twitter.Name.equals(platformName)) {
            mIcon = "jiguang_socialize_twitter";
            mGrayIcon = "jiguang_socialize_twitter";
            mShowWord = "jiguang_socialize_text_twitter_key";
        }
        return ShareBoard.createSnsPlatform(mShowWord, mKeyword, mIcon, mGrayIcon, 0);
    }


    public void show() {
        this.mShareBoardView = new ShareBoardView(this.activity, this.platformlist, mConfig);
        if (this.boardlistener != null) {
            this.mShareBoardView.setShareBoardlistener(this.boardlistener);
        }
        this.mShareBoardView.setFocusable(true);
        this.mShareBoardView.setBackgroundDrawable(new BitmapDrawable());
        if (this.showatView == null) {
            this.showatView = this.activity.getWindow().getDecorView();
        }
        this.mShareBoardView.showAtLocation(this.showatView, Gravity.BOTTOM, 0, 0);
    }

    public void close() {
        if (this.mShareBoardView != null) {
            this.mShareBoardView.dismiss();
            this.mShareBoardView = null;
        }

    }

}
