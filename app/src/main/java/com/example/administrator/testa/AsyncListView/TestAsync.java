package com.example.administrator.testa.AsyncListView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.administrator.testa.R;

/**
 * Created Jansen on 2016/1/22.
 */
public class TestAsync extends Activity {
    ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.async_list_layout);
        initView();
    }

    private void initView() {
        mList = (ListView) findViewById(R.id.mList);
        ImageAdapter mImageAdapter = new ImageAdapter(this, R.layout.async_list_item, Images.imageUrls);
        mList.setAdapter(mImageAdapter);
    }
}
