package hotel.main.Information;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wj on 2018/2/19.
 */
public class Dao {
    private TextSQLiteOpenHelper helper;

    public Dao(Context context) {
        helper = new TextSQLiteOpenHelper(context);
    }

    //添加
    public void add(String name, String content) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into Text (name,content)values(?,?)", new Object[]{name, content});
        db.close();

    }

    public void delete(Integer id){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from Text where id=?",new Object[]{id});
        db.close();
    }

    public Bean find(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Text WHERE id = ?",
                new String[]{String.valueOf(id)});
        //存在数据才返回true
        if (cursor.moveToFirst()) {
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            return new Bean(content,name);
        }
        cursor.close();
        return null;
    }


    public void update (Bean b){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("update Text set content=? ,name=? where id=?",new Object[]{b.getContent(),b.getName(),b.getId()});
        db.close();
    }
    public List<Bean> findAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Bean> persons = new ArrayList<Bean>();
        Cursor cursor = db.rawQuery("select id,content,name from Text", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            Bean p = new Bean(id, name, content);
            persons.add(p);
        }
        cursor.close();
        db.close();
        return persons;

    }
    public Bean find(String name){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select name,content,id from Text where name=?", new String[]{name});
        while (cursor.moveToNext()){
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String Ming = cursor.getString(cursor.getColumnIndex("name"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            return new Bean(id,Ming,content);
        }
        cursor.close();
        db.close();
        return null;
    }
}