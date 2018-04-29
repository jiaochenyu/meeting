package com.legend.roomreservation.bsae;

/**
 * Created by JCY on 2018/2/23.
 * 说明：
 */

public class ConnectUrl {
    public static String IMAGE_BANNER_URL = "https://pic3.zhimg.com/80/d1127c7fef2460c71c19bd3ad4b1e69a_hd.jpg";
    public static String IMAGE_BANNER_URL2 = "https://pic4.zhimg.com/91ef714dafaf9ffee4f43f842d6d4b78_r.jpg";
    public static String IMAGE_BANNER_URL3 = "https://pic2.zhimg.com/80/c119699b1c8c977df846d6182a7032cc_hd.jpg";

    /**
     * 登录注册
     */

//    public static String HTTP = "http://server2.guanweiming.com:8415/";
    public static String HTTP = "http://192.168.0.106:8426/";
//    public static String HTTP = "http://192.168.16.110:8415/";


    /**
     * 首页
     */

    public static String AREA_LIST = HTTP + "Rooms/getCategoryList"; //
    public static String AREA_REQUIREMENT = HTTP + "Rooms/getRequirement"; //


    /**
     * 搜索
     */
    public static String SEARCH = HTTP + "Rooms/searchRooms"; // post







    public static String CART_DELETE = HTTP + "shopCar/clear"; //


    /**
     * 订单
     */
    public static String ORDER_LIST = HTTP + "order/find"; //
    public static String ORDER_DOWN = HTTP + "order/down"; //
    public static String ORDER_PAY = HTTP + "order/pay"; //
    public static String ORDER_CHANGE_STATUS = HTTP + "order/changeStatus";
    public static String ORDER_DELETE = HTTP + "order/delete"; // post 请求






}

