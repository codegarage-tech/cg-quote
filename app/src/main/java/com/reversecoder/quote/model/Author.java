package com.reversecoder.quote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;
import com.orm.annotation.Unique;

public class Author extends SugarRecord implements Parcelable {

    @Unique
    private String authorName = "";
    private String birthDate = "";
    private String deathDate = "";
    private String occupation = "";
    private String nationality = "";
    private int profileImage = -1;
    private boolean isAuthor = true;

    public Author() {
    }

    public Author(String authorName, String birthDate, String deathDate, String occupation, String nationality, int profileImage, boolean isAuthor) {
        this.authorName = authorName;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.occupation = occupation;
        this.nationality = nationality;
        this.profileImage = profileImage;
        this.isAuthor = isAuthor;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }

    public boolean isAuthor() {
        return isAuthor;
    }

    public void setAuthor(boolean author) {
        isAuthor = author;
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorName='" + authorName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", deathDate='" + deathDate + '\'' +
                ", occupation='" + occupation + '\'' +
                ", nationality='" + nationality + '\'' +
                ", profileImage=" + profileImage +
                ", isAuthor=" + isAuthor +
                '}';
    }

    //parcelable methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(authorName);
        dest.writeString(birthDate);
        dest.writeString(deathDate);
        dest.writeString(occupation);
        dest.writeString(nationality);
        dest.writeInt(profileImage);
        dest.writeInt(isAuthor ? 1 : 0);
    }

    // Creator
    public static final Creator CREATOR
            = new Creator() {
        public Author createFromParcel(Parcel in) {
            return new Author(in);
        }

        public Author[] newArray(int size) {
            return new Author[size];
        }
    };

    // "De-parcel object
    public Author(Parcel in) {
        authorName = in.readString();
        birthDate = in.readString();
        deathDate = in.readString();
        occupation = in.readString();
        nationality = in.readString();
        profileImage = in.readInt();
        isAuthor = (in.readInt() == 0) ? false : true;
    }
}