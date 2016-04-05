package com.example.administrator.testa;

import android.content.Context;
import android.widget.EditText;

import java.util.List;

/**
 * Created by wp on 2015/12/15.
 */
public class OrderModifyListAdapter extends CommonAdapter<SellerOrderGoodBean>  {

    public OrderModifyListAdapter(Context context, List<SellerOrderGoodBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, SellerOrderGoodBean t, int position) {
        if (t!=null){
            if (t.getFruit_name()!=null){
                holder.setText(R.id.tv_seller_product_name,t.getFruit_name());
            }
            if (t.getKg_per_box()!=0){
                holder.setText(R.id.tv_seller_product_type,"约"+t.getKg_per_box()+"斤/箱");
            }
            if (t.getTotal_weight()!=0){
                holder.setText(R.id.tv_seller_product_shuangdingdan_amount,"约"+t.getTotal_weight()+"斤");
            }
            if (t.getUnit_price()!=0){
                holder.setText(R.id.tv_seller_product_price,"￥"+t.getUnit_price());
            }
            if (t.getGood_unit()!=null){
                holder.setText(R.id.tv_seller_product_unit,t.getGood_unit());
            }
            if (t.getTotal_box()!=0){
                holder.setText(R.id.tv_seller_product_num,"x"+t.getTotal_box() +"箱");
            }
            if (t.getTotal_weight()!=0){
                holder.setText(R.id.pre_weight,"x"+t.getTotal_weight()+"斤");
            }
            if (t.getTotal_price()!=0){
                holder.setText(R.id.pre_total_price,"x"+t.getTotal_price()+"箱");
            }
        }
        EditText total_amount=(EditText) holder.getView(R.id.new_weight);
        EditText total_money=(EditText) holder.getView(R.id.new_price);
    }

}
