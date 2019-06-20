package hotel.main.Information;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wj on 2018/2/19.
 */
public class TextSQLiteOpenHelper extends SQLiteOpenHelper {
    public TextSQLiteOpenHelper(Context context) {
        super(context, "Text.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Text (id integer primary key autoincrement,content varchar(20),name varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}

