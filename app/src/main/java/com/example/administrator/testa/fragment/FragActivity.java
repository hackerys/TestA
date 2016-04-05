package com.example.administrator.testa.fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Display;

import com.example.administrator.testa.R;

/**
 * Created by wp on 2015/12/24.
 */
public class FragActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fra_container);
        Display mDisplay= getWindowManager().getDefaultDisplay();
        FragmentManager mManager=getFragmentManager();
        FragmentTransaction mTransaction=mManager.beginTransaction();
        if (mDisplay.getWidth()>mDisplay.getHeight()){
            mTransaction.replace(R.id.container,new FragmentTest());
            mTransaction.commit();
        }else {
            mTransaction.replace(R.id.container,new FragmentTest2());
            mTransaction.commit();
        }
    }
}
