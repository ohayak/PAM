package eirb.ohayak.pam.androidapp.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;
import eirb.ohayak.pam.androidapp.*;
import eirb.ohayak.pam.androidapp.helper.TourHelper;
import eirb.ohayak.pam.androidapp.object.Tour;
import eirb.ohayak.pam.androidapp.object.TourExpandableListAdapter;
import eirb.ohayak.pam.androidapp.object.User;
import eirb.ohayak.pam.androidapp.service.LocationService;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST = 0;
    private static final int CREATE_TOUR = 0;
    private static final String TAG = "MainActivity";
    private FloatingActionButton newTour;
    private ExpandableListView expandableListViewActive;
    private ExpandableListView expandableListViewOld;
    private TourExpandableListAdapter expandableListAdapterActive;
    private TourExpandableListAdapter expandableListAdapterOld;
    private User curentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                    MY_PERMISSIONS_REQUEST);
        }

        newTour = (FloatingActionButton) findViewById(R.id.btn_new_tour);
        newTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tourIntent = new Intent(view.getContext(), TourActivity.class);
                Log.d(TAG,"starting TourActivity");
                startActivityForResult(tourIntent,CREATE_TOUR);
            }
        });

        expandableListViewActive = (ExpandableListView) findViewById(R.id.expandableListViewActive);
        expandableListViewOld = (ExpandableListView) findViewById(R.id.expandableListViewOld);

        expandableListAdapterActive = new TourExpandableListAdapter(this, null);
        expandableListAdapterOld = new TourExpandableListAdapter(this, null);

        expandableListViewActive.setAdapter(expandableListAdapterActive);
        expandableListViewOld.setAdapter(expandableListAdapterOld);

        loadUserData();

    }

    private void loadUserData() {
        Log.d(TAG, "loading user data");
        Intent calling = getIntent();
        curentUser = (User) calling.getParcelableExtra(LoginActivity.KEY_CONNECTED_USER);
        TextView welcome = (TextView) findViewById(R.id.txt_welcome);
        welcome.setText("Welcome "+ curentUser.getLastname());
        TourHelper th = TourHelper.getInstance();
        List<Tour> tours = th.getByUserId(curentUser.getId());
        for (Tour entry : tours) {
            if (entry.isActive())
                expandableListAdapterActive.addTour(entry);
            else
                expandableListAdapterOld.addTour(entry);
        }
        expandableListAdapterOld.notifyDataSetChanged();
        expandableListAdapterActive.notifyDataSetChanged();
        Intent intent = new Intent(getApplicationContext(), LocationService.class);
        intent.putExtra(LoginActivity.KEY_CONNECTED_USER, curentUser);
        Log.d(TAG, "starting location service");
        startService(intent);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == CREATE_TOUR) {
            Toast.makeText(getApplicationContext(),
                    "Tour created", Toast.LENGTH_SHORT).show();
            expandableListAdapterActive.addTour((Tour) data.getParcelableExtra("newTour"));
            expandableListAdapterActive.notifyDataSetChanged();
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

}
