package com.app.onebuy.bean;

/**
 * Created by otis on 2018/6/7.
 */

public class InviteHasPresentBean extends BasisBean{
    /**
     * share_title : 分享标题分享标题
     * share_content : 分享内容分享内容分享内容
     * share_imgage_id : http://api.ebuy.suncco.com/uploads/file/20171122/4264231bbd7474424ba3d5afa19d9204.jpeg
     */
    private String recommended_code;
    private String recommended_url;
    private String reward_price;
    private int recommended_id;
    private String reward_amount;
    private int success_num;

    private String share_title;
    private String share_content;
    private String share_imgage_id;

    public String getReward_price() {
        return reward_price;
    }

    public void setReward_price(String reward_price) {
        this.reward_price = reward_price;
    }

    public void setRecommended_code(String recommended_code) {
        this.recommended_code = recommended_code;
    }
    public String getRecommended_code() {
        return recommended_code;
    }

    public void setRecommended_url(String recommended_url) {
        this.recommended_url = recommended_url;
    }
    public String getRecommended_url() {
        return recommended_url;
    }

    public void setRecommended_id(int recommended_id) {
        this.recommended_id = recommended_id;
    }
    public int getRecommended_id() {
        return recommended_id;
    }

    public void setReward_amount(String reward_amount) {
        this.reward_amount = reward_amount;
    }
    public String getReward_amount() {
        return reward_amount;
    }

    public void setSuccess_num(int success_num) {
        this.success_num = success_num;
    }
    public int getSuccess_num() {
        return success_num;
    }

    public String getShare_title() {
        return share_title;
    }

    public void setShare_title(String share_title) {
        this.share_title = share_title;
    }

    public String getShare_content() {
        return share_content;
    }

    public void setShare_content(String share_content) {
        this.share_content = share_content;
    }

    public String getShare_imgage_id() {
        return share_imgage_id;
    }

    public void setShare_imgage_id(String share_imgage_id) {
        this.share_imgage_id = share_imgage_id;
    }
}
