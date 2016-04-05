package com.example.administrator.testa.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wp on 2016/1/4.
 */
public class CounterView extends View implements View.OnClickListener {
    private Paint mPaint;
    private int count=0;
    private Rect mRect;

    public CounterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRect = new Rect();
        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
        mPaint.setColor(Color.YELLOW);
        String text="当前数字是:"+count;
        mPaint.getTextBounds(text,0,text.length(),mRect);
        float textWidth=mRect.width();
        float textHeight=mRect.height();
        canvas.drawText(text,(getWidth()-textWidth)/2,(getHeight()-textHeight)/2,mPaint);
    }

    @Override
    public void onClick(View v) {
        count++;
        invalidate();
    }
}
