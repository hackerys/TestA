package com.example.administrator.testa.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by wp on 2016/1/11.
 */
public class AutoIncrementView extends TextView {
    //开始数字
    private int start_number = 0;
    //结束数字
    private int end_number = 10000;
    //持续时间
    private int duration = 3000;
    //发送消息标识码
    public static final int END_ADD = 100;
    //当前数字
    private int current_number = 1;
    //每一次数字增加的大小
    private int number_step;
    //每一次刷新的间隔时间   (通常保持不变)
    private int time_sleep = 50;
    //以前是1毫秒刷新一次，要除以时间步数
    private int real_time;
    //接收消息的handler
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //必须实现父类方法接收消息
            super.handleMessage(msg);
            switch (msg.what) {
                case END_ADD:
                    //增加的过程
                    if (current_number < end_number) {
                        setText(current_number + "");
                    } else {
                        //结束时当前数字可能会超出目标数字，重新设置
                        setText(end_number + "");
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public AutoIncrementView(Context context) {
        this(context, null);
    }

    public AutoIncrementView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoIncrementView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        number_step = end_number / (duration / time_sleep);
        //UI刷新线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //数字自增循环体
                while (current_number < end_number) {
                    //当前的数字增加
                    current_number += number_step;
                    try {
                        Thread.sleep(time_sleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mHandler.sendEmptyMessage(END_ADD);
                }
            }
        }).start();
    }


}
