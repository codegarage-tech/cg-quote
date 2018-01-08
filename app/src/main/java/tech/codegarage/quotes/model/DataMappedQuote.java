package tech.codegarage.quotes.model;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Md. Rashadul Alam
 */
public class DataMappedQuote {

    private ArrayList<MappedQuote> mappedQuotes = new ArrayList<>();

    public DataMappedQuote(ArrayList<MappedQuote> mappedQuotes) {
        this.mappedQuotes = mappedQuotes;
    }

    public ArrayList<MappedQuote> getMappedQuotes() {
        return mappedQuotes;
    }

    public void setMappedQuotes(ArrayList<MappedQuote> mappedQuotes) {
        this.mappedQuotes = mappedQuotes;
    }

    @Override
    public String toString() {
        return "{" +
                "mappedQuotes=" + mappedQuotes +
                '}';
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
