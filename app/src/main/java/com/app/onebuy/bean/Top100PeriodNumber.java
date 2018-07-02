package com.app.onebuy.bean;

/**
 * Created by otis on 2018/5/25.
 */

public class Top100PeriodNumber {

    private String number;
    private String order_sn;
    private int period_id;
    private String datetime;
    private long convert_val;
    private int is_robot;
    private long user_id;
    private long create_time;
    public void setNumber(String number) {
        this.number = number;
    }
    public String getNumber() {
        return number;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }
    public String getOrder_sn() {
        return order_sn;
    }

    public void setPeriod_id(int period_id) {
        this.period_id = period_id;
    }
    public int getPeriod_id() {
        return period_id;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
    public String getDatetime() {
        return datetime;
    }

    public void setConvert_val(long convert_val) {
        this.convert_val = convert_val;
    }
    public long getConvert_val() {
        return convert_val;
    }

    public void setIs_robot(int is_robot) {
        this.is_robot = is_robot;
    }
    public int getIs_robot() {
        return is_robot;
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
