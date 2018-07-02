package com.app.onebuy.login.countrychoose;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisActivity;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.login.countrychoose.db.CityDBOperator;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import my.LogUtil;
import my.http.HttpRestClient;
import my.http.MyHttpListener;

public class CountryChooseActivity extends BasisActivity implements OnClickListener,
		OnItemClickListener {
	
	public final static int REQUEST_CHOOSE_CITY = 101;
	
	final static int WEB_LIST_RESULT = 11;
	
	final static String tag = "CountryChooseActivity";
	
	private BaseAdapter adapter;
	private ListView mListViewCountry;
	private TextView overlay;
	private MyLetterListView letterListView;
	private HashMap<String, Integer> alphaIndexerForCountry;
	private String[] sectionsForcountry;

	private Handler handler;
	private OverlayThread overlayThread;
//	private SQLiteDatabase database;
	private ArrayList<CountryListDate> mListCity;

	EditText mEditSearch;
	ListView mListViewSearch;
	SearchCityListAdapter mSearchListAdapter;
	boolean isOnSearch = false;

	CityDBOperator mDbOperator;

	@Override
	public void initViews() {
		setContentView(R.layout.city_choose_activity);
		super.initViews();
		setTitleLeftButton(this);
		setTitle("选择国家地区");
		mListViewCountry = findViewById(R.id.city_listview);
		letterListView = findViewById(R.id.city_cityLetterListView);
		
		// database.close();
		letterListView
				.setOnTouchingLetterChangedListener(new LetterListViewListener());
		alphaIndexerForCountry = new HashMap<String, Integer>();
		handler = new Handler();
		overlayThread = new OverlayThread();
		initOverlay();
		
		mListViewCountry.setOnItemClickListener(this);

		mListViewSearch = findViewById(R.id.city_search_listview);
		mEditSearch = findViewById(R.id.edit_search);
		mEditSearch.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				String key = mEditSearch.getText().toString();
				if (TextUtils.isEmpty(key)) {
					isOnSearch = false;
					setSearchView();
				} else {
					isOnSearch = true;
					setSearchView();
					setSearchListView(key);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				// mListViewSearch.setVisibility(View.INVISIBLE);
			}
		});
		mListViewSearch.setOnItemClickListener(this);

	}

	@Override
	public void initData(Bundle savedInstanceState) {
		super.initData(savedInstanceState);
		mDbOperator = CityDBOperator.getInstance(mContext);
		mListCity = mDbOperator.getAllCityListData();
		if(mListCity.size() == 0){
			getList();
		}else{
			setAdapter();
		}
	}


		

	private void getList() {
		RequestParams params = new RequestParams();
		showLoading();
            HttpRestClient.get(Constants.URL_USER_COUNTRY, params, new MyHttpListener() {
				@Override
				public void onSuccess(int httpWhat, Object result) {
					CountryListBean bean = (CountryListBean) result;
					onCityListGet(bean);
				}

				@Override
				public void onFinish(int httpWhat) {

				}
			}, 0, CountryListBean.class);


	}

	final static int HANDLER_DATA_READY = 11;
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case HANDLER_DATA_READY:
				setAdapter();
				dismissProgress();
				break;
			default:
				break;
			}
		}
		
	};


	private void setSearchListView(String string) {
		ArrayList<CountryListDate> list = mDbOperator.getCityListDataByKeywor(string);
		LogUtil.i(tag,"serarch----->"+list.size());
		mSearchListAdapter = new SearchCityListAdapter(this, list);
		mListViewSearch.setAdapter(mSearchListAdapter);
	}
	
	private void setSearchView() {
		if(isOnSearch){
			mListViewSearch.setVisibility(View.VISIBLE);
		}else{
//			mEditSearch.setText("");
			mListViewSearch.setVisibility(View.INVISIBLE);
		}
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(isOnSearch){
				isOnSearch = false;
				setSearchView();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		CountryListDate cityMode = null;
		if (arg0.equals(mListViewCountry)) {
			cityMode = mListCity.get(arg2);
		}  else {
			if (mSearchListAdapter != null)
				cityMode = mSearchListAdapter.getItem(arg2);
		}
		if (cityMode == null)
			return;
		Intent data = new Intent();
		data.putExtra("data", cityMode);
		setResult(Activity.RESULT_OK, data);
		finish();
//		BaseApp.showToast("" + cityMode.city_name);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.base_btn_back:
			finish();
			break;
		default:
			break;
		}
	}

	private void setAdapter() {
		LogUtil.i(tag, "setAdapter---->"+mListCity.size());
		if (mListCity != null) {
			adapter = new ListAdapter(this, mListCity);
			mListViewCountry.setAdapter(adapter);
			LogUtil.i(tag, "setAdapter---->"+adapter.getCount());
		}
	}


	/**
	 * ListViewAdapter
	 * 
	 * @author sy
	 * 
	 */
	private class ListAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private List<CountryListDate> list;

		public ListAdapter(Context context, List<CountryListDate> list) {

			this.inflater = LayoutInflater.from(context);
			this.list = list;


				alphaIndexerForCountry = new HashMap<String, Integer>();
				sectionsForcountry = new String[list.size()];
//				alphaIndexerForCountry.put("热门城市", 0);

			CountryListDate data;
			for (int i = 0; i < list.size(); i++) {
				data = list.get(i);
				String currentStr = data.getPinyin_first();
				String previewStr = (i - 1) >= 0 ? list.get(i - 1)
						.getPinyin_first(): " ";
				if (!previewStr.equals(currentStr)) {
					String name = list.get(i).getPinyin_first();
					alphaIndexerForCountry.put(name, i);
					sectionsForcountry[i] = name;
				}
			}
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.city_choose_list_item,
						null);
				holder = new ViewHolder();
				holder.alpha = convertView
						.findViewById(R.id.city_alpha);
				holder.name = convertView
						.findViewById(R.id.city_name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.name.setText(list.get(position).getCn_name());
			String currentStr = list.get(position).getPinyin_first();
			String previewStr = (position - 1) >= 0 ? list.get(position - 1)
					.getPinyin_first() : " ";
			if (!previewStr.equals(currentStr)) {
				holder.alpha.setVisibility(View.VISIBLE);
				holder.alpha.setText(currentStr);
			} else {
				holder.alpha.setVisibility(View.GONE);
			}
			return convertView;
		}

		private class ViewHolder {
			TextView alpha;
			TextView name;
		}

	}

	private void initOverlay() {
		LayoutInflater inflater = LayoutInflater.from(this);
		overlay = (TextView) inflater.inflate(R.layout.city_choose_overlay,
				null);
		overlay.setVisibility(View.INVISIBLE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
				PixelFormat.TRANSLUCENT);
		WindowManager windowManager = (WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE);
		windowManager.addView(overlay, lp);
	}

	private class LetterListViewListener implements
			MyLetterListView.OnTouchingLetterChangedListener {

		@Override
		public void onTouchingLetterChanged(final String s) {
			String slow = s.toLowerCase();
				if (alphaIndexerForCountry.get(slow) != null) {
					int position = alphaIndexerForCountry.get(slow);
					mListViewCountry.setSelection(position);
					overlay.setText(sectionsForcountry[position]);
					overlay.setVisibility(View.VISIBLE);
					handler.removeCallbacks(overlayThread);
					handler.postDelayed(overlayThread, 1500);
				}
		}

	}

	private class OverlayThread implements Runnable {

		@Override
		public void run() {
			overlay.setVisibility(View.GONE);
		}

	}

	private void onCityListGet(CountryListBean bean) {
		mListCity = bean.getList();
		new Thread(new Runnable() {
			public void run() {
				mDbOperator.addCityListBean(mListCity);
				mHandler.sendEmptyMessage(HANDLER_DATA_READY);
			}
		}).start();
//		mHandler.post(new Runnable() {
//			public void run() {
//				// TODO Auto-generated method stub
//				setAdapter();
//				mProgress.dismiss();
//			}
//		});
	}
	
}