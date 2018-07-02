package com.app.onebuy.bean;

public class UserChargeHistoryListData extends BasisBean {


	/**
	 * bill_id : 14
	 * user_id : 621803681
	 * money : 5.00
	 * order_sn : 2718041815091587
	 * pay_type : balance_pay
	 * title : 华为(HUAWEI) Mate 10 全网通版4GB+64GB 4G手机
	 * date_time : 2018-04-18 15:09:15
	 * pay_type_desc : 余额支付
	 */
	private int number;
	private String bill_id;
	private String user_id;
	private String money;
	private String order_sn;
	private String pay_type;
	private String title;
	private String date_time;
	private String pay_type_desc;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	public String getBill_id() {
		return bill_id;
	}

	public void setBill_id(String bill_id) {
		this.bill_id = bill_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getOrder_sn() {
		return order_sn;
	}

	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate_time() {
		return date_time;
	}

	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}

	public String getPay_type_desc() {
		return pay_type_desc;
	}

	public void setPay_type_desc(String pay_type_desc) {
		this.pay_type_desc = pay_type_desc;
	}
}
