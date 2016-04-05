package com.example.administrator.testa.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.administrator.testa.IMyAidlInterface;

public class MyService extends Service {
    public static final String TAG="MyService";
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"service created");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.e(TAG,"service binded");
        return mStub;
    }

    IMyAidlInterface.Stub mStub = new IMyAidlInterface.Stub() {
        @Override
        public String toUpper(String str) throws RemoteException {
            return str.toUpperCase();
        }
    };
}
