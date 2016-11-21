package eirb.ohayak.pam.androidapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mrhyk on 15/11/2016.
 */
public class MyBaseSQLite extends SQLiteOpenHelper{
    private final static String CREATE_TABLE = "CREATE TABLE books(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "isbn TEXT NOT NULL," +
            "title TEXT NOT NULL)";

    public MyBaseSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE books");
        sqLiteDatabase.execSQL(CREATE_TABLE);

    }
}
