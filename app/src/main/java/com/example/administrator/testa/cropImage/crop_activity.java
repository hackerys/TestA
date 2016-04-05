package com.example.administrator.testa.cropImage;

import android.app.Activity;
import android.os.Bundle;

import com.edmodo.cropper.CropImageView;
import com.example.administrator.testa.R;

/**
 * Created Jansen on 2016/3/10.
 */
public class crop_activity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_layout);
        CropImageView cropImageView = (CropImageView) findViewById(R.id.CropImageView);
        cropImageView.setAspectRatio(5, 10);
        cropImageView.setFixedAspectRatio(true);
        cropImageView.setGuidelines(1);
        cropImageView.setImageResource(R.drawable.ic_launcher);
    }
}
