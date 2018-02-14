package com.reversecoder.gcm.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class GcmData implements Parcelable {

    private int id = 420;
    private String title = "";
    private String subtitle = "";
    private String message = "";
    private String tickerText = "";
    private String from = "";
    private String sound = "";
    private String vibrate = "";

    public GcmData() {
    }

    public GcmData(String title, String subtitle, String message, String tickerText, String from, String sound, String vibrate) {
        this.title = title;
        this.subtitle = subtitle;
        this.message = message;
        this.tickerText = tickerText;
        this.from = from;
        this.sound = sound;
        this.vibrate = vibrate;
    }

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

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTickerText() {
        return tickerText;
    }

    public void setTickerText(String tickerText) {
        this.tickerText = tickerText;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getVibrate() {
        return vibrate;
    }

    public void setVibrate(String vibrate) {
        this.vibrate = vibrate;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", message='" + message + '\'' +
                ", tickerText='" + tickerText + '\'' +
                ", from='" + from + '\'' +
                ", sound='" + sound + '\'' +
                ", vibrate='" + vibrate + '\'' +
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
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(subtitle);
        dest.writeString(message);
        dest.writeString(tickerText);
        dest.writeString(from);
        dest.writeString(sound);
        dest.writeString(vibrate);
    }

    // Creator
    public static final Creator CREATOR
            = new Creator() {
        public GcmData createFromParcel(Parcel in) {
            return new GcmData(in);
        }

        public GcmData[] newArray(int size) {
            return new GcmData[size];
        }
    };

    // "De-parcel object
    public GcmData(Parcel in) {
        id = in.readInt();
        title = in.readString();
        subtitle = in.readString();
        message = in.readString();
        tickerText = in.readString();
        from = in.readString();
        sound = in.readString();
        vibrate = in.readString();
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