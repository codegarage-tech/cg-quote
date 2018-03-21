package tech.codegarage.quotes.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

public class QuoteOfTheDay implements Parcelable {

    private AppDataBuilder appDataBuilder;
    private AppDataBuilder.QuoteBuilder quoteBuilder;
    private String today = "";

    public QuoteOfTheDay(AppDataBuilder appDataBuilder, AppDataBuilder.QuoteBuilder quoteBuilder, String today) {
        this.appDataBuilder = appDataBuilder;
        this.quoteBuilder = quoteBuilder;
        this.today = today;
    }

    public AppDataBuilder getAppDataBuilder() {
        return appDataBuilder;
    }

    public void setAppDataBuilder(AppDataBuilder appDataBuilder) {
        this.appDataBuilder = appDataBuilder;
    }

    public AppDataBuilder.QuoteBuilder getQuoteBuilder() {
        return quoteBuilder;
    }

    public void setQuoteBuilder(AppDataBuilder.QuoteBuilder quoteBuilder) {
        this.quoteBuilder = quoteBuilder;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    @Override
    public String toString() {
        return "{" +
                "appDataBuilder=" + appDataBuilder +
                ", quoteBuilder=" + quoteBuilder +
                ", today=" + today +
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
        dest.writeParcelable(appDataBuilder, flags);
//        dest.writeParcelable(litePalLanguage, flags);
//        dest.writeParcelable(litePalAuthor, flags);
        dest.writeParcelable(quoteBuilder, flags);
        dest.writeString(today);
    }

    // Creator
    public static final Creator CREATOR
            = new Creator() {
        public QuoteOfTheDay createFromParcel(Parcel in) {
            return new QuoteOfTheDay(in);
        }

        public QuoteOfTheDay[] newArray(int size) {
            return new QuoteOfTheDay[size];
        }
    };

    // "De-parcel object
    public QuoteOfTheDay(Parcel in) {
        this.appDataBuilder = in.readParcelable(AppDataBuilder.class.getClassLoader());
//        this.litePalLanguage = in.readParcelable(Language.class.getClassLoader());
//        this.litePalAuthor = in.readParcelable(Author.class.getClassLoader());
        this.quoteBuilder = in.readParcelable(AppDataBuilder.QuoteBuilder.class.getClassLoader());
        this.today = in.readString();
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