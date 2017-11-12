package com.reversecoder.quote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;
import com.orm.annotation.Unique;

public class Quote extends SugarRecord implements Parcelable {

    @Unique
    private String quoteDescription = "";
    private boolean isFavourite = false;
    private boolean isQuote = false;
    private Language language;
    private Author author;

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

    @Override
    public String toString() {
        return "Quote{" +
                "quoteDescription='" + quoteDescription + '\'' +
                ", isFavourite=" + isFavourite +
                ", isQuote=" + isQuote +
                ", language=" + language +
                ", author=" + author +
                '}';
    }

    //parcelable methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(quoteDescription);
        dest.writeInt(isFavourite ? 1 : 0);
        dest.writeInt(isQuote ? 1 : 0);
        dest.writeParcelable(language, flags);
        dest.writeParcelable(author, flags);
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
        quoteDescription = in.readString();
        isFavourite = (in.readInt() == 0) ? false : true;
        isQuote = (in.readInt() == 0) ? false : true;
        this.language = in.readParcelable(Language.class.getClassLoader());
        this.author = in.readParcelable(Author.class.getClassLoader());
    }
}