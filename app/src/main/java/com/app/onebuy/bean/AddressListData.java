package com.app.onebuy.bean;

public class AddressListData extends BasisBean {

	/**
	 * address_id : 3
	 * user_id : 958213672
	 * area_ids : 3431
	 * realname : adsfad
	 * address : adfsdf
	 * mobile : 15880214362
	 * email :
	 * zip_code :
	 * is_default : 0
	 * edit_time : 0
	 * create_time : 1526285630
	 * status : 1
	 * full_address : 马斯巴地adfsdf
	 */

	private String address_id;
	private String user_id;
	private String area_ids;
	private String realname;
	private String address;
	private String mobile;
	private String email;
	private String zip_code;
	private int is_default;
	private int edit_time;
	private int create_time;
	private int status;
	private String full_address;

	public String getAddress_id() {
		return address_id;
	}

	public void setAddress_id(String address_id) {
		this.address_id = address_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getArea_ids() {
		return area_ids;
	}

	public void setArea_ids(String area_ids) {
		this.area_ids = area_ids;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZip_code() {
		return zip_code;
	}

	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}

	public int getIs_default() {
		return is_default;
	}
	public boolean getIs_defaultBoolean() {
		return is_default == 1;
	}

	public void setIs_default(int is_default) {
		this.is_default = is_default;
	}

	public int getEdit_time() {
		return edit_time;
	}

	public void setEdit_time(int edit_time) {
		this.edit_time = edit_time;
	}

	public int getCreate_time() {
		return create_time;
	}

	public void setCreate_time(int create_time) {
		this.create_time = create_time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getFull_address() {
		return full_address;
	}

	public void setFull_address(String full_address) {
		this.full_address = full_address;
	}
}
