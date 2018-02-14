package com.reversecoder.gcm.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class RegisterApp implements Parcelable {

    private String id = "";
    private String push_id = "";
    private String unique_id = "";

    public RegisterApp() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPush_id() {
        return push_id;
    }

    public void setPush_id(String push_id) {
        this.push_id = push_id;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", push_id='" + push_id + '\'' +
                ", unique_id='" + unique_id + '\'' +
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
        dest.writeString(id);
        dest.writeString(push_id);
        dest.writeString(unique_id);
    }

    // Creator
    public static final Creator CREATOR
            = new Creator() {
        public RegisterApp createFromParcel(Parcel in) {
            return new RegisterApp(in);
        }

        public RegisterApp[] newArray(int size) {
            return new RegisterApp[size];
        }
    };

    // "De-parcel object
    public RegisterApp(Parcel in) {
        id = in.readString();
        push_id = in.readString();
        unique_id = in.readString();
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