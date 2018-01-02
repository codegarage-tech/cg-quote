package com.reversecoder.quote.model.database;

import java.util.ArrayList;

public class LitePalDataBuilder {

    private LitePalLanguage litePalLanguage;
    private LitePalAuthor litePalAuthor;
    private ArrayList<LitePalQuoteBuilder> litePalQuoteBuilders = new ArrayList<>();

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

        public LitePalQuoteBuilder build() {
            if (litePalQuote != null) {
                LitePalDataHandler.insertQuote(litePalQuote);
            }
            return this;
        }
    }
}