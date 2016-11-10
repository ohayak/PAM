package eirb.ohayak.pam.androidapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST = 0;
    private Switch locationSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationSwitch = (Switch) findViewById(R.id.location_switch);
        locationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locationSwitch.isChecked()) {
                    start(view);
                } else {
                    stop(view);
                }
            }
        });


        Button get = (Button) findViewById(R.id.get_count);
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get(view);
            }
        });

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                    MY_PERMISSIONS_REQUEST);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted,

                } else {
                    // permission denied,
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                            MY_PERMISSIONS_REQUEST);
                }
            }
        }
    }

    public void start(View view) {
        startService(new Intent(MainActivity.this, LocationService.class));
    }

    public void stop(View view) {
        stopService(new Intent(MainActivity.this, LocationService.class));
    }

    public void get(View view) {
        if (locationSwitch.isChecked()) {
            Location location = LocationService.getLocation();
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();
            Toast.makeText(getBaseContext(),
                    "Voici les coordonnées de votre téléphone : " + latitude + " " + longitude,
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getBaseContext(),"Service not started",Toast.LENGTH_SHORT).show();
        }

    }


}
