package eirb.ohayak.pam.androidapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class LocationService extends Service {
    private static LocationManager locationMgr = null;
    private static List<Tour> activeTours = new ArrayList<Tour>();
    private static LocationHelper locationHelper = LocationHelper.getInstance();
    private static TourHelper tourHelper = TourHelper.getInstance();
    private static LocationListener onLocationChange;
    private static LocationService instance = null;
    public static final int ADD_TOUR =0;
    public static final int INIT_LIST_TOUR =1;
    public static final int END_TOUR =2;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        onLocationChange = new LocationListener() {
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }

            @Override
            public void onLocationChanged(Location location) {
                for (Tour entry : activeTours) {
                    locationHelper.insertWithId(new LatLng(location.getLatitude(), location.getLongitude()), entry.getId());
                }
            }
        };
        locationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000, 1, onLocationChange);
        locationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 1, onLocationChange);
        instance = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int request = intent.getIntExtra("request",-1);
        if (request == ADD_TOUR) {
            Tour tour = intent.getParcelableExtra("new_tour");
            activeTours.add(tour);
            return START_NOT_STICKY;
        }

        if (request == INIT_LIST_TOUR) {
            User user = intent.getParcelableExtra(LoginActivity.KEY_CONNECTED_USER);
            activeTours = tourHelper.getByUserId(user.getId());
            return START_NOT_STICKY;
        }

        if (request == END_TOUR) {
            Tour tour = intent.getParcelableExtra("new_tour");
            activeTours.remove(tour);
            return START_NOT_STICKY;
        }
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationHelper.close();
        locationMgr.removeUpdates(onLocationChange);
    }

}
