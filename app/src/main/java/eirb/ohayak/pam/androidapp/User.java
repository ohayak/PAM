package eirb.ohayak.pam.androidapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mrhyk on 21/12/2016.
 */
public class User implements Parcelable {
    private long id;
    private String email;
    private String pwd;
    private String firstname;
    private String lastname;

    public User() {
    }

    public User(long id, String email, String pwd, String firstname, String lastname) {
        this.id = id;
        this.email = email;
        this.pwd = pwd;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    protected User(Parcel in) {
        id = in.readLong();
        email = in.readString();
        pwd = in.readString();
        firstname = in.readString();
        lastname = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(email);
        parcel.writeString(pwd);
        parcel.writeString(firstname);
        parcel.writeString(lastname);
    }
}
