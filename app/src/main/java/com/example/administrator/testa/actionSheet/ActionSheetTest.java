package com.example.administrator.testa.actionSheet;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baoyz.actionsheet.ActionSheet;
import com.example.administrator.testa.R;

/**
 * Created Jansen on 2016/2/24.
 */
public class ActionSheetTest extends FragmentActivity implements ActionSheet.ActionSheetListener {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actionsheet_layout);
        initView();
    }

    @Override
        public void onDismiss(ActionSheet mActionSheet, boolean b) {

        Toast.makeText(getApplicationContext(), "dismissed isCancle = " + b, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOtherButtonClick(ActionSheet mActionSheet, int i) {
        Toast.makeText(getApplicationContext(), "click item index = " + i,
                Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionSheet.createBuilder(ActionSheetTest.this, getSupportFragmentManager())
                        .setCancelButtonTitle("取消")
                        .setOtherButtonTitles("获取图片的方式", "从相册中获取", "拍照")
                        .setCancelableOnTouchOutside(true)
                        .setListener(ActionSheetTest.this).show();
            }
        });
    }
}
