package tech.codegarage.quotes.model.database;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

public class LitePalDataBuilder implements Parcelable {

    private LitePalLanguage litePalLanguage;
    private LitePalAuthor litePalAuthor;
    private ArrayList<LitePalQuoteBuilder> litePalQuoteBuilders = new ArrayList<>();
    private boolean isLitePalData = true;
    private transient DataInputListener dataInputListener;

    public LitePalDataBuilder() {
    }

    public LitePalDataBuilder(LitePalLanguage litePalLanguage, LitePalAuthor litePalAuthor, ArrayList<LitePalQuoteBuilder> litePalQuoteBuilders, boolean isLitePalData) {
        this.litePalLanguage = litePalLanguage;
        this.litePalAuthor = litePalAuthor;
        this.litePalQuoteBuilders = litePalQuoteBuilders;
        this.isLitePalData = isLitePalData;
    }

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

    public LitePalDataBuilder setDataInputListener(DataInputListener dataInputListener) {
        this.dataInputListener = dataInputListener;
        return this;
    }

    public LitePalDataBuilder buildAuthor() {
        if (litePalLanguage != null && litePalAuthor != null && getLitePalQuoteBuilders().size() > 0) {
            LitePalDataHandler.insertQuoteLanguageAuthorTag(this, ((dataInputListener != null) ? dataInputListener : null));
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

    /**************************
     * Methods for parcelable *
     **************************/
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(litePalLanguage, flags);
        dest.writeParcelable(litePalAuthor, flags);
        dest.writeList(litePalQuoteBuilders);
        dest.writeInt(isLitePalData ? 1 : 0);
    }

    // Creator
    public static final Creator CREATOR
            = new Creator() {
        public LitePalDataBuilder createFromParcel(Parcel in) {
            return new LitePalDataBuilder(in);
        }

        public LitePalDataBuilder[] newArray(int size) {
            return new LitePalDataBuilder[size];
        }
    };

    // "De-parcel object
    public LitePalDataBuilder(Parcel in) {
        this.litePalLanguage = in.readParcelable(LitePalLanguage.class.getClassLoader());
        this.litePalAuthor = in.readParcelable(LitePalAuthor.class.getClassLoader());
        this.litePalQuoteBuilders = in.readArrayList(LitePalQuoteBuilder.class.getClassLoader());
        this.isLitePalData = (in.readInt() == 0) ? false : true;
    }

    public static class LitePalQuoteBuilder implements Parcelable {

        private LitePalQuote litePalQuote;
        private ArrayList<LitePalTag> litePalTags = new ArrayList<>();
        private transient DataInputListener dataInputListener;

        public LitePalQuoteBuilder() {
        }

        public LitePalQuoteBuilder(LitePalQuote litePalQuote, ArrayList<LitePalTag> litePalTags) {
            this.litePalQuote = litePalQuote;
            this.litePalTags = litePalTags;
        }

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

        public LitePalQuoteBuilder setDataInputListener(DataInputListener dataInputListener) {
            this.dataInputListener = dataInputListener;
            return this;
        }

        public LitePalQuoteBuilder buildQuotes() {
            if (litePalQuote != null) {
                litePalQuote = LitePalDataHandler.insertQuote(litePalQuote, ((dataInputListener != null) ? dataInputListener : null));
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

        /**************************
         * Methods for parcelable *
         **************************/
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(litePalQuote, flags);
            dest.writeList(litePalTags);
        }

        // Creator
        public static final Creator CREATOR
                = new Creator() {
            public LitePalQuoteBuilder createFromParcel(Parcel in) {
                return new LitePalQuoteBuilder(in);
            }

            public LitePalQuoteBuilder[] newArray(int size) {
                return new LitePalQuoteBuilder[size];
            }
        };

        // "De-parcel object
        public LitePalQuoteBuilder(Parcel in) {
            this.litePalQuote = in.readParcelable(LitePalQuote.class.getClassLoader());
            this.litePalTags = in.readArrayList(LitePalTag.class.getClassLoader());
        }
    }
}