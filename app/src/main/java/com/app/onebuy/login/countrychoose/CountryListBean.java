package com.app.onebuy.login.countrychoose;


import com.app.onebuy.bean.BasisBean;

import java.util.ArrayList;

public class CountryListBean extends BasisBean {

	// public static final String nameSpace =
	// Constans.SERVICE_URL+"SearchCity.jws";
	// public static final String soapAction =nameSpace+methodName;

	private ArrayList<CountryListDate> list = new ArrayList<CountryListDate>();


	public ArrayList<CountryListDate> getList() {
		return list;
	}

	public void setList(ArrayList<CountryListDate> list) {
		this.list = list;
	}
}
