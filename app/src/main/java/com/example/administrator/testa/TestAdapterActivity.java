package com.example.administrator.testa;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TestAdapterActivity extends Activity implements OrderOperateListener{
    private ExpandableListView listView;
    private SellerOrderAdapter mSellerOrderAdapter;
    private ArrayList<SellerOderBean> mSellerOderBeans;
    private ArrayList<SellerOrderGoodBean> mOrderGoodBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSellerOderBeans=new ArrayList<SellerOderBean>();
        mOrderGoodBeans=new ArrayList<SellerOrderGoodBean>();
        SellerOrderGoodBean mGoodBean=new SellerOrderGoodBean(R.drawable.ic_launcher,"二定西瓜 山东",26,1.3,20,2000,"/斤");
        SellerOrderGoodBean mGoodBean1=new SellerOrderGoodBean(R.drawable.ic_launcher,"二定西瓜 山东",26,1.3,20,2000,"/斤");
        SellerOrderGoodBean mGoodBean2=new SellerOrderGoodBean(R.drawable.ic_launcher,"二定西瓜 山东",26,1.3,20,2000,"/斤");
        mOrderGoodBeans.add(mGoodBean);
        mOrderGoodBeans.add(mGoodBean1);
        mOrderGoodBeans.add(mGoodBean2);
        //待付款
        SellerOderBean mOderBean1=new SellerOderBean(20.0,300.00,"张三","7854625349876354",6,mOrderGoodBeans,1,true);
        SellerOderBean mOderBean2=new SellerOderBean(20.0,300.00,"张三","7854625349876354",6,mOrderGoodBeans,2,true);
        SellerOderBean mOderBean3=new SellerOderBean(20.0,300.00,"张三","7854625349876354",6,mOrderGoodBeans,1,false);
        SellerOderBean mOderBean4=new SellerOderBean(20.0,300.00,"张三11","7854625349876354",6,mOrderGoodBeans,2,false);
        mSellerOderBeans.add(mOderBean1);
        mSellerOderBeans.add(mOderBean2);
        mSellerOderBeans.add(mOderBean3);
        mSellerOderBeans.add(mOderBean4);
        //预订单
        SellerOderBean mOderBean5=new SellerOderBean(20.0,300.00,"张三","7854625349876354",1,mOrderGoodBeans,1,true);
        SellerOderBean mOderBean6=new SellerOderBean(20.0,300.00,"张三","7854625349876354",1,mOrderGoodBeans,2,true);
        mSellerOderBeans.add(mOderBean5);
        mSellerOderBeans.add(mOderBean6);
        //待发货
        SellerOderBean mOderBean7=new SellerOderBean(20.0,300.00,"张三","7854625349876354",2,mOrderGoodBeans,1,true);
        SellerOderBean mOderBean8=new SellerOderBean(20.0,300.00,"张三","7854625349876354",2,mOrderGoodBeans,2,false);
        mSellerOderBeans.add(mOderBean7);
        mSellerOderBeans.add(mOderBean8);
        //已发货
        SellerOderBean mOderBean9=new SellerOderBean(20.0,300.00,"张三","7854625349876354",3,mOrderGoodBeans,1,true);
        mSellerOderBeans.add(mOderBean9);
        //已完成
        SellerOderBean mOderBean10=new SellerOderBean(20.0,300.00,"张三","7854625349876354",4,mOrderGoodBeans,1,true);
        mSellerOderBeans.add(mOderBean10);
        //已取消
        SellerOderBean mOderBean11=new SellerOderBean(20.0,300.00,"张三","7854625349876354",5,mOrderGoodBeans,1,true);
        mSellerOderBeans.add(mOderBean11);
        listView=(ExpandableListView)findViewById(R.id.test_order_list);
        mSellerOrderAdapter=new SellerOrderAdapter(TestAdapterActivity.this,mSellerOderBeans,this);
        listView.setAdapter(mSellerOrderAdapter);
        int groupCount = listView.getCount();
        for (int i = 0; i < groupCount; i++) {
            listView.expandGroup(i);
        }
        //拦截组点击事件
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        mSellerOrderAdapter.notifyDataSetChanged();
    }

    @Override
    public void operateOrder(int gpos, int operate) {
        switch (operate){
            case 1://发货
                Toast.makeText(TestAdapterActivity.this,gpos+"发货",Toast.LENGTH_SHORT).show();
                break;
            case 2://取消
                Toast.makeText(TestAdapterActivity.this,gpos+"取消",Toast.LENGTH_SHORT).show();
                break;
            case 3://删除
                Toast.makeText(TestAdapterActivity.this,gpos+"删除",Toast.LENGTH_SHORT).show();
                break;
            case 4://修改
                Toast.makeText(TestAdapterActivity.this,gpos+"修改",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
