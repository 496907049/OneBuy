package com.app.onebuy.bean;

/**
 * Created by otis on 2018/4/20.
 */

public class GoodsBean extends BasisBean{

    private String goods_name;
    private String img_url;

    private int lottery_type;     //lottery_type=1为满额2为到点

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }
    public String getGoods_name() {
        return goods_name;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
    public String getImg_url() {
        return img_url;
    }

    private int period_id;

    private String win_number;

    private int win_order_number;

    private int express_type;

    private int goods_id;

    private int time_length;

    private int is_commented;

    private String express_number;

    private int used_num;

    private int win_user_id;

    private int express_status;

    private int period_num;

    private int status;

    private int comment_time;

    private double finish_ratio;

    private int confirm_express_time;

    private int limit_num;

    private int express_time;

    private String win_user_name;

    private Long finish_time;

    private String finish_ratio_desc;

    private int address_id;

    private String status_desc;

    private String surplus_num;

    private long surplus_time;

    private String win_head_url;       //用户头像

    private int create_time;

    private int lottery_time;

    private long end_time;

    private String win_datetime;            //揭晓时间

    public String getWin_datetime() {
        return win_datetime;
    }

    public void setWin_datetime(String win_datetime) {
        this.win_datetime = win_datetime;
    }
    public int getLottery_type() {
        return lottery_type;
    }

    public void setLottery_type(int lottery_type) {
        this.lottery_type = lottery_type;
    }

    public long getSurplus_time() {
        return surplus_time;
    }

    public void setSurplus_time(long surplus_time) {
        this.surplus_time = surplus_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public String getWin_head_url() {
        return win_head_url;
    }

    public void setWin_head_url(String win_head_url) {
        this.win_head_url = win_head_url;
    }

    public String getSurplus_num() {
        return surplus_num;
    }

    public void setSurplus_num(String surplus_num) {
        this.surplus_num = surplus_num;
    }

    public void setPeriod_id(int period_id){
        this.period_id = period_id;
    }
    public int getPeriod_id(){
        return this.period_id;
    }
    public void setWin_number(String win_number){
        this.win_number = win_number;
    }
    public String getWin_number(){
        return this.win_number;
    }
    public void setWin_order_number(int win_order_number){
        this.win_order_number = win_order_number;
    }
    public int getWin_order_number(){
        return this.win_order_number;
    }
    public void setExpress_type(int express_type){
        this.express_type = express_type;
    }
    public int getExpress_type(){
        return this.express_type;
    }
    public void setGoods_id(int goods_id){
        this.goods_id = goods_id;
    }
    public int getGoods_id(){
        return this.goods_id;
    }
    public void setTime_length(int time_length){
        this.time_length = time_length;
    }
    public int getTime_length(){
        return this.time_length;
    }
    public void setIs_commented(int is_commented){
        this.is_commented = is_commented;
    }
    public int getIs_commented(){
        return this.is_commented;
    }
    public void setExpress_number(String express_number){
        this.express_number = express_number;
    }
    public String getExpress_number(){
        return this.express_number;
    }
    public void setUsed_num(int used_num){
        this.used_num = used_num;
    }
    public int getUsed_num(){
        return this.used_num;
    }
    public void setWin_user_id(int win_user_id){
        this.win_user_id = win_user_id;
    }
    public int getWin_user_id(){
        return this.win_user_id;
    }
    public void setExpress_status(int express_status){
        this.express_status = express_status;
    }
    public int getExpress_status(){
        return this.express_status;
    }
    public void setPeriod_num(int period_num){
        this.period_num = period_num;
    }
    public int getPeriod_num(){
        return this.period_num;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
    public void setComment_time(int comment_time){
        this.comment_time = comment_time;
    }
    public int getComment_time(){
        return this.comment_time;
    }
    public void setFinish_ratio(double finish_ratio){
        this.finish_ratio = finish_ratio;
    }
    public double getFinish_ratio(){
        return this.finish_ratio;
    }
    public void setConfirm_express_time(int confirm_express_time){
        this.confirm_express_time = confirm_express_time;
    }
    public int getConfirm_express_time(){
        return this.confirm_express_time;
    }
    public void setLimit_num(int limit_num){
        this.limit_num = limit_num;
    }
    public int getLimit_num(){
        return this.limit_num;
    }
    public void setExpress_time(int express_time){
        this.express_time = express_time;
    }
    public int getExpress_time(){
        return this.express_time;
    }
    public void setWin_user_name(String win_user_name){
        this.win_user_name = win_user_name;
    }
    public String getWin_user_name(){
        return this.win_user_name;
    }
    public void setFinish_time(Long finish_time){
        this.finish_time = finish_time;
    }
    public Long getFinish_time(){
        return this.finish_time;
    }
    public void setFinish_ratio_desc(String finish_ratio_desc){
        this.finish_ratio_desc = finish_ratio_desc;
    }
    public String getFinish_ratio_desc(){
        return this.finish_ratio_desc;
    }
    public void setAddress_id(int address_id){
        this.address_id = address_id;
    }
    public int getAddress_id(){
        return this.address_id;
    }
    public void setStatus_desc(String status_desc){
        this.status_desc = status_desc;
    }
    public String getStatus_desc(){
        return this.status_desc;
    }
    public void setCreate_time(int create_time){
        this.create_time = create_time;
    }
    public int getCreate_time(){
        return this.create_time;
    }
    public void setLottery_time(int lottery_time){
        this.lottery_time = lottery_time;
    }
    public int getLottery_time(){
        return this.lottery_time;
    }


}
