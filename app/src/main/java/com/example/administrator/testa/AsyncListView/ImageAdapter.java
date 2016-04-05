package com.example.administrator.testa.AsyncListView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.testa.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created Jansen on 2016/1/22.
 */
public class ImageAdapter extends BaseAdapter {
    //图片缓存技术的核心类，用于存储所有下载好的图片，在程序的没存到达设定值时，会将最近最少使用的图片删除掉
    private LruCache<String, BitmapDrawable> mMemoryCach;
    private Context mContext;
    private int resouceId;
    private String[] datas;
    private ListView mListView;

    public ImageAdapter(Context mContext, int mResouceId, String[] mDatas) {
        this.mContext = mContext;
        resouceId = mResouceId;
        datas = mDatas;
        //获取应用程序的最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        mMemoryCach = new LruCache<String, BitmapDrawable>(cacheSize) {
            @Override
            protected int sizeOf(String key, BitmapDrawable value) {
                return value.getBitmap().getByteCount();
            }
        };

    }

    @Override
    public int getCount() {
        return datas.length;
    }

    @Override
    public Object getItem(int position) {
        return datas[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (parent != null) {
            mListView = (ListView) parent;
        }
        String img_url = datas[position];
        View mView;
        if (convertView == null) {
            mView = LayoutInflater.from(mContext).inflate(resouceId, null);
        } else {
            mView = convertView;
        }
        ImageView mImageView = (ImageView) mView.findViewById(R.id.images);
        mImageView.setImageResource(R.drawable.ic_launcher);
        mImageView.setTag(img_url);
        BitmapDrawable mDrawable = getBitmapFromMemoryCache(img_url);
        if (mDrawable != null) {
            mImageView.setImageDrawable(mDrawable);
        } else {
            new BitmapAsyncTask().execute(img_url);
        }
        return mView;
    }

    /**
     * 将一张图片存储到lruCache中
     *
     * @param key             图片的url地址
     * @param mBitmapDrawable 从网络下载的BitmapDrawable
     */
    public void addBitmapToMemoryCache(String key, BitmapDrawable mBitmapDrawable) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCach.put(key, mBitmapDrawable);
        }
    }

    /**
     * 从lruCache中取出一张图片
     *
     * @param key 图片的url地址
     * @return 取出的BitmapDrawable对象或者null
     */
    public BitmapDrawable getBitmapFromMemoryCache(String key) {
        return mMemoryCach.get(key);
    }

    class BitmapAsyncTask extends AsyncTask<String, Void, BitmapDrawable> {
        private String image_url;

        @Override
        protected BitmapDrawable doInBackground(String... params) {
            image_url = params[0];
            Bitmap mBitmap = downLoadBitmap(image_url);
            BitmapDrawable mDrawable = new BitmapDrawable(mContext.getResources(), mBitmap);
            addBitmapToMemoryCache(image_url, mDrawable);
            return mDrawable;
        }

        @Override
        protected void onPostExecute(BitmapDrawable result) {
            ImageView mImageView = (ImageView) mListView.findViewWithTag(image_url);
            if (mImageView != null && result != null) {
                mImageView.setImageDrawable(result);
            }
        }
    }

    /**
     * @param url 图片的url地址
     * @return 下载的图片bitmap
     */
    public Bitmap downLoadBitmap(String url) {
        HttpURLConnection mConnection = null;
        Bitmap mBitmap = null;
        try {
            URL mURL = new URL(url);
            mConnection = (HttpURLConnection) mURL.openConnection();
            mConnection.setConnectTimeout(5 * 1000);
            mConnection.setReadTimeout(10 * 1000);
            mBitmap = BitmapFactory.decodeStream(mConnection.getInputStream());
            Log.e("downLoadBitmap", mBitmap + "");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mBitmap;
    }
}
