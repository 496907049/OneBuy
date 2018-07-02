package com.app.onebuy.bean;


import java.util.ArrayList;

public class UserSpendHistoryListBean extends BaseListBean {

    private ArrayList<UserSpendHistoryListData> list = new ArrayList<UserSpendHistoryListData>();

    public ArrayList<UserSpendHistoryListData> getList() {
        return list;
    }

    public void setList(ArrayList<UserSpendHistoryListData> list) {
        this.list = list;
    }

    public void addListBean(UserSpendHistoryListBean listbean) {
        if (listbean == null)
            return;
        if (this.getCurrentPage() == INIT_PAGE) {
            setList(listbean.getList());
        } else {
            if (list == null)
                list = new ArrayList<UserSpendHistoryListData>();
            list.addAll(listbean.getList());
        }
        setListBeanData(listbean);
    }
}
