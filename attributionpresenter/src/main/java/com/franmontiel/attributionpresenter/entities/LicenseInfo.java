/*
 * Copyright (c)  2017  Francisco José Montiel Navarro.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.franmontiel.attributionpresenter.entities;

import android.os.Parcel;
import android.os.Parcelable;

public final class LicenseInfo implements Parcelable {

    private String name;
    private String text;
    private String textUrl;

    public LicenseInfo(String name, String text, String textUrl) {
        this.name = name;
        this.text = text;
        this.textUrl = textUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextUrl() {
        return textUrl;
    }

    public void setTextUrl(String textUrl) {
        this.textUrl = textUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LicenseInfo)) return false;

        LicenseInfo that = (LicenseInfo) o;

        if (!name.equals(that.name)) return false;
        return textUrl.equals(that.textUrl);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + textUrl.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "LicenseInfo{" +
                "name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", textUrl='" + textUrl + '\'' +
                '}';
    }

    //parcelable methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(text);
        dest.writeString(textUrl);
    }

    // Creator
    public static final Creator CREATOR = new Creator() {
        public LicenseInfo createFromParcel(Parcel in) {
            return new LicenseInfo(in);
        }

        public LicenseInfo[] newArray(int size) {
            return new LicenseInfo[size];
        }
    };

    // "De-parcel object
    public LicenseInfo(Parcel in) {
        this.name = in.readString();
        this.text = in.readString();
        this.textUrl = in.readString();
    }
}
