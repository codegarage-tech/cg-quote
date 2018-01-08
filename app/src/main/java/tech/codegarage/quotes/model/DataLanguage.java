package tech.codegarage.quotes.model;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Md. Rashadul Alam
 */
public class DataLanguage {

    private ArrayList<Language> languages = new ArrayList<>();

    public DataLanguage(ArrayList<Language> mLanguages) {
        this.languages = mLanguages;
    }

    public ArrayList<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<Language> mLanguages) {
        this.languages = mLanguages;
    }

    @Override
    public String toString() {
        return "{" +
                "languages=" + languages +
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
