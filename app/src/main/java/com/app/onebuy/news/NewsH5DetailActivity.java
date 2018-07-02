package com.app.onebuy.news;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.NewsListData;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import butterknife.BindView;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

public class NewsH5DetailActivity extends BasisActivity {


    @BindView(R.id.webview)
    WebView mWebView;

    String id;
    String content;
    String title;
    NewsListData mDetailBean;

    boolean isSingle = false;


    public static void toDetail(Context mContext, String id) {
        Intent intent = new Intent(mContext, NewsH5DetailActivity.class);
        intent.putExtra("id", id);
        mContext.startActivity(intent);
    }

//	public static void toDetail(Context mContext,String title,String content){
//		Intent intent = new Intent(mContext,NewsH5DetailActivity.class);
//		intent.putExtra("title",title);
//		intent.putExtra("content",content);
//		mContext.startActivity(intent);
//	}

    public static void toDetail(Context mContext, NewsListData data) {
        Intent intent = new Intent(mContext, NewsH5DetailActivity.class);
        intent.putExtra("data", data);
        mContext.startActivity(intent);
    }

    public static void toDetail(Context mContext, String id, boolean isSingle) {
        Intent intent = new Intent(mContext, NewsH5DetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("single", true);
        mContext.startActivity(intent);
    }

    @Override
    public void initViews() {
        // TODO Auto-generated method stub
        super.initViews();
        setContentView(R.layout.news_h5_detail);
        setTitle("文章详情");
        setTitleLeftButton(null);

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
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.initData(savedInstanceState);
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(mContext);
        ImageLoader.getInstance().init(configuration);

        NewsListData newsListData = (NewsListData) getIntent().getSerializableExtra("data");

        if(newsListData != null){
            mDetailBean = newsListData;
            setView();
        }else{
            isSingle = getIntent().getBooleanExtra("single", false);
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            getDetail();
        }

//
    }


    void getDetail() {
        RequestParams params = new RequestParams();
        String url = Constants.URL_NEWS_DETAIL;
        String paramsP = "article_id";
        if (isSingle) {
            url = Constants.URL_NEWS_ARTICLEPAGE;
            paramsP = "module";
        }
        showProgress();
        params.put(paramsP, id);
        HttpRestClient.post(url, params, new MyHttpListener() {
                    @Override
                    public void onSuccess(int httpWhat, Object result) {
                        mDetailBean = (NewsListData) result;
                        setView();
                    }

                    @Override
                    public void onFinish(int httpWhat) {
                        dismissProgress();
                    }
                },
                0, NewsListData.class);
    }

    private void setView() {
        if (mDetailBean == null) return;
        setTitle(mDetailBean.getTitle());
//		setTitle(title);

        mWebView.loadDataWithBaseURL(null,
                mDetailBean.getContent(),
                "text/html", "utf-8", null);
//		mWebView
//				.loadDataWithBaseURL(null,
//                content,
//                "text/html", "utf-8", null);
    }

    @Override
    protected void onDestroy() {
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
            dismissProgress();
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
}
