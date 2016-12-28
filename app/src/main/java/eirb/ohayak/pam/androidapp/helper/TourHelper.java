package eirb.ohayak.pam.androidapp.helper;

import android.content.ContentValues;
import android.database.Cursor;
import eirb.ohayak.pam.androidapp.object.Tour;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrhyk on 22/12/2016.
 */
public class TourHelper extends TableHelper<Tour> {
    public static final String TABLE_NAME = "tours";
    public static final String KEY_ID = "tourid";
    public static final String KEY_USER_ID = "userid";
    public static final String KEY_NAME = "name";
    public static final String KEY_STATUS = "status";
    public static final String KEY_END = "end";
    public static final String KEY_START = "start";
    public static final String KEY_SPEED = "speed";
    public static final String KEY_TOPSPEED = "topspeed";
    public static final String KEY_DETAILS = "details";
    private static TourHelper instance = null;
    private static LocationHelper lh = LocationHelper.getInstance();

    public TourHelper() {
        super();
        open();
    }

    @Override
    public synchronized long insert(Tour o) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, o.getName());
        values.put(KEY_STATUS, o.isActive());
        values.put(KEY_END, o.getEnd());
        values.put(KEY_START, o.getStart());
        values.put(KEY_SPEED, o.getSpeed());
        values.put(KEY_TOPSPEED, o.getTopspeed());
        values.put(KEY_DETAILS, o.getDetails());
        values.put(KEY_USER_ID, o.getUserId());
        o.setId(database.insert(TABLE_NAME, null, values));
        return o.getId();
    }

    public synchronized long update(Tour o) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, o.getName());
        values.put(KEY_STATUS, o.isActive());
        values.put(KEY_END, o.getEnd());
        values.put(KEY_START, o.getStart());
        values.put(KEY_SPEED, o.getSpeed());
        values.put(KEY_TOPSPEED, o.getTopspeed());
        values.put(KEY_DETAILS, o.getDetails());
        values.put(KEY_USER_ID, o.getUserId());
        database.update(TABLE_NAME, values, KEY_ID +"=?",o.getId());
        return o.getId();
    }

    @Override
    public Tour getById(long id) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ KEY_ID +" =?;",
                new String[]{String.valueOf(id)});
        if (cursor.getCount() > 0)
            return cursorToItem(cursor,0);
        else
            return null;
    }

    public List<Tour> getByUserId(long id) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ UserHelper.KEY_ID +" =?;",
                new String[]{String.valueOf(id)});
        ArrayList<Tour> result = new ArrayList<Tour>();
        for(int i =0 ; i < cursor.getCount(); i++) {
            result.add(cursorToItem(cursor, i));
        }
        return result;
    }

    @Override
    protected Tour cursorToItem(Cursor cursor, int position) {
        Tour tour = new Tour();
        cursor.moveToPosition(position);
        tour.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
        tour.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
        tour.setActive(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(KEY_STATUS))));
        tour.setEnd(cursor.getString(cursor.getColumnIndex(KEY_END)));
        tour.setStart(cursor.getString(cursor.getColumnIndex(KEY_START)));
        tour.setSpeed(Float.parseFloat(cursor.getString(cursor.getColumnIndex(KEY_SPEED))));
        tour.setTopspeed(Float.parseFloat(cursor.getString(cursor.getColumnIndex(KEY_TOPSPEED))));
        tour.setDetails(cursor.getString(cursor.getColumnIndex(KEY_DETAILS)));
        tour.setLocations(lh.getByTourId(tour.getId()));
        return tour;
    }

    public synchronized static TourHelper getInstance() {
        if (instance == null)
            instance = new TourHelper();
        return instance;
    }
}
