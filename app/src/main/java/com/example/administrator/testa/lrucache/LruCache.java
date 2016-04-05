package com.example.administrator.testa.lrucache;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.testa.R;

/**
 * Created Jansen on 2016/1/25.
 */
public class LruCache extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lru_cache_layout);
        // getMemory();
        //getImageInfo();

    }

    /**
     * 将大图缩略成小图
     *
     * @param mResources
     * @param res
     * @param reWidth
     * @param reHeight
     * @return
     */
    public Bitmap decodeSampleBitmapFromResouce(Resources mResources, int res, int reWidth, int reHeight) {
        BitmapFactory.Options mOptions = new BitmapFactory.Options();
        mOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(mResources, R.drawable.ic_launcher, mOptions);
        mOptions.inSampleSize = caculateInsapleSize(mOptions, reWidth, reHeight);
        mOptions.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(getResources(), res, mOptions);
    }

    /**
     * @param mOptions 已经被解析过的bitmap的options
     * @param reWidth  需要的宽
     * @param reHeight 需要的高
     * @return 缩放比例
     */
    public int caculateInsapleSize(BitmapFactory.Options mOptions, int reWidth, int reHeight) {
        int width = mOptions.outWidth;
        int height = mOptions.outHeight;
        int inSampleSize = 1;
        if (height > reHeight || width > reWidth) {
            int heightRatio = Math.round((float) height / (float) reHeight);
            int widthRatio = Math.round((float) width / (float) reWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 解析图片获得图片的基本信息
     */
    private void getImageInfo() {
        BitmapFactory.Options mOptions = new BitmapFactory.Options();
        mOptions.inJustDecodeBounds = true;
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher, mOptions);
        int image_width = mOptions.outWidth;
        int image_height = mOptions.outHeight;
        String image_type = mOptions.outMimeType;
        Log.e("Bitmap", "image_width:" + image_width + ",image_height:" + image_height + ",image_type:" + image_type);
    }

    /**
     * 获得手机的内部的可用内存大小
     */
    private void getMemory() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory());
        Log.e("maxMemory:", maxMemory / (1024 * 1024) + "");
    }
}
