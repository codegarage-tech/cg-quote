package tech.codegarage.quotes.model.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

public class QuoteOfTheDay implements Parcelable {

    private LitePalDataBuilder litePalDataBuilder;
//    private LitePalLanguage litePalLanguage;
//    private LitePalAuthor litePalAuthor;
    private LitePalDataBuilder.LitePalQuoteBuilder litePalQuoteBuilder;
    private String today = "";

    public QuoteOfTheDay(LitePalDataBuilder litePalDataBuilder, LitePalDataBuilder.LitePalQuoteBuilder litePalQuoteBuilder, String today) {
//        this.litePalLanguage = litePalLanguage;
//        this.litePalAuthor = litePalAuthor;
        this.litePalDataBuilder = litePalDataBuilder;
        this.litePalQuoteBuilder = litePalQuoteBuilder;
        this.today = today;
    }

    public LitePalDataBuilder getLitePalDataBuilder() {
        return litePalDataBuilder;
    }

    public void setLitePalDataBuilder(LitePalDataBuilder litePalDataBuilder) {
        this.litePalDataBuilder = litePalDataBuilder;
    }
    //    public LitePalLanguage getLitePalLanguage() {
//        return litePalLanguage;
//    }
//
//    public void setLitePalLanguage(LitePalLanguage litePalLanguage) {
//        this.litePalLanguage = litePalLanguage;
//    }
//
//    public LitePalAuthor getLitePalAuthor() {
//        return litePalAuthor;
//    }
//
//    public void setLitePalAuthor(LitePalAuthor litePalAuthor) {
//        this.litePalAuthor = litePalAuthor;
//    }

    public LitePalDataBuilder.LitePalQuoteBuilder getLitePalQuoteBuilder() {
        return litePalQuoteBuilder;
    }

    public void setLitePalQuoteBuilder(LitePalDataBuilder.LitePalQuoteBuilder litePalQuoteBuilder) {
        this.litePalQuoteBuilder = litePalQuoteBuilder;
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
                "litePalDataBuilder=" + litePalDataBuilder +
//                "litePalLanguage=" + litePalLanguage +
//                ", litePalAuthor=" + litePalAuthor +
                ", litePalQuoteBuilder=" + litePalQuoteBuilder +
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
        dest.writeParcelable(litePalDataBuilder, flags);
//        dest.writeParcelable(litePalLanguage, flags);
//        dest.writeParcelable(litePalAuthor, flags);
        dest.writeParcelable(litePalQuoteBuilder, flags);
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
        this.litePalDataBuilder = in.readParcelable(LitePalDataBuilder.class.getClassLoader());
//        this.litePalLanguage = in.readParcelable(LitePalLanguage.class.getClassLoader());
//        this.litePalAuthor = in.readParcelable(LitePalAuthor.class.getClassLoader());
        this.litePalQuoteBuilder = in.readParcelable(LitePalDataBuilder.LitePalQuoteBuilder.class.getClassLoader());
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