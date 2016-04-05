package com.example.administrator.testa.sqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.testa.R;

/**
 * Created by wp on 2015/12/23.
 */
public class SqliteActivity extends Activity implements View.OnClickListener {
    public static final String TAG = "SqliteActivity";
    private SQLiteDatabase db;
    private int count = 0;
    private EditText _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sql_operate_layout);
        //创建数据库
        db = openOrCreateDatabase("mytest.db", Context.MODE_PRIVATE, null);
        //初始化清空表
        db.execSQL("drop table if exists person");

        findViewById(R.id.insert).setOnClickListener(this);
        findViewById(R.id.update).setOnClickListener(this);
        findViewById(R.id.delete).setOnClickListener(this);
        findViewById(R.id.query).setOnClickListener(this);

        //创建person表
        db.execSQL("CREATE TABLE person (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, age SMALLINT)");
    }

    @Override
    public void onClick(View v) {
        ContentValues mValues;
        switch (v.getId()) {
            case R.id.insert:
                //api插入
                Person mPerson = new Person("tom", 20);
                db.execSQL("INSERT INTO person VALUES (NULL, ?, ?)", new Object[]{mPerson.getName(), mPerson.getAge()});
                Toast.makeText(SqliteActivity.this, "插入", Toast.LENGTH_SHORT).show();
                //db.execSQL("insert into person(name,age) values(?,?)",new Object[]{mPerson.getName(),mPerson.getAge()});
                break;
            case R.id.delete:
                _id = (EditText) findViewById(R.id.person_id);
                db.delete("person", "_id=?", new String[]{_id.getText().toString()});
                break;
            case R.id.query:
                Cursor mCursor = db.rawQuery("select * from person", new String[]{});
                while (mCursor.moveToNext()) {
                    int id = mCursor.getInt(mCursor.getColumnIndex("_id"));
                    String name = mCursor.getString(mCursor.getColumnIndex("name"));
                    String age = mCursor.getString(mCursor.getColumnIndex("age"));
                    Log.d("TAG", "姓名:" + name + ",nianl:" + age);
                }
                break;
            case R.id.update:
                _id = (EditText) findViewById(R.id.person_id);
                mValues = new ContentValues();
                mValues.put("age", 100);
                db.update("person", mValues, "_id=?", new String[]{_id.getText().toString()});
                break;
            default:
                break;
        }
    }
}
