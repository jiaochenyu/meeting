package com.legend.roomreservation.entity;

import java.io.Serializable;

/**
 * Created by JCY on 2018/4/25.
 * 说明：
 */

public class Room implements Serializable {

    /**
     * id : 1
     * peoplenumMin : 10
     * peoplenumMax : 25
     * addressKm : 0
     * addressDetail : 丰台北路
     * price : 3
     * picUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524646222120&di=ad2e707424429beed18289069abcccd7&imgtype=0&src=http%3A%2F%2Fpic.qiantucdn.com%2F58pic%2F15%2F37%2F73%2F78N58PICM3J_1024.jpg
     * timeAvaluable : 2018-04-25 05:22:38
     * ifVideo : 0
     * ifSofa : 0
     * ifWarm : 0
     * ifWashroom : 0
     * ifCarpet : 0
     * ifWindow : 0
     */

    private int id;
    private int peoplenumMin;
    private int peoplenumMax;
    private float addressKm;
    private String name;
    private String addressDetail;
    private float price;
    private String picUrl;
    private String timeAvaluable;
    private int ifVideo;
    private int ifSofa;
    private int ifWarm;
    private int ifWashroom;
    private int ifCarpet;
    private int ifWindow;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPeoplenumMin() {
        return peoplenumMin;
    }

    public void setPeoplenumMin(int peoplenumMin) {
        this.peoplenumMin = peoplenumMin;
    }

    public int getPeoplenumMax() {
        return peoplenumMax;
    }

    public void setPeoplenumMax(int peoplenumMax) {
        this.peoplenumMax = peoplenumMax;
    }

    public float getAddressKm() {
        return addressKm;
    }

    public void setAddressKm(float addressKm) {
        this.addressKm = addressKm;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getTimeAvaluable() {
        return timeAvaluable;
    }

    public void setTimeAvaluable(String timeAvaluable) {
        this.timeAvaluable = timeAvaluable;
    }

    public int getIfVideo() {
        return ifVideo;
    }

    public void setIfVideo(int ifVideo) {
        this.ifVideo = ifVideo;
    }

    public int getIfSofa() {
        return ifSofa;
    }

    public void setIfSofa(int ifSofa) {
        this.ifSofa = ifSofa;
    }

    public int getIfWarm() {
        return ifWarm;
    }

    public void setIfWarm(int ifWarm) {
        this.ifWarm = ifWarm;
    }

    public int getIfWashroom() {
        return ifWashroom;
    }

    public void setIfWashroom(int ifWashroom) {
        this.ifWashroom = ifWashroom;
    }

    public int getIfCarpet() {
        return ifCarpet;
    }

    public void setIfCarpet(int ifCarpet) {
        this.ifCarpet = ifCarpet;
    }

    public int getIfWindow() {
        return ifWindow;
    }

    public void setIfWindow(int ifWindow) {
        this.ifWindow = ifWindow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
