package tech.codegarage.quotes.model;

public enum EnumLanguage {

    ENGLISH("English"),
    BENGALI("Bengali");

    private LitePalLanguage litePalLanguage;

    EnumLanguage(String languageName) {
        litePalLanguage = LitePalDataHandler.insetLanguage(new LitePalLanguage(languageName), LitePalDataHandler.dataInputListener);
    }

    public LitePalLanguage getLitePalLanguage() {
        return litePalLanguage;
    }
}
