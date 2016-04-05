package com.example.administrator.testa.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by wp on 2015/12/24.
 */
public class DBHelper extends SQLiteOpenHelper{
    public static final String TAG="DBHelper";
    public static final String DB_NAME="ys.db";
    public static final int DB_VERSION=1;
    public  DBHelper(Context mContext){
        super(mContext,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists person(id integer primary key autoincrement,name varchar,age smallint)");
        Log.d(TAG,"DB create");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG,"DB version change");
    }
}
