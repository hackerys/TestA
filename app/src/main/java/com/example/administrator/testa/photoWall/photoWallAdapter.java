package com.example.administrator.testa.photoWall;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.administrator.testa.R;
import com.example.administrator.testa.lib.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created Jansen on 2016/1/26.
 */
public class photoWallAdapter extends BaseAdapter implements AbsListView.OnScrollListener {
    /**
     * 图片缓存的核心技术类，用于缓存所有下载好的图片，在程序内存达到设定值时会将最近最少使用的图片移除掉
     */
    LruCache<String, Bitmap> mMemoryCach;

    /**
     * gridview实例
     */
    GridView mGridView;
    /**
     * 第一张可见图片的下标
     */
    int mFirstVsibleItem;
    /**
     * 一屏有多少张图片可见
     */
    int mVisibleItemCount;
    /**
     * 解决第一次进入屏幕没有滚动图片不加载的问题
     */
    private boolean isFirstEnter;
    private int resId;
    private Context mContext;
    /**
     * 图片地址
     */
    private String[] datas;
    /**
     * 记录正在下载，或等待下载的任务
     */
    private Set<BitmapAsyncTask> mAsyncTasks;
    /**
     * 硬盘缓存的核心类
     */
    private DiskLruCache mDiskLruCache;
    /**
     * 记录每个子项的高度
     */
    private int itemHeight;

