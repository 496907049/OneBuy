package com.app.onebuy.bean;

import android.text.TextUtils;

import com.app.onebuy.basis.BasisApp;

import java.io.Serializable;

import my.FileUtils;
import my.LogUtil;


public class BasisBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 数据 */
	private String resultData;
	/** 借口反馈信息 */
	private String statusInfo;
	private String api_time;
	/** 状态 200正常 */
	private int statusCode = -1;
	
	public static final int TRUE = 1,FALSE = 0;
	
	public static final String NULL = "null";

	public static final int CODE_OK = 200;
	public static final int CODE_LOGINPAST = 202;

	public String getResultData() {
		return resultData;
	}

	public void setResultData(String resultData) {
		this.resultData = resultData;
	}

	public String getStatusInfo() {
		return statusInfo;
	}

	public void setStatusInfo(String statusInfo) {
		this.statusInfo = statusInfo;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public  boolean isCodeOk(){
		return statusCode == CODE_OK;
	}
	
	public static boolean isEmpty(String str){
		return TextUtils.isEmpty(str) || str.equals(NULL);
	}


	public String getApi_time() {
		return api_time;
	}

	public void setApi_time(String api_time) {
		this.api_time = api_time;
	}



	public Object getObjFromCache() {
		String FILE_CACHE = BasisApp.mContext.getFilesDir()
				+ "/"+this.getClass().getSimpleName()+".data";

		Object obj =  FileUtils.readObject(FILE_CACHE);
		LogUtil.i("getFromCache---"+FILE_CACHE+ (obj == null));
		return obj;
	}

	public void saveObj() {
		String FILE_CACHE = BasisApp.mContext.getFilesDir()
				+ "/"+this.getClass().getSimpleName()+".data";
		FileUtils.cacheObject(FILE_CACHE, this);
		LogUtil.i("save---"+FILE_CACHE);
	}

	private  void deleteObj() {
		String FILE_CACHE = BasisApp.mContext.getFilesDir()
				+ "/"+this.getClass().getSimpleName()+".data";
		FileUtils.cacheObject(FILE_CACHE, null);
	}
}
