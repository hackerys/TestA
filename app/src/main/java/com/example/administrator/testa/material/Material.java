package com.example.administrator.testa.material;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;

import com.example.administrator.testa.R;

/**
 * Created Jansen on 2016/1/29.
 */
public class Material extends Activity implements View.OnClickListener {

    Button show;
    Button hide;
    Button viewshow;
    Animator mAnimatorShow, mAnimatorHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_layout);
        initView();
        int cx = viewshow.getWidth() / 2;
        int cy = viewshow.getHeight() / 2;
        int finalRadius = Math.max(viewshow.getWidth(), viewshow.getHeight());
        mAnimatorShow = ViewAnimationUtils.createCircularReveal(viewshow, cx, cy, 0, finalRadius);
        mAnimatorHide = ViewAnimationUtils.createCircularReveal(viewshow, cx, cy, finalRadius, 0);
        mAnimatorHide.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                viewshow.setVisibility(View.INVISIBLE);
            }
        });
    }


    private void initView() {
        show = (Button) findViewById(R.id.show);
        hide = (Button) findViewById(R.id.hide);
        viewshow = (Button) findViewById(R.id.view_show);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show:
                viewshow.setVisibility(View.VISIBLE);
                mAnimatorShow.start();
                break;
            case R.id.hide:
                mAnimatorHide.start();
                break;
            case R.id.view_show:
                break;
            default:
                break;
        }
    }
}
