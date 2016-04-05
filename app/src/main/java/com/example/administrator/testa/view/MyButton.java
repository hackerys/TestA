package com.example.administrator.testa.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by wp on 2016/1/14.
 */
public class MyButton extends Button {
    private VelocityTracker mVelocityTracker;
    private float xDown, xMove, xUp;
    private int screenWidth;
    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager mManager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth=mManager.getDefaultDisplay().getWidth();
    }

    public void createVelocityTracker(MotionEvent mEvent) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(mEvent);
    }

    public int getVeloicty() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getXVelocity();
        return velocity;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        createVelocityTracker(event);
        boolean result=super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown=event.getRawX();
            case MotionEvent.ACTION_MOVE:
                xMove=event.getRawX();
            case MotionEvent.ACTION_UP:
                xUp=event.getRawX();
                if (Math.abs(xUp-xDown)>screenWidth/3){
                    result= false;
                }else {
                    result= true;
                }
            default:
                break;
        }
        return result;
    }
}
