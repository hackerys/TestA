package com.example.administrator.testa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wp on 2015/12/14.
 */
public class SellerOrderAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private ArrayList<SellerOderBean> mSellerOderBeans;
    private LayoutInflater mInflater;
    private OrderOperateListener mOrderOperateListener;

    public SellerOrderAdapter(Context mContext, ArrayList<SellerOderBean> mSellerOderBeans, OrderOperateListener mOrderOperateListener) {
        this.mContext = mContext;
        this.mSellerOderBeans = mSellerOderBeans;
        mInflater = LayoutInflater.from(mContext);
        this.mOrderOperateListener = mOrderOperateListener;
    }

    @Override
    public int getGroupCount() {
        return mSellerOderBeans.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mSellerOderBeans.get(i).getOrderGoodBeans().size();
    }

    @Override
    public Object getGroup(int i) {
        return mSellerOderBeans.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mSellerOderBeans.get(i).getOrderGoodBeans().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        View header = mInflater.inflate(R.layout.order_header_layout, null);
        TextView name = (TextView) header.findViewById(R.id.order_name);
        TextView bill_number = (TextView) header.findViewById(R.id.order_number);
        TextView order_status = (TextView) header.findViewById(R.id.order_status);
        name.setText(mSellerOderBeans.get(i).getOrder_custom());
        bill_number.setText(mSellerOderBeans.get(i).getBill_number());
        order_status.setText(getOrderStatus(mSellerOderBeans.get(i).getOrder_status()));
        return header;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final int gpos = i;
        final int cpos = i1;
        View order_item = mInflater.inflate(R.layout.order_item, null);
        SellerOderBean mOderBean = mSellerOderBeans.get(i);
        SellerOrderGoodBean mGoodBean = mOderBean.getOrderGoodBeans().get(i1);
        setGoodValue(order_item, mGoodBean);
        if (b) {
            //子项最后一项
            setOrderFooter(mOderBean.getOrder_status(), order_item, mOderBean);
        }
        setListener(gpos, order_item);
        return order_item;
    }

    private void setListener(final int mGpos, View mOrder_item) {
        mOrder_item.findViewById(R.id.order_modify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderOperateListener.operateOrder(mGpos, 4);
            }
        });
        mOrder_item.findViewById(R.id.order_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderOperateListener.operateOrder(mGpos, 3);
            }
        });
        mOrder_item.findViewById(R.id.order_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderOperateListener.operateOrder(mGpos, 2);
            }
        });
        mOrder_item.findViewById(R.id.delever_goods).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderOperateListener.operateOrder(mGpos, 1);
            }
        });
    }

    private void setGoodValue(View mOrder_item, SellerOrderGoodBean mGoodBean) {
        ImageView tv_seller_product_image = (ImageView) mOrder_item.findViewById(R.id.tv_seller_product_image);
        TextView tv_seller_product_name = (TextView) mOrder_item.findViewById(R.id.tv_seller_product_name);
        TextView tv_seller_product_type = (TextView) mOrder_item.findViewById(R.id.tv_seller_product_type);
        TextView tv_seller_product_shuangdingdan_amount = (TextView) mOrder_item.findViewById(R.id.tv_seller_product_shuangdingdan_amount);
        TextView tv_seller_product_price = (TextView) mOrder_item.findViewById(R.id.tv_seller_product_price);
        TextView tv_seller_product_unit = (TextView) mOrder_item.findViewById(R.id.tv_seller_product_unit);
        TextView tv_seller_product_num = (TextView) mOrder_item.findViewById(R.id.tv_seller_product_num);
        tv_seller_product_image.setImageResource(mGoodBean.getFruit_img());
        tv_seller_product_name.setText(mGoodBean.getFruit_name());
        tv_seller_product_type.setText(mGoodBean.getKg_per_box() + "个/每箱");
        tv_seller_product_shuangdingdan_amount.setText("约" + mGoodBean.getTotal_weight() + "斤");
        tv_seller_product_price.setText(mGoodBean.getUnit_price() + "");
        tv_seller_product_unit.setText(mGoodBean.getGood_unit());
        tv_seller_product_num.setText("x" + mGoodBean.getTotal_box() + "/箱");
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    public String getOrderStatus(int status) {
        String str = "";
        switch (status) {
            case 1:
                str = "已付定金";
                break;
            case 2:
                str = "待发货";
                break;
            case 3:
                str = "已发货";
                break;
            case 4:
                str = "已完成";
                break;
            case 5:
                str = "已取消";
                break;
            case 6:
                str = "待付款";
                break;
        }
        return str;
    }

    public void setOrderFooter(int status, View mView, SellerOderBean mOderBean) {
        TextView goods_amount = (TextView) mView.findViewById(R.id.goods_amount);
        TextView account_receivable = (TextView) mView.findViewById(R.id.account_receivable);
        TextView account_unreceivable = (TextView) mView.findViewById(R.id.account_unreceivable);
        goods_amount.setText("共" + mOderBean.getOrderGoodBeans().size() + "件商品");
        account_receivable.setText("实收款:￥" + mOderBean.getShould_pay());
        account_unreceivable.setText("未收款:￥" + mOderBean.getUnpay());

        setViewVisibility(status, mView, mOderBean, account_unreceivable);
    }

    private void setViewVisibility(int status, View mView, SellerOderBean mOderBean, TextView mAccount_unreceivable) {
        switch (status) {
            case 1:
                mView.findViewById(R.id.summation).setVisibility(View.VISIBLE);
                mView.findViewById(R.id.order_operate).setVisibility(View.VISIBLE);
                mAccount_unreceivable.setVisibility(View.VISIBLE);
                break;
            case 2:
                mView.findViewById(R.id.summation).setVisibility(View.VISIBLE);
                mView.findViewById(R.id.order_operate).setVisibility(View.VISIBLE);
                break;
            case 3:
                mView.findViewById(R.id.summation).setVisibility(View.VISIBLE);
                break;
            case 4:
                mView.findViewById(R.id.summation).setVisibility(View.VISIBLE);
                mView.findViewById(R.id.order_delete).setVisibility(View.VISIBLE);
                break;
            case 5:
                mView.findViewById(R.id.summation).setVisibility(View.VISIBLE);
                mView.findViewById(R.id.order_delete).setVisibility(View.VISIBLE);
                break;
            case 6:
                mView.findViewById(R.id.summation).setVisibility(View.VISIBLE);
                mView.findViewById(R.id.order_operate).setVisibility(View.VISIBLE);
                if (mOderBean.is_order()) {
                    mView.findViewById(R.id.delever_goods).setVisibility(View.GONE);
                    mView.findViewById(R.id.order_cancel).setVisibility(View.VISIBLE);
                } else if (mOderBean.getPayway() == 2) {
                    mView.findViewById(R.id.order_cancel).setVisibility(View.VISIBLE);
                } else {
                    mView.findViewById(R.id.delever_goods).setVisibility(View.GONE);
                    mView.findViewById(R.id.order_cancel).setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
        mView.findViewById(R.id.footer_line1).setVisibility(View.VISIBLE);
        mView.findViewById(R.id.footer_line2).setVisibility(View.VISIBLE);
    }
}
