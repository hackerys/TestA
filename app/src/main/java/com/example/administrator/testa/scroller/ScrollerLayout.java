package com.example.administrator.testa.scroller;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created Jansen on 2016/1/21.
 */
public class ScrollerLayout extends ViewGroup {
    //用于完成滚动操作的实例
    private Scroller mScroller;
    //拖动的最小移动像素
    private int mTouchSlop;
    //手指按下时的屏幕坐标
    private float xDown;
    //手指当前的移动的实时坐标
    private float xMove;
    //上次action_move的坐标
    private float xLastMove;
    //界面可滚动的左边界
    private int leftBorder;
    //界面可滚动的右边界
    private int rightBorder;

    public ScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //第一步，创建Scroller的实例
        mScroller = new Scroller(context);
        //获取TouchSlop
        ViewConfiguration mConfiguration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(mConfiguration);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            //我每一个子组件测量
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                childView.layout(i * childView.getMeasuredWidth(), 0, (i + 1) * childView.getMeasuredWidth(), childView.getMeasuredHeight());
            }
            leftBorder = getChildAt(0).getLeft();
            rightBorder = getChildAt(childCount - 1).getRight();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = ev.getRawX();
                xLastMove = xDown;
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = ev.getRawX();
                float diff = Math.abs(xMove - xDown);
                xLastMove = xMove;
                //当手指的移动大于可移动值时，认为应该进行滚动，拦截到子组件的事件
                if (diff > mTouchSlop) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    int scrollX = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE:
                xMove = event.getRawX();
                scrollX = (int) (xLastMove - xMove);
                //到达最左边时，不能再往左边移动了
                if (getScrollX() + scrollX < leftBorder) {
                    scrollTo(leftBorder, 0);
                    return true;
                } else if (getScrollX() + getWidth() + scrollX > rightBorder) {
                    //到达右边时，不能往右移动了
                    scrollTo(rightBorder-getWidth(), 0);
                    return true;
                }
                scrollBy(scrollX, 0);
                xLastMove = xMove;
                break;
            case MotionEvent.ACTION_UP:
                //当手指抬起时，判断应该滚到到那个控件,默认超过控件的0.5倍就会被滚出去,int类型会被强制转换成整数
                int targetIndex = 0;
                if (scrollX > 0) {
                    targetIndex = (getScrollX() + getWidth() * 3 / 4) / getWidth();
                } else {
                    targetIndex = (getScrollX() + getWidth() / 4) / getWidth();
                }
                int dx = targetIndex * getWidth() - getScrollX();
                mScroller.startScroll(getScrollX(), 0, dx, 0);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
