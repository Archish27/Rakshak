package com.markdevelopers.rakshak.data.remote.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Archish on 1/29/2017.
 */

public class Ngo implements Parcelable {
    int id;
    String icon,ngoname,city,state;

    public Ngo(int id, String icon, String ngoname, String city, String state) {
        this.id = id;
        this.icon = icon;
        this.ngoname = ngoname;
        this.city = city;
        this.state = state;
    }

    protected Ngo(Parcel in) {
        id = in.readInt();
        icon = in.readString();
        ngoname = in.readString();
        city = in.readString();
        state = in.readString();
    }

    public static final Creator<Ngo> CREATOR = new Creator<Ngo>() {
        @Override
        public Ngo createFromParcel(Parcel in) {
            return new Ngo(in);
        }

        @Override
        public Ngo[] newArray(int size) {
            return new Ngo[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNgoname() {
        return ngoname;
    }

    public void setNgoname(String ngoname) {
        this.ngoname = ngoname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(icon);
        parcel.writeString(ngoname);
        parcel.writeString(city);
        parcel.writeString(state);
    }
}
