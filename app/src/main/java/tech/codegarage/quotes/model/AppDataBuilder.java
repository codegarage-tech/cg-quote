package tech.codegarage.quotes.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

import tech.codegarage.quotes.interfaces.DataInputListener;

public class AppDataBuilder implements Parcelable {

    private Language language;
    private Author author;
    private ArrayList<QuoteBuilder> quoteBuilders = new ArrayList<>();
    private boolean isLitePalData = true;
    private transient DataInputListener dataInputListener;

    public AppDataBuilder() {
    }

    public AppDataBuilder(Language language, Author author, ArrayList<QuoteBuilder> quoteBuilders, boolean isLitePalData) {
        this.language = language;
        this.author = author;
        this.quoteBuilders = quoteBuilders;
        this.isLitePalData = isLitePalData;
    }

    public Language getLanguage() {
        return language;
    }

    public AppDataBuilder setLanguage(Language language) {
        this.language = language;
        return this;
    }

    public Author getAuthor() {
        return author;
    }

    public AppDataBuilder setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public ArrayList<QuoteBuilder> getQuoteBuilders() {
        return quoteBuilders;
    }

    public AppDataBuilder setQuoteBuilders(ArrayList<QuoteBuilder> quoteBuilders) {
        this.quoteBuilders = quoteBuilders;
        return this;
    }

    public AppDataBuilder addLitePalQuotes(QuoteBuilder quoteBuilder) {
        this.quoteBuilders.add(quoteBuilder);
        return this;
    }

    public boolean isLitePalData() {
        return isLitePalData;
    }

    public AppDataBuilder setLitePalData(boolean litePalData) {
        isLitePalData = litePalData;
        return this;
    }

    public AppDataBuilder setDataInputListener(DataInputListener dataInputListener) {
        this.dataInputListener = dataInputListener;
        return this;
    }

    public AppDataBuilder buildAuthor() {
        if (language != null && author != null && getQuoteBuilders().size() > 0) {
            AppDataHandler.insertQuoteLanguageAuthorTag(this, ((dataInputListener != null) ? dataInputListener : null));
        }
        Log.d(AppDataBuilder.class.getSimpleName(), toString());
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "language=" + language +
                ", author=" + author +
                ", quoteBuilders=" + quoteBuilders +
                '}';
    }

    /**************************
     * Methods for parcelable *
     **************************/
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(language, flags);
        dest.writeParcelable(author, flags);
        dest.writeList(quoteBuilders);
        dest.writeInt(isLitePalData ? 1 : 0);
    }

    // Creator
    public static final Creator CREATOR
            = new Creator() {
        public AppDataBuilder createFromParcel(Parcel in) {
            return new AppDataBuilder(in);
        }

        public AppDataBuilder[] newArray(int size) {
            return new AppDataBuilder[size];
        }
    };

    // "De-parcel object
    public AppDataBuilder(Parcel in) {
        this.language = in.readParcelable(Language.class.getClassLoader());
        this.author = in.readParcelable(Author.class.getClassLoader());
        this.quoteBuilders = in.readArrayList(QuoteBuilder.class.getClassLoader());
        this.isLitePalData = (in.readInt() == 0) ? false : true;
    }

    public static class QuoteBuilder implements Parcelable {

        private Quote quote;
        private ArrayList<Tag> tags = new ArrayList<>();
//        private transient DataInputListener dataInputListener;

        public QuoteBuilder() {
        }

        public QuoteBuilder(Quote quote, ArrayList<Tag> tags) {
            this.quote = quote;
            this.tags = tags;
        }

        public QuoteBuilder setTags(ArrayList<Tag> tags) {
            this.tags = tags;
            return this;
        }

        public QuoteBuilder addLitePalTags(Tag tag) {
            this.tags.add(tag);
            return this;
        }

        public QuoteBuilder setQuote(Quote quote) {
            this.quote = quote;
            return this;
        }

        public Quote getQuote() {
            return quote;
        }

        public ArrayList<Tag> getTags() {
            return tags;
        }

//        public QuoteBuilder setDataInputListener(DataInputListener dataInputListener) {
//            this.dataInputListener = dataInputListener;
//            return this;
//        }

        public QuoteBuilder buildQuotes() {
            if (quote != null) {
                quote = AppDataHandler.insertQuote(quote, ((AppDataHandler.mDataInputListener != null) ? AppDataHandler.mDataInputListener : null));
            }
            return this;
        }

        @Override
        public String toString() {
            return "{" +
                    "quote=" + quote +
                    ", tags=" + tags +
                    '}';
        }

        /**************************
         * Methods for parcelable *
         **************************/
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(quote, flags);
            dest.writeList(tags);
        }

        // Creator
        public static final Creator CREATOR
                = new Creator() {
            public QuoteBuilder createFromParcel(Parcel in) {
                return new QuoteBuilder(in);
            }

            public QuoteBuilder[] newArray(int size) {
                return new QuoteBuilder[size];
            }
        };

        // "De-parcel object
        public QuoteBuilder(Parcel in) {
            this.quote = in.readParcelable(Quote.class.getClassLoader());
            this.tags = in.readArrayList(Tag.class.getClassLoader());
        }
    }
}