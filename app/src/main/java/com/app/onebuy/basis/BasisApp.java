package com.app.onebuy.basis;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.onebuy.R;
import com.bumptech.glide.Glide;
import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceBottomEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatformConfig;
import cn.jpush.android.api.JPushInterface;

public class BasisApp extends Application {
	private static BasisApp APP;

	public static Context mContext;
	private static Handler handler;
	public static String DIR_PATH_USER;

	public static boolean isProgramExit = false;
	static Toast mToast;

	public static BaseAnimatorSet bas_in = new BounceBottomEnter();
	public static BaseAnimatorSet bas_out = new SlideBottomExit();
	
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getBaseContext();
		isProgramExit = false;
		APP = this;
//		SDKInitializer.initialize(this);
		handler = new Handler();
		DIR_PATH_USER = getFilesDir().getAbsolutePath() + "/";
		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
//				SDKInitializer.initialize(this);
//		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
		ChangeLanguageHelper.init(this);

		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
		JShareInterface.setDebugMode(true);
		PlatformConfig platformConfig = new PlatformConfig()
//				.setWechat("wxc40e16f3ba6ebabc", "dcad950cd0633a27e353477c4ec12e7a")
//				.setQQ("1106011004", "YIbPvONmBQBZUGaN")
//				.setSinaWeibo("374535501", "baccd12c166f1df96736b51ffbf600a2", "https://www.jiguang.cn")
				.setFacebook("appId", "com.app.onebuy")
				.setTwitter("consumerKey", "consumerSecret");
		JShareInterface.init(this,platformConfig);
	}


	public static BasisApp getInstance() {
		return APP;
	}

	/**
	 * 短消息提示
	 */
	public static void showToast(final int resId) {
		handler.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
//				if (mToast != null) {
//					mToast.cancel();
//				}
				mToast = Toast.makeText(mContext, resId, Toast.LENGTH_SHORT);
				mToast.show();
			}
		});
	}

	/**
	 * 短消息提示
	 */
	public static void showToast(final String text) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
//				if (mToast != null) {
//					mToast.cancel();
//				}
				mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
				mToast.show();
			}
		});
	}


	public static void loadImg(Context mContext, String imgurl, ImageView imageView, int res_default){

//		if(TextUtil.isEmpty(imgurl))return;
		Glide.with(mContext).load(imgurl)
//				.apply(new RequestOptions().placeholder(res_default).error(res_default))
				.placeholder(res_default).error(res_default)
				.into(imageView);
	}
	public static void loadImg(Context mContext, String imgurl, ImageView imageView){
		loadImg(mContext,imgurl,imageView, R.drawable.base_transparent);
	}
	public static void loadImg(String imgurl, ImageView imageView){
		loadImg(BasisApp.mContext,imgurl,imageView,R.drawable.base_transparent);
	}

	public static void loadImgAsbitmap(Context mContext, String imgurl, ImageView imageView, int res_default) {

//		if(TextUtil.isEmpty(imgurl))return;
		Glide.with(mContext)
//				.asBitmap()
				.load(imgurl)
				.asBitmap()
				.placeholder(res_default)
//				.apply(new RequestOptions().placeholder(res_default).error(res_default))
				.into(imageView);
	}

	public static String getStringL(int resid){
		return mContext.getResources().getString(resid);
//		return  ChangeLanguageHelper.getStringById(resid);
	}
}
