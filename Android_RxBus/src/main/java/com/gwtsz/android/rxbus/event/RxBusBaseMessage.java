package com.gwtsz.android.rxbus.event;

/**
 * Created by ari on 2017/7/7.
 */

public class RxBusBaseMessage {

    private  String code;
    private Object object;
    private RxBusBaseMessage(){}
    public RxBusBaseMessage(String code, Object object){
        this.code=code;
        this.object=object;
    }

    public String getCode() {
        return code;
    }

    public Object getObject() {
        return object;
    }
}
