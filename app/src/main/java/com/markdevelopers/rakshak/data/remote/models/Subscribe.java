package com.markdevelopers.rakshak.data.remote.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Archish on 1/28/2017.
 */

public class Subscribe implements Parcelable {
    int id;
    String name,description;
    boolean isSubscribed;

    public Subscribe(int id, String name, String description, boolean isSubscribed) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isSubscribed = isSubscribed;
    }

    protected Subscribe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        isSubscribed = in.readByte() != 0;
    }

    public static final Creator<Subscribe> CREATOR = new Creator<Subscribe>() {
        @Override
        public Subscribe createFromParcel(Parcel in) {
            return new Subscribe(in);
        }

        @Override
        public Subscribe[] newArray(int size) {
            return new Subscribe[size];
        }
    };

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

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeByte((byte) (isSubscribed ? 1 : 0));
    }
}
