package com.app.onebuy.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by otis on 2018/5/8.
 */

public class GoodsTypeBean extends BasisBean {

    public final static int DOING = 1;    //进行中
    public final static int  PUBLISH = 2;    //即将揭晓
    public final static int  END = 3;    //已揭晓
    public final static int  ME = 1;    //满额
    public final static int  DD = 2;    //到点



    private int parent_id;
    private String title;
    private String module;
    private int order_id;
    private int user_id;
    private int is_input_text;
    private String mark;
    private String description;
    private String lottery_time_desc;    //开奖时间
    private int status;

    private int order_num = 1;//购买数量，用于订单提交页面。


    public String getLottery_time_desc() {
        return lottery_time_desc;
    }
    public void setLottery_time_desc(String lottery_time_desc) {
        this.lottery_time_desc = lottery_time_desc;
    }
    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }
    public int getParent_id() {
        return parent_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setModule(String module) {
        this.module = module;
    }
    public String getModule() {
        return module;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
    public int getOrder_id() {
        return order_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public int getUser_id() {
        return user_id;
    }

    public void setIs_input_text(int is_input_text) {
        this.is_input_text = is_input_text;
    }
    public int getIs_input_text() {
        return is_input_text;
    }


    public void setMark(String mark) {
        this.mark = mark;
    }
    public String getMark() {
        return mark;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }


    private String category_name;
    private GoodsBean period_info;
    private String period_num;
    private String goods_name;
    private String price;
    private int period_id;
    private String img_url;
    private String images_id;
    private int goods_id;
    private String category_id;
    private String user_head_url;       //用户头像
    private String surplus_num;         //剩余人次
    private String amount;         //总价值
    private long create_time;
    private int lottery_type;     //lottery_type=1为满额2为到点


    private long surplus_time;  //剩余时间

    public long getSurplus_time() {
        return surplus_time;
    }

    public void setSurplus_time(long surplus_time) {
        this.surplus_time = surplus_time;
    }

    public int getLottery_type() {
        return lottery_type;
    }

    public void setLottery_type(int lottery_type) {
        this.lottery_type = lottery_type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUser_head_url() {
        return user_head_url;
    }

    public void setUser_head_url(String user_head_url) {
        this.user_head_url = user_head_url;
    }

    public String getSurplus_num() {
        return surplus_num;
    }

    public void setSurplus_num(String surplus_num) {
        this.surplus_num = surplus_num;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
    public String getCategory_name() {
        return category_name;
    }

    public void setPeriod_info(GoodsBean period_info) {
        this.period_info = period_info;
    }
    public GoodsBean getPeriod_info() {
        return period_info;
    }

    public void setPeriod_num(String period_num) {
        this.period_num = period_num;
    }
    public String getPeriod_num() {
        return period_num;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }
    public String getGoods_name() {
        return goods_name;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String getPrice() {
        return price;
    }

    public void setPeriod_id(int period_id) {
        this.period_id = period_id;
    }
    public int getPeriod_id() {
        return period_id;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
    public String getImg_url() {
        return img_url;
    }

    public void setImages_id(String images_id) {
        this.images_id = images_id;
    }
    public String getImages_id() {
        return images_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }
    public int getGoods_id() {
        return goods_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }
    public String getCategory_id() {
        return category_id;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }
    public long getCreate_time() {
        return create_time;
    }



    private List<String> img_list;
    private int time_length;
    private String content;
    private long edit_time;
    private Long limit_num;

    public void setImg_list(List<String> img_list) {
        this.img_list = img_list;
    }
    public List<String> getImg_list() {
        return img_list;
    }
    public void setTime_length(int time_length) {
        this.time_length = time_length;
    }
    public int getTime_length() {
        return time_length;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public void setEdit_time(long edit_time) {
        this.edit_time = edit_time;
    }
    public long getEdit_time() {
        return edit_time;
    }

    public void setLimit_num(Long limit_num) {
        this.limit_num = limit_num;
    }
    public Long getLimit_num() {
        return limit_num;
    }


    /**
     * 往期中奖
     */
    private String win_number;
    private int win_order_number;
    private String date_time;
    private String win_user_name;
    private String head_url;
    private long win_user_id;

    public String getPeriod_num_desc() {
        return period_num_desc;
    }

    public void setPeriod_num_desc(String period_num_desc) {
        this.period_num_desc = period_num_desc;
    }

    private String period_num_desc;
    public void setWin_number(String win_number) {
        this.win_number = win_number;
    }
    public String getWin_number() {
        return win_number;
    }

    public void setWin_order_number(int win_order_number) {
        this.win_order_number = win_order_number;
    }
    public int getWin_order_number() {
        return win_order_number;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
    public String getDate_time() {
        return date_time;
    }

    public void setWin_user_name(String win_user_name) {
        this.win_user_name = win_user_name;
    }
    public String getWin_user_name() {
        return win_user_name;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }
    public String getHead_url() {
        return head_url;
    }

    public void setWin_user_id(long win_user_id) {
        this.win_user_id = win_user_id;
    }
    public long getWin_user_id() {
        return win_user_id;
    }


    /**
     * 参与记录
     */
    private int number;
    private String nickname;
    private String ip;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 首页 加载更多
     */
    private String finish_ratio_desc;
    private double finish_ratio;

    public String getFinish_ratio_desc() {
        return finish_ratio_desc;
    }

    public void setFinish_ratio_desc(String finish_ratio_desc) {
        this.finish_ratio_desc = finish_ratio_desc;
    }

    public double getFinish_ratio() {
        return finish_ratio;
    }

    public void setFinish_ratio(double finish_ratio) {
        this.finish_ratio = finish_ratio;
    }

    private List<String> images_list;
    public List<String> getImages_list() {
        return images_list;
    }

    public void setImages_list(List<String> images_list) {
        this.images_list = images_list;
    }

    public int getOrder_num() {
        return order_num;
    }

    public void setOrder_num(int order_num) {
        this.order_num = order_num;
    }
    public void plusNum(){
        if(order_num >= getLeftNum())return;
        order_num++;
    }
    public void minusNum(){
        if(order_num <= 1){
            order_num = 1;
            return;
        }
        order_num--;
    }
    public void setOrderNum(int num){
        if(num > getLeftNum()){
            order_num = getLeftNum();
            return;
        }
        order_num = num;
    }
    public int getLeftNum(){
        return  period_info.getLimit_num() - period_info.getUsed_num();
    }
    public String getTotalPrice(){
       Double one = Double.valueOf(order_num);
       Double two = Double.valueOf(price);
        BigDecimal b1=new BigDecimal(Double.toString(one));
        BigDecimal b2=new BigDecimal(Double.toString(two));
        return String .format("%.2f",b1.multiply(b2).doubleValue());
    }
}
