package com.example.administrator.testa.animatorUse;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * Created Jansen on 2016/1/20.
 */
public class MyAniamView extends View {
    public static final float RADIUS = 50f;
    private Pointer currentPoint;
    private Paint mPaint;
    private String color;

    public String getColor() {
        return color;

    }

    public void setColor(String mColor) {
        color = mColor;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    public MyAniamView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (currentPoint == null) {
            currentPoint = new Pointer(RADIUS, RADIUS);
            drawCircle(canvas);
            // startAnimation();
            startVerticleAnimation2();
        } else {
            drawCircle(canvas);
        }
    }

    public void drawCircle(Canvas mCanvas) {
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        mCanvas.drawCircle(x, y, RADIUS, mPaint);
    }

    public void startAnimation() {
        Pointer mPointer1 = new Pointer(RADIUS, RADIUS);
        Pointer mPointer2 = new Pointer(getWidth() - RADIUS, getHeight() - RADIUS);
        ValueAnimator mAnimator = ValueAnimator.ofObject(new PointEvaluator(), mPointer1, mPointer2);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Pointer) animation.getAnimatedValue();
                invalidate();
            }
        });
        ObjectAnimator mAnimator1 = ObjectAnimator.ofObject(this, "color", new ColorEvaluator(), "#0000FF", "#FF0000");
        AnimatorSet mAnimatorSet = new AnimatorSet();
        mAnimatorSet.play(mAnimator).with(mAnimator1);
        mAnimatorSet.setDuration(5000);
        mAnimatorSet.start();
    }

    public void startVerticleAnimation2() {
        Pointer startPoint = new Pointer(getWidth() / 2, RADIUS);
        Pointer endPoint = new Pointer(getWidth() / 2, getHeight() - RADIUS);
        ValueAnimator mAnimator = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Pointer) animation.getAnimatedValue();
                invalidate();
            }
        });
        //可使用系统提供的多种插值器
        //自定义的插值器
        mAnimator.setInterpolator(new DeccelerateAceelerateInterpolator());
        //落地弹起动画
        mAnimator.setInterpolator(new BounceInterpolator());
        mAnimator.setDuration(4000);
        mAnimator.start();
    }
}
