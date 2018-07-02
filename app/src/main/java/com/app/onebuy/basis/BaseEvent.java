package com.app.onebuy.basis;

public class BaseEvent {


    public static final int TYPE_LANGUAGE = 1;
    private int type = TYPE_LANGUAGE;

    public BaseEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}