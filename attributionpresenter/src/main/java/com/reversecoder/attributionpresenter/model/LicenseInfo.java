package com.reversecoder.attributionpresenter.model;

import android.os.Parcel;
import android.os.Parcelable;

public final class LicenseInfo implements Parcelable {

    private String copyrightNotice;
    private License license;

    public LicenseInfo(String copyrightNotice, License license) {
        this.copyrightNotice = copyrightNotice;
        this.license = license;
    }

    public String getCopyrightNotice() {
        return copyrightNotice;
    }

    public void setCopyrightNotice(String copyrightNotice) {
        this.copyrightNotice = copyrightNotice;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    @Override
    public String toString() {
        return "LicenseInfo{" +
                "copyrightNotice='" + copyrightNotice + '\'' +
                ", license=" + license + '\'' +
                '}';
    }

    //parcelable methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(copyrightNotice);
        dest.writeInt(this.license == null ? -1 : this.license.ordinal());
    }

    // Creator
    public static final Creator CREATOR
            = new Creator() {
        public LicenseInfo createFromParcel(Parcel in) {
            return new LicenseInfo(in);
        }

        public LicenseInfo[] newArray(int size) {
            return new LicenseInfo[size];
        }
    };

    // "De-parcel object
    public LicenseInfo(Parcel in) {
        copyrightNotice = in.readString();
        int tmpLicense = in.readInt();
        license = tmpLicense == -1 ? null : License.values()[tmpLicense];
    }
}
