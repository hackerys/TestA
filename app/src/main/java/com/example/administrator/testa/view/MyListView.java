package com.example.administrator.testa.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.administrator.testa.R;

/**
 * Created by wp on 2016/1/4.
 */
public class MyListView extends ListView implements View.OnTouchListener, GestureDetector.OnGestureListener {
    //默认按钮标志位false 不出现
    private boolean isDelShow = false;
    //删除监听
    private onDeleteListener mDeleteListener;
    //选中的item布局
    private ViewGroup item_layout;
    //删除按钮
    private View del_btn;
    //选中的位置
    private int selected_item;
    //手势监听
    private GestureDetector mDetector;

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //设置监听器
        setOnTouchListener(this);
        mDetector = new GestureDetector(getContext(), this);
    }

    public onDeleteListener getDeleteListener() {
        return mDeleteListener;
    }

    public void setDeleteListener(onDeleteListener mDeleteListener) {
        this.mDeleteListener = mDeleteListener;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        //按钮不显示时确定按下的是哪个item
        if (!isDelShow) {
            selected_item = pointToPosition((int) e.getX(), (int) e.getY());
        }
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (!isDelShow && Math.abs(velocityX) > Math.abs(velocityY)) {
            //加载删除按钮布局
            del_btn = LayoutInflater.from(getContext()).inflate(R.layout.del_button_layout, null);
            //设置按钮的监听
            del_btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    item_layout.removeView(del_btn);
                    del_btn = null;
                    isDelShow = false;
                    mDeleteListener.ondelete();
                }
            });
        }
        //依据按下的位置取得item布局
        item_layout = (ViewGroup) getChildAt(selected_item - getFirstVisiblePosition());
        //设置删除按钮在布局中的布局方式
        RelativeLayout.LayoutParams mParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mParams.addRule(RelativeLayout.CENTER_VERTICAL);
        //对象使用前一定要记得判空！！！
        if (item_layout != null && del_btn != null) {
            //添加删除按钮
            item_layout.addView(del_btn, mParams);
        }
        //标志位设为显示
        isDelShow = true;
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //按钮显示就隐藏，没有显示交给手势处理
        if (isDelShow) {
            if (item_layout != null && del_btn != null)
                item_layout.removeView(del_btn);
            del_btn = null;
            isDelShow = false;
            return false;
        }
        return mDetector.onTouchEvent(event);
    }
}
