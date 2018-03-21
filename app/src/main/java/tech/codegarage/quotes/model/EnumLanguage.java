package tech.codegarage.quotes.model;

public enum EnumLanguage {

    ENGLISH("English"),
    BENGALI("Bengali");

    private Language language;

    EnumLanguage(String languageName) {
        language = AppDataHandler.insetLanguage(new Language(languageName), AppDataHandler.mDataInputListener);
    }

    public Language getLanguage() {
        return language;
    }
}
