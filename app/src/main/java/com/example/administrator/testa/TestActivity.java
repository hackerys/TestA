package com.example.administrator.testa;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.testa.service.MyService;

/**
 * Created by wp on 2016/1/4.
 */
public class TestActivity extends Activity implements View.OnClickListener {
    private Button bind, excute;
    private IMyAidlInterface binder;
    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        bind = (Button) findViewById(R.id.bind);
        excute = (Button) findViewById(R.id.excute);
        bind.setOnClickListener(this);
        excute.setOnClickListener(this);
        StatusBarCompat.compat(this, Color.BLUE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bind) {
            Intent mIntent = new Intent(this, MyService.class);
            bindService(mIntent, mConnection, BIND_AUTO_CREATE);
        } else if (v.getId() == R.id.excute) {
            try {
                String result = binder.toUpper("sddfgdfgdfg");
                Toast.makeText(TestActivity.this, result, Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
