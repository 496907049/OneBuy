package com.app.onebuy.bean;

public class IntegralDetailListData extends BasisBean {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * log_id : 1
	 * recommended_id : 3
	 * user_id : 108139527
	 * full_mobile : 86-13695049662
	 * to_reward_amount : 50.00
	 * self_reward_amount : 10.00
	 * reward_time : 1525442625
	 * create_time : 1525442625
	 * status : 1
	 * recommended_type : 邀请注册
	 * head_url : http://api.ebuy.suncco.com/public/images/head-man-default.png
	 * user_name : 108139527
	 * reward_time_desc : 2018-05-04 22:03:45
	 */

	private int log_id;
	private int recommended_id;
	private int user_id;
	private String full_mobile;
	private String to_reward_amount;
	private String self_reward_amount;
	private int reward_time;
	private int create_time;
	private int status;
	private String recommended_type;
	private String head_url;
	private int user_name;
	private String reward_time_desc;

	public int getLog_id() {
		return log_id;
	}

	public void setLog_id(int log_id) {
		this.log_id = log_id;
	}

	public int getRecommended_id() {
		return recommended_id;
	}

	public void setRecommended_id(int recommended_id) {
		this.recommended_id = recommended_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getFull_mobile() {
		return full_mobile;
	}

	public void setFull_mobile(String full_mobile) {
		this.full_mobile = full_mobile;
	}

	public String getTo_reward_amount() {
		return to_reward_amount;
	}

	public void setTo_reward_amount(String to_reward_amount) {
		this.to_reward_amount = to_reward_amount;
	}

	public String getSelf_reward_amount() {
		return self_reward_amount;
	}

	public void setSelf_reward_amount(String self_reward_amount) {
		this.self_reward_amount = self_reward_amount;
	}

	public int getReward_time() {
		return reward_time;
	}

	public void setReward_time(int reward_time) {
		this.reward_time = reward_time;
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

	public String getRecommended_type() {
		return recommended_type;
	}

	public void setRecommended_type(String recommended_type) {
		this.recommended_type = recommended_type;
	}

	public String getHead_url() {
		return head_url;
	}

	public void setHead_url(String head_url) {
		this.head_url = head_url;
	}

	public int getUser_name() {
		return user_name;
	}

	public void setUser_name(int user_name) {
		this.user_name = user_name;
	}

	public String getReward_time_desc() {
		return reward_time_desc;
	}

	public void setReward_time_desc(String reward_time_desc) {
		this.reward_time_desc = reward_time_desc;
	}
}
