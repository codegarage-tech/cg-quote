package tech.codegarage.quotes.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MappedQuote implements Parcelable {

    private Author author;
    private Language language;
    private ArrayList<Quote> quotes = new ArrayList<Quote>();
    private boolean isMappedQuote = false;

    public MappedQuote(Author author, Language language, ArrayList<Quote> quotes, boolean isMappedQuote) {
        this.author = author;
        this.language = language;
        this.quotes = quotes;
        this.isMappedQuote = isMappedQuote;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public ArrayList<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(ArrayList<Quote> quotes) {
        this.quotes = quotes;
    }

    public boolean isMappedQuote() {
        return isMappedQuote;
    }

    public void setMappedQuote(boolean mappedQuote) {
        isMappedQuote = mappedQuote;
    }

    @Override
    public String toString() {
        return "{" +
                "author=" + author +
                ", language=" + language +
                ", quotes=" + quotes +
                ", isMappedQuote=" + isMappedQuote +
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
        dest.writeParcelable(author, flags);
        dest.writeParcelable(language, flags);
        dest.writeList(quotes);
        dest.writeInt(isMappedQuote ? 1 : 0);
    }

    // Creator
    public static final Creator CREATOR = new Creator() {
        public MappedQuote createFromParcel(Parcel in) {
            return new MappedQuote(in);
        }

        public MappedQuote[] newArray(int size) {
            return new MappedQuote[size];
        }
    };

    // "De-parcel object
    public MappedQuote(Parcel in) {
        this.author = in.readParcelable(Author.class.getClassLoader());
        this.language = in.readParcelable(Language.class.getClassLoader());
        this.quotes = in.readArrayList(Quote.class.getClassLoader());
        isMappedQuote = (in.readInt() == 0) ? false : true;
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