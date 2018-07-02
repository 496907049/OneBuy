package com.app.onebuy.home.goods.info.fragment.img;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisFragment;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.GoodsTypeBean;
import com.app.onebuy.other.PhotoScanActivity;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import my.LogUtil;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

/**
 * Created by otis on 2018/4/19.
 */
@SuppressLint("ValidFragment")
public class ImageTextInfoFragment extends BasisFragment {

    @BindView(R.id.webview)
    WebView mWebView;

    private int goodsId;

    public static ImageTextInfoFragment getInstance(int goodsId) {
        ImageTextInfoFragment fragment = new ImageTextInfoFragment();
        fragment.goodsId = goodsId;
        return fragment;
    }
    @SuppressLint("SetJavaScriptEnabled")
    @JavascriptInterface
    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.activity_h5_detail);
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(false);
        mWebView.getSettings().setBuiltInZoomControls(false);
//		mWebView.setInitialScale(20);
//		mWebView.setDownloadListener(downloadListener);
//		mWebView.getSettings().setUseWideViewPort(true);
//		mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//         mWebView.getSettings().setAppCachePath("external/chromium/net/disk_cache/");
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.setSaveEnabled(true);
        mWebView.getSettings().setAllowContentAccess(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mWebView.getSettings().setDomStorageEnabled(true);
        // setZoomControlGone(mWebView);
//        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.clearCache(true);
//		webSet.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //
        mWebView.setWebViewClient(new MyWebViewClient());
//        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.setWebChromeClient(chromeClient);
        mWebView.requestFocus();

        mWebView.addJavascriptInterface(new MJavascriptInterface(), "imagelistener");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(mContext);
        ImageLoader.getInstance().init(configuration);

        getDetail();
    }

    void getDetail() {
        showProgress();
        RequestParams params = new RequestParams();
        params.put("goods_id", goodsId);
        showProgress();
        HttpRestClient.get(Constants.URL_GET_CONTENT, params, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {
                        GoodsTypeBean bean = (GoodsTypeBean) result;
                        setView(bean);
                        dismissProgress();
                    }

                    @Override
                    public void onFinish(int httpWhat) {

                    }
                },
                0, GoodsTypeBean.class);
    }

    private void setView(GoodsTypeBean bean) {
        if (bean == null) return;
        mWebView.loadDataWithBaseURL(null, bean.getContent(), "text/html", "utf-8", null);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        if (mWebView != null) {
            try {
                mWebView.setWebViewClient(null);
                mWebView.destroy();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    @SuppressLint("NewApi")
    class MyWebViewClient extends WebViewClient {
        String loginCookie;

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            // if(myProgressDialog != null &&
            // !myProgressDialog.isShowing())myProgressDialog.show();
            if (url.startsWith("mailto:") || url.startsWith("geo:")
                    || url.startsWith("tel:") || url.indexOf(".3gp") != -1 || url.indexOf(".mp4") != -1
                    || url.indexOf(".flv") != -1
                    || url.indexOf(".swf") != -1) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else if (url.startsWith("data:")) {
                return true;
            } else if (URLUtil.isNetworkUrl(url)) {
                view.loadUrl(url);
            } else {
                return super.shouldOverrideUrlLoading(view, url);
            }
            return true;
        }

        @Override
        public void onReceivedError(WebView webView, int i, String s, String s1) {
            super.onReceivedError(webView, i, s, s1);
            dismissProgress();
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            // TODO Auto-generated method stub
            // CookieManager cookieManager = CookieManager.getInstance();
            // loginCookie = cookieManager.getCookie(url);
            super.onLoadResource(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            // CookieManager cookieManager = CookieManager.getInstance();
            // cookieManager.setCookie(url, loginCookie);
            super.onPageFinished(view, url);
            imgReset();
            dismissProgress();
            addImageClickListener(view);
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            if (!mContext.isFinishing()) {
                showProgress();
            }
        }

    }

    private WebChromeClient chromeClient = new WebChromeClient() {

        @Override
        public boolean onJsConfirm(WebView arg0, String arg1, String arg2, JsResult arg3) {
            return super.onJsConfirm(arg0, arg1, arg2, arg3);
        }

        @Override
        public boolean onJsAlert(WebView webView, String s, String s1, JsResult jsResult) {
            return super.onJsAlert(webView, s, s1, jsResult);
        }

        @Override
        public boolean onJsPrompt(WebView webView, String s, String s1, String s2, JsPromptResult jsPromptResult) {
            return super.onJsPrompt(webView, s, s1, s2, jsPromptResult);
        }


        @Override
        public void onReceivedTitle(WebView arg0, final String arg1) {
            super.onReceivedTitle(arg0, arg1);
//            Log.i("yuanhaizhou", "webpage title is " + arg1);

        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(String messageEvent) {
        if (messageEvent.equals("ImageTextInfoFragment")) {
            getDetail();
        }
    }

    private void imgReset() {
        mWebView.loadUrl("javascript:(function(){"
                + "var objs = document.getElementsByTagName('img'); "
                + "for(var i=0;i<objs.length;i++)  " + "{"
                + "var img = objs[i];   "
                + "    img.style.width = '100%';   "
                + "    img.style.height = 'auto';   "
                + "}" + "})()");

    }

    private void addImageClickListener(WebView webView) {
        LogUtil.i("addImageClickListener------->");
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistener.openImage(this.src);  " +//通过js代码找到标签为img的代码块，设置点击的监听方法与本地的openImage方法进行连接
                "    }  " +
                "}" +
                "})()");
    }
    public class MJavascriptInterface {

        public MJavascriptInterface() {
        }

        @android.webkit.JavascriptInterface
        public void openImage(String img) {
            LogUtil.i("addImageClickListener------openImage---->" + img);
            PhotoScanActivity.toImgScanActivityt(mContext, img, "");
        }
    }

}
