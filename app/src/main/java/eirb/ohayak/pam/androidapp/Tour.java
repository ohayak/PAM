package eirb.ohayak.pam.androidapp;

import android.location.Location;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by mrhyk on 16/12/2016.
 */
public class Tour implements Serializable {
    private boolean status;
    private String id;
    private String name;
    private String day;
    private String details;
    private List<LatLng> locations;

    public Tour(String name, boolean s) {
        status = s;
        id = UUID.randomUUID().toString();
        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy 'at' h:mm a");
        day = format.format(Calendar.getInstance().getTime());
        this.name = name;
        locations = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<LatLng> getLocations() {
        return locations;
    }

    public void setLocations(List<LatLng> locations) {
        this.locations = locations;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void addLocation(LatLng entry) {
        locations.add(entry);
    }

    public boolean isActive() {
        return status;
    }
}
