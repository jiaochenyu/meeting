package com.legend.roomreservation.entity;

import java.io.Serializable;

/**
 * Created by JCY on 2018/4/28.
 * 说明：
 */

public class Requirement implements Serializable {

    /**
     * id : 0
     * name : string
     */

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
