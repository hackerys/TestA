package com.example.administrator.testa.volley;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.testa.R;
import com.example.administrator.testa.photoWall.BitmapCache;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created Jansen on 2016/1/28.
 */
public class VolleyActivity extends Activity {

    NetworkImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volley_layout);
        initView();
        RequestQueue mQueue = Volley.newRequestQueue(this);
        stringRequest(mQueue);
        //jsonRequest(mQueue);
        //imageRequest(mQueue);
        // imageLoader(mQueue);
        //netWorkImageView(mQueue);
    }

    /**
     * 使用NetworkImageView组件更加简单，会依据组件大小自动裁剪图片
     *
     * @param mQueue 请求队列
     */
    private void netWorkImageView(RequestQueue mQueue) {
        String url = "http://img.my.csdn.net/uploads/201407/26/1406383091_3119.jpg";
        img.setDefaultImageResId(R.drawable.ic_launcher);
        img.setErrorImageResId(R.drawable.empty_photo);
        ImageLoader mImageLoader = new ImageLoader(mQueue, new BitmapCache());
        img.setImageUrl(url, mImageLoader);
    }

    /**
     * 效率更高的图片下载
     *
     * @param mQueue 请求队列
     */
    private void imageLoader(RequestQueue mQueue) {
        String url = "http://img.my.csdn.net/uploads/201407/26/1406383091_3119.jpg";
        ImageLoader mImageLoader = new ImageLoader(mQueue, new BitmapCache());
        ImageLoader.ImageListener mImageListener = ImageLoader.getImageListener(img, R.drawable.ic_launcher, R.drawable.empty_photo);
        mImageLoader.get(url, mImageListener);
    }

    /**
     * 专门用来请求图片数据的request
     *
     * @param mQueue 请求队列
     */
    private void imageRequest(RequestQueue mQueue) {
        String url = "http://img.my.csdn.net/uploads/201407/26/1406383091_3119.jpg";
        ImageRequest mImageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                img.setImageBitmap(response);
            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                img.setImageResource(R.drawable.empty_photo);
            }
        });
        mQueue.add(mImageRequest);
        mQueue.start();
    }

    /**
     * 专门用来请求json数据的request
     *
     * @param mQueue 请求队列
     */
    private void jsonRequest(RequestQueue mQueue) {
        String url = "http://pollution.api.juhe.cn/jhapi/pollution/cityList?=&key=72efb6f355add14f9173746da0a50d25";
        JsonObjectRequest mObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.getMessage());
            }
        });
        mQueue.add(mObjectRequest);
        mQueue.start();
    }

    /**
     * 专门用来请求字符串的request
     *
     * @param mQueue 请求队列
     */
    private void stringRequest(RequestQueue mQueue) {
        StringRequest mRequest = new StringRequest("https://hao.360.cn/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.getMessage()+"");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

        };
        mQueue.add(mRequest);
        mQueue.start();
    }

    private void initView() {
        img = (NetworkImageView) findViewById(R.id.img);
    }
}
