package com.example.administrator.testa.imageCompress;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.administrator.testa.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created Jansen on 2016/3/7.
 */
public class ImageCompressActivity extends Activity {
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_compress_layout);
        initView();
        Bitmap mBitmap = imageUtils.getSmallBitmap(R.drawable.my, this);
        Log.e("Bitmap所占内存", (mBitmap.getByteCount() / 1024) + "kb");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int quality = 100;
        mBitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
        while ((bos.toByteArray().length / 1024) > 100) {
            bos.reset();
            quality -= 5;
            mBitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
        }
        File mFile = new File(this.getCacheDir(), "my.jpg");
        Log.e("图片存储目录", mFile.getName());
        try {
            FileOutputStream fs = new FileOutputStream(mFile);
            fs.write(bos.toByteArray());
            fs.flush();
            fs.close();
            Log.e("存储成功", "存储成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("压缩后的大小", (bos.toByteArray().length / 1024) + "kb");
        Bitmap mBitmap1 = BitmapFactory.decodeFile(mFile.getAbsolutePath());
        img.setImageBitmap(mBitmap1);
        Log.e("图片路径", mFile.getAbsolutePath());
    }

    private void initView() {
        img = (ImageView) findViewById(R.id.img);
    }
}
