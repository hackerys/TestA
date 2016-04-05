package com.example.administrator.testa;

import java.util.ArrayList;

/**
 * Created by wp on 2015/12/14.
 */
public class SellerOderBean {
    //未付款
    private double unpay;
    //实付款
    private double should_pay;
    //客户名
    private String order_custom;
    //订单号
    private String bill_number;
    //订单状态   1预订单 2待发货 3已发货 4已完成 5已取消 6待付款
    private int order_status;
    //商品
    private ArrayList<SellerOrderGoodBean> mOrderGoodBeans;
    //线上1  线下2
    private int payway;
    //预定 true预定  fasle没有预定
    private boolean is_order;
    public SellerOderBean(double mUnpay, double mShould_pay, String mOrder_custom, String mBill_number, int mOrder_status, ArrayList<SellerOrderGoodBean> mOrderGoodBeans,int payway, boolean is_order) {
        unpay = mUnpay;
        should_pay = mShould_pay;
        order_custom = mOrder_custom;
        bill_number = mBill_number;
        order_status = mOrder_status;
        this.mOrderGoodBeans = mOrderGoodBeans;
        this.payway=payway;
        this.is_order=is_order;
    }

    public double getShould_pay() {
        return should_pay;
    }

    public void setShould_pay(double mShould_pay) {
        should_pay = mShould_pay;
    }

    public double getUnpay() {
        return unpay;
    }

    public void setUnpay(double mUnpay) {
        unpay = mUnpay;
    }

    public String getOrder_custom() {
        return order_custom;
    }

    public void setOrder_custom(String mOrder_custom) {
        order_custom = mOrder_custom;
    }

    public String getBill_number() {
        return bill_number;
    }

    public void setBill_number(String mBill_number) {
        bill_number = mBill_number;
    }

    public ArrayList<SellerOrderGoodBean> getOrderGoodBeans() {
        return mOrderGoodBeans;
    }

    public void setOrderGoodBeans(ArrayList<SellerOrderGoodBean> mOrderGoodBeans) {
        this.mOrderGoodBeans = mOrderGoodBeans;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int mOrder_status) {
        order_status = mOrder_status;
    }

    public int getPayway() {
        return payway;
    }

    public void setPayway(int mPayway) {
        payway = mPayway;
    }

    public boolean is_order() {
        return is_order;
    }

    public void setIs_order(boolean mIs_order) {
        is_order = mIs_order;
    }
}
