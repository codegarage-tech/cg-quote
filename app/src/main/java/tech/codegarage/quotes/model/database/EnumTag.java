package tech.codegarage.quotes.model.database;

public enum EnumTag {

    FUTURE("Future"),
    TEACHER("Teacher"),
    CHARACTER("Character"),
    MOTIVATIONAL("Motivational"),
    DEFEAT("Defeat"),
    GIVE("Give");

    private LitePalTag litePalTag;

    EnumTag(String tagName) {
        litePalTag = LitePalDataHandler.insetTag(new LitePalTag(tagName));
    }

    public LitePalTag getLitePalTag() {
        return litePalTag;
    }
}
