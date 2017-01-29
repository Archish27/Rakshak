package com.markdevelopers.rakshak.data.remote.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Archish on 1/29/2017.
 */

public class Updates implements Parcelable {
    int id;
    String title;
    String description;
    String image;
    String postedby;

    public Updates(int id, String title, String description, String image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
    }

    protected Updates(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        image = in.readString();
    }

    public static final Creator<Updates> CREATOR = new Creator<Updates>() {
        @Override
        public Updates createFromParcel(Parcel in) {
            return new Updates(in);
        }

        @Override
        public Updates[] newArray(int size) {
            return new Updates[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(image);
    }
}
