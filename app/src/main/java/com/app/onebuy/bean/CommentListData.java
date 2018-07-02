package com.app.onebuy.bean;

import java.util.List;

import my.TimeUtils;

public class CommentListData extends BasisBean {

	/**
	 *"period_num": 2,
	 *"goods_name": "华为(HUAWEI) Mate 10 全网通版4GB+64GB 4G手机",
	 *"period_id": 6,
	 *"nickname": "天天",
	 *"images_list": ["http://api.ebuy.suncco.com/uploads/file/20180515/82fb2a10caa5b4ad03a96d5a20b1f0bd.jpg", "http://api.ebuy.suncco.com/uploads/file/20180515/e0d0680b589d822e116ac9adeac7d301.jpg"],
	 *"object_id": "3",
	 *"images_id": "86,87",
	 *"comment_id": 1,
	 *"date": "2018-05-15",
	 *"content": "1521213213",
	 *"head_url": "http://api.ebuy.suncco.com/uploads/file/20180531/444bbace2764e803daef3152de321cb9.jpg",
	 *"user_id": 621803681,
	 *"create_time": 1526356882
	 */

	private int period_num;
	private String goods_name;
	private int period_id;
	private String nickname;
	private List<String> images_list;
	private String object_id;
	private String images_id;
	private int comment_id;
	private String date;
	private String content;
	private String head_url;
	private long user_id;
	private long create_time;
	public void setPeriod_num(int period_num) {
		this.period_num = period_num;
	}
	public int getPeriod_num() {
		return period_num;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public String getGoods_name() {
		return goods_name;
	}

	public void setPeriod_id(int period_id) {
		this.period_id = period_id;
	}
	public int getPeriod_id() {
		return period_id;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getNickname() {
		return nickname;
	}

	public void setImages_list(List<String> images_list) {
		this.images_list = images_list;
	}
	public List<String> getImages_list() {
		return images_list;
	}

	public void setObject_id(String object_id) {
		this.object_id = object_id;
	}
	public String getObject_id() {
		return object_id;
	}

	public void setImages_id(String images_id) {
		this.images_id = images_id;
	}
	public String getImages_id() {
		return images_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}
	public int getComment_id() {
		return comment_id;
	}

	public void setDate(String date) {
		this.date = date;
	}
	public String getDate() {
		return date;
	}

	public void setContent(String content) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}

	public void setHead_url(String head_url) {
		this.head_url = head_url;
	}
	public String getHead_url() {
		return head_url;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public long getUser_id() {
		return user_id;
	}

	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}
	public long getCreate_time() {
		return create_time;
	}

}
