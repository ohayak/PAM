package eirb.ohayak.pam.androidapp.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mrhyk on 20/12/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static int DATABASE_VERSION = 1;
    private static DatabaseHelper instance;

    // Database Name
    private static final String DATABASE_NAME = "toursDatabase";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE "+ UserHelper.TABLE_NAME + "("
            + UserHelper.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + UserHelper.KEY_EMAIL + " TEXT,"
            + UserHelper.KEY_PWD + " TEXT,"
            + UserHelper.KEY_FIRSTNAME + " TEXT,"
            + UserHelper.KEY_LASTNAME + " TEXT"
            + ");";

    private static final String CREATE_TABLE_TOURS = "CREATE TABLE "+ TourHelper.TABLE_NAME + "("
            + TourHelper.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TourHelper.KEY_USER_ID + " INTEGER,"
            + TourHelper.KEY_NAME + " TEXT,"
            + TourHelper.KEY_STATUS + " TEXT,"
            + TourHelper.KEY_END + " TEXT,"
            + TourHelper.KEY_START + " TEXT,"
            + TourHelper.KEY_SPEED + " TEXT,"
            + TourHelper.KEY_TOPSPEED + " TEXT,"
            + TourHelper.KEY_DETAILS + " TEXT,"
            + "FOREIGN KEY ("+ TourHelper.KEY_USER_ID +") REFERENCES "+ UserHelper.TABLE_NAME +"("+ UserHelper.KEY_ID +"));";

    private static final String CREATE_TABLE_LOCATIONS = "CREATE TABLE "+ LocationHelper.TABLE_NAME + "("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + LocationHelper.KEY_ID + " INTEGER,"
            + LocationHelper.KEY_LONGITUDE + " DOUBLE,"
            + LocationHelper.KEY_LATITUDE + " DOUBLE );";

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_TOURS);
        db.execSQL(CREATE_TABLE_LOCATIONS);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+ UserHelper.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS"+ TourHelper.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS"+ LocationHelper.TABLE_NAME);
        onCreate(db);
    }

    public static void create(Context context) {
        if (instance == null)
            instance = new DatabaseHelper(context);
    }

    public static DatabaseHelper getInstance() {
        return instance;
    }
}
