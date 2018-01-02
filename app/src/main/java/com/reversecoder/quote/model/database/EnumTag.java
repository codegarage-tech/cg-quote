package com.reversecoder.quote.model.database;

public enum EnumTag {

    MOTIVATIONAL("Motivational"),
    ROMANTIC("Romantic"),
    INSPIRATIONAL("Inspirational");

    private LitePalTag litePalTag;

    EnumTag(String tagName) {
        litePalTag = LitePalDataHandler.insetTag(new LitePalTag(tagName));
    }

    public LitePalTag getLitePalTag() {
        return litePalTag;
    }
}
