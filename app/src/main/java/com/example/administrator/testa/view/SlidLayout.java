package com.example.administrator.testa.view;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by wp on 2016/1/15.
 */
public class SlidLayout extends LinearLayout implements View.OnScrollChangeListener{
    private float xDown, xUp, xMove;
    private View head, content;
    private LayoutParams headParams;
    private LayoutParams contentParams;
    private VelocityTracker mVelocityTracker;
    private int bottomEdge = 0;
    private int topEdge = -700;
    private boolean isHeadShow = true;
    private int screenWidth;

    public SlidLayout(Context context) {
        this(context, null);
    }

    public SlidLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager mManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = mManager.getDefaultDisplay().getWidth();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        head = getChildAt(0);
        content = getChildAt(1);
        content.setOnScrollChangeListener(this);
        head.setOnScrollChangeListener(this);
        headParams = (LayoutParams) head.getLayoutParams();
        headParams.height = -topEdge;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        createVlocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = event.getRawX();
                int distance = (int) (xMove - xDown);
                //topmaigin一直是一个负值
                if (isHeadShow) {
                    headParams.topMargin = distance;
                } else {
                    headParams.topMargin = distance + topEdge;
                }
                if (headParams.topMargin < topEdge) {
                    headParams.topMargin = topEdge;
                } else if (headParams.topMargin > 0) {
                    headParams.topMargin = bottomEdge;
                }
                head.setLayoutParams(headParams);
                break;
            case MotionEvent.ACTION_UP:
                xUp = event.getRawX();
                if (wantShowHead()) {
                    if (wantscrollShowHead()) {
                        scrollShowHead();
                    } else {
                        scrollHideHead();
                    }
                }
                if (wantHideHead()) {
                    if (wantscrollHideHead()) {
                        scrollHideHead();
                    } else {
                        scrollShowHead();
                    }
                }
                mVelocityTracker.recycle();
                mVelocityTracker = null;
                break;
            default:
                break;

        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result = super.onInterceptTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return result;
    }

    public void createVlocityTracker(MotionEvent mEvent) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(mEvent);
    }

    public int getVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getXVelocity();
        return Math.abs(velocity);
    }

    public boolean wantShowHead() {
        return xUp - xDown > 0 && !isHeadShow;
    }

    public boolean wantHideHead() {
        return xUp - xDown < 0 && isHeadShow;
    }

    public boolean wantscrollShowHead() {
        return xUp - xDown - topEdge > screenWidth / 2 || getVelocity() > 200;
    }

    public boolean wantscrollHideHead() {
        return xDown - xUp > screenWidth / 2 || getVelocity() > 200;
    }

    public void scrollShowHead() {
        new scrollTask().execute(50);
    }

    public void scrollHideHead() {
        new scrollTask().execute(-50);
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

    }


    class scrollTask extends AsyncTask<Integer, Integer, Integer> {
        int topMargin = headParams.topMargin;

        @Override
        protected Integer doInBackground(Integer... params) {
            while (true) {
                topMargin += params[0];
                if (topMargin > 0) {
                    topMargin = bottomEdge;
                    break;
                }
                if (topMargin < topEdge) {
                    topMargin = topEdge;
                    break;
                }
                if (params[0] > 0) {
                    isHeadShow = true;
                } else {
                    isHeadShow = false;
                }
                publishProgress(topMargin);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return topMargin;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            headParams.topMargin = values[0];
            head.setLayoutParams(headParams);
        }

        @Override
        protected void onPostExecute(Integer result) {
            headParams.topMargin = result;
            head.setLayoutParams(headParams);
        }
    }
}
