package tech.codegarage.quotes.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

public class Language extends DataSupport implements Parcelable {


    private long id;
    @Column(unique = true)
    private String languageName = "";

    public Language() {
    }

    public long getId() {
        return id;
    }

    public Language(String languageName) {
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
        public Language createFromParcel(Parcel in) {
            return new Language(in);
        }

        public Language[] newArray(int size) {
            return new Language[size];
        }
    };

    // "De-parcel object
    public Language(Parcel in) {
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
