package com.reversecoder.quote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.annotation.Unique;

public class FoldableQuote implements Parcelable {

    private String quoteDescription = "";
    private boolean isFavourite = false;
    private boolean isQuote = false;
    private Language language;
    private Author author;
    private int imageId;

    public FoldableQuote() {
    }

    public FoldableQuote(String quoteDescription, boolean isFavourite, boolean isQuote, Language language, Author author, int imageId) {
        this.quoteDescription = quoteDescription;
        this.isFavourite = isFavourite;
        this.isQuote = isQuote;
        this.language = language;
        this.author = author;
        this.imageId = imageId;
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

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public static Creator getCREATOR() {
        return CREATOR;
    }

    @Override
    public String toString() {
        return "FoldableQuote{" +
                "quoteDescription='" + quoteDescription + '\'' +
                ", isFavourite=" + isFavourite +
                ", isQuote=" + isQuote +
                ", language=" + language +
                ", author=" + author +
                ", imageId=" + imageId +
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
        dest.writeInt(imageId);
    }

    // Creator
    public static final Creator CREATOR
            = new Creator() {
        public FoldableQuote createFromParcel(Parcel in) {
            return new FoldableQuote(in);
        }

        public FoldableQuote[] newArray(int size) {
            return new FoldableQuote[size];
        }
    };

    // "De-parcel object
    public FoldableQuote(Parcel in) {
        quoteDescription = in.readString();
        isFavourite = (in.readInt() == 0) ? false : true;
        isQuote = (in.readInt() == 0) ? false : true;
        this.language = in.readParcelable(Language.class.getClassLoader());
        this.author = in.readParcelable(Author.class.getClassLoader());
        imageId = in.readInt();
    }
}