package com.legend.roomreservation.entity;

import java.io.Serializable;

/**
 * Created by JCY on 2018/3/10.
 * 说明：
 */

public class EventFlag implements Serializable {
    String flag;
    Object mObject;

    public EventFlag(String flag) {
        this.flag = flag;
    }

    public EventFlag(String flag, Object object) {
        this.flag = flag;
        mObject = object;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Object getObject() {
        return mObject;
    }

    public void setObject(Object object) {
        mObject = object;
    }
}
