package com.example.administrator.testa.scroller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.testa.R;

/**
 * Created Jansen on 2016/1/21.
 */
public class TestScroller extends Activity {
    private LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_scroller_layout);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("button", "别点我");
            }
        });
    }
}
