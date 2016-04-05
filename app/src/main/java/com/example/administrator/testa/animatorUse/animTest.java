package com.example.administrator.testa.animatorUse;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.administrator.testa.R;

/**
 * Created Jansen on 2016/1/20.
 */
public class animTest extends Activity{
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_layout);
        initView();
       // txt.animate().alpha(0f);
       // txt.animate().x(500).y(500).setDuration(5000).setInterpolator(new BounceInterpolator());
    }

    private void initView() {
        txt = (TextView) findViewById(R.id.txt);
    }
}
