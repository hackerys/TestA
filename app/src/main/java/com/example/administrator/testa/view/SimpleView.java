package com.example.administrator.testa.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wp on 2016/1/4.
 */
public class SimpleView extends ViewGroup{
    public SimpleView(Context context,AttributeSet mAttributeSet) {
        super(context,mAttributeSet);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount()>0){
            View mView=getChildAt(0);
            measureChild(mView,widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount()>0){
            View mView=getChildAt(0);
            mView.layout(0,0,mView.getMeasuredWidth(),mView.getMeasuredHeight());
        }
    }
}
