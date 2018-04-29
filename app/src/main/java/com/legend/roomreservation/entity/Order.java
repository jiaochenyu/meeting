package com.legend.roomreservation.entity;

import android.location.Address;

import java.io.Serializable;

/**
 * Created by JCY on 2018/4/20.
 * 说明：
 */

public class Order implements Serializable {

    /**
     * id : 3
     * orderNo : 20180418233909
     * userId : 0
     * money : 0
     * createTime : 2018-04-18 23:39:10
     * status : 1
     * addressId : 1
     */

    private int id;
    private String orderNo;
    private int userId;
    private float money;
    private String createTime;
    private int status; //1已支付等待收货， 0 未支付 ，2 确认收货，订单结束
    private int addressId;
    private Address address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
