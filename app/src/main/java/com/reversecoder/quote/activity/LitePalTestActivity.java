package com.reversecoder.quote.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.reversecoder.quote.model.database.EnumAuthor;
import com.reversecoder.quote.model.database.EnumLanguage;
import com.reversecoder.quote.model.database.EnumTag;
import com.reversecoder.quote.model.database.LitePalAuthor;
import com.reversecoder.quote.model.database.LitePalQuote;
import com.reversecoder.quote.model.database.LitePalDataBuilder;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class LitePalTestActivity extends AppCompatActivity {

    private String TAG = "LitePalTestRashed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        insertAllQuotes();

        getAllData();
    }

    private void insertAllQuotes() {
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
    }

    private void getAllData() {
        Cursor cursor = DataSupport.findBySQL("select * from litepalquotelanguageauthortag where authorid = ?", "2");
        while (cursor.moveToNext()) {
            Log.d(TAG, "CursorData: " + DataSupport.find(LitePalAuthor.class, cursor.getLong(cursor.getColumnIndex("authorid"))));
        }
    }
}
