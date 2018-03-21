package tech.codegarage.quotes.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

public class Quote extends DataSupport implements Parcelable {

    private long id;
    @Column(unique = true)
    private String quoteDescription = "";
    private boolean isFavourite = false;
    private boolean isQuote = false;

    public Quote() {
    }

    public Quote(String quoteDescription, boolean isFavourite, boolean isQuote) {
        this.quoteDescription = quoteDescription;
        this.isFavourite = isFavourite;
        this.isQuote = isQuote;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "{" +
                "id=" + getId() +
                ", quoteDescription=" + quoteDescription +
                ", isFavourite=" + isFavourite +
                ", isQuote=" + isQuote +
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
    }

    // Creator
    public static final Creator CREATOR
            = new Creator() {
        public Quote createFromParcel(Parcel in) {
            return new Quote(in);
        }

        public Quote[] newArray(int size) {
            return new Quote[size];
        }
    };

    // "De-parcel object
    public Quote(Parcel in) {
        id = in.readLong();
        this.quoteDescription = in.readString();
        this.isFavourite = (in.readInt() == 0) ? false : true;
        this.isQuote = (in.readInt() == 0) ? false : true;
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