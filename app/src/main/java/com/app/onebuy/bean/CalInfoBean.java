package com.app.onebuy.bean;

import java.util.List;

/**
 * Created by otis on 2018/5/25.
 */

public class CalInfoBean  extends BasisBean{

    private static CalInfoBean calInfoBean;

    private int win_a_number;
    private String win_number;
    private int express_type;
    private int goods_id;
    private String express_number;
    private int used_num;
    private int win_user_id;
    private int express_status;
    private int period_num;
    private String win_init_number;
    private int status;
    private int comment_time;
    private int confirm_express_time;
    private String win_user_name;
    private long finish_time;
    private String finish_ratio_desc;
    private int address_id;
    private String status_desc;
    private int period_id;
    private int win_order_number;
    private int time_length;
    private int win_remainder;
    private int is_commented;
    private int finish_ratio;
    private int limit_num;
    private int express_time;
    private long create_time;
    private int lottery_time;
    private List<Top100PeriodNumber> top100_period_number;

    public static CalInfoBean getCalInfoBean() {
        return calInfoBean;
    }

    public static void setCalInfoBean(CalInfoBean calInfoBean) {
        CalInfoBean.calInfoBean = calInfoBean;
    }

    public void setWin_a_number(int win_a_number) {
        this.win_a_number = win_a_number;
    }
    public int getWin_a_number() {
        return win_a_number;
    }

    public void setWin_number(String win_number) {
        this.win_number = win_number;
    }
    public String getWin_number() {
        return win_number;
    }

    public void setExpress_type(int express_type) {
        this.express_type = express_type;
    }
    public int getExpress_type() {
        return express_type;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }
    public int getGoods_id() {
        return goods_id;
    }

    public void setExpress_number(String express_number) {
        this.express_number = express_number;
    }
    public String getExpress_number() {
        return express_number;
    }

    public void setUsed_num(int used_num) {
        this.used_num = used_num;
    }
    public int getUsed_num() {
        return used_num;
    }

    public void setWin_user_id(int win_user_id) {
        this.win_user_id = win_user_id;
    }
    public int getWin_user_id() {
        return win_user_id;
    }

    public void setExpress_status(int express_status) {
        this.express_status = express_status;
    }
    public int getExpress_status() {
        return express_status;
    }

    public void setPeriod_num(int period_num) {
        this.period_num = period_num;
    }
    public int getPeriod_num() {
        return period_num;
    }

    public void setWin_init_number(String win_init_number) {
        this.win_init_number = win_init_number;
    }
    public String getWin_init_number() {
        return win_init_number;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }

    public void setComment_time(int comment_time) {
        this.comment_time = comment_time;
    }
    public int getComment_time() {
        return comment_time;
    }

    public void setConfirm_express_time(int confirm_express_time) {
        this.confirm_express_time = confirm_express_time;
    }
    public int getConfirm_express_time() {
        return confirm_express_time;
    }

    public void setWin_user_name(String win_user_name) {
        this.win_user_name = win_user_name;
    }
    public String getWin_user_name() {
        return win_user_name;
    }

    public void setFinish_time(long finish_time) {
        this.finish_time = finish_time;
    }
    public long getFinish_time() {
        return finish_time;
    }

    public void setFinish_ratio_desc(String finish_ratio_desc) {
        this.finish_ratio_desc = finish_ratio_desc;
    }
    public String getFinish_ratio_desc() {
        return finish_ratio_desc;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }
    public int getAddress_id() {
        return address_id;
    }

    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }
    public String getStatus_desc() {
        return status_desc;
    }

    public void setTop100_period_number(List<Top100PeriodNumber> top100_period_number) {
        this.top100_period_number = top100_period_number;
    }
    public List<Top100PeriodNumber> getTop100_period_number() {
        return top100_period_number;
    }

    public void setPeriod_id(int period_id) {
        this.period_id = period_id;
    }
    public int getPeriod_id() {
        return period_id;
    }

    public void setWin_order_number(int win_order_number) {
        this.win_order_number = win_order_number;
    }
    public int getWin_order_number() {
        return win_order_number;
    }

    public void setTime_length(int time_length) {
        this.time_length = time_length;
    }
    public int getTime_length() {
        return time_length;
    }

    public void setWin_remainder(int win_remainder) {
        this.win_remainder = win_remainder;
    }
    public int getWin_remainder() {
        return win_remainder;
    }

    public void setIs_commented(int is_commented) {
        this.is_commented = is_commented;
    }
    public int getIs_commented() {
        return is_commented;
    }

    public void setFinish_ratio(int finish_ratio) {
        this.finish_ratio = finish_ratio;
    }
    public int getFinish_ratio() {
        return finish_ratio;
    }

    public void setLimit_num(int limit_num) {
        this.limit_num = limit_num;
    }
    public int getLimit_num() {
        return limit_num;
    }

    public void setExpress_time(int express_time) {
        this.express_time = express_time;
    }
    public int getExpress_time() {
        return express_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }
    public long getCreate_time() {
        return create_time;
    }

    public void setLottery_time(int lottery_time) {
        this.lottery_time = lottery_time;
    }
    public int getLottery_time() {
        return lottery_time;
    }

}
