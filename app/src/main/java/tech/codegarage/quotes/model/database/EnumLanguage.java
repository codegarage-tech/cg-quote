package tech.codegarage.quotes.model.database;

public enum EnumLanguage {

    ENGLISH("English"),
    BENGALI("Bengali");

    private LitePalLanguage litePalLanguage;

    EnumLanguage(String languageName) {
        litePalLanguage = LitePalDataHandler.insetLanguage(new LitePalLanguage(languageName), null);
    }

    public LitePalLanguage getLitePalLanguage() {
        return litePalLanguage;
    }
}
