package com.app.onebuy.bean;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;

public class AddressListBean extends BaseListBean {

    @JSONField(name = "data")
    private ArrayList<AddressListData> list = new ArrayList<AddressListData>();

    public ArrayList<AddressListData> getList() {
        return list;
    }

    public void setList(ArrayList<AddressListData> list) {
        this.list = list;
    }

    public void addListBean(AddressListBean listbean) {
        if (listbean == null)
            return;
        if (this.getCurrentPage() == INIT_PAGE) {
            setList(listbean.getList());
        } else {
            if (list == null)
                list = new ArrayList<AddressListData>();
            list.addAll(listbean.getList());
        }
        setListBeanData(listbean);
    }

    public int getDefaultPosition(){
        for(int i = 0, l = list.size(); i < l ; i ++){
            if(list.get(i).getIs_defaultBoolean())return i;
        }
        return 0;
    }
}