    public photoWallAdapter(GridView mGridView, int mResId, Context mContext, String[] mDatas) {
        this.mGridView = mGridView;
        resId = mResId;
        this.mContext = mContext;
        datas = mDatas;
        mAsyncTasks = new HashSet<BitmapAsyncTask>();
        //获取应用程序的最大可用内存
        int maxMemory = (int) (Runtime.getRuntime().maxMemory());
        //设置图片缓存大小为最大内存的8分之1
        int cacheSize = maxMemory / 8;
        mMemoryCach = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
        File cacheDir = DiskLruCache.getDiskCacheDir(mContext, "bitmap");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        try {
            mDiskLruCache = DiskLruCache.open(cacheDir, DiskLruCache.getAppVersion(mContext), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mGridView.setOnScrollListener(this);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String image_url = datas[position];
        View mView = null;
        if (convertView == null) {
            mView = LayoutInflater.from(mContext).inflate(resId, null);
        } else {
            mView = convertView;
        }
        ImageView mImageView = (ImageView) mView.findViewById(R.id.photo_item);
        Bitmap mBitmap = getBitmapFromMemoryCache(image_url);
        //关键给imageview设置一个tag,保证图片不会乱序
        mImageView.setTag(image_url);
        /**
         * 给imageview设置图片，首先先从缓存中取，娶不到先设置一张默认图片
         */
        if (mBitmap != null) {
            mImageView.setImageBitmap(mBitmap);
        } else {
            mImageView.setImageResource(R.drawable.empty_photo);
        }
        return mView;
    }

    /**
     * 加载Bitmap对象。此方法会在LruCache中检查所有屏幕中可见的ImageView的Bitmap对象，
     * 如果发现任何一个ImageView的Bitmap对象不在缓存中，就会开启异步线程去下载图片。
     *
     * @param mFirstVsibleItem  第一个可见的ImageView的下标
     * @param mVisibleItemCount 屏幕中总共可见的元素数
     */
    private void loadVisibleItem(int mFirstVsibleItem, int mVisibleItemCount) {
        try {
            for (int i = mFirstVsibleItem; i < mVisibleItemCount; i++) {
                String image_url = images.imageThumbUrls[i];
                Bitmap mBitmap = getBitmapFromMemoryCache(image_url);
                if (mBitmap == null) {
                    BitmapAsyncTask mAsyncTask = new BitmapAsyncTask();
                    mAsyncTasks.add(mAsyncTask);
                    mAsyncTask.execute(image_url);
                } else {
                    ImageView mImageView = (ImageView) mGridView.findViewWithTag(image_url);
                    if (mImageView != null) {
                        mImageView.setImageBitmap(mBitmap);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class BitmapAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private String image_url;
        FileDescriptor mFileDescriptor = null;
        FileInputStream mFileInputStream = null;
        DiskLruCache.Snapshot mSnapshot = null;

        @Override
        protected Bitmap doInBackground(String... params) {
            image_url = params[0];
            String key = DiskLruCache.hashKeyForDisk(image_url);
            try {
                mSnapshot = mDiskLruCache.get(key);
                if (mSnapshot == null) {
                    //如果没有找到对应的硬盘缓存，就从网络上下载
                    try {
                        DiskLruCache.Editor mEditor = mDiskLruCache.edit(key);
                        if (mEditor != null) {
                            OutputStream mOutputStream = mEditor.newOutputStream(0);
                            if (downLoadImageToStream(image_url, mOutputStream)) {
                                mEditor.commit();
                                Log.e("downThread", "下载成功");
                            } else {
                                mEditor.abort();
                            }
                        }
                        mDiskLruCache.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //缓存被写入后，再次查找缓存
                    mSnapshot = mDiskLruCache.get(key);
                }
                if (mSnapshot != null) {
                    //    Bitmap mBitmap = BitmapFactory.decodeStream(mSnapshot.getInputStream(0));
                    mFileInputStream = (FileInputStream) mSnapshot.getInputStream(0);
                    mFileDescriptor = mFileInputStream.getFD();
                }
                Bitmap mBitmap = null;
                if (mFileDescriptor != null) {
                    mBitmap = BitmapFactory.decodeFileDescriptor(mFileDescriptor);
                }
                if (mBitmap != null) {
                    addBitmapToMemoryCache(params[0], mBitmap);
                }
                return mBitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            ImageView mImageView = (ImageView) mGridView.findViewWithTag(image_url);
            if (mImageView != null && result != null) {
                mImageView.setImageBitmap(result);
            }
            mAsyncTasks.remove(this);
        }
    }

    /**
     * @param url           图片的url
     * @param mOutputStream 存储了图片流的输出流
     * @return 是否下载成功
     */
    public boolean downLoadImageToStream(String url, OutputStream mOutputStream) {
        HttpURLConnection mConnection = null;
        BufferedOutputStream mBufferedOutputStream = null;
        BufferedInputStream mBufferedInputStream = null;
        try {
            URL mURL = new URL(url);
            mConnection = (HttpURLConnection) mURL.openConnection();
            mBufferedInputStream = new BufferedInputStream(mConnection.getInputStream(), 8 * 1024);
            mBufferedOutputStream = new BufferedOutputStream(mOutputStream, 8 * 1024);
            int b = 0;
            while ((b = mBufferedInputStream.read()) != -1) {
                mBufferedOutputStream.write(b);
            }
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (mConnection != null) {
                mConnection.disconnect();
            }

            try {
                if (mBufferedOutputStream != null) {
                    mBufferedOutputStream.close();
                }
                if (mBufferedInputStream != null) {
                    mBufferedInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    /**
     * 取消所有正在下载或等待下载的任务。
     */
    public void cancleAllTasks() {
        if (mAsyncTasks != null) {
            for (BitmapAsyncTask mTask : mAsyncTasks) {
                mTask.cancel(false);
            }
        }
    }

    /**
     * 设置子项的高度
     *
     * @param height
     */
    public void setItemHeight(int height) {
        if (height == itemHeight) {
            return;
        }
        itemHeight = height;
        notifyDataSetChanged();
    }

    /**
     * 将记录同步到journal文件中
     */
    public void flushCache() {
        if (mDiskLruCache != null) {
            try {
                mDiskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //仅当gridview静止时采取下载图片，滑动时取消所有的下载任务
        if (scrollState == SCROLL_STATE_IDLE) {
            loadVisibleItem(mFirstVsibleItem, mVisibleItemCount);
        } else {
            cancleAllTasks();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mFirstVsibleItem = firstVisibleItem;
        mVisibleItemCount = visibleItemCount;
        //下载的任务应该在onScrollStateChanged中调用，但是第一次进入时并不会触发，所以这里要判断是否是第一次进入
        if (isFirstEnter) {
            loadVisibleItem(mFirstVsibleItem, mVisibleItemCount);
            isFirstEnter = false;
        }
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

    /**
     * 将一张图片存储到 lruCache中
     *
     * @param key             图片的url地址
     * @param mBitmapDrawable 从网络下载的BitmapDrawable
     */
    public void addBitmapToMemoryCache(String key, Bitmap mBitmapDrawable) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCach.put(key, mBitmapDrawable);
        }
    }

    /**
     * 从 lruCache中取出一张图片
     *
     * @param key 图片的url地址
     * @return 取出的BitmapDrawable对象或者 null
     */
    public Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCach.get(key);
    }

}
