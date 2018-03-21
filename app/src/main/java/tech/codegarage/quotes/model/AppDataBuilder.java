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
    private boolean isData = true;
    private transient DataInputListener dataInputListener;

    public AppDataBuilder() {
        this.language = null;
        this.author = null;
        this.dataInputListener = null;

        Log.d(AppDataBuilder.class.getSimpleName(), "TAG-AppDataBuilder-1");
    }

    public AppDataBuilder(Language language, Author author, ArrayList<QuoteBuilder> quoteBuilders, boolean isData) {
        this.language = language;
        this.author = author;
        this.quoteBuilders = quoteBuilders;
        this.isData = isData;
        this.dataInputListener = null;
    }

    public Language getLanguage() {
        return language;
    }

    public AppDataBuilder setLanguage(Language language) {
        this.language = language;
        Log.d(AppDataBuilder.class.getSimpleName(), "TAG-AppDataBuilder-3");
        return this;
    }

    public Author getAuthor() {
        return author;
    }

    public AppDataBuilder setAuthor(Author author) {
        this.author = author;
        Log.d(AppDataBuilder.class.getSimpleName(), "TAG-AppDataBuilder-4");
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
        Log.d(AppDataBuilder.class.getSimpleName(), "TAG-AppDataBuilder-5");
        return this;
    }

    public boolean isData() {
        return isData;
    }

    public AppDataBuilder setData(boolean data) {
        isData = data;
        return this;
    }

    public AppDataBuilder setDataInputListener(DataInputListener dataInputListener) {
        this.dataInputListener = dataInputListener;
        Log.d(AppDataBuilder.class.getSimpleName(), "TAG-AppDataBuilder-2");
        return this;
    }

    public AppDataBuilder buildAuthor() {
        if (language != null && author != null && getQuoteBuilders().size() > 0) {
            Log.d(AppDataBuilder.class.getSimpleName(), "TAG-AppDataBuilder-6");
            AppDataHandler.insertQuoteLanguageAuthorTag(this, ((dataInputListener != null) ? dataInputListener : null));
            Log.d(AppDataBuilder.class.getSimpleName(), "TAG-AppDataBuilder-7");
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
        dest.writeInt(isData ? 1 : 0);
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
        this.isData = (in.readInt() == 0) ? false : true;
    }

    public static class QuoteBuilder implements Parcelable {

        private Quote quote;
        private ArrayList<Tag> tags = new ArrayList<>();
//        private transient DataInputListener dataInputListener;

        public QuoteBuilder() {
            Quote quote = null;
            Log.d(QuoteBuilder.class.getSimpleName(), "TAG-QuoteBuilder-1");
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
                Log.d(QuoteBuilder.class.getSimpleName(), "TAG-QuoteBuilder-2");
                quote = AppDataHandler.insertQuote(quote, ((AppDataHandler.mDataInputListener != null) ? AppDataHandler.mDataInputListener : null));

                Log.d(QuoteBuilder.class.getSimpleName(), "TAG-QuoteBuilder-3");
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