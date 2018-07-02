package com.app.onebuy.basis;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.onebuy.R;


public abstract class ContainerFragment extends BasisFragment {

	private static final String TAG = ContainerFragment.class.getSimpleName();

	// public View mView;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
	}
	// @Override
	// public void onLowMemory() {
	// // TODO Auto-generated method stub
	// super.onLowMemory();
	// Log.v(TAG, "fragment onLowMemory call gc");
	// Log.v(TAG, "before gc memory free size"
	// + Runtime.getRuntime().freeMemory());
	// Runtime.getRuntime().gc();
	// Log.v(TAG, "after gc memory free size"
	// + Runtime.getRuntime().freeMemory());
	// }

	// @Override
	// public void onCreate(Bundle savedInstanceState) {
	// // TODO Auto-generated method stub
	// super.onCreate(savedInstanceState);
	// LogUtil.e("onCreate");
	// }
	//
	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	// // TODO Auto-generated method stub
	// LogUtil.e("onCreateView");
	// return super.onCreateView(inflater, container, savedInstanceState);
	// }

	public void onBackPressed() {
		onBack();
	}

	public boolean onBack() {
		return false;
	}

	public void setTitle(String titleStr) {
		TextView title = (TextView) findViewById(R.id.base_title_text);
		if (title == null) {
			return;
		}
		title.setVisibility(View.VISIBLE);
		title.setText(titleStr);
		findViewById(R.id.base_title_logo).setVisibility(View.GONE);
	}
	public void setTitleBackgroundColor(int colorid) {
		if (findViewById(R.id.base_title_view) == null)
			return;
		findViewById(R.id.base_title_view).setBackgroundResource(colorid);
	}

	public void setTitle(int titleResId) {
		String titleStr = getResources().getString(titleResId);
		setTitle(titleStr + "");
	}
	
	public void setTitleLogo(int resId) {
		if (findViewById(R.id.base_title_logo) == null)
			return;
		((ImageView) findViewById(R.id.base_title_logo))
				.setImageResource(resId);
	}

	public void setTitleRightButton(int drawableRes,
			View.OnClickListener onClickListener) {
		if (findViewById(R.id.base_btn_right) == null)
			return;
		findViewById(R.id.base_btn_right).setOnClickListener(onClickListener);
		((ImageView) findViewById(R.id.base_btn_right_icon))
				.setImageResource(drawableRes);
		findViewById(R.id.base_btn_right).setVisibility(
				drawableRes == 0 ? View.INVISIBLE : View.VISIBLE);
	}

	public void setTitleRightButton2(int drawableRes,
			View.OnClickListener onClickListener) {
		if (findViewById(R.id.base_btn_right2) == null)
			return;
		findViewById(R.id.base_btn_right2).setOnClickListener(onClickListener);
		((ImageView) findViewById(R.id.base_btn_right_icon2))
				.setImageResource(drawableRes);
		findViewById(R.id.base_btn_right2).setVisibility(View.VISIBLE);
	}

	public void setTitleLeftButton(int drawableRes,
			View.OnClickListener onClickListener) {
		if (findViewById(R.id.base_btn_back) == null)
			return;
		findViewById(R.id.base_btn_back).setOnClickListener(onClickListener);
		((ImageView) findViewById(R.id.base_btn_back_icon))
				.setImageResource(drawableRes);
		findViewById(R.id.base_btn_back).setVisibility(View.VISIBLE);
	}

	public void setTitleLeftButton(View.OnClickListener onClickListener) {
		if (findViewById(R.id.base_btn_back) == null)
			return;
		if (onClickListener == null) {
			onClickListener = new View.OnClickListener() {
				public void onClick(View v) {
					finish();
				}
			};
		}
		findViewById(R.id.base_btn_back).setOnClickListener(onClickListener);
		findViewById(R.id.base_btn_back).setVisibility(View.VISIBLE);
	}

	public void setTitleRightText(String text, View.OnClickListener listenner) {
		TextView textRight = (TextView) findViewById(R.id.base_title_right_text);
		if (textRight == null)
			return;
		textRight.setVisibility(View.VISIBLE);
		textRight.setText(text);
		textRight.setOnClickListener(listenner);
	}

	public void setTitleRightText(int titleResId, View.OnClickListener listenner) {
		String titleStr = getResources().getString(titleResId);
		setTitleRightText(titleStr + "", listenner);
	}

	public void setTitleLeftText(String text, View.OnClickListener listenner) {
		TextView textRight = (TextView) findViewById(R.id.base_title_left_text);
		if (textRight == null)
			return;
		textRight.setVisibility(View.VISIBLE);
		textRight.setText(text);
		textRight.setOnClickListener(listenner);
	}

	public void setTitleLeftText(int titleResId, View.OnClickListener listenner) {
		String titleStr = getResources().getString(titleResId);
		setTitleLeftText(titleStr + "", listenner);
	}

}
