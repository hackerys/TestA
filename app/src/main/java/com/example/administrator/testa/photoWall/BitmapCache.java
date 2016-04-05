package com.example.administrator.testa.photoWall;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created Jansen on 2016/1/28.
 */
public class BitmapCache implements ImageLoader.ImageCache {
    private LruCache<String, Bitmap> imageCache;
    public static final int MAX_CACHE_SIZE = 10 * 1024 * 1024;

    public BitmapCache() {
        imageCache = new LruCache<String, Bitmap>(MAX_CACHE_SIZE) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        return imageCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        imageCache.put(url, bitmap);
    }
}
