package my.http;


import com.app.onebuy.basis.BasisApp;
import com.app.onebuy.bean.BasisBean;

public abstract class MyHttpListener {

	/**
	 * 网络请求前
	 * 
	 * @param httpWhat
	 *            网络请求编码
	 */
	public void onStart(int httpWhat) {
	}

	/**
	 * 数据下载/上传进度更新
	 * 
	 * @param httpWhat
	 *            网络请求编码
	 * @param totalSize
	 *            总进度
	 * @param bytesWritten
	 *            当前进度
	 */
	public void onProgress(int httpWhat, long bytesWritten, long totalSize) {
	}

	/**
	 * 网络请求成功
	 * 
	 * @param httpWhat
	 *            网络请求编码
	 * @param result
	 *            运行结果
	 */
	public abstract void onSuccess(int httpWhat, Object result);

	/**
	 * 网络请求失败
	 * 
	 * @param httpWhat
	 *            网络请求编码
	 */
	public void onFailure(int httpWhat, Object result) {
		if (result == null) {
			onHttpFailure(httpWhat, null);
		} else {
			BasisApp.showToast(((BasisBean) result).getStatusInfo());
		}

	}

	public boolean onHttpFailure(int httpWhat, Throwable arg3) {
		BasisApp.showToast("请求失败");
		return true;
	}

	/**
	 * 网络请求取消
	 * 
	 * @param httpWhat
	 *            网络请求编码
	 */
	public void onCancel(int httpWhat) {
	}

	public abstract void onFinish(int httpWhat);

}