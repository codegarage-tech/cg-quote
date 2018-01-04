package com.reversecoder.quote.model.database;

import android.util.Log;

import java.util.ArrayList;

public class LitePalDataBuilder {

    private LitePalLanguage litePalLanguage;
    private LitePalAuthor litePalAuthor;
    private ArrayList<LitePalQuoteBuilder> litePalQuoteBuilders = new ArrayList<>();
    private boolean isLitePalData = true;

    public LitePalLanguage getLitePalLanguage() {
        return litePalLanguage;
    }

    public LitePalDataBuilder setLitePalLanguage(LitePalLanguage litePalLanguage) {
        this.litePalLanguage = litePalLanguage;
        return this;
    }

    public LitePalAuthor getLitePalAuthor() {
        return litePalAuthor;
    }

    public LitePalDataBuilder setLitePalAuthor(LitePalAuthor litePalAuthor) {
        this.litePalAuthor = litePalAuthor;
        return this;
    }

    public ArrayList<LitePalQuoteBuilder> getLitePalQuoteBuilders() {
        return litePalQuoteBuilders;
    }

    public LitePalDataBuilder setLitePalQuoteBuilders(ArrayList<LitePalQuoteBuilder> litePalQuoteBuilders) {
        this.litePalQuoteBuilders = litePalQuoteBuilders;
        return this;
    }

    public LitePalDataBuilder addLitePalQuotes(LitePalQuoteBuilder litePalQuoteBuilder) {
        this.litePalQuoteBuilders.add(litePalQuoteBuilder);
        return this;
    }

    public boolean isLitePalData() {
        return isLitePalData;
    }

    public LitePalDataBuilder setLitePalData(boolean litePalData) {
        isLitePalData = litePalData;
        return this;
    }

    public LitePalDataBuilder buildAuthor() {
        if (litePalLanguage != null && litePalAuthor != null && getLitePalQuoteBuilders().size() > 0) {
            LitePalDataHandler.insertQuoteLanguageAuthorTag(this);
        }
        Log.d(LitePalDataBuilder.class.getSimpleName(), toString());
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "litePalLanguage=" + litePalLanguage +
                ", litePalAuthor=" + litePalAuthor +
                ", litePalQuoteBuilders=" + litePalQuoteBuilders +
                '}';
    }

    public static class LitePalQuoteBuilder {

        private LitePalQuote litePalQuote;
        private ArrayList<LitePalTag> litePalTags = new ArrayList<>();

        public LitePalQuoteBuilder setLitePalTags(ArrayList<LitePalTag> litePalTags) {
            this.litePalTags = litePalTags;
            return this;
        }

        public LitePalQuoteBuilder addLitePalTags(LitePalTag litePalTag) {
            this.litePalTags.add(litePalTag);
            return this;
        }

        public LitePalQuoteBuilder setLitePalQuote(LitePalQuote litePalQuote) {
            this.litePalQuote = litePalQuote;
            return this;
        }

        public LitePalQuote getLitePalQuote() {
            return litePalQuote;
        }

        public ArrayList<LitePalTag> getLitePalTags() {
            return litePalTags;
        }

        public LitePalQuoteBuilder buildQuotes() {
            if (litePalQuote != null) {
                litePalQuote = LitePalDataHandler.insertQuote(litePalQuote);
            }
            return this;
        }

        @Override
        public String toString() {
            return "{" +
                    "litePalQuote=" + litePalQuote +
                    ", litePalTags=" + litePalTags +
                    '}';
        }
    }
}