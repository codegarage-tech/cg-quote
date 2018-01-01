package com.reversecoder.quote.model.database;

public enum EnumTag {

    MOTIVATIONAL("Motivational"),
    ROMANTIC("Romantic"),
    INSPIRATIONAL("Inspirational");

    private String mTagName;

    EnumTag(String tagName) {
        mTagName = tagName;
    }

    public LitePalTag getTag() {
        return LitePalDataHandler.insetTag(new LitePalTag(mTagName));
    }
}
