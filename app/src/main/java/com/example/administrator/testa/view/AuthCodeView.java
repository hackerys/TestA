package com.example.administrator.testa.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by wp on 2016/1/12.
 */
public class AuthCodeView extends TextView implements View.OnClickListener{
    //发送验证码
    public static final int SEND_CODE=100;
    //重置
    public static final int RESET=101;
    //当前时间,初始值就是要定时的总时间
    private int current_time=20;
    //是否是第一次发送验证码(更新UI)
    private boolean first_send=true;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SEND_CODE:  //
                    setText("已发送("+current_time+")");
                    setClickable(false);
                    setBackgroundColor(Color.GRAY);
                    break;
                case RESET:   //重置
                    setStatus();
                    break;
            }
        }
    };
    public AuthCodeView(Context context) {
        this(context, null);
    }

    public AuthCodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AuthCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置初始状态
        setStatus();
        //设置监听器
        setOnClickListener(this);
    }
    public void setStatus(){
        if (first_send){
            setText("获取验证码");
            setBackgroundColor(Color.RED);
            setClickable(true);
            first_send=false;
        }else {
            setText("再次获取");
            setBackgroundColor(Color.RED);
            setClickable(true);
            current_time=20;
        }
    }
    @Override
    public void onClick(View v) {
        //刷新UI线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (current_time>0){
                    mHandler.sendEmptyMessage(SEND_CODE);
                    current_time--;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                    mHandler.sendEmptyMessage(RESET);
            }
        }).start();
    }
}
