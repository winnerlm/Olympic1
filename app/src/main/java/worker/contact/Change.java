package worker.contact;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Created by 高宇 on 2016/9/13.
 * 邮箱：741208260@qq.com
 */
public class Change {
    //构造方法里完成数据库helper初始化
    private SQLite helper;
    public Change(Context context){
        helper = new SQLite(context);

    }
    //添加
    public void add(String name, String number) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into person (name,number)values(?,?)", new Object[]{name, number});
        db.close();

    }

    //修改
    public void update (Person p){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("update person set number=? ,name=? where id=?",new Object[]{p.getNumber(),p.getName(),p.getId()});
        db.close();

    }

    //删除
    public void delete(Integer id){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from person where id=?",new Object[]{id});
        db.close();
    }
    //按标题查询
    public Person findname(String name){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select name,number,id from person where name=?", new String[]{name});
        while (cursor.moveToNext()){
            String numner = cursor.getString(cursor.getColumnIndex("number"));
            String Ming = cursor.getString(cursor.getColumnIndex("name"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            return new Person(id,Ming,numner);
        }
        cursor.close();
        db.close();
        return null;

    }
    //查询全部
    public List<Person> findAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Person> persons = new ArrayList<Person>();
        Cursor cursor = db.rawQuery("select id,number,name from person", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String number = cursor.getString(cursor.getColumnIndex("number"));
            Person p = new Person(id, name, number);
            persons.add(p);
        }
        cursor.close();
        db.close();
        return persons;

    }
    //查询单个的不需要数组
    public Person find(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM person WHERE id = ?",
                new String[]{String.valueOf(id)});
        //存在数据才返回true
        if (cursor.moveToFirst()) {
            String number = cursor.getString(cursor.getColumnIndex("number"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            return new Person(number,name);
        }
        cursor.close();
        return null;
    }
}
