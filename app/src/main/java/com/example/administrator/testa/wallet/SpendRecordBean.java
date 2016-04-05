package com.example.administrator.testa.wallet;

import java.util.ArrayList;

/**
 * Created by wp on 2015/12/16.
 */
public class SpendRecordBean {
    private int month;
    private ArrayList<RecordBean> mRecordBeans;

    public int getMonth() {
        return month;
    }

    public void setMonth(int mMonth) {
        month = mMonth;
    }

    public ArrayList<RecordBean> getRecordBeans() {
        return mRecordBeans;
    }

    public void setRecordBeans(ArrayList<RecordBean> mRecordBeans) {
        this.mRecordBeans = mRecordBeans;
    }
}
