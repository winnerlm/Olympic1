package player.health.personInformation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 作者：Created by 高宇 on 2016/11/20.
 * 邮箱：741208260@qq.com
 */

public class Information_Sqllite extends SQLiteOpenHelper {
    public Information_Sqllite(Context context) {
        super(context,"Information_Sqllite",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //初始化表结构
        db.execSQL("create table Information_Sqllite (id integer primary key autoincrement,rate varchar(20),date varchar(20))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
