package com.app.onebuy.login.countrychoose;


import com.alibaba.fastjson.annotation.JSONField;
import com.app.onebuy.basis.Constants;
import com.app.onebuy.bean.BasisBean;

import my.FileUtils;

public class CountryListDate extends BasisBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String FILE_CACHE = Constants.DIR_FILE
			+ "citydata.suncco";

	
	/**
	 * id : 1
	 * en_name : Angola
	 * cn_name : 安哥拉
	 * pinyin : angela
	 * pinyin_first : a
	 * domain : AO
	 * tel_code : 244
	 * time_diff : -7
	 */

	private String id;
	private String en_name;

	@JSONField(name = "title")
	private String cn_name;

	@JSONField(name = "pinyin_all")
	private String pinyin;
	private String pinyin_first;

	@JSONField(name = "pinyin_simple")
	private String domain;

	@JSONField(name = "mobile_prefix")
	private String tel_code;
	private String time_diff;


	public static CountryListDate getFromCache() {
		CountryListDate data;
		data = (CountryListDate) FileUtils.readObject(FILE_CACHE);
		if(data == null){
			data = new CountryListDate();
			data.setId("189");
			data.setEn_name("China");
			data.setCn_name("中国");
			data.setTel_code("86");
			data.save();
		}
		return data;
	}

	public void save() {
		FileUtils.cacheObject(FILE_CACHE, this);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEn_name() {
		return en_name;
	}

	public void setEn_name(String en_name) {
		this.en_name = en_name;
	}

	public String getCn_name() {
		return cn_name;
	}

	public void setCn_name(String cn_name) {
		this.cn_name = cn_name;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getPinyin_first() {
		return pinyin_first.toLowerCase();
	}

	public void setPinyin_first(String pinyin_first) {
		this.pinyin_first = pinyin_first;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getTel_code() {
		return tel_code;
	}

	public void setTel_code(String tel_code) {
		this.tel_code = tel_code;
	}

	public String getTime_diff() {
		return time_diff;
	}

	public void setTime_diff(String time_diff) {
		this.time_diff = time_diff;
	}
}
