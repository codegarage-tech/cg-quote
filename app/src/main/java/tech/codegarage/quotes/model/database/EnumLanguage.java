package tech.codegarage.quotes.model.database;

public enum EnumLanguage {

    ENGLISH("English"),
    BANGLA("Bangla");

    private LitePalLanguage litePalLanguage;

    EnumLanguage(String languageName) {
        litePalLanguage = LitePalDataHandler.insetLanguage(new LitePalLanguage(languageName));
    }

    public LitePalLanguage getLitePalLanguage() {
        return litePalLanguage;
    }
}
