package com.example.administrator.testa.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by wp on 2016/1/14.
 */
public class EventListView extends ListView {
    private float yDown,yUp;
    public EventListView(Context context) {
        super(context);
    }

    public EventListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EventListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean result=super.onTouchEvent(ev);
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            yDown=ev.getRawX();
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            yUp=ev.getRawX();
            if (Math.abs(yUp-yDown)>50){
                result=false;
            }
        }
        return false;
    }

}
