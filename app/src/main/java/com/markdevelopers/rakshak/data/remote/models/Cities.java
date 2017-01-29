package com.markdevelopers.rakshak.data.remote.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Archish on 1/28/2017.
 */

public class Cities implements Parcelable {
    String name;

    public Cities(String name) {
        this.name = name;
    }

    protected Cities(Parcel in) {
        name = in.readString();
    }

    public static final Creator<Cities> CREATOR = new Creator<Cities>() {
        @Override
        public Cities createFromParcel(Parcel in) {
            return new Cities(in);
        }

        @Override
        public Cities[] newArray(int size) {
            return new Cities[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
    }
}
