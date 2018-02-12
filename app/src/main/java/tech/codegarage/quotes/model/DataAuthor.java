//package tech.codegarage.quotes.model;
//
//import com.google.gson.Gson;
//
//import java.util.ArrayList;
//
///**
// * Md. Rashadul Alam
// */
//public class DataAuthor {
//
//    private ArrayList<Author> authors = new ArrayList<>();
//
//    public DataAuthor(ArrayList<Author> mAuthors) {
//        this.authors = mAuthors;
//    }
//
//    public ArrayList<Author> getAuthors() {
//        return authors;
//    }
//
//    public void setAuthors(ArrayList<Author> mAuthors) {
//        this.authors = mAuthors;
//    }
//
//    @Override
//    public String toString() {
//        return "{" +
//                "authors=" + authors +
//                '}';
//    }
//
//    /**************************
//     * Methods for convertion *
//     **************************/
//    public static <T> T convertFromStringToObject(String jsonString, Class<T> clazz) {
//        Gson gson = new Gson();
//        return gson.fromJson(jsonString, clazz);
//    }
//
//    public static <T> String convertFromObjectToString(T object) {
//        Gson gson = new Gson();
//        return gson.toJson(object);
//    }
//}
