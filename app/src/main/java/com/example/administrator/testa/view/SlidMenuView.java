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
 * Created by wp on 2016/1/16.
 */
public class SlidMenuView extends LinearLayout {
    //滚动显示、隐藏菜单时手指需要达到的速度
    public static final int SNAP_VELOCITY = 200;
    //屏幕的宽度
    private int screenWith;
    //menu最多可以滑动到的左边缘，值由menu的宽度来决定,leftmargin的最小值，到达之后不能再减少
    private int leftegde;
    //menu最多可以到达的右边缘，值恒为0，以menu左边框的margin为基准
    private int rightedge = 0;
    //menu完全显示时，留给content的宽度
    private int menuPadding = 80;
    //内容布局
    private View content;
    //菜单布局
    private View menu;
    //关键**  menu的布局参数，通过此参数动态改变leftmargin的值，
    private LinearLayout.LayoutParams menuParams;
    private LinearLayout.LayoutParams contentParams;
    //手指按下的横坐标
    private float xDown;
    //手指移动的横坐标
    private float xMove;
    //手指抬起的横坐标
    private float xUp;
    //标示menu的显示状态，只有完全显示或完全隐藏的时候才会改变值
    private boolean isMenuVisible;
    //计算手指的滑动速度
    private VelocityTracker mVelocityTracker;

    public SlidMenuView(Context context) {
        this(context, null);
    }

    public SlidMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager mManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWith = mManager.getDefaultDisplay().getWidth();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        content = getChildAt(1);
        menu = getChildAt(0);
        menuParams = (LinearLayout.LayoutParams) menu.getLayoutParams();
        contentParams = (LinearLayout.LayoutParams) content.getLayoutParams();
        //将menu的宽度设置为屏幕宽度-padding
        menuParams.width = screenWith - menuPadding;
        //左边缘的阀值为menu宽度的负数，是一个固定的值
        leftegde = -menuParams.width;
        //leftmargin是一个动态改变的值，初始值为-leftedge,意味隐藏menu
        menuParams.leftMargin = leftegde;
        //将content的宽度设为屏幕的宽度
        content.getLayoutParams().width = screenWith;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        createVelovityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //手指按下，记录横坐标
                xDown = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                //手机移动时，对比按下时的横坐标，计算出移动的距离，来调整leftmargin的值，从而显示和隐藏menu
                xMove = event.getRawX();
                int distanceX = (int) (xMove - xDown);
                if (isMenuVisible) {  //leftmargin从小到大 最小0
                    menuParams.leftMargin = distanceX;
                } else {  //leftmargin从大到小 最大leftedge
                    menuParams.leftMargin = leftegde + distanceX;
                }
                //边界情况的处理
                if (menuParams.leftMargin < leftegde) {
                    menuParams.leftMargin = leftegde;
                } else if (menuParams.leftMargin > rightedge) {
                    menuParams.leftMargin = rightedge;
                }
                menu.setLayoutParams(menuParams);
                break;
            case MotionEvent.ACTION_UP:
                xUp = event.getRawX();
                if (wantToShowMenu()) {
                    if (shoulScroolMenu()) {  //满足条件滚动显示menu
                        scrollToMenu();
                    } else { //不满足条件将手指拖动出来的menu隐藏回去
                        scrollToContent();
                    }
                }
                if (wantToShowContent()) {
                    if (shouldScroolContent()) {
                        scrollToContent();
                    } else {
                        scrollToMenu();
                    }
                }
                break;
            default:
                break;
        }
        return true;
    }

    //判断当前的手势是不是想显示content,如果手指的移动距离是负数，且menu是可见的，则认为当前手势想显示content
    private boolean wantToShowContent() {
        return xUp - xDown < 0 && isMenuVisible;
    }

    //判断当前的手势是不是想显示menu，如果手机的移动的距离是正数，且menu是不可见的,则认为挡墙的手势想显示menu
    private boolean wantToShowMenu() {
        return xUp - xDown > 0 && !isMenuVisible;
    }

    //判断是否滚动将menu展示出来,如果手指的移动距离大于屏幕的1/2,或者手指的移动速度大于snap_velocity
    private boolean shoulScroolMenu() {
        return xUp - xDown > screenWith / 2 || getScrollVelocity() > SNAP_VELOCITY;
    }

    private boolean shouldScroolContent() {
        return xDown - xUp + menuPadding > screenWith / 2 || getScrollVelocity() > SNAP_VELOCITY;
    }

    //计算当前x方向手指滑动的速度
    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getXVelocity();
        return Math.abs(velocity);
    }

    //回收velocitytracker
    private void reclcleVelocity() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }

    //创建速度捕捉对象，并将触摸content的touch事件传入tracker中
    public void createVelovityTracker(MotionEvent mEvent) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(mEvent);
    }

    //将menu滚动显示
    private void scrollToMenu() {
        new ScroolTask().execute(30);
    }

    //将menu滚动隐藏
    private void scrollToContent() {
        new ScroolTask().execute(-30);
    }


    class ScroolTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... speed) {
            int leftMargin = menuParams.leftMargin;
            while (true) {
                leftMargin = leftMargin + speed[0];
                if (leftMargin < leftegde) {
                    leftMargin = leftegde;
                    break;
                }
                if (leftMargin > rightedge) {
                    leftMargin = rightedge;
                    break;
                }
                publishProgress(leftMargin);
                //为了使滚动效果产生，每次循环睡眠20毫秒，这样才能肉眼看到滚动效果
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (speed[0] > 0) {
                    isMenuVisible = true;
                } else {
                    isMenuVisible = false;
                }
            }
            return leftMargin;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            menuParams.leftMargin = values[0];
            menu.setLayoutParams(menuParams);
        }

        @Override
        protected void onPostExecute(Integer result) {
            menuParams.leftMargin = result;
            menu.setLayoutParams(menuParams);
        }
    }
}
