package com.reversecoder.quote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;
import com.orm.annotation.Table;
import com.orm.annotation.Unique;

public class Language  extends SugarRecord implements Parcelable {

    @Unique
    private String languageName = "";

    public Language() {
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
        return "Language{" +
                "languageName='" + languageName + '\'' +
                '}';
    }

    //parcelable methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(languageName);
    }

    // Creator
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public Language createFromParcel(Parcel in) {
            return new Language(in);
        }

        public Language[] newArray(int size) {
            return new Language[size];
        }
    };

    // "De-parcel object
    public Language(Parcel in) {
        languageName = in.readString();
    }
}
