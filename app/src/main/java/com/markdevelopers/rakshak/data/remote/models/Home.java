package com.markdevelopers.rakshak.data.remote.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Archish on 1/29/2017.
 */

public class Home implements Parcelable {
    int hid;
    String htitle, hdesc, himage;

    public Home(int hid, String htitle, String hdesc, String himage) {
        this.hid = hid;
        this.htitle = htitle;
        this.hdesc = hdesc;
        this.himage = himage;
    }

    public int getHid() {
        return hid;
    }

    public void setHid(int hid) {
        this.hid = hid;
    }

    public String getHtitle() {
        return htitle;
    }

    public void setHtitle(String htitle) {
        this.htitle = htitle;
    }

    public String getHdesc() {
        return hdesc;
    }

    public void setHdesc(String hdesc) {
        this.hdesc = hdesc;
    }

    public String getHimage() {
        return himage;
    }

    public void setHimage(String himage) {
        this.himage = himage;
    }

    protected Home(Parcel in) {
        hid = in.readInt();
        htitle = in.readString();
        hdesc = in.readString();
        himage = in.readString();
    }

    public static final Creator<Home> CREATOR = new Creator<Home>() {
        @Override
        public Home createFromParcel(Parcel in) {
            return new Home(in);
        }

        @Override
        public Home[] newArray(int size) {
            return new Home[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(hid);
        parcel.writeString(htitle);
        parcel.writeString(hdesc);
        parcel.writeString(himage);
    }
}
