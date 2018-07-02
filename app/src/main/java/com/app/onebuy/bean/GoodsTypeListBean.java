package com.app.onebuy.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by otis on 2018/5/8.
 */

public class GoodsTypeListBean extends BaseListBean {

    private List<GoodsTypeBean> list;
    public void setList(List<GoodsTypeBean> list) {
        this.list = list;
    }
    public List<GoodsTypeBean> getList() {
        return list;
    }

    public void addListBean(GoodsTypeListBean listbean) {
        if (listbean == null)
            return;
        if (this.getCurrentPage() == INIT_PAGE) {
            setList(listbean.getList());
        } else {
            if (list == null)
                list = new ArrayList<GoodsTypeBean>();
            list.addAll(listbean.getList());
        }
        setListBeanData(listbean);
    }

}
