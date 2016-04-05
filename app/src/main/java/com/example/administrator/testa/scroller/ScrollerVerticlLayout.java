package com.example.administrator.testa.scroller;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created Jansen on 2016/1/21.
 */
public class ScrollerVerticlLayout extends ViewGroup {
    //用于完成滚动操作的实例
    private Scroller mScroller;
    //拖动的最小移动像素
    private int mTouchSlop;
    //手指按下时的屏幕坐标
    private float yDown;
    //手指当前的移动的实时坐标
    private float yMove;
    //上次action_move的坐标
    private float yLastMove;
    //界面可滚动的左边界
    private int topBorder;
    //界面可滚动的右边界
    private int bottomBorder;
    //添加一个scrollview
    private BottomScrollView mScrollView1;
    //ScrollView的高度
    private int scrollViewHeight;
    //当前的页面索引，默认是0
    private int targetIndex = 0;

    public ScrollerVerticlLayout(Context context, AttributeSet attrs) {
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
                childView.layout(0, childView.getMeasuredHeight() * i, childView.getMeasuredWidth(), (i + 1) * childView.getMeasuredHeight());
            }
            topBorder = getChildAt(0).getTop();
            bottomBorder = getChildAt(childCount - 1).getBottom();
        }
        mScrollView1 = (BottomScrollView) getChildAt(0);
        //  scrollViewHeight = mScrollView1.getMeasuredHeight();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                yDown = ev.getRawY();
                yLastMove = yDown;
                break;
            case MotionEvent.ACTION_MOVE:
                yMove = ev.getRawY();
                float diff = Math.abs(yMove - yDown);
                float diff2 = (yMove - yDown);
                yLastMove = yMove;
                Log.e("ACTION_MOVE", String.valueOf(mScrollView1.isBottom()));
                if (targetIndex == 0 && diff2 < 0 && mScrollView1.isBottom()) {
                    return true;
                }
                if (targetIndex == 1 && diff2 > 0) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    int scrollY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE:
                yMove = event.getRawY();
                scrollY = (int) (yLastMove - yMove);
                //到达最左边时，不能再往左边移动了
                if (getScrollY() + scrollY < topBorder) {
                    scrollTo(0, topBorder);
                    return true;
                } else if (getScrollY() + getHeight() + scrollY > bottomBorder) {
                    //到达右边时，不能往右移动了
                    scrollTo(0, bottomBorder - getHeight());
                    return true;
                }
                scrollBy(0, scrollY);
                yLastMove = yMove;
                break;
            case MotionEvent.ACTION_UP:
                //当手指抬起时，判断应该滚到到那个控件,默认超过控件的0.5倍就会被滚出去,int类型会被强制转换成整数
                if (scrollY > 0) {
                    targetIndex = (getScrollY() + getHeight() * 3 / 4) / getHeight();
                } else {
                    targetIndex = (getScrollY() + getHeight() / 4) / getHeight();
                }
                if (targetIndex == 1) {
                    mScrollView1.setIsBottom(false);
                }
                int dx = targetIndex * getHeight() - getScrollY();
                mScroller.startScroll(0, getScrollY(), 0, dx);
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
