package com.app.onebuy.login.countrychoose;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.onebuy.R;

import java.util.ArrayList;

public class SearchCityListAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<CountryListDate> mListBean;

	public SearchCityListAdapter(Context context, ArrayList<CountryListDate> bean) {
		this.mContext = context;
		this.mListBean = bean;
	}

	private class ViewHolder {
		public TextView name;
	}

	@Override
	public int getCount() {
		return mListBean == null ? 0 : mListBean.size();
	}

	@Override
	public CountryListDate getItem(int position) {
		return mListBean.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.city_choose_search_list_item, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.city_name);
			// holder.name.setTextColor(mContext.getResources().getColor(R.color.text_black));
			// holder.name.setPadding(10, 3, 10, 3);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		CountryListDate data = getItem(position);
		if (data == null)
			return convertView;
		holder.name.setText(data.getCn_name() + "");

		return convertView;
	}
}
