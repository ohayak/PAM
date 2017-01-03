package eirb.ohayak.pam.androidapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import eirb.ohayak.pam.androidapp.R;
import eirb.ohayak.pam.androidapp.helper.LocationHelper;
import eirb.ohayak.pam.androidapp.object.Tour;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Location> points;
    private Polyline line;
    private LocationHelper lh = LocationHelper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent intent = getIntent();
        Tour tour = intent.getParcelableExtra("tour");
        tour.setLocations(lh.getByTourId(tour.getId()));
        if (tour.getLocations() == null)
            points = new ArrayList<>(0);
        else
            points = tour.getLocations();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.clear();  //clears all Markers and Polylines

        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        for (int i = 0; i < points.size(); i++) {
            Location point = points.get(i);
            LatLng ll = new LatLng(point.getLatitude(), point.getLongitude());
            options.add(ll);
        }
        googleMap.addPolyline(options); //add Polyline
        if (points.size() > 0) {
            Location point = points.get(0);
            LatLng ll = new LatLng(point.getLatitude(), point.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(ll));
        }

    }
}
