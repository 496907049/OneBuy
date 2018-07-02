package com.app.onebuy.bean;


import java.util.ArrayList;

public class IntegralDetailListBean extends BaseListBean {

    private ArrayList<IntegralDetailListData> list = new ArrayList<IntegralDetailListData>();
//    @JSONField(name = "data")
//    private ArrayList<IntegralDetailListData> listFormember = new ArrayList<IntegralDetailListData>();

//    private String promotePoints;
//    private String totalAmount ;
//    private String success_invite_num;
//    private String invite_points;

    public ArrayList<IntegralDetailListData> getList() {
        return list;
    }

    public void setList(ArrayList<IntegralDetailListData> list) {
        this.list = list;
    }

    public void addListBean(IntegralDetailListBean listbean) {
        if (listbean == null)
            return;
        if (this.getCurrentPage() == INIT_PAGE) {
            setList(listbean.getList());
        } else {
            if (list == null)
                list = new ArrayList<IntegralDetailListData>();
            list.addAll(listbean.getList());
        }
        setListBeanData(listbean);
    }

//    public String getPromotePoints() {
//        return promotePoints;
//    }
//
//    public void setPromotePoints(String promotePoints) {
//        this.promotePoints = promotePoints;
//    }
//
//    public String getTotalAmount() {
//        return totalAmount;
//    }
//
//    public void setTotalAmount(String totalAmount) {
//        this.totalAmount = totalAmount;
//    }
//
//    public String getSuccess_invite_num() {
//        if(TextUtils.isEmpty(success_invite_num))return "0";
//        return success_invite_num;
//    }
//
//    public void setSuccess_invite_num(String success_invite_num) {
//        this.success_invite_num = success_invite_num;
//    }
//
//    public String getInvite_points() {
//        if(TextUtils.isEmpty(invite_points))return "0";
//        return invite_points;
//    }
//
//    public void setInvite_points(String invite_points) {
//        this.invite_points = invite_points;
//    }

//    public ArrayList<IntegralDetailListData> getListFormember() {
//        return listFormember;
//    }
//
//    public void setListFormember(ArrayList<IntegralDetailListData> listFormember) {
//        this.listFormember = listFormember;
//    }
//
//    public void format(){
//        this.list.addAll(listFormember);
//    }
}
