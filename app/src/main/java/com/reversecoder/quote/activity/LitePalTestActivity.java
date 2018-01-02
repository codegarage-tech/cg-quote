package com.reversecoder.quote.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.reversecoder.quote.model.database.EnumTag;
import com.reversecoder.quote.model.database.LitePalAuthor;
import com.reversecoder.quote.model.database.LitePalLanguage;
import com.reversecoder.quote.model.database.LitePalQuote;
import com.reversecoder.quote.model.database.LitePalQuoteBuilder;
import com.reversecoder.quote.model.database.LitePalTag;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;

import static com.reversecoder.quote.model.database.LitePalDataHandler.insertQuote;
import static com.reversecoder.quote.model.database.LitePalDataHandler.insertQuoteLanguageAuthorTag;
import static com.reversecoder.quote.model.database.LitePalDataHandler.insetAuthor;
import static com.reversecoder.quote.model.database.LitePalDataHandler.insetLanguage;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class LitePalTestActivity extends AppCompatActivity {

    private String TAG = "LitePalTestRashed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<LitePalQuoteBuilder> litePalQuoteBuilders = new ArrayList<>();
        LitePalLanguage litePalLanguage = insetLanguage(new LitePalLanguage("English"));
        LitePalAuthor litePalAuthor = insetAuthor(new LitePalAuthor("Rashed", "20-12-87", "", "Software Engineer", "Bangladeshi", -1, true));
        LitePalAuthor litePalAuthor2 = insetAuthor(new LitePalAuthor("Mou", "20-12-87", "", "Software Engineer", "Bangladeshi", -1, true));

        Log.d(TAG, "(litePalLanguage == null): " + ((litePalLanguage == null) ? true + "" : false + ""));
        Log.d(TAG, "(litePalAuthor == null): " + ((litePalAuthor == null) ? true + "" : false + ""));
        Log.d(TAG, "litePalLanguage: " + litePalLanguage.toString());
        Log.d(TAG, "litePalAuthor: " + litePalAuthor.toString());

        LitePalQuote litePalQuote = new LitePalQuote("Do what you want.", false, true);
        litePalQuoteBuilders.add(new LitePalQuoteBuilder()
                .setLitePalQuote(insertQuote(litePalQuote))
                .setLitePalLanguage(litePalLanguage)
                .setLitePalAuthor(litePalAuthor)
                .setLitePalTags(new ArrayList<LitePalTag>() {{
                    add(EnumTag.INSPIRATIONAL.getTag());
                    add(EnumTag.MOTIVATIONAL.getTag());
                    add(EnumTag.ROMANTIC.getTag());
                }})
                .build());

        LitePalQuote litePalQuote2 = new LitePalQuote("yes.", false, true);
        litePalQuoteBuilders.add(new LitePalQuoteBuilder()
                .setLitePalQuote(insertQuote(litePalQuote2))
                .setLitePalLanguage(litePalLanguage)
                .setLitePalAuthor(litePalAuthor)
                .setLitePalTags(new ArrayList<LitePalTag>() {{
                    add(EnumTag.ROMANTIC.getTag());
                }})
                .build());

        LitePalQuote litePalQuote3 = new LitePalQuote("no.", false, true);
        litePalQuoteBuilders.add(new LitePalQuoteBuilder()
                .setLitePalQuote(insertQuote(litePalQuote3))
                .setLitePalLanguage(litePalLanguage)
                .setLitePalAuthor(litePalAuthor2)
                .setLitePalTags(new ArrayList<LitePalTag>() {{
                    add(EnumTag.INSPIRATIONAL.getTag());
                    add(EnumTag.MOTIVATIONAL.getTag());
                }})
                .build());

        for (int i = 0; i < litePalQuoteBuilders.size(); i++) {
            Log.d(TAG, "LitePalQuote " + i + " is saving: " + litePalQuoteBuilders.get(i).getLitePalQuote().toString());
            insertQuoteLanguageAuthorTag(litePalQuoteBuilders.get(i));
        }

        getAllData();
    }

    private void getAllData(){
        Cursor cursor = DataSupport.findBySQL("select * from litepalquotelanguageauthortag where authorid = ?","2");
        while(cursor.moveToNext()) {
            Log.d(TAG, "CursorData: "+DataSupport.find(LitePalAuthor.class,cursor.getLong(cursor.getColumnIndex("authorid"))));
        }
    }
}
