package com.example.administrator.testa.wallet;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.example.administrator.testa.R;

/**
 * Created by wp on 2015/12/16.
 */
public class WalletActivity extends Activity{
    private ExpandableListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_layout);
    }

}
