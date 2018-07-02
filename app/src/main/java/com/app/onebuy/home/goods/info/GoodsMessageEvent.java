package com.app.onebuy.home.goods.info;

/**
 * Created by otis on 2018/5/11.
 */

public class GoodsMessageEvent {

    private String message;

    public  GoodsMessageEvent(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
