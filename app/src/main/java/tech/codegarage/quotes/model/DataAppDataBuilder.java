package tech.codegarage.quotes.model;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Md. Rashadul Alam
 */
public class DataAppDataBuilder {

    ArrayList<AppDataBuilder> appDataBuilders = new ArrayList<>();

    public DataAppDataBuilder(ArrayList<AppDataBuilder> appDataBuilders) {
        this.appDataBuilders = appDataBuilders;
    }

    public ArrayList<AppDataBuilder> getAppDataBuilders() {
        return appDataBuilders;
    }

    public void setAppDataBuilders(ArrayList<AppDataBuilder> appDataBuilders) {
        this.appDataBuilders = appDataBuilders;
    }

    @Override
    public String toString() {
        return "{" +
                "appDataBuilders=" + appDataBuilders +
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
