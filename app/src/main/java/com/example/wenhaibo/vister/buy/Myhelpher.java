package com.example.wenhaibo.vister.buy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 刘明 on 2018/12/6.
 */
public class Myhelpher extends SQLiteOpenHelper {
    public Myhelpher(Context context){
        super(context,"sss.db",null,2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建表
        sqLiteDatabase.execSQL("create table shuju1(id integer primary key autoincrement,json text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}