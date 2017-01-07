package eirb.ohayak.pam.androidapp.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import eirb.ohayak.pam.androidapp.activity.LoginActivity;
import eirb.ohayak.pam.androidapp.object.Tour;
import eirb.ohayak.pam.androidapp.helper.TourHelper;
import eirb.ohayak.pam.androidapp.object.User;
import eirb.ohayak.pam.androidapp.helper.LocationHelper;

import java.util.ArrayList;
import java.util.List;

public class LocationService extends Service {
    private static final String TAG = "LocationService";
    private static LocationManager locationMgr = null;
    private static List<Tour> activeTours = new ArrayList<Tour>();
    private static LocationHelper locationHelper = LocationHelper.getInstance();
    private static TourHelper tourHelper = TourHelper.getInstance();
    private static LocationListener onLocationChange;
    private static LocationService instance = null;
    public static final int ADD_TOUR =0;
    public static final int INIT_LIST_TOUR =1;
    public static final int END_TOUR =2;

    private Location lastLocation;

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
                lastLocation = locationMgr.getLastKnownLocation(provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
            }

            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG, "location changed");
                for (Tour entry : activeTours) {
                    lastLocation = location;
                    float speed = location.getSpeed();
                    float distance = lastLocation.distanceTo(location);
                    entry.setSpeed(speed);
                    if (entry.getTopspeed() < speed )
                        entry.setTopspeed(speed);
                    entry.setDistance(entry.getDistance()+distance);
                    locationHelper.insertWithId(location, entry.getId());
                    tourHelper.update(entry);
                }
            }
        };
        locationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, onLocationChange);
        locationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, onLocationChange);
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
            activeTours = intent.getParcelableArrayListExtra("active_tours");
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
        locationMgr.removeUpdates(onLocationChange);
    }

}
