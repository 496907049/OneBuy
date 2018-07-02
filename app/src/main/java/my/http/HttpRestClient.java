package my.http;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.app.onebuy.basis.BasisApp;
import com.app.onebuy.basis.ChangeLanguageHelper;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.BaseMsgBean;
import com.app.onebuy.bean.BasicBeanStr;
import com.app.onebuy.bean.BasisBean;
import com.app.onebuy.bean.LoginBean;
import com.app.onebuy.login.LoginActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cz.msebera.android.httpclient.Header;
import my.LogUtil;
import my.MD5;
import my.SystemParamsUtils;


public class HttpRestClient {
	public final static String tag = "HTTP";
	public final static boolean DEBUG = Constants.DEBUG;

	private static final String BASE_URL = "";

	private static AsyncHttpClient client = new AsyncHttpClient();
	private static final int TIME_OUT = 20 * 1000;

	static {
		client.setTimeout(TIME_OUT); // 设置链接超时，如果不设置，默认为10s
		setHeader(client);
	}

	public static AsyncHttpClient getAsyncHttpClient (){
		return client;
	}
	public static void get(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		AsyncHttpClient client = new AsyncHttpClient(true,80,443);
		setHeader(client);
		setCommentParams(params);
		LogUtil.i(tag, url);
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}

	public static void get(String url, RequestParams params,
			MyHttpListener mListener, int what) {
		AsyncHttpClient client = new AsyncHttpClient(true,80,443);
		setHeader(client);
		setCommentParams(params);
		LogUtil.i(tag, url +"\nwhat---->"+what);
		client.get(getAbsoluteUrl(url), params, new MyHttpResposeHandler(
				mListener, what, null));
	}

	public static void get(String url, RequestParams params,
			MyHttpListener mListener, int what, Class<?> class1) {
		AsyncHttpClient client = new AsyncHttpClient(true,80,443);
		setHeader(client);
		setCommentParams(params);
		LogUtil.i(tag, url +"\nwhat---->"+what);
		client.get(getAbsoluteUrl(url), params, new MyHttpResposeHandler(
				mListener, what, class1));
	}

	public static void post(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		AsyncHttpClient client = new AsyncHttpClient(true,80,443);
		setHeader(client);
		setCommentParams(params);
		LogUtil.i(tag, url);
		client.post(getAbsoluteUrl(url), params, responseHandler);
	}
	public static void post(String url, RequestParams params,
			JsonHttpResponseHandler responseHandler) {
		AsyncHttpClient client = new AsyncHttpClient(true,80,443);
		setHeader(client);
		setCommentParams(params);
		LogUtil.i(tag, url);
		client.post(getAbsoluteUrl(url), params, responseHandler);
	}

	public static void post(String url, RequestParams params,
			MyHttpListener mListener, int what) {
		AsyncHttpClient client = new AsyncHttpClient(true,80,443);
		setHeader(client);
		setCommentParams(params);
		LogUtil.i(tag, url +"\nwhat---->"+what);
		client.post(getAbsoluteUrl(url), params, new MyHttpResposeHandler(
				mListener, what, null));
	}

	public static void post(String url, RequestParams params,
			MyHttpListener mListener, int what, Class<?> class1) {
		AsyncHttpClient client = new AsyncHttpClient(true,80,443);
		setHeader(client);
		setCommentParams(params);
		LogUtil.i(tag, url +"\nwhat---->"+what);

		client.post(getAbsoluteUrl(url), params, new MyHttpResposeHandler(
				mListener, what, class1));
	}
	public static void post(String url, RequestParams params,
			MyHttpListener mListener, int what,int timeout, Class<?> class1) {
		AsyncHttpClient client = new AsyncHttpClient(true,80,443);
		setHeader(client);
		setCommentParams(params);
		LogUtil.i(tag, url +"\nwhat---->"+what);
		client.setTimeout(timeout);
		client.post(getAbsoluteUrl(url), params, new MyHttpResposeHandler(
				mListener, what, class1));
	}

