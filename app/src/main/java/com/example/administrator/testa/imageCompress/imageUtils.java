package com.example.administrator.testa.imageCompress;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created Jansen on 2016/3/7.
 */
public class imageUtils {
    /**
     * 压缩图片的像素值
     *
     * @param desireWidth
     * @param desireHeight
     * @param mOptions
     * @return
     */
    public static int caculateInsmapleSize(int desireWidth, int desireHeight, BitmapFactory.Options mOptions) {
        int width = mOptions.outWidth;
        int height = mOptions.outHeight;
        int insmapleSize = 1;
        if (width > desireWidth || height > desireHeight) {
            int heightRatio = Math.round((float) height / desireHeight);
            int widthRatio = Math.round((float) width / desireWidth);
            insmapleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return insmapleSize;
    }

    public static Bitmap getSmallBitmap(String filePath) {
        BitmapFactory.Options mOptions = new BitmapFactory.Options();
        mOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath);
        mOptions.inSampleSize = caculateInsmapleSize(480, 800, mOptions);
        mOptions.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath);
    }
    //从资源文件中获取图片
    public static Bitmap getSmallBitmap(int resId,Context mContext) {
        BitmapFactory.Options mOptions = new BitmapFactory.Options();
        mOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(mContext.getResources(),resId,mOptions);
        mOptions.inSampleSize = caculateInsmapleSize(200, 200, mOptions);
        mOptions.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(mContext.getResources(),resId,mOptions);
    }

    public static File compressImage(String filePath, String compressedPath) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Bitmap bm = getSmallBitmap(filePath);
        File mFile = new File(compressedPath);
        int quality = 100;
        bm.compress(Bitmap.CompressFormat.JPEG, quality, bos);
        while ((bos.toByteArray().length / 1024) > 100) {
            quality -= 5;
            bm.compress(Bitmap.CompressFormat.JPEG, quality, bos);
        }
        try {
            FileOutputStream fs = new FileOutputStream(mFile);
            fs.write(bos.toByteArray());
            fs.flush();
            fs.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mFile;
    }
}
