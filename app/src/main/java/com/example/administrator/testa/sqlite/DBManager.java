package com.example.administrator.testa.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wp on 2015/12/24.
 */
public class DBManager {
    public static final String DB_MANAGER = "DBManager";
    private DBHelper mDBHelper;
    private SQLiteDatabase mSQLiteDatabase;

    public DBManager(Context mContext) {
        mDBHelper = new DBHelper(mContext);
        mSQLiteDatabase = mDBHelper.getWritableDatabase();
    }

    public void insert(List<Person> mPersons) {
        for (Person mPerson : mPersons) {
            mSQLiteDatabase.execSQL("insert into person(name,age) values(?,?)", new Object[]{mPerson.getName(), mPerson.getAge()});
        }
        Log.d(DB_MANAGER, "插入");
    }

    public void delete(Person mPerson) {
        mSQLiteDatabase.delete("person","id=?",new String[]{"1"});
        Log.d(DB_MANAGER, "删除");
    }

    public List<Person>  query() {
        List<Person> mPersons = new ArrayList<>();
        Cursor mCursor = mSQLiteDatabase.rawQuery("select * from person", new String[]{});
        while (mCursor.moveToNext()) {
            String name = mCursor.getString(mCursor.getColumnIndex("name"));
            int age = mCursor.getInt(mCursor.getColumnIndex("age"));
            Person mPerson = new Person(name, age);
            mPersons.add(mPerson);
        }
        mCursor.close();
        Log.d(DB_MANAGER, "查询");
        return mPersons;
    }

    public void update(Person mPerson) {
        ContentValues mValues=new ContentValues();
        mValues.put("age",100);
        mSQLiteDatabase.update("person",mValues,"name=?",new String[]{mPerson.getName()});
        Log.d(DB_MANAGER, "更新");
    }

    public void closeDB(){
        mSQLiteDatabase.close();
    }
}
