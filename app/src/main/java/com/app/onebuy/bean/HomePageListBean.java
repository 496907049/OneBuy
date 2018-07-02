package com.app.onebuy.bean;

import java.util.List;

/**
 * Created by otis on 2018/4/12.
 */

public class HomePageListBean extends BasisBean {

    private List<Banner> banner;
    private List<String> lottery_list;
    private List<GoodsBean> goods_list;
    public void setBanner(List<Banner> banner) {
        this.banner = banner;
    }
    public List<Banner> getBanner() {
        return banner;
    }

    public void setLottery_list(List<String> lottery_list) {
        this.lottery_list = lottery_list;
    }
    public List<String> getLottery_list() {
        return lottery_list;
    }

    public List<GoodsBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsBean> goods_Bean_list) {
        this.goods_list = goods_Bean_list;
    }

}
