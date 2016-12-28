package eirb.ohayak.pam.androidapp.helper;

import android.content.ContentValues;
import android.database.Cursor;
import android.location.Location;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrhyk on 22/12/2016.
 */
public class LocationHelper extends TableHelper<Location> {
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
    public long insert(Location o) {
        return 0;
    }

    public long insertWithId(Location o, long id) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, id);
        values.put(KEY_LATITUDE, o.getLatitude());
        values.put(KEY_LONGITUDE, o.getLongitude());
        database.insert(TABLE_NAME, null, values);
        return id;
    }

    @Override
    public Location getById(long id) {
        return null;
    }


    public List<Location> getByTourId(long id) {
        Cursor cursor = database.rawQuery("SELECT * FROM" + TABLE_NAME + "WHERE "+ KEY_ID +"=?",
                new String[]{String.valueOf(id)});
        List<Location> result = null;
        if (cursor.getCount() > 0) {
            result = new ArrayList<Location>();
            for (int i = 0; i < cursor.getCount(); i++) {
                result.add(cursorToItem(cursor, i));
            }
        }
        return result;
    }

    @Override
    protected Location cursorToItem(Cursor cursor, int position) {
        Location location = null;
        if (cursor.getCount()-1 <= position) {
            cursor.moveToPosition(position);
            location.setLatitude(Double.parseDouble(cursor.getString(cursor.getColumnIndex(KEY_LATITUDE))));
            location.setLongitude(Double.parseDouble(cursor.getString(cursor.getColumnIndex(KEY_LONGITUDE))));
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
