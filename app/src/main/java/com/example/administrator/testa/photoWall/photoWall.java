package com.example.administrator.testa.photoWall;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.administrator.testa.R;

/**
 * Created Jansen on 2016/1/26.
 */
public class photoWall extends Activity {
    private GridView mGridView;
    photoWallAdapter mPhotoWallAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_wall_layout);
        mGridView = (GridView) findViewById(R.id.photo_wall);
        mPhotoWallAdapter = new photoWallAdapter(mGridView, R.layout.photo_wall_item_layout, this, images.imageThumbUrls);
        mGridView.setAdapter(mPhotoWallAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPhotoWallAdapter.cancleAllTasks();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPhotoWallAdapter.flushCache();
    }
}
