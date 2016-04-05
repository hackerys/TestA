package com.example.administrator.testa.cropImage;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.testa.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created Jansen on 2016/3/10.
 */
public class cropImageActivity extends Activity implements View.OnClickListener{
    Button gallery;
    Button button;
    ImageView showimg;
    public static final int IMAGE_FROM_GALLERY=1;
    public static final int IMAGE_FROM_CAPTURE=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_image_layout);
        initView();
        bindListener();
    }

    private void initView() {
        gallery = (Button) findViewById(R.id.gallery);
        button = (Button) findViewById(R.id.button);
        showimg = (ImageView) findViewById(R.id.show_img);
    }
    public void bindListener(){
        gallery.setOnClickListener(this);
        button.setOnClickListener(this);
        showimg.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //从相册选取
        if (resultCode==RESULT_OK&&requestCode==IMAGE_FROM_GALLERY){
            Uri uri = data.getData();
            String path;
            if (isMediaDocument(uri) && Build.VERSION.SDK_INT >= 19) {
                path = getDocumentImagePath(uri);
            } else {
                path = getDataColumImagePath(uri);
            }
            // 添加压缩逻辑....
            File dir = new File(Environment.getExternalStorageDirectory() + "/wn/pictures");
            if (!dir.exists()) {
                dir.mkdir();
            }
            String outPath = dir + "/" + Constant.GALLERY_IMAGE + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg";
            Bitmap mBitmap1 = null;
            try {
                mBitmap1 = ImageUtils.getSmallBitmap(path, outPath,
                        300, 300);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            showimg.setImageBitmap(mBitmap1);
        }
        //拍照获取
        if (resultCode==RESULT_OK&&requestCode==IMAGE_FROM_CAPTURE){

        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.gallery){
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
            intent.setType("image/*");
            startActivityForResult(intent, IMAGE_FROM_GALLERY);
        }

        if (v.getId()==R.id.button){

        }

        if (v.getId()==R.id.show_img){

        }
    }
    private String getDataColumImagePath(Uri mUri) {
        Log.e("Uri", mUri + "");
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(mUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private String getDocumentImagePath(Uri mSelectedImage) {
        String wholeID = "";
        if (Build.VERSION.SDK_INT >= 19) {
            wholeID = DocumentsContract.getDocumentId(mSelectedImage);
        }
        String id = wholeID.split(":")[1];
        String[] column = {MediaStore.Images.Media.DATA};
        String sel = MediaStore.Images.Media._ID + "=?";
        Cursor cursor = getContentResolver().
                query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        column, sel, new String[]{id}, null);
        String path = "";
        int columnIndex = cursor.getColumnIndex(column[0]);
        if (cursor.moveToFirst()) {
            path = cursor.getString(columnIndex);
        }
        cursor.close();
        return path;
    }
    /**
     * 判断uri是否是document格式
     *
     * @param uri
     * @return
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
