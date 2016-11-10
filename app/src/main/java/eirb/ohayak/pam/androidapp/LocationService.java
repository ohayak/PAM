package eirb.ohayak.pam.androidapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicBoolean;

public class LocationService extends Service {
    private static LocationManager locationMgr = null;
    private LocationListener onLocationChange = new LocationListener() {
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
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            Toast.makeText(getBaseContext(),
                    "Vos coordonnées : \n"
                            + "Latitude = "+ latitude + "\n"
                            + "Longitude = " + longitude,
                    Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        locationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, onLocationChange);
        locationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, onLocationChange);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationMgr.removeUpdates(onLocationChange);
    }

    public static Location getLocation() {
        return locationMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

}
