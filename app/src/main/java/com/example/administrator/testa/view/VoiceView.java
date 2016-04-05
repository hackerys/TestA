package com.example.administrator.testa.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.testa.R;

/**
 * Created by wp on 2016/1/11.
 */
public class VoiceView extends View implements View.OnTouchListener {
    //音量加颜色
    private int color_on;
    //音量减颜色
    private int color_off;
    //中间的图片
    private Bitmap center_image;
    //圆的宽度
    private float circle_width;
    //点的数量
    private int dot_count;
    //点的间隙
    private int split_size;
    //画笔
    private Paint mPaint;
    //图片的范围
    private Rect mRect;
    //圆的外切正方形
    private RectF mRectF;
    //当前音量
    private int current_count = 5;

    public VoiceView(Context context) {
        this(context, null);
    }

    public VoiceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VoiceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.VoiceView, defStyleAttr, 0);
        color_on = ta.getColor(R.styleable.VoiceView_color_on, Color.WHITE);
        color_off = ta.getColor(R.styleable.VoiceView_color_off, Color.BLACK);
        center_image = BitmapFactory.decodeResource(getResources(), ta.getResourceId(R.styleable.VoiceView_center_image, R.drawable.ic_launcher));
        circle_width = ta.getDimension(R.styleable.VoiceView_circle_width, 5);
        dot_count = ta.getInt(R.styleable.VoiceView_dot_count, 10);
        split_size = ta.getInt(R.styleable.VoiceView_split_size, 10);
        ta.recycle();
        //初始化画笔
        mPaint = new Paint();
        //图片的区域
        mRect = new Rect();
        //圆的外切正方形
        mRectF = new RectF();
        //设置touchlistetner
        setOnTouchListener(this);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //初始化画笔
        mPaint.setAntiAlias(true); //抗锯齿
        mPaint.setStrokeWidth(circle_width); //设置圆环的宽度
        mPaint.setStyle(Paint.Style.STROKE); //画出来时是空心的圆
        mPaint.setStrokeCap(Paint.Cap.ROUND); //断点是圆形的
        //算的圆基准的半径和圆心
        int center = getWidth() / 2;
        float radius = center - circle_width / 2;
        drawDot(canvas, center, radius);
        //内圆半径
        double realRadius = radius - circle_width / 2;
        //内切正方形，圆心到边的垂直距离
        double s = realRadius / Math.sqrt(2);
        mRect.left = center - center_image.getWidth() / 2;
        mRect.top = center - center_image.getHeight() / 2;
        mRect.right = center + center_image.getWidth();
        mRect.bottom = center + center_image.getHeight() / 2;
        canvas.drawBitmap(center_image, null, mRect, mPaint);
    }

    private void drawDot(Canvas canvas, int mCenter, float mRadius) {
        //依据间隔大小和数量均分圆
        float itemSize = (360 * 1.0f - split_size * dot_count) / dot_count;
        mRectF.set(mCenter - mRadius, mCenter - mRadius, mCenter + mRadius, mCenter + mRadius);
        mPaint.setColor(color_off);
        for (int i = 0; i < dot_count; i++) {
            canvas.drawArc(mRectF, i * (itemSize + split_size), itemSize, false, mPaint);
        }
        mPaint.setColor(color_on);
        for (int i = 0; i < current_count; i++) {
            canvas.drawArc(mRectF, i * (itemSize + split_size), itemSize, false, mPaint);
        }

    }

    public void up() {
        if (current_count < dot_count) {
            current_count++;
        }
        Log.e("---","up");
        postInvalidate();
    }

    public void down() {
        if (current_count > 0) {
            current_count--;
        }
        Log.e("---","down");
        postInvalidate();
    }
    int statY,endY;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("---","ACTION_DOWN");
                statY =(int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                endY = (int)event.getY();
                if (statY > endY) {
                    up();
                } else {
                    down();
                }
                Log.e("---","ACTION_UP");
                break;
            default:
                break;
        }
        return true;
    }

}
