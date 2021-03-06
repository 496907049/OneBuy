package my.http;

import com.alibaba.fastjson.annotation.JSONField;

public class MyBaseBean {

	/** 数据 */
	private String resultData;
	
//	/** 数据 */
//	@JsonName("result")
//	private String otherResultData;

	/** 借口反馈信息 */
	private String statusInfo;
	/** 状态 200正常 */
	private int statusCode = 0;
	
	private int errCode = -1;

	public static final int CODE_OK = 200;


	private String api_time;

	public String getApi_time() {
		return api_time;
	}

	public void setApi_time(String api_time) {
		this.api_time = api_time;
	}
	
	@JSONField(name="data")
	public String getResultData() {
		return resultData;
	}

	@JSONField(name="data")  
	public void setResultData(String resultData) {
		this.resultData = resultData;
	}

	@JSONField(name="info")
	public String getStatusInfo() {
		return statusInfo;
	}

	@JSONField(name="info")
	public void setStatusInfo(String statusInfo) {
		this.statusInfo = statusInfo;
	}

	@JSONField(name="status")
	public int getStatusCode() {
		if(errCode == CODE_OK)return errCode;
		return statusCode;
	}

	@JSONField(name="status")
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public  boolean isCodeOk(){
		return statusCode == CODE_OK;
	}
//	
//	@JSONField(name="data")  
//	public String getOtherResultData() {
//		return otherResultData;
//	}
//
//	@JSONField(name="data")  
//	public void setOtherResultData(String otherResultData) {
//		this.otherResultData = otherResultData;
//	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}
	
}
