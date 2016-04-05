package com.example.administrator.testa.animatorUse;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created Jansen on 2016/1/20.
 */
public class MagicTextView extends TextView {
    private float currentNumber = 0;
    private float endNumber = 1000f;
    private DecimalFormat mFormat=new DecimalFormat("###");
    public MagicTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        startAnim();
    }

    public void startAnim() {
        ValueAnimator mAnimator = ValueAnimator.ofFloat(0f, endNumber);
        mAnimator.setDuration(3000);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentNumber = (float) animation.getAnimatedValue();
                setText(mFormat.format(currentNumber) + "");
            }
        });
        mAnimator.start();
    }
}
