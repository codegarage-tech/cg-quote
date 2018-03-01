package tech.codegarage.quotes.model;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Md. Rashadul Alam
 */
public class DataLitePalDataBuilder {

    ArrayList<LitePalDataBuilder> litePalDataBuilders = new ArrayList<>();

    public DataLitePalDataBuilder(ArrayList<LitePalDataBuilder> litePalDataBuilders) {
        this.litePalDataBuilders = litePalDataBuilders;
    }

    public ArrayList<LitePalDataBuilder> getLitePalDataBuilders() {
        return litePalDataBuilders;
    }

    public void setLitePalDataBuilders(ArrayList<LitePalDataBuilder> litePalDataBuilders) {
        this.litePalDataBuilders = litePalDataBuilders;
    }

    @Override
    public String toString() {
        return "{" +
                "litePalDataBuilders=" + litePalDataBuilders +
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
