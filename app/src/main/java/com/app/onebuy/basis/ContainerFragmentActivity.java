package com.app.onebuy.basis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;

import my.LogUtil;
import com.app.onebuy.R;

public class ContainerFragmentActivity extends BasisActivity {

	private static final String tag = ContainerFragmentActivity.class
			.getSimpleName();

	private FragmentManager fm;
	private Fragment currentFragment;

	// TextView mTitleText;

	public static void toFragment(Context context, Class<?> name) {
		Intent intent = new Intent(context, ContainerFragmentActivity.class);
		intent.putExtra("class", name.getName());
		context.startActivity(intent);
	}
	
	public static void toFragment(Context context, Class<?> name, Bundle extras) {
		Intent intent = new Intent(context, ContainerFragmentActivity.class);
		intent.putExtra("class", name.getName());
		intent.putExtras(extras);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	
	@Override
	public void initConfig(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.initConfig(savedInstanceState);
		setContentView(R.layout.base_containerfragment_activity);
		String classname = getIntent().getStringExtra("class");
		Bundle extras = getIntent().getExtras();
		fm = getSupportFragmentManager();
		if (!TextUtils.isEmpty(classname)) {
			Fragment f = Fragment.instantiate(ContainerFragmentActivity.this,
					classname, extras);
			initFragment(f);
		}
	}


	public void initFragment(Fragment f) {
		changeFragment(f, true,f.getClass().getName());
	}

	public void changeFragment(Fragment f) {
		changeFragment(f, false,f.getClass().getName());
	}
	
	public void changeFragment(Fragment f, String tagF) {
		changeFragment(f, false,tagF);
	}

	private void changeFragment(Fragment f, boolean init, String tagF) {
		if (f == null) {
			return;
		}
		FragmentTransaction ft = fm.beginTransaction();

		int content = R.id.content;
		String tag = tagF;

		if (init) {

			for (int i = 0, len = fm.getBackStackEntryCount(); i < len; i++) {
				fm.popBackStack();
			}
			ft.replace(content, f, tag);
			currentFragment = f;

		} else {
			if (currentFragment != null && tag.equals(currentFragment.getTag())) {
				return;
			}
			ft.setCustomAnimations(R.anim.left_in, R.anim.left_out,
					R.anim.right_in, R.anim.right_out);

			for (Fragment preF : fm.getFragments()) {
				if (preF != null) {
//					Log.v(TAG, "hide:" + preF.getClass());
//					Log.v(TAG, "isHide: " + preF.isHidden());
//					Log.v(TAG, "isVisible:" + preF.isVisible());
					if (preF.isVisible()) {
						ft.hide(preF);
					}
				}
			}
			ft.add(content, f, tag);
			ft.addToBackStack(null);
			currentFragment = f;
		}

		ft.commit();
	}

	public boolean onPopBack() {
		LogUtil.i("getBackStackEntryCount---?"+fm.getBackStackEntryCount());
		if (fm.getBackStackEntryCount() > 0) {
			fm.popBackStack();
			currentFragment = null;
			return true;
		}
		return false;
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (fm.findFragmentById(R.id.content) != null) {
			fm.findFragmentById(R.id.content).onActivityResult(requestCode,
					resultCode, data);
		}
	}

	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	public boolean onBackPresse() {
		Fragment f = fm.findFragmentById(R.id.content);
		currentFragment = null;
		if (f != null && f instanceof ContainerFragment) {
			if (((ContainerFragment) f).onBack()) {
				return true;
			}

			return onPopBack();
		}
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK){
			if(onBackPresse())return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
