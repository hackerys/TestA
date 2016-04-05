package com.example.administrator.testa.wallet;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.testa.R;

import java.util.ArrayList;

/**
 * Created by wp on 2016/1/14.
 */
public class VerticleSlideActivity extends Activity {
    public static final int SANP_VELOCITY = 100;
    private LinearLayout head;
    private LinearLayout list;
    private VelocityTracker mVelocityTracker;
    private float xDown, xMove, xUp;
    private boolean isHeadVisible = true;
    private LinearLayout.LayoutParams listParams;
    private int topEdge = 0;
    private int bottomEdge = 600;
    private int screenWidth;
    private ListView mListView;
    private ArrayList<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verticle_slid_layout);
        inite();
    }

    public void inite() {
        head = (LinearLayout) findViewById(R.id.head);
        list = (LinearLayout) findViewById(R.id.list);
        mListView = (ListView) findViewById(R.id.list_view);
        WindowManager mManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        screenWidth = mManager.getDefaultDisplay().getWidth();
        listParams = (LinearLayout.LayoutParams) list.getLayoutParams();
        listParams.topMargin = bottomEdge;
        list.setLayoutParams(listParams);
        for (int i = 0; i < 20; i++) {
            data.add("测试数据" + i);
        }
        Adapter mAdapter = new Adapter();
        mListView.setAdapter(mAdapter);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        createVelocity(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = event.getRawX();
                int distance = (int) (xMove - xDown);
                if (isHeadVisible) {
                    listParams.topMargin = distance + bottomEdge;
                } else {
                    listParams.topMargin = distance;
                }
                if (listParams.topMargin > bottomEdge) {
                    listParams.topMargin = bottomEdge;
                } else if (listParams.topMargin < topEdge) {
                    listParams.topMargin = topEdge;
                }
                list.setLayoutParams(listParams);
                break;
            case MotionEvent.ACTION_UP:
                xUp = event.getRawX();
                if (wantShowHead()) {
                    if (scrollShowHead()) {
                        showHead();
                    } else {
                        hideHead();
                    }
                }
                if (wantHideHead()) {
                    if (scrollHideHead()) {
                        hideHead();
                    } else {
                        showHead();
                    }
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void createVelocity(MotionEvent mEvent) {
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
        return xUp - xDown > 0 && !isHeadVisible;
    }

    public boolean wantHideHead() {
        return xUp - xDown < 0 && isHeadVisible;
    }

    public boolean scrollShowHead() {
        return xUp - xDown > screenWidth / 3 || getVelocity() > SANP_VELOCITY;
    }

    public boolean scrollHideHead() {
        return xDown - xUp > screenWidth / 3 || getVelocity() > SANP_VELOCITY;
    }

    public void showHead() {
        new scrollTask().execute(30);
    }

    public void hideHead() {
        new scrollTask().execute(-30);
    }

    class scrollTask extends AsyncTask<Integer, Integer, Integer> {
        int topMargin = listParams.topMargin;

        @Override
        protected Integer doInBackground(Integer... params) {
            while (true) {
                topMargin = topMargin + params[0];
                publishProgress(topMargin);
                if (topMargin > bottomEdge) {
                    topMargin = bottomEdge;
                    break;
                }
                if (topMargin < topEdge) {
                    topMargin = topEdge;
                    break;
                }
                if (params[0] > 0) {
                    isHeadVisible = true;
                } else {
                    isHeadVisible = false;
                }
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
            listParams.topMargin = values[0];
            list.setLayoutParams(listParams);
        }

        @Override
        protected void onPostExecute(Integer result) {
            listParams.topMargin = result;
            list.setLayoutParams(listParams);
        }
    }

    class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View content = LayoutInflater.from(VerticleSlideActivity.this).inflate(R.layout.list_item, null);
            TextView mTextView = (TextView) content.findViewById(R.id.txt);
            mTextView.setText(data.get(position));
            return content;
        }
    }
}
