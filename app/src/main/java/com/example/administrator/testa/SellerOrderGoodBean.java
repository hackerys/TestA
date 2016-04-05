package com.example.administrator.testa;

/**
 * Created by wp on 2015/12/14.
 */
public class SellerOrderGoodBean {
    private int fruit_img;
    private String fruit_name;
    private double kg_per_box;
    private double unit_price;
    private int total_box;
    private double total_weight;
    private String good_unit;
    private double total_price;

    public SellerOrderGoodBean(int mFruit_img, String mFruit_name, double mKg_per_box, double mUnit_price, int mTotal_box, double mTotal_weight, String mGood_unit, double total_price) {
        fruit_img = mFruit_img;
        fruit_name = mFruit_name;
        kg_per_box = mKg_per_box;
        unit_price = mUnit_price;
        total_box = mTotal_box;
        total_weight = mTotal_weight;
        good_unit = mGood_unit;
        this.total_price = total_price;
    }

    public SellerOrderGoodBean(int mFruit_img, String mFruit_name, double mKg_per_box, double mUnit_price, int mTotal_box, double mTotal_weight, String mGood_unit) {
        fruit_img = mFruit_img;
        fruit_name = mFruit_name;
        kg_per_box = mKg_per_box;
        unit_price = mUnit_price;
        total_box = mTotal_box;
        total_weight = mTotal_weight;
        good_unit = mGood_unit;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double mTotal_price) {
        total_price = mTotal_price;
    }

    public String getGood_unit() {
        return good_unit;
    }

    public void setGood_unit(String mGood_unit) {
        good_unit = mGood_unit;
    }

    public double getTotal_weight() {
        return total_weight;
    }

    public void setTotal_weight(double mTotal_weight) {
        total_weight = mTotal_weight;
    }

    public int getTotal_box() {
        return total_box;
    }

    public void setTotal_box(int mTotal_box) {
        total_box = mTotal_box;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double mUnit_price) {
        unit_price = mUnit_price;
    }

    public double getKg_per_box() {
        return kg_per_box;
    }

    public void setKg_per_box(double mKg_per_box) {
        kg_per_box = mKg_per_box;
    }

    public String getFruit_name() {
        return fruit_name;
    }

    public void setFruit_name(String mFruit_name) {
        fruit_name = mFruit_name;
    }

    public int getFruit_img() {
        return fruit_img;
    }

    public void setFruit_img(int mFruit_img) {
        fruit_img = mFruit_img;
    }
}
