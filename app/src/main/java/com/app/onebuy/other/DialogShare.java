package com.app.onebuy.other;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisApp;
import com.app.onebuy.bean.InviteHasPresentBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.flyco.animation.SlideEnter.SlideBottomEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.widget.base.BottomBaseDialog;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatActionListener;
import cn.jiguang.share.android.api.Platform;
import cn.jiguang.share.android.api.ShareParams;
import cn.jiguang.share.wechat.Wechat;
import cn.jiguang.share.wechat.WechatMoments;
import my.ImageUtils;
import my.IntentUtils;
import my.LogUtil;

public class DialogShare extends BottomBaseDialog<DialogShare> {


    String content;
    KProgressHUD mProgress;
    Activity mContext;
    String url;
    String title;
    String copycontent;
    String imgurl;
    String invite_share_friends_title;
    Bitmap bitmap;

    boolean isShowCopy = false;

    @BindView(R.id.view_copy)
    View view_copy;
    @BindView(R.id.view_holder)
    View view_holder;

    public DialogShare(Activity context, String  content) {
        super(context);
        setCanceledOnTouchOutside(false);

        this.mContext = context;
        this.title = content;
        this.content =content;
//        this.url = content;
        this.copycontent = content;
        this.invite_share_friends_title = content;
    }
    public DialogShare(Activity context, InviteHasPresentBean shareBean) {
        super(context);
        setCanceledOnTouchOutside(false);

        this.mContext = context;
        this.title = shareBean.getShare_title();
        this.content =shareBean.getShare_content();
        this.url = shareBean.getRecommended_url();
        this.copycontent = shareBean.getShare_content();
        this.imgurl = shareBean.getShare_imgage_id();
        this.invite_share_friends_title = shareBean.getShare_content();

            isShowCopy = false;
        if(!TextUtils.isEmpty(imgurl)){
            Glide.with(mContext).load(imgurl).asBitmap().into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                  @Override
                   public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                      bitmap = resource;
                      if(bitmap.getWidth() != bitmap.getHeight()){
                          bitmap  = ImageUtils.zoomImg(bitmap,400,400);
                      }
                  }
            } );
        }

    }

//    public DialogShare(Activity context, ProjectListData data) {
//        super(context);
//        setCanceledOnTouchOutside(false);
//
//        this.mContext = context;
//        this.title = data.getTitle();
//        this.content =data.getBrief();
//        this.url = data.getWeb_address();
////        this.copycontent = shareBean.getInvite_share_copytext();
////        this.imgurl = shareBean.getInvite_share_icon();
////        this.invite_share_friends_title = shareBean.getInvite_share_friends_title();
//
//        if(!TextUtils.isEmpty(imgurl)){
//            Glide.with(mContext).load(imgurl).asBitmap().listener(new RequestListener<String, Bitmap>() {
//                @Override
//                public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
//                    return false;
//                }
//
//                @Override
//                public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                    bitmap = resource;
//                    return false;
//                }
//            }).into(500,500);
//        }
//
//    }

    @Override
    public View onCreateView() {
//        widthScale(1.0f);
//        showAnim(new FadeEnter());
//        dismissAnim(new FadeExit());

//        heightScale(1.0f);
        showAnim(new SlideBottomEnter());
        dismissAnim(new SlideBottomExit());
        View inflate = View.inflate(mContext, R.layout.share_view, null);
        ButterKnife.bind(this, inflate);
//        inflate.setBackgroundDrawable(
//                CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), dp2px(5)));

        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        mProgress = KProgressHUD.create(mContext, KProgressHUD.Style.SPIN_INDETERMINATE).setSize(75,75).setCancellable(true);

        if(isShowCopy){
            view_holder.setVisibility(View.GONE);
            view_copy.setVisibility(View.VISIBLE);
        }else{
            view_holder.setVisibility(View.INVISIBLE);
            view_copy.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.view_copy)
    void copy(){
        IntentUtils.CopyToClipboard(mContext,copycontent);
        BasisApp.showToast("已将分享内容拷贝至剪贴板");
        dismiss();
    }


    @OnClick(R.id.view_wechat)
    void wechat(){
//        new ShareAction(mContext)
//                .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
//                .withText("hello")//分享内容
//                .withMedia(new UMImage(mContext,""))
//                .setCallback(umShareListener)//回调监听器
//                .share();

         ShareParams shareParams = new ShareParams();
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
//        shareParams.setTitle(mContext.getResources().getString(R.string.app_name));
        shareParams.setTitle(title);
        shareParams.setText(content);
        shareParams.setUrl(url);
        if(TextUtils.isEmpty(imgurl)){
            shareParams.setImageData(ImageUtils.getBitmapForResource(mContext,R.mipmap.ic_launcher));
        }else{
            if(bitmap == null){
                shareParams.setImageData(ImageUtils.getBitmapForResource(mContext,R.mipmap.ic_launcher));
            }else{
                shareParams.setImageData(bitmap);
            }

        }
        //        shareParams.setImageUrl("图片地址");
        JShareInterface.share(Wechat.Name, shareParams, new PlatActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                LogUtil.i("onComplete--->");
            }

            @Override
            public void onError(Platform platform, int i, int i1, Throwable throwable) {
            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        dismiss();
//        new AndroidShare(mContext).shareWeChatFriend(mContext.getResources().getString(R.string.app_name),content,AndroidShare.TEXT,null);
    }
    @OnClick(R.id.view_wxcircle)
    void wechatCircle(){
//        new ShareAction(mContext)
//                .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
//                .withText("hello")//分享内容
//                .withMedia(new UMImage(mContext,""))
//                .setCallback(umShareListener)//回调监听器
//                .share();

        final ShareParams shareParams = new ShareParams();
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
//        shareParams.setTitle(mContext.getResources().getString(R.string.app_name));
        shareParams.setTitle(invite_share_friends_title);
        shareParams.setText(invite_share_friends_title);
        shareParams.setUrl(url);
        if(TextUtils.isEmpty(imgurl)){
            shareParams.setImageData(ImageUtils.getBitmapForResource(mContext,R.mipmap.ic_launcher));
        }else{
            if(bitmap == null){
                shareParams.setImageData(ImageUtils.getBitmapForResource(mContext,R.mipmap.ic_launcher));
            }else{
                shareParams.setImageData(bitmap);
            }
//            shareParams.setImageUrl(imgurl);
        }
//        shareParams.setImageUrl("图片地址");
        JShareInterface.share(WechatMoments.Name, shareParams, new PlatActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

            }

            @Override
            public void onError(Platform platform, int i, int i1, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        dismiss();
//        new AndroidShare(mContext).shareWeChatFriendCircle(mContext.getResources().getString(R.string.app_name),content, BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher));
    }

    @OnClick(R.id.btn_close)
    void close(){
        dismiss();
    }

    @Override
    public void dismiss() {
        bitmap = null;
        super.dismiss();
    }
}
