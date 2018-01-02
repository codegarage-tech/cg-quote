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
import com.reversecoder.quote.model.database.LitePalQuoteBuilder;
import com.reversecoder.quote.model.database.LitePalTag;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

import static com.reversecoder.quote.model.database.LitePalDataHandler.insertQuote;
import static com.reversecoder.quote.model.database.LitePalDataHandler.insertQuoteLanguageAuthorTag;

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
        ArrayList<LitePalQuoteBuilder> litePalQuoteBuilders = new ArrayList<>();

        LitePalQuote litePalQuote = new LitePalQuote("Do what you want.", false, true);
        litePalQuoteBuilders.add(new LitePalQuoteBuilder()
                .setLitePalLanguage(EnumLanguage.ENGLISH.getLitePalLanguage())
                .setLitePalAuthor(EnumAuthor.ARISTOTLE.getLitePalAuthor())
                .setLitePalQuote(insertQuote(litePalQuote))
                .setLitePalTags(new ArrayList<LitePalTag>() {{
                    add(EnumTag.INSPIRATIONAL.getLitePalTag());
                    add(EnumTag.MOTIVATIONAL.getLitePalTag());
                    add(EnumTag.ROMANTIC.getLitePalTag());
                }})
                .build());

        LitePalQuote litePalQuote2 = new LitePalQuote("yes.", false, true);
        litePalQuoteBuilders.add(new LitePalQuoteBuilder()
                .setLitePalQuote(insertQuote(litePalQuote2))
                .setLitePalLanguage(EnumLanguage.ENGLISH.getLitePalLanguage())
                .setLitePalAuthor(EnumAuthor.ARISTOTLE.getLitePalAuthor())
                .setLitePalTags(new ArrayList<LitePalTag>() {{
                    add(EnumTag.ROMANTIC.getLitePalTag());
                }})
                .build());

        LitePalQuote litePalQuote3 = new LitePalQuote("no.", false, true);
        litePalQuoteBuilders.add(new LitePalQuoteBuilder()
                .setLitePalQuote(insertQuote(litePalQuote3))
                .setLitePalLanguage(EnumLanguage.ENGLISH.getLitePalLanguage())
                .setLitePalAuthor(EnumAuthor.APJ_ABDUL_KALAM.getLitePalAuthor())
                .setLitePalTags(new ArrayList<LitePalTag>() {{
                    add(EnumTag.INSPIRATIONAL.getLitePalTag());
                    add(EnumTag.MOTIVATIONAL.getLitePalTag());
                }})
                .build());

        for (int i = 0; i < litePalQuoteBuilders.size(); i++) {
            Log.d(TAG, "LitePalQuote " + i + " is saving: " + litePalQuoteBuilders.get(i).getLitePalQuote().toString());
            insertQuoteLanguageAuthorTag(litePalQuoteBuilders.get(i));
        }
    }

    private void getAllData() {
        Cursor cursor = DataSupport.findBySQL("select * from litepalquotelanguageauthortag where authorid = ?", "2");
        while (cursor.moveToNext()) {
            Log.d(TAG, "CursorData: " + DataSupport.find(LitePalAuthor.class, cursor.getLong(cursor.getColumnIndex("authorid"))));
        }
    }
}
