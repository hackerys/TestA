package com.example.administrator.testa.wallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wp on 2015/12/16.
 */
public class WalletListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private ArrayList<SpendRecordBean> mSpendRecordBeans;
    private LayoutInflater mInflater;

    public WalletListAdapter(Context mContext, ArrayList<SpendRecordBean> mSpendRecordBeans) {
        this.mContext = mContext;
        this.mSpendRecordBeans = mSpendRecordBeans;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getGroupCount() {
        return mSpendRecordBeans == null ? 0 : mSpendRecordBeans.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mSpendRecordBeans.get(groupPosition).getRecordBeans() == null ? 0 : mSpendRecordBeans.get(groupPosition).getRecordBeans().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mSpendRecordBeans.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mSpendRecordBeans.get(groupPosition).getRecordBeans().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        groupViewHolder mGroupViewHolder;
        if (convertView==null){
        }
        return null;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    static class groupViewHolder {
        //月份
        TextView month;
        //箭头
        ImageView arrow;
    }

    static class childViewHolder {
        //名字
        TextView name;
        //类型
        TextView type;
        //时间
        TextView time;
        //金额
        TextView amount;
    }
}