	public static void download(String url,
			FileAsyncHttpResponseHandler mListener) {
//		setHeader(client);
//		setCommentParams(params);
		LogUtil.i(tag, url);
		client.setMaxRetriesAndTimeout(0,TIME_OUT);
		client.get(url, mListener);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		if(relativeUrl.startsWith("http")){
			return relativeUrl;
		}else{

//			return Constants.getServerIp() + relativeUrl;
			return relativeUrl;
		}
	}

	private static final String HEADER_PARAMS1 = "XMGOV-API-SourceID";
	private static final String HEADER_PARAMS2 = "XMGOV-API-AuthTime";
	private static final String HEADER_PARAMS3 = "XMGOV-API-AuthKey";
	private static final String HEADER_HTTP_SourceID = "1";
	private static final String HEADER_HTTP_AuthKey = "asdf";
//	private static final String HTTP_APP_VERTYPE = "APP-VERTYPE";
//	private static final String HTTP_APP_VERCODE = "APP-VERCODE";
//	private static final String APP_VERTYPE = "1";

	@SuppressLint("MissingPermission")
	@RequiresApi(api = Build.VERSION_CODES.O)
	private static void setHeader(AsyncHttpClient client) {
			client.addHeader("APP-TOKEN", LoginBean.getUserToken());
		 long time = new Date().getTime();
		 client.addHeader("APP-ID", HEADER_HTTP_SourceID);
		 client.addHeader(
		 "APP-SIGN",
		 MD5.getMD5ofStr(
		 HEADER_HTTP_AuthKey + time + HEADER_HTTP_SourceID)
		 .substring(0, 32));
		 client.addHeader("APP-AUTHTIME", time + "");
		 client.addHeader("APP-TYPE",  "Android");
		 client.addHeader("APP-DEVICE",
				SystemParamsUtils.getPhoneModel() + "");
		client.addHeader("APP-OS",
				Build.VERSION.RELEASE + "");
		client.addHeader("APP-LANG", ChangeLanguageHelper.getLanguageHeader());
//		LogUtil.i("APP-LANG", ChangeLanguageHelper.getLanguageHeader());
//		LogUtil.i("APP-DEVICE",
//				SystemParamsUtils.getPhoneModel() + "");
//		LogUtil.i("APP-OS",
//				Build.VERSION.RELEASE + "");
		LogUtil.i("APP-TOKEN", LoginBean.getUserToken());

	}

	private static void setCommentParams(RequestParams params) {
		
		
		if(DEBUG){
			LogUtil.i(tag, params.toString());
		}
		client.setTimeout(TIME_OUT);
		// if (params != null)
		// LogUtil.i("RequestParams--->" + params.toString());
		// if (!TextUtils.isEmpty(BasisApp.sAppId)
		// && !TextUtils.isEmpty(BasisApp.sAppId)) {
		// params.add("app_id", BasisApp.sAppId);
		// params.add("msisdn", BasisApp.sMsisdn);
		// }
	}

}

//class MyDownloadListener extends BinaryHttpResponseHandler {
//
//	@Override
//	public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onProgress(long bytesWritten, long totalSize) {
//		// TODO Auto-generated method stub
//		super.onProgress(bytesWritten, totalSize);
//	}
//
//}

class MyHttpResposeHandler extends AsyncHttpResponseHandler {
	public final static String tag = "HTTP";
	int what = 0;
	MyHttpListener mListener;
	Class<?> clazz;

	public MyHttpResposeHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MyHttpResposeHandler(MyHttpListener mListener, int what,
			Class<?> clazz) {
		super();
		this.what = what;
		this.mListener = mListener;
		this.clazz = clazz;

	}

