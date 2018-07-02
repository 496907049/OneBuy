package my;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class ViewUtils {

	public static void hideInput(Activity mContext, EditText currentET) {

		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (currentET == null) {
			if (mContext.getCurrentFocus() != null) {
				imm.hideSoftInputFromInputMethod(mContext.getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			} else {
				imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}
		} else {
			currentET.requestFocusFromTouch();
			imm.hideSoftInputFromWindow(currentET.getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	public static void hideInput(Activity mContext,View rootView) {

		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
	}

	public static void showInput(Activity mContext, EditText currentET) {

		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (currentET == null) {
			if (mContext.getCurrentFocus() != null) {
				imm.hideSoftInputFromInputMethod(mContext.getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				imm.showSoftInput(mContext.getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
			} else {
				imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
			}
		} else {
			currentET.requestFocusFromTouch();
//			imm.hideSoftInputFromWindow(currentET.getWindowToken(),
//					InputMethodManager.HIDE_NOT_ALWAYS);
			imm.showSoftInput(currentET, InputMethodManager.SHOW_IMPLICIT);
		}
	}
	
}
