package eirb.ohayak.pam.androidapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by mrhyk on 21/12/2016.
 */
public abstract class TableHelper<T> {
    protected static SQLiteDatabase database;
    protected static DatabaseHelper mBaseSQLite =  DatabaseHelper.getInstance();
    public TableHelper(){
    }

    protected void open(){
        database = mBaseSQLite.getWritableDatabase();
    }

    protected void close(){
        mBaseSQLite.close();
    }

    public abstract long insert(T o);

    public abstract T getById(long id);

    protected abstract T cursorToItem(Cursor cursor, int position);
}
