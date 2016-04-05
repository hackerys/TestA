package com.example.administrator.testa.wallet;

/**
 * Created by wp on 2015/12/16.
 */
public class RecordBean {
    private String name;
    private String fruit_type;
    private String deal_time;
    private double turnover;

    public String getName() {
        return name;
    }

    public void setName(String mName) {
        name = mName;
    }

    public String getFruit_type() {
        return fruit_type;
    }

    public void setFruit_type(String mFruit_type) {
        fruit_type = mFruit_type;
    }

    public String getDeal_time() {
        return deal_time;
    }

    public void setDeal_time(String mDeal_time) {
        deal_time = mDeal_time;
    }

    public double getTurnover() {
        return turnover;
    }

    public void setTurnover(double mTurnover) {
        turnover = mTurnover;
    }
}
