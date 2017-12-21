package com.reversecoder.attributionpresenter.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that contain information needed to comply with library licenses.
 */
public final class Attribution implements Parcelable{

    private String attributionName = "";
    private String attributionWebsite = "";
    private List<LicenseInfo> licensesInfo;

    public Attribution(String attributionName, String attributionWebsite, List<LicenseInfo> licensesInfo) {
        this.attributionName = attributionName;
        this.attributionWebsite = attributionWebsite;
        this.licensesInfo = licensesInfo;
    }

    public String getAttributionName() {
        return attributionName;
    }

    public void setAttributionName(String attributionName) {
        this.attributionName = attributionName;
    }

    public String getAttributionWebsite() {
        return attributionWebsite;
    }

    public void setAttributionWebsite(String attributionWebsite) {
        this.attributionWebsite = attributionWebsite;
    }

    public List<LicenseInfo> getLicensesInfo() {
        return licensesInfo;
    }

    public void setLicensesInfo(List<LicenseInfo> licensesInfo) {
        this.licensesInfo = licensesInfo;
    }

    @Override
    public String toString() {
        return "Attribution{" +
                ", attributionName='" + attributionName + '\'' +
                ", attributionWebsite='" + attributionWebsite + '\'' +
                ", licensesInfo=" + licensesInfo +
                '}';
    }

    public static class Builder {
        private String name = "";
        private String website = "";
        private List<LicenseInfo> licenseInfos;

        public Builder(String attributionName, String attributionWebsite) {
            this.name = attributionName;
            this.website = attributionWebsite;
            this.licenseInfos = new ArrayList<>();
        }

        public Builder addLicenseInfo(LicenseInfo licenseInfo) {
            licenseInfos.add(licenseInfo);
            return this;
        }

        public Attribution build() {
            return new Attribution(name, website, licenseInfos);
        }
    }

    //parcelable methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(attributionName);
        dest.writeString(attributionWebsite);
        dest.writeList(licensesInfo);
    }

    // Creator
    public static final Creator CREATOR
            = new Creator() {
        public Attribution createFromParcel(Parcel in) {
            return new Attribution(in);
        }

        public Attribution[] newArray(int size) {
            return new Attribution[size];
        }
    };

    // "De-parcel object
    public Attribution(Parcel in) {
        attributionName = in.readString();
        attributionWebsite = in.readString();
        licensesInfo = in.readArrayList(LicenseInfo.class.getClassLoader());
    }
}