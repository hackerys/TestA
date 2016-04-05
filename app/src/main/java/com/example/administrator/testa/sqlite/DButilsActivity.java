package com.example.administrator.testa.sqlite;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.administrator.testa.R;
import com.example.administrator.testa.sqliteB.DBManager;
import com.example.administrator.testa.sqliteB.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wp on 2015/12/24.
 */
public class DButilsActivity extends Activity implements View.OnClickListener {
    private com.example.administrator.testa.sqliteB.DBManager mDBManager;
    public static final String TAG = "DButilsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sql_operate_layout);
        mDBManager = new DBManager(this);

        findViewById(R.id.insert).setOnClickListener(this);
        findViewById(R.id.update).setOnClickListener(this);
        findViewById(R.id.delete).setOnClickListener(this);
        findViewById(R.id.query).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.insert:
                List<com.example.administrator.testa.sqliteB.Person> mPersons1 = new ArrayList<>();
                com.example.administrator.testa.sqliteB.Person mPerson = new Person("张三", 20, "未婚");
                com.example.administrator.testa.sqliteB.Person mPerson1 = new Person("王五", 22, "未婚");
                com.example.administrator.testa.sqliteB.Person mPerson2 = new Person("赵六", 27, "已婚");
                mPersons1.add(mPerson);
                mPersons1.add(mPerson1);
                mPersons1.add(mPerson2);
                mDBManager.add(mPersons1);
                Log.d(TAG, "插入");
                break;
            case R.id.delete:
                com.example.administrator.testa.sqliteB.Person mPerson4 = new Person("孙七", 17, "未婚");
                mDBManager.deleteOldPerson(mPerson4);
                Log.d(TAG, "删除");
                break;
            case R.id.query:
                Log.d(TAG, "查询");
                List<com.example.administrator.testa.sqliteB.Person> mPersons = mDBManager.query();
                for (Person mPerson3 : mPersons) {
                    String name = mPerson3.name;
                    Log.d(TAG, "name:"+name+",age:"+mPerson3.age);
                }
                break;
            case R.id.update:
                Log.d(TAG, "更新");
                com.example.administrator.testa.sqliteB.Person mPerson5 = new Person("张三", 100, "未婚");
                mDBManager.updateAge(mPerson5);
                break;
            default:
                break;
        }
    }
}
