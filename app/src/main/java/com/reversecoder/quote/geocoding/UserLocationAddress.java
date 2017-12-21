package com.reversecoder.quote.geocoding;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

public class UserLocationAddress implements Parcelable {

    private double latitude = 0;
    private double longitude = 0;
    private String streetAddress = "";
    private String city = "";
    private String state = "";
    private String country = "";
    private String countryCode = "";
    private String postalCode = "";
    private String knownName = "";

    public UserLocationAddress() {
    }

    public UserLocationAddress(double latitude, double longitude, String streetAddress, String city, String state, String country, String countryCode, String postalCode, String knownName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.country = country;
        this.countryCode = countryCode;
        this.postalCode = postalCode;
        this.knownName = knownName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getKnownName() {
        return knownName;
    }

    public void setKnownName(String knownName) {
        this.knownName = knownName;
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
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(streetAddress);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(country);
        dest.writeString(countryCode);
        dest.writeString(postalCode);
        dest.writeString(knownName);
    }

    // Creator
    public static final Creator CREATOR
            = new Creator() {
        public UserLocationAddress createFromParcel(Parcel in) {
            return new UserLocationAddress(in);
        }

        public UserLocationAddress[] newArray(int size) {
            return new UserLocationAddress[size];
        }
    };

    // "De-parcel object
    public UserLocationAddress(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
        streetAddress = in.readString();
        city = in.readString();
        state = in.readString();
        country = in.readString();
        countryCode = in.readString();
        postalCode = in.readString();
        knownName = in.readString();
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