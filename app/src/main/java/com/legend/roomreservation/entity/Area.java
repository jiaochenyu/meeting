package com.legend.roomreservation.entity;

import java.io.Serializable;

/**
 * Created by JCY on 2018/4/27.
 * 说明：
 */

public class Area implements Serializable {

    /**
     * id : 1
     * peopleMax : 5
     * peopleMin : 0
     * area : 10
     */

    private int id;
    private int peopleMax;
    private int peopleMin;
    private int area;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPeopleMax() {
        return peopleMax;
    }

    public void setPeopleMax(int peopleMax) {
        this.peopleMax = peopleMax;
    }

    public int getPeopleMin() {
        return peopleMin;
    }

    public void setPeopleMin(int peopleMin) {
        this.peopleMin = peopleMin;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }
}
