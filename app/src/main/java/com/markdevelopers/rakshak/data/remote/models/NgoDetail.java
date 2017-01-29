package com.markdevelopers.rakshak.data.remote.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Archish on 1/29/2017.
 */

public class NgoDetail implements Parcelable {
    ArrayList<Data> data;

    public NgoDetail(ArrayList<Data> data) {
        this.data = data;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    protected NgoDetail(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NgoDetail> CREATOR = new Creator<NgoDetail>() {
        @Override
        public NgoDetail createFromParcel(Parcel in) {
            return new NgoDetail(in);
        }

        @Override
        public NgoDetail[] newArray(int size) {
            return new NgoDetail[size];
        }
    };
}
