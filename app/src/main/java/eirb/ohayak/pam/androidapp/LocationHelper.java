package eirb.ohayak.pam.androidapp;

import android.content.ContentValues;
import android.database.Cursor;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrhyk on 22/12/2016.
 */
public class LocationHelper extends TableHelper<LatLng> {
    public static final String TABLE_NAME = "locations";
    public static final String KEY_ID = "locationid";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";
    private static LocationHelper instance = null;

    public LocationHelper() {
        super();
        open();
    }

    @Override
    public long insert(LatLng o) {
        return 0;
    }

    public long insertWithId(LatLng o, long id) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, id);
        values.put(KEY_LATITUDE, o.latitude);
        values.put(KEY_LONGITUDE, o.longitude);
        return database.insert(TABLE_NAME, null, values);
    }

    @Override
    public LatLng getById(long id) {
        return null;
    }


    public List<LatLng> getByTourId(long id) {
        Cursor cursor = database.rawQuery("SELECT * FROM" + TABLE_NAME + "WHERE "+ KEY_ID +"=?",
                new String[]{String.valueOf(id)});
        List<LatLng> result = null;
        if (cursor.getCount() > 0) {
            result = new ArrayList<LatLng>();
            for (int i = 0; i < cursor.getCount(); i++) {
                result.add(cursorToItem(cursor, i));
            }
        }
        return result;
    }

    @Override
    protected LatLng cursorToItem(Cursor cursor, int position) {
        LatLng location = null;
        if (cursor.getCount()-1 <= position) {
            cursor.moveToPosition(position);
            long lat = (Long.parseLong(cursor.getString(cursor.getColumnIndex(KEY_LATITUDE))));
            long lnt = (Long.parseLong(cursor.getString(cursor.getColumnIndex(KEY_LONGITUDE))));
            location = new LatLng(lat, lnt);
        }
        return location;
    }

    public synchronized static LocationHelper getInstance() {
        if (instance ==null) {
            instance = new LocationHelper();
        }
        return instance;
    }
}
