package com.example.administrator.testa;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.testa.view.NoScrollView;

import java.util.ArrayList;

/**
 * Created by wp on 2015/12/15.
 */
public class OrderModifyActivity extends Activity implements View.OnClickListener,AdapterEvent{
    private NoScrollView mListView;
    private OrderModifyListAdapter mModifyListAdapter;
    private ArrayList<SellerOrderGoodBean> mOrderGoodBeans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_modify_layout);
        mListView=(NoScrollView)findViewById(R.id.good_list);
        View footer2= LayoutInflater.from(this).inflate(R.layout.order_modify_footer_info,null);
        findViewById(R.id.confirm_modify).setOnClickListener(this);
        mListView.addFooterView(footer2);
        mOrderGoodBeans=new ArrayList<SellerOrderGoodBean>();
        SellerOrderGoodBean mGoodBean=new SellerOrderGoodBean(R.drawable.ic_launcher,"二定西瓜 山东",26,1.3,20,2000,"/斤");
        SellerOrderGoodBean mGoodBean1=new SellerOrderGoodBean(R.drawable.ic_launcher,"二定西瓜 山东",26,1.3,20,2000,"/斤");
        SellerOrderGoodBean mGoodBean2=new SellerOrderGoodBean(R.drawable.ic_launcher,"二定西瓜 山东",26,1.3,20,2000,"/斤");
        mOrderGoodBeans.add(mGoodBean);
        mOrderGoodBeans.add(mGoodBean1);
        mOrderGoodBeans.add(mGoodBean2);
        mModifyListAdapter=new OrderModifyListAdapter(OrderModifyActivity.this,mOrderGoodBeans,R.layout.order_modify_item);
        mListView.setAdapter(mModifyListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.confirm_modify:
                Toast.makeText(OrderModifyActivity.this,"确认修改",Toast.LENGTH_SHORT).show();
            break;
        }
    }

    @Override
    public void setViewEvent(ViewHolder holder) {
        EditText new_total_amount=(EditText) holder.getView(R.id.new_weight);
        EditText new_total_money=(EditText) holder.getView(R.id.new_price);
        new_total_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
