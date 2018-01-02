package com.reversecoder.quote.model.database;

import java.util.ArrayList;

public class LitePalQuoteBuilder {

    private LitePalQuote litePalQuote;
    private LitePalLanguage litePalLanguage;
    private LitePalAuthor litePalAuthor;
    private ArrayList<LitePalTag> litePalTags;

    public LitePalQuote getLitePalQuote() {
        return litePalQuote;
    }

    public LitePalQuoteBuilder setLitePalQuote(LitePalQuote litePalQuote) {
        this.litePalQuote = litePalQuote;
        return this;
    }

    public LitePalLanguage getLitePalLanguage() {
        return litePalLanguage;
    }

    public LitePalQuoteBuilder setLitePalLanguage(LitePalLanguage litePalLanguage) {
        this.litePalLanguage = litePalLanguage;
        return this;
    }

    public LitePalAuthor getLitePalAuthor() {
        return litePalAuthor;
    }

    public LitePalQuoteBuilder setLitePalAuthor(LitePalAuthor litePalAuthor) {
        this.litePalAuthor = litePalAuthor;
        return this;
    }

    public ArrayList<LitePalTag> getLitePalTags() {
        return litePalTags;
    }

    public LitePalQuoteBuilder setLitePalTags(ArrayList<LitePalTag> litePalTags) {
        this.litePalTags = litePalTags;
        return this;
    }

    public LitePalQuoteBuilder build() {
        if (litePalQuote != null) {
            litePalQuote.save();
        }
        return this;
    }
}