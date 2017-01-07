package eirb.ohayak.pam.androidapp.helper;

import android.content.ContentValues;
import android.database.Cursor;
import eirb.ohayak.pam.androidapp.object.User;

/**
 * Created by mrhyk on 21/12/2016.
 */
public class UserHelper extends TableHelper<User> {
    public static final String TABLE_NAME = "users";
    public static final String KEY_ID = "userid";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PWD = "pwd";
    public static final String KEY_FIRSTNAME = "firstname";
    public static final String KEY_LASTNAME = "lastname";
    private static UserHelper instance = null;

    public UserHelper() {
        super();
        open();
    }

    @Override
    public synchronized long insert(User o) {
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, o.getEmail());
        values.put(KEY_PWD, o.getPwd());
        values.put(KEY_FIRSTNAME, o.getFirstname());
        values.put(KEY_LASTNAME, o.getLastname());
        o.setId(database.insert(TABLE_NAME, null, values));
        return o.getId();

    }

    @Override
    public User getById(long id) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ KEY_ID +" =?",
                new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        if (cursor.getCount() > 0)
            return cursorToItem(cursor,0);
        else
            return null;
    }

    @Override
    protected User cursorToItem(Cursor cursor, int position) {
        User user = new User();
        cursor.moveToPosition(position);
        user.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
        user.setPwd(cursor.getString(cursor.getColumnIndex(KEY_PWD)));
        user.setFirstname(cursor.getString(cursor.getColumnIndex(KEY_FIRSTNAME)));
        user.setLastname(cursor.getString(cursor.getColumnIndex(KEY_LASTNAME)));
        return user;
    }

    public User getByEmail(String mEmail) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ KEY_EMAIL +" =?",
                new String[]{mEmail});
        cursor.moveToFirst();
        if (cursor.getCount() > 0)
            return cursorToItem(cursor,0);
        else
            return null;
    }

    public synchronized static UserHelper getInstance() {
        if (instance == null)
            instance = new UserHelper();
        return instance;
    }
}
