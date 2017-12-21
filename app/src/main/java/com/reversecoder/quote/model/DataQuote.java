package com.reversecoder.quote.model;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Md. Rashadul Alam
 */
public class DataQuote {

    private ArrayList<Quote> quotes = new ArrayList<>();

    public DataQuote(ArrayList<Quote> quotes) {
        this.quotes = quotes;
    }

    public ArrayList<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(ArrayList<Quote> quotes) {
        this.quotes = quotes;
    }

    @Override
    public String toString() {
        return "{" +
                "quotes=" + quotes +
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
