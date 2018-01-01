package com.reversecoder.quote.model.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

public class LitePalLanguage extends DataSupport implements Parcelable {


    private long id;
    @Column(unique = true)
    private String languageName = "";

    public LitePalLanguage() {
    }

    public long getId() {
        return id;
    }

    public LitePalLanguage(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + getId() +
                ", languageName='" + languageName + '\'' +
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
        dest.writeString(languageName);
    }

    // Creator
    public static final Creator CREATOR
            = new Creator() {
        public LitePalLanguage createFromParcel(Parcel in) {
            return new LitePalLanguage(in);
        }

        public LitePalLanguage[] newArray(int size) {
            return new LitePalLanguage[size];
        }
    };

    // "De-parcel object
    public LitePalLanguage(Parcel in) {
        id = in.readLong();
        languageName = in.readString();
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
