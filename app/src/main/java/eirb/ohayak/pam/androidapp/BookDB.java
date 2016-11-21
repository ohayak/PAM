package eirb.ohayak.pam.androidapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrhyk on 20/11/2016.
 */
public class BookDB {
    private final static int DB_VERSION = 1;
    private SQLiteDatabase db;
    private MyBaseSQLite mBaseSQLite;
    public BookDB(Context context){
        //On crée la BDD et sa table :
        mBaseSQLite = new MyBaseSQLite(context,"books",null,DB_VERSION);
    }

    public void open(){
        //On ouvre la BDD en écriture
        db = mBaseSQLite.getWritableDatabase();
    }

    public void close(){
        mBaseSQLite.close();
    }

    public SQLiteDatabase getDataBase(){
        return db;
    }

    public long insertBook(Book book){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        values.put("isbn",book.getIsbn());
        values.put("title",book.getTitle());
        //on insère l'objet dans la BDD via le ContentValues
        return db.insert("books",null,values);
    }

    public List<Book> getBookByTitle(String title){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD
        Cursor cursor = db.rawQuery("SELECT * FROM books WHERE title=?", new String[]{title});
        return cursorToBook(cursor);
    }

    private List<Book> cursorToBook(Cursor cursor){
        List<Book> result = new ArrayList<Book>();
        if (cursor.getCount() == 0)
            return null;
        else {
            cursor.moveToFirst();
            for(int i=0 ; i < cursor.getCount(); i++) {
                Book book = new Book();
                book.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                book.setIsbn(cursor.getString(cursor.getColumnIndex("isbn")));
                book.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
                result.add(book);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return result;
    }
}
