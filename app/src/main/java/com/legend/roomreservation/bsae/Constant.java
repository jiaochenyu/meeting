package com.legend.roomreservation.bsae;

/**
 * Created by JCY on 2018/2/23.
 * 说明：
 */

public class Constant {


    public static int REQUEST_WHAT = 10001;

    public static String JUMP_USER_ADDRESS = "a";
    public static String SELECT_ADDRESS = "b";


    public static String CREATE_ADDRESS = "create";
    public static String EDIT_ADDRESS = "edit";

    public static String[] CONDITIONs= {"音响","沙发坐","投影","空调","暖气","落地窗","洗手间","地毯","投影仪"};
    public static String[] LARGERS= {"10-20(m²)","20-50(m²)","50-100(m²)","100-200(m²)","200以上(m²)","其他"};






    /**
     * EventBus
     */
    public static String EV_SELECT_ADDRESS = "ev_select_address";
    public static String EV_UPDATE_ADDRESS = "ev_update_address";
    public static String EV_CHANGE_CART = "ev_change_cart";//购物车改变了。
    public static String EV_UPDATE_COLLECT = "ev_change_collect";//改变了收藏状态。
    public static String EV_ORDER = "ev_change_ORDER";//
    public static String EV_UPDATE_USER = "ev_updade_user";//







}
