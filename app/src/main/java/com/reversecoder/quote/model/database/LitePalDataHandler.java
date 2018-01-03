package com.reversecoder.quote.model.database;

import android.util.Log;

import com.reversecoder.library.storage.SessionManager;
import com.reversecoder.library.util.AllSettingsManager;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import static com.reversecoder.quote.application.QuoteApp.getGlobalContext;
import static com.reversecoder.quote.util.AllConstants.SESSION_DATA_DATA_BUILDER;

public class LitePalDataHandler {

    private static String TAG = LitePalDataHandler.class.getSimpleName();

    /**************
     * Data input *
     **************/
    public static ArrayList<LitePalDataBuilder> initAllQuotes() {
        ArrayList<LitePalDataBuilder> litePalDataBuilders = new ArrayList<>();

        litePalDataBuilders.add(
                new LitePalDataBuilder()
                        .setLitePalLanguage(EnumLanguage.ENGLISH.getLitePalLanguage())
                        .setLitePalAuthor(EnumAuthor.ARISTOTLE.getLitePalAuthor())
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("Do what you want.", false, true))
                                .addLitePalTags(EnumTag.INSPIRATIONAL.getLitePalTag())
                                .addLitePalTags(EnumTag.ROMANTIC.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("Yes", false, true))
                                .addLitePalTags(EnumTag.ROMANTIC.getLitePalTag())
                                .buildQuotes()
                        )
                        .addLitePalQuotes(new LitePalDataBuilder.LitePalQuoteBuilder()
                                .setLitePalQuote(new LitePalQuote("No", false, true))
                                .addLitePalTags(EnumTag.MOTIVATIONAL.getLitePalTag())
                                .addLitePalTags(EnumTag.ROMANTIC.getLitePalTag())
                                .buildQuotes()
                        )
                        .buildAuthor()
        );

        DataLitePalDataBuilder dataLitePalDataBuilder = new DataLitePalDataBuilder(litePalDataBuilders);
        SessionManager.setStringSetting(getGlobalContext(), SESSION_DATA_DATA_BUILDER, DataLitePalDataBuilder.convertFromObjectToString(dataLitePalDataBuilder));

        return litePalDataBuilders;
    }

    public static ArrayList<LitePalDataBuilder> getAllQuotes() {
        ArrayList<LitePalDataBuilder> litePalDataBuilders = new ArrayList<>();
        if (!AllSettingsManager.isNullOrEmpty(SessionManager.getStringSetting(getGlobalContext(), SESSION_DATA_DATA_BUILDER))) {
            DataLitePalDataBuilder dataLitePalDataBuilder = DataLitePalDataBuilder.convertFromStringToObject(SessionManager.getStringSetting(getGlobalContext(), SESSION_DATA_DATA_BUILDER), DataLitePalDataBuilder.class);
            litePalDataBuilders = dataLitePalDataBuilder.getLitePalDataBuilders();
        }
        return litePalDataBuilders;
    }

    /*******************
     * Methods for Tag *
     *******************/
    public static ArrayList<LitePalTag> insetTags(ArrayList<LitePalTag> litePalTags) {
        ArrayList<LitePalTag> mLitePalTags = new ArrayList<>();
        for (int i = 0; i < litePalTags.size(); i++) {
            mLitePalTags.add(insetTag(litePalTags.get(i)));
        }
        return mLitePalTags;
    }

    public static LitePalTag insetTag(LitePalTag litePalTag) {
        LitePalTag mSavedTag = getTag(litePalTag.getTagName());
        if (mSavedTag != null) {
            Log.d(TAG, "insetTag(existing): " + mSavedTag.toString());
            return mSavedTag;
        } else {
            if (litePalTag.save()) {
                LitePalTag savedTag = getTag(litePalTag.getTagName());
                if (savedTag != null) {
                    Log.d(TAG, "insetTag(new): " + savedTag.toString());
                    return savedTag;
                }
            }
        }
        return null;
    }

    public static LitePalTag getTag(String tagName) {
        List<LitePalTag> savedTags = DataSupport.where("tagName = ?", tagName).find(LitePalTag.class);
        if (savedTags != null && savedTags.size() == 1) {
            Log.d(TAG, "getTag: " + savedTags.get(0).toString());
            return savedTags.get(0);
        }
        return null;
    }

    /**********************
     * Methods for Author *
     **********************/
    public static LitePalAuthor insetAuthor(LitePalAuthor litePalAuthor) {
        LitePalAuthor savedAuthor = getAuthor(litePalAuthor.getAuthorName());
        if (savedAuthor != null) {
            Log.d(TAG, "insetAuthor(existing): " + savedAuthor.toString());
            return savedAuthor;
        } else {
            if (litePalAuthor.save()) {
                LitePalAuthor mSavedAuthor = getAuthor(litePalAuthor.getAuthorName());
                if (mSavedAuthor != null) {
                    Log.d(TAG, "insetAuthor(new): " + mSavedAuthor.toString());
                    return mSavedAuthor;
                }
            }
        }
        return null;
    }

    public static LitePalAuthor getAuthor(String authorName) {
        List<LitePalAuthor> savedAuthors = DataSupport.where("authorName = ?", authorName).find(LitePalAuthor.class);
        if (savedAuthors != null && savedAuthors.size() == 1) {
            Log.d(TAG, "getAuthor: " + savedAuthors.get(0).toString());
            return savedAuthors.get(0);
        }
        return null;
    }

    /************************
     * Methods for Language *
     ************************/
    public static LitePalLanguage insetLanguage(LitePalLanguage litePalLanguage) {
        LitePalLanguage savedLanguage = getLanguage(litePalLanguage.getLanguageName());
        if (savedLanguage != null) {
            Log.d(TAG, "insetLanguage(Existing): " + savedLanguage.toString());
            return savedLanguage;
        } else {
            if (litePalLanguage.save()) {
                LitePalLanguage mSavedLanguage = getLanguage(litePalLanguage.getLanguageName());
                if (mSavedLanguage != null) {
                    Log.d(TAG, "insetLanguage(new): " + litePalLanguage.toString());
                    return litePalLanguage;
                }
            }
        }
        return null;
    }

    public static LitePalLanguage getLanguage(String languageName) {
        List<LitePalLanguage> savedLanguage = DataSupport.where("languageName = ?", languageName).find(LitePalLanguage.class);
        if (savedLanguage != null && savedLanguage.size() == 1) {
            Log.d(TAG, "getLanguage: " + savedLanguage.get(0).toString());
            return savedLanguage.get(0);
        }
        return null;
    }

    /*********************
     * Methods for Quote *
     *********************/
    public static LitePalQuote insertQuote(LitePalQuote litePalQuote) {
        LitePalQuote savedQuote = getQuote(litePalQuote.getQuoteDescription());
        if (savedQuote != null) {
            Log.d(TAG, "insetQuote(existing): " + savedQuote.toString());
            return savedQuote;
        } else {
            if (litePalQuote.save()) {
                LitePalQuote mSavedQuote = getQuote(litePalQuote.getQuoteDescription());
                if (mSavedQuote != null) {
                    Log.d(TAG, "insetQuote(new): " + mSavedQuote.toString());
                    return mSavedQuote;
                }
                return litePalQuote;
            }
        }
        return null;
    }

    public static LitePalQuote getQuote(String quoteDescription) {
        List<LitePalQuote> savedQuotes = DataSupport.where("quoteDescription = ?", quoteDescription).find(LitePalQuote.class);
        if (savedQuotes != null && savedQuotes.size() == 1) {
            Log.d(TAG, "getQuote(existing): " + savedQuotes.get(0).toString());
            return savedQuotes.get(0);
        }
        return null;
    }

    /********************************************
     * Methods for Quote, Language, Author, Tag *
     ********************************************/
    public static void insertQuoteLanguageAuthorTag(LitePalDataBuilder litePalDataBuilder) {
        for (int i = 0; i < litePalDataBuilder.getLitePalQuoteBuilders().size(); i++) {
            LitePalDataBuilder.LitePalQuoteBuilder litePalQuoteBuilder = litePalDataBuilder.getLitePalQuoteBuilders().get(i);
            for (int j = 0; j < litePalQuoteBuilder.getLitePalTags().size(); j++) {
                Log.d(TAG, "insertQuoteLanguageAuthorTag(quote): " + litePalQuoteBuilder.getLitePalQuote().toString());
                LitePalQuoteLanguageAuthorTag litePalQuoteLanguageAuthorTag = new LitePalQuoteLanguageAuthorTag(litePalQuoteBuilder.getLitePalQuote().getId(), litePalDataBuilder.getLitePalLanguage().getId(), litePalDataBuilder.getLitePalAuthor().getId(), litePalQuoteBuilder.getLitePalTags().get(j).getId());

                LitePalQuoteLanguageAuthorTag mSavedData = getQuoteLanguageAuthorTag(litePalQuoteLanguageAuthorTag.getMd5());
                if (mSavedData == null) {
                    Log.d(TAG, "insertQuoteLanguageAuthorTag(new): " + litePalQuoteLanguageAuthorTag.toString());
                    litePalQuoteLanguageAuthorTag.save();
                }
            }
        }
    }

    public static LitePalQuoteLanguageAuthorTag getQuoteLanguageAuthorTag(String md5) {
        List<LitePalQuoteLanguageAuthorTag> savedQuotes = DataSupport.where("md5 = ?", md5).find(LitePalQuoteLanguageAuthorTag.class);
        if (savedQuotes != null && savedQuotes.size() == 1) {
            Log.d(TAG, "getQuoteLanguageAuthorTag(existing): " + savedQuotes.get(0).toString());
            return savedQuotes.get(0);
        }
        return null;
    }
}