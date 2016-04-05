package com.example.administrator.testa;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wp on 2016/1/11.
 */
public class TestActivity2 extends Activity {
    private ArrayList<String> mStrings = new ArrayList<>();
    private ListView mListView;
    public class MainActivity2 extends Activity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.slid_menu_layout);
            mListView = (ListView) findViewById(R.id.listview);
            for (int i = 0; i < 20; i++) {
                mStrings.add("测试数据" + i);
            }
            Adapter mAdapter = new Adapter();
            mListView.setAdapter(mAdapter);
        }

        class Adapter extends BaseAdapter {

            @Override
            public int getCount() {
                return mStrings.size();
            }

            @Override
            public Object getItem(int position) {
                return mStrings.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View mView = LayoutInflater.from(TestActivity2.this).inflate(R.layout.list_item, null);
                TextView mTextView = (TextView) mView.findViewById(R.id.txt);
                mTextView.setText(mStrings.get(position));
                return mView;
            }
        }
    }
}
