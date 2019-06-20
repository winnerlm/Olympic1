package player.health.personInformation;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者：Created by 高宇 on 2016/11/20.
 * 邮箱：741208260@qq.com
 */

public class Information_SQL_adapter {
    //构造方法里完成数据库helper初始化
    private Information_Sqllite helper;

    public Information_SQL_adapter(Context context) {
        helper = new Information_Sqllite(context);

    }

  //添加
    public void add(String rate, String data) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Log.e("gaoyu_add","向数据库中填条目");
        db.execSQL("insert into Information_Sqllite (rate,date)values(?,?)", new Object[]{rate, data});
        db.close();

    }


    //删除
    public void delete(Integer id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from Information_Sqllite where id=?", new Object[]{id});
        db.close();
    }

    //查询全部
    public List<Person> findAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Person> persons = new ArrayList<Person>();
        Cursor cursor = db.rawQuery("select id,rate,date from Information_Sqllite", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String rate = cursor.getString(cursor.getColumnIndex("rate"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            Person p = new Person(id, rate, date);
            persons.add(p);
        }
        cursor.close();
        db.close();
        return persons;

    }
    //删除数据库
    public void dropDatabase(){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("DELETE FROM Information_Sqllite");
        db.close();

    }
}
