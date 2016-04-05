package com.example.administrator.testa.FifthJack;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.testa.R;

/**
 * Created Jansen on 2016/1/20.
 */
public class FifthJacjActivity extends Activity {
    private TextView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fifth_layout);
        mView = (TextView) findViewById(R.id.refresh_state);
        //valueAnim();
        //rotateTextView();
        //translate();
        //scale1();
        //animSet();
        Animator mAnimatorSet = AnimatorInflater.loadAnimator(this, R.animator.anim_set);
        mAnimatorSet.setTarget(mView);
        mAnimatorSet.start();
    }

    private void animSet() {
        float currentTranlateX = mView.getTranslationX();
        ObjectAnimator trans = ObjectAnimator.ofFloat(mView, "translationX", -500f, currentTranlateX);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(mView, "rotation", 0f, 360f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mView, "alpha", 1f, 0f, 1f);
        AnimatorSet mAnimatorSet = new AnimatorSet();
        mAnimatorSet.play(rotate).with(alpha).after(trans);
        mAnimatorSet.setDuration(10000);
        mAnimatorSet.start();
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
    }

    private void scale1() {
        ObjectAnimator mAnimator = ObjectAnimator.ofFloat(mView, "scaleY", 1f, 3f, 1f);
        mAnimator.setDuration(3000);
        mAnimator.start();
    }

    private void translate() {
        float currentTranlateX = mView.getTranslationX();
        ObjectAnimator mAnimator = ObjectAnimator.ofFloat(mView, "translationX", currentTranlateX, -500f, currentTranlateX);
        mAnimator.setDuration(5000);
        mAnimator.start();
    }

    private void rotateTextView() {
        ObjectAnimator mAnimator = ObjectAnimator.ofFloat(mView, "rotation", 0f, 360f);
        mAnimator.setDuration(3000);
        mAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        mAnimator.start();
    }

    private void valueAnim() {
        ValueAnimator mAnimator = ValueAnimator.ofFloat(0f, 1f);
        mAnimator.setDuration(3000);
        mAnimator.start();
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                Log.e("onAnimationUpdate", currentValue + "");
            }
        });
    }
}
