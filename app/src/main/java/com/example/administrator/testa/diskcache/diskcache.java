package com.example.administrator.testa.diskcache;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.administrator.testa.R;
import com.example.administrator.testa.lib.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created Jansen on 2016/1/25.
 */
public class diskcache extends Activity {
    DiskLruCache mDiskcache = null;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disk_cache_layout);
        init();
        // new downThread().start();
        getImage();
    }

    public void getImage() {
        mImageView = (ImageView) findViewById(R.id.img);
        String image_url = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
        String key = DiskLruCache.hashKeyForDisk(image_url);
        try {
            DiskLruCache.Snapshot mSnapshot = mDiskcache.get(key);
            if (mSnapshot != null) {
                Bitmap mBitmap = BitmapFactory.decodeStream(mSnapshot.getInputStream(0));
                if (mBitmap != null) {
                    mImageView.setImageBitmap(mBitmap);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    class downThread extends Thread {
        @Override
        public void run() {
            String image_url = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
            String key = DiskLruCache.hashKeyForDisk(image_url);
            try {
                DiskLruCache.Editor mEditor = mDiskcache.edit(key);
                if (mEditor != null) {
                    OutputStream mOutputStream = mEditor.newOutputStream(0);
                    if (downLoadImageToStream(image_url, mOutputStream)) {
                        mEditor.commit();
                        Log.e("downThread", "下载成功");
                    } else {
                        mEditor.abort();
                    }
                }
                mDiskcache.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            mDiskcache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mDiskcache.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void init() {
        try {
            File cacheFile = DiskLruCache.getDiskCacheDir(this, "bitmap");
            if (!cacheFile.exists()) {
                cacheFile.mkdirs();
            }
            mDiskcache = DiskLruCache.open(cacheFile, DiskLruCache.getAppVersion(this), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
}
