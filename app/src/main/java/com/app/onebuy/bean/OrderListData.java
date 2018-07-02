package com.app.onebuy.bean;

import java.util.Date;
import java.util.List;

public class OrderListData extends BasisBean {

    /**
     * period_id : 6
     * period_num : 2
     * goods_id : 3
     * goods_name : 华为(HUAWEI) Mate 10 全网通版4GB+64GB 4G手机
     * limit_num : 4399
     * used_num : 4399
     * time_length : 0
     * win_user_id : 621803681
     * win_user_name : 天天
     * win_number : 10000072
     * win_a_number : 0
     * win_remainder : 0
     * win_order_number : 26
     * address_id : 1
     * express_type : 0
     * express_number :
     * express_status : 未发货
     * express_time : 0
     * confirm_express_time : 0
     * is_commented : 0
     * comment_time : 0
     * finish_time : 1524123878
     * lottery_time : 1524125156
     * create_time : 1523887434
     * status : 3
     * img_url : http://api.ebuy.suncco.com/uploads/file/20180416/aba47d8bab0b7b1225fc7b335e6518c4.jpg
     * number_list : ["10004234","10000855","10003698","10003024","10000354","10000072","10002224","10003385","10001647","10001828","10004059","10001387","10003607","10003227","10002589","10003005","10003563","10003729","10001422","10001605","10001731","10002128","10003313","10001207","10003726","10004377"]
     * order_number : 26
     * finish_ratio : 1
     * finish_ratio_desc : 100%
     * status_desc : 已揭晓
     * date_time : 2018-04-16 22:03:54
     * express_type_desc :
     */

    public static final String STATUS_PARAMS_ALL = "", STATUS_PARAMS_DOING = "doing", STATUS_PARAMS_FINISH = "finish";

    public static final int STATUS_FINISH = 3, STATUS_DOING = 1, STATUS_COMING = 2;

    public static final int STATUS2_NO_ADDRESS = 1,   //没有地址
            STATUS2_WAITING_SEND = 2,                   //待发货
            STATUS2_WAITING_RECEIVED = 3,               //待收货
            STATUS2_TO_SHOW = 4,                            //去晒单
            STATUS2_COMPLETE = 5;                           //交易完成

    private String period_id;
    private int period_num;
    private String goods_id;
    private String goods_name;
    private int limit_num;
    private int used_num;
    private int time_length;
    private int win_user_id;
    private String win_user_name;
    private String win_number;
    private String win_a_number;
    private int win_remainder;
    private int win_order_number;
    private int address_id;
    private int express_type;
    private String express_number;
    private String express_status;
    private int express_time;
    private int confirm_express_time;
    private int is_commented;
    private int comment_time;
    private long finish_time;
    private long lottery_time;
    private long create_time;
    private String status;
    private String img_url;
    private String order_number;
    private int finish_ratio;
    private String finish_ratio_desc;
    private String status_desc;
    private String date_time;
    private String express_type_desc;
    private List<String> number_list;
    private int status2;

    public int getStatus2() {
        return status2;
    }

    public void setStatus2(int status2) {
        this.status2 = status2;
    }

    public String getPeriod_id() {
        return period_id;
    }

    public void setPeriod_id(String period_id) {
        this.period_id = period_id;
    }

    public int getPeriod_num() {
        return period_num;
    }

    public void setPeriod_num(int period_num) {
        this.period_num = period_num;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public int getLimit_num() {
        return limit_num;
    }

    public void setLimit_num(int limit_num) {
        this.limit_num = limit_num;
    }

    public int getUsed_num() {
        return used_num;
    }

    public void setUsed_num(int used_num) {
        this.used_num = used_num;
    }

    public int getTime_length() {
        return time_length;
    }

    public void setTime_length(int time_length) {
        this.time_length = time_length;
    }

    public int getWin_user_id() {
        return win_user_id;
    }

    public void setWin_user_id(int win_user_id) {
        this.win_user_id = win_user_id;
    }

    public String getWin_user_name() {
        return win_user_name;
    }

    public void setWin_user_name(String win_user_name) {
        this.win_user_name = win_user_name;
    }

    public String getWin_number() {
        return win_number;
    }

    public void setWin_number(String win_number) {
        this.win_number = win_number;
    }

    public String getWin_a_number() {
        return win_a_number;
    }

    public void setWin_a_number(String win_a_number) {
        this.win_a_number = win_a_number;
    }

    public int getWin_remainder() {
        return win_remainder;
    }

    public void setWin_remainder(int win_remainder) {
        this.win_remainder = win_remainder;
    }

    public int getWin_order_number() {
        return win_order_number;
    }

    public void setWin_order_number(int win_order_number) {
        this.win_order_number = win_order_number;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getExpress_type() {
        return express_type;
    }

    public void setExpress_type(int express_type) {
        this.express_type = express_type;
    }

    public String getExpress_number() {
        return express_number;
    }

    public void setExpress_number(String express_number) {
        this.express_number = express_number;
    }

    public String getExpress_status() {
        return express_status;
    }

    public void setExpress_status(String express_status) {
        this.express_status = express_status;
    }

    public int getExpress_time() {
        return express_time;
    }

    public void setExpress_time(int express_time) {
        this.express_time = express_time;
    }

    public int getConfirm_express_time() {
        return confirm_express_time;
    }

    public void setConfirm_express_time(int confirm_express_time) {
        this.confirm_express_time = confirm_express_time;
    }

    public int getIs_commented() {
        return is_commented;
    }

    public boolean getIs_commentedBoolean() {
        return is_commented == 1;
    }

    public void setIs_commented(int is_commented) {
        this.is_commented = is_commented;
    }

    public int getComment_time() {
        return comment_time;
    }

    public void setComment_time(int comment_time) {
        this.comment_time = comment_time;
    }

    public long getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(long finish_time) {
        this.finish_time = finish_time;
    }

    public long getFinishTimeLeft() {
        return finish_time - new Date().getTime();
    }

    public long getLottery_time() {
        return lottery_time;
    }

    public void setLottery_time(long lottery_time) {
        this.lottery_time = lottery_time;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public int getFinish_ratio() {
        return finish_ratio;
    }

    public void setFinish_ratio(int finish_ratio) {
        this.finish_ratio = finish_ratio;
    }

    public String getFinish_ratio_desc() {
        return finish_ratio_desc;
    }

    public void setFinish_ratio_desc(String finish_ratio_desc) {
        this.finish_ratio_desc = finish_ratio_desc;
    }

    public String getStatus_desc() {
        return status_desc;
    }

    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getExpress_type_desc() {
        return express_type_desc;
    }

    public void setExpress_type_desc(String express_type_desc) {
        this.express_type_desc = express_type_desc;
    }

    public List<String> getNumber_list() {
        return number_list;
    }

    public void setNumber_list(List<String> number_list) {
        this.number_list = number_list;
    }
}
