package com.markdevelopers.rakshak.data.remote.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Archish on 1/28/2017.
 */

public class NewsFeed implements Parcelable {
    int id;
    String name;
    String description;
    String location;
    String severity;
    String latitude, longitude;
    String starttime, endtime;
    boolean isSubscribed;

    public NewsFeed(int id, String name, String description, String location, String severity, String latitude, String longitude, String starttime, String endtime, boolean isSubscribed) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.severity = severity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.starttime = starttime;
        this.endtime = endtime;
        this.isSubscribed = isSubscribed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }

    protected NewsFeed(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        location = in.readString();
        severity = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        starttime = in.readString();
        endtime = in.readString();
        isSubscribed = in.readByte() != 0;
    }

    public static final Creator<NewsFeed> CREATOR = new Creator<NewsFeed>() {
        @Override
        public NewsFeed createFromParcel(Parcel in) {
            return new NewsFeed(in);
        }

        @Override
        public NewsFeed[] newArray(int size) {
            return new NewsFeed[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(location);
        parcel.writeString(severity);
        parcel.writeString(latitude);
        parcel.writeString(longitude);
        parcel.writeString(starttime);
        parcel.writeString(endtime);
        parcel.writeByte((byte) (isSubscribed ? 1 : 0));
    }
}
