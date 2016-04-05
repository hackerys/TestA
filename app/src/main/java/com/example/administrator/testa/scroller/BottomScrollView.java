package com.example.administrator.testa.scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created Jansen on 2016/1/21.
 */
public class BottomScrollView extends ScrollView {
    private boolean isBottom = false;

    public boolean isBottom() {
        return isBottom;
    }

    public void setIsBottom(boolean mIsBottom) {
        isBottom = mIsBottom;
    }

    private ScrollBottomListener scrollBottomListener;

    public BottomScrollView(Context context) {
        super(context);
    }

    public BottomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (t + getHeight() >= computeVerticalScrollRange()) {
            //ScrollView滑动到底部了
            isBottom = true;
        } else {
            isBottom = false;
        }
    }

    public void setScrollBottomListener(ScrollBottomListener scrollBottomListener) {
        this.scrollBottomListener = scrollBottomListener;
    }

    public interface ScrollBottomListener {
        public void scrollBottom();
    }
}