	@Override
	public void onFailure(int statusCode, Header[] headers,
			byte[] responseBody, Throwable arg3) {
		LogUtil.i(tag, "onFailure"+statusCode);
		if (mListener == null)
			return;
		mListener.onHttpFailure(what, arg3);
		mListener.onFinish(what);
	}

//	Object object;
	@Override
	public void onSuccess(int statusCode, Header[] headers, final byte[] responseBody) {
		// TODO Auto-generated method stub
		LogUtil.i(tag, "onSuccess"+statusCode);
		if (mListener == null)
			return;
//		object = null;
		new AsyncTask<String,Integer,Object>(){

			@Override
			protected Object doInBackground(String... strings) {
				Object object = parseResoposeBody(responseBody, clazz);
				return object;
			}

			@Override
			protected void onPostExecute(Object object) {
				super.onPostExecute(object);
				if (object == null) {
					LogUtil.i(tag,"onFailure object null");
					mListener.onFailure(what,null);
				} else {
					BasisBean bean = (BasisBean) object;
					if (bean.isCodeOk()) {
						LogUtil.i(tag,"onSuccess");
						mListener.onSuccess(what, object);
					} else if(BasisBean.CODE_LOGINPAST == bean.getStatusCode()){
						BasisApp.showToast(bean.getStatusInfo());
						LoginActivity.toLoadingAllClear(BasisApp.getInstance());
					}else  {
						LogUtil.i(tag,"onFailure  code no_ok");
						mListener.onFailure(what, object);
					}
				}
				mListener.onFinish(what);
			}
		}.executeOnExecutor((ExecutorService) Executors.newCachedThreadPool(),"");

	}


	@Override
	public void onCancel() {
		// TODO Auto-generated method stub
		LogUtil.i(tag, "onCancel");
		super.onCancel();
		if (mListener == null)
			return;
		mListener.onCancel(what);
		mListener.onFinish(what);
	}

	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		super.onFinish();
		LogUtil.i(tag, "onFinish");
		if (mListener == null)
			return;
//		mListener.onFinish(what);
	}

	@Override
	public void onProgress(long bytesWritten, long totalSize) {
		// TODO Auto-generated method stub
		super.onProgress(bytesWritten, totalSize);
		if (mListener == null)
			return;
		mListener.onProgress(what, bytesWritten, totalSize);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (mListener == null)
			return;
		mListener.onStart(what);
	}

	private static Object parseResoposeBody(byte[] responseBody, Class<?> clazz) {
		String result = new String(responseBody);
		LogUtil.i(tag, "" + result.toString());
		if (TextUtils.isEmpty(result))
			return null;
		try {
			// result = StringUtil.decodeUnicode(result);
			MyBaseBean bean = JSON.parseObject(result, MyBaseBean.class);
//			MyBaseBean bean = JSON.parseObject(result, MyBaseBean.class);
			BasisBean resultBean;
			if (clazz == null) {
				resultBean = new BasisBean();
			} else if (!TextUtils.isEmpty(bean.getResultData())) {
				if(bean.getResultData().startsWith("[")){
					if(bean.getResultData().startsWith("[\"")){
						bean.setResultData("{\"list\":[]}");
					}else{

						bean.setResultData("{\"list\":"+bean.getResultData()+"}");
					}
				}
				if ( BasicBeanStr.class.isAssignableFrom(clazz) ) {
//					JSONObject jsonObject = JSON.parseObject(bean.getResultData());
//					bean.setResultData("{\"msg\":\""+bean.getResultData()+"\"}");
				}
				if ( BaseMsgBean.class.isAssignableFrom(clazz) ) {
//					JSONObject jsonObject = JSON.parseObject(bean.getResultData());
					bean.setResultData("{\"msg\":\""+bean.getResultData()+"\"}");
				}
				if(!TextUtils.isEmpty(bean.getResultData()) && !bean.getResultData().startsWith("{")){
					resultBean = new BasisBean();
				}else{

					resultBean = (BasisBean) JSON.parseObject(
							bean.getResultData(), clazz);
				}
			} else if (bean.getResultData() == null
					|| bean.getResultData().equals("null")
					|| bean.getResultData().equals("{}")
					|| (bean.getResultData().length() == 0)) {
				resultBean = (BasisBean) clazz.newInstance();
			} else {
				resultBean = (BasisBean) JSON.parseObject(bean.getResultData(),
						clazz);
			}
			resultBean.setStatusCode(bean.getStatusCode());
			resultBean.setStatusInfo(bean.getStatusInfo());
			resultBean.setResultData(bean.getResultData());
			resultBean.setApi_time(bean.getApi_time());
			return resultBean;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}