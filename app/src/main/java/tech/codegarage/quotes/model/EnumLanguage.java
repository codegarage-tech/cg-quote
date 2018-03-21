package tech.codegarage.quotes.model;

import android.util.Log;

public enum EnumLanguage {

    ENGLISH("English"),
    BENGALI("Bengali");

    private Language language = null;

    EnumLanguage(String languageName) {
        Log.d(EnumLanguage.class.getSimpleName(), "TAG-EnumLanguage-1");
        language = AppDataHandler.insetLanguage(new Language(languageName), AppDataHandler.mDataInputListener);
        Log.d(EnumLanguage.class.getSimpleName(), "TAG-EnumLanguage-2");
    }

    public Language getLanguage() {
        Log.d(EnumLanguage.class.getSimpleName(), "TAG-EnumLanguage-3");
        return language;
    }
}
