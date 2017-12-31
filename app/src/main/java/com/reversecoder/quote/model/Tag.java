package com.reversecoder.quote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.orm.SugarRecord;
import com.orm.annotation.Unique;

public class Tag extends SugarRecord implements Parcelable {

    @Unique
    private String tagName = "";

    public Tag() {
    }

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + getId() +
                ", tagName='" + tagName + '\'' +
                '}';
    }

    /**************************
     * Methods for parcelable *
     **************************/
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(getId());
        dest.writeString(tagName);
        ;
    }

    // Creator
    public static final Creator CREATOR
            = new Creator() {
        public Tag createFromParcel(Parcel in) {
            return new Tag(in);
        }

        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };

    // "De-parcel object
    public Tag(Parcel in) {
        setId(in.readLong());
        tagName = in.readString();
    }

    /**************************
     * Methods for convertion *
     **************************/
    public static <T> T convertFromStringToObject(String jsonString, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, clazz);
    }

    public static <T> String convertFromObjectToString(T object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}