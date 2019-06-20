package worker.contact;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 作者：Created by 高宇 on 2016/9/13.
 * 邮箱：741208260@qq.com
 * 数据库
 */
public class SQLite extends SQLiteOpenHelper {

    public SQLite(Context context) {
        //对数据库进行修改时后  必须跟新版本号
        super(context,"person.db",null,1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        //初始化表结构
        db.execSQL("create table person (id integer primary key autoincrement,name varchar(20),number varchar(20))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
