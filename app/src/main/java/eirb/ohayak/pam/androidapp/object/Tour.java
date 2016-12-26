package eirb.ohayak.pam.androidapp.object;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.maps.model.LatLng;

import java.util.*;

/**
 * Created by mrhyk on 16/12/2016.
 */
public class Tour implements Parcelable, Comparable<Tour> {
    private boolean active;
    private long id;
    private long userId;
    private String name;
    private String start;
    private String end;
    private String details;
    private float speed;
    private float topspeed;
    private List<LatLng> locations;

    public Tour(){}

    public Tour(boolean active, long id, long userId, String name, String start, String end, String details, float speed, float topspeed, List<LatLng> locations) {
        this.active = active;
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.start = start;
        this.end = end;
        this.details = details;
        this.speed = speed;
        this.topspeed = topspeed;
        this.locations = locations;
    }

    protected Tour(Parcel in) {
        active = in.readByte() != 0;
        id = in.readLong();
        userId = in.readLong();
        name = in.readString();
        start = in.readString();
        end = in.readString();
        details = in.readString();
        speed = in.readFloat();
        topspeed = in.readFloat();
        locations = in.createTypedArrayList(LatLng.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (active ? 1 : 0));
        dest.writeLong(id);
        dest.writeLong(userId);
        dest.writeString(name);
        dest.writeString(start);
        dest.writeString(end);
        dest.writeString(details);
        dest.writeFloat(speed);
        dest.writeFloat(topspeed);
        dest.writeTypedList(locations);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Tour> CREATOR = new Creator<Tour>() {
        @Override
        public Tour createFromParcel(Parcel in) {
            return new Tour(in);
        }

        @Override
        public Tour[] newArray(int size) {
            return new Tour[size];
        }
    };

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LatLng> getLocations() {
        return locations;
    }

    public void setLocations(List<LatLng> locations) {
        this.locations = (List<LatLng>) locations;
    }

    public String getDetails() {
        return details;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getTopspeed() {
        return topspeed;
    }

    public void setTopspeed(float topspeed) {
        this.topspeed = topspeed;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void addLocation(LatLng entry) {
        locations.add(entry);
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public int compareTo(Tour tour) {
        return Long.compare(tour.getId(),id);
    }
}
