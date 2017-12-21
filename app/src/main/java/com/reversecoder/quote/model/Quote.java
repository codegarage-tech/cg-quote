package com.reversecoder.quote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.orm.SugarRecord;
import com.orm.annotation.Ignore;
import com.orm.annotation.Unique;

public class Quote extends SugarRecord implements Parcelable {

    @Unique
    private String quoteDescription = "";
    private boolean isFavourite = false;
    private boolean isQuote = false;
    private Language language;
    private Author author;

    @Ignore
    private boolean isHover = false;

    public Quote() {
    }

    public Quote(String quoteDescription, boolean isFavourite, boolean isQuote, Language language, Author author) {
        this.quoteDescription = quoteDescription;
        this.isFavourite = isFavourite;
        this.isQuote = isQuote;
        this.language = language;
        this.author = author;
    }

    public String getQuoteDescription() {
        return quoteDescription;
    }

    public void setQuoteDescription(String quoteDescription) {
        this.quoteDescription = quoteDescription;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public boolean isQuote() {
        return isQuote;
    }

    public void setQuote(boolean quote) {
        isQuote = quote;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public boolean isHover() {
        return isHover;
    }

    public void setHover(boolean hover) {
        isHover = hover;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + getId() +
                ", quoteDescription=" + quoteDescription +
                ", isFavourite=" + isFavourite +
                ", isQuote=" + isQuote +
                ", language=" + language +
                ", author=" + author +
                ", isHover=" + isHover +
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
        dest.writeString(quoteDescription);
        dest.writeInt(isFavourite ? 1 : 0);
        dest.writeInt(isQuote ? 1 : 0);
        dest.writeParcelable(language, flags);
        dest.writeParcelable(author, flags);
        dest.writeInt(isHover ? 1 : 0);
    }

    // Creator
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public Quote createFromParcel(Parcel in) {
            return new Quote(in);
        }

        public Quote[] newArray(int size) {
            return new Quote[size];
        }
    };

    // "De-parcel object
    public Quote(Parcel in) {
        setId(in.readLong());
        this.quoteDescription = in.readString();
        this.isFavourite = (in.readInt() == 0) ? false : true;
        this.isQuote = (in.readInt() == 0) ? false : true;
        this.language = in.readParcelable(Language.class.getClassLoader());
        this.author = in.readParcelable(Author.class.getClassLoader());
        this.isHover = (in.readInt() == 0) ? false : true;
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