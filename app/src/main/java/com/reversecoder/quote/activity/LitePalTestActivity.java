package com.reversecoder.quote.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.reversecoder.quote.model.database.EnumTag;
import com.reversecoder.quote.model.database.LitePalAuthor;
import com.reversecoder.quote.model.database.LitePalDataHandler;
import com.reversecoder.quote.model.database.LitePalLanguage;
import com.reversecoder.quote.model.database.LitePalQuote;
import com.reversecoder.quote.model.database.LitePalTag;

import java.util.ArrayList;

import static com.reversecoder.quote.model.database.LitePalDataHandler.insertQuote;
import static com.reversecoder.quote.model.database.LitePalDataHandler.insetAuthor;
import static com.reversecoder.quote.model.database.LitePalDataHandler.insetLanguage;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class LitePalTestActivity extends AppCompatActivity {

    private String TAG = LitePalTestActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LitePalLanguage litePalLanguage = insetLanguage(new LitePalLanguage("English"));
        LitePalAuthor litePalAuthor = insetAuthor(new LitePalAuthor("Rashed", "20-12-87", "", "Software Engineer", "Bangladeshi", -1, true));
        LitePalAuthor litePalAuthor2 = insetAuthor(new LitePalAuthor("Mou", "20-12-87", "", "Software Engineer", "Bangladeshi", -1, true));

        Log.d(TAG, "(litePalLanguage == null): " + ((litePalLanguage == null) ? true + "" : false + ""));
        Log.d(TAG, "(litePalAuthor == null): " + ((litePalAuthor == null) ? true + "" : false + ""));
        Log.d(TAG, "litePalLanguage: " + litePalLanguage.toString());
        Log.d(TAG, "litePalAuthor: " + litePalAuthor.toString());

//        {{
//            add(insetQuote(new LitePalQuote("Do what you want.", false, true,litePalLanguage , litePalAuthor, insetTags(new ArrayList<LitePalTag>() {{
//                add(new LitePalTag("Inspirational"));
//                add(new LitePalTag("Motivational"));
//                add(new LitePalTag("Romantic"));
//            }}))));
//
//            add(insetQuote(new LitePalQuote("You have to.", false, true, insetLanguage(new LitePalLanguage("English")), insetAuthor(new LitePalAuthor("Rashed", "20-12-87", "", "Software Engineer", "Bangladeshi", -1, true)), insetTags(new ArrayList<LitePalTag>() {{
//                add(new LitePalTag("Motivational"));
//            }}))));
//
//            add(insetQuote(new LitePalQuote("There is no rule in love and war.", false, true, insetLanguage(new LitePalLanguage("English")), insetAuthor(new LitePalAuthor("Rashed", "20-12-87", "", "Software Engineer", "Bangladeshi", -1, true)), insetTags(new ArrayList<LitePalTag>() {{
//                add(new LitePalTag("Romantic"));
//            }}))));
//        }};

//        ArrayList<LitePalTag> litePalTags = new ArrayList<>();
//        litePalTags.add(EnumTag.INSPIRATIONAL.getTag());
//        litePalTags.add(EnumTag.MOTIVATIONAL.getTag());
//        litePalTags.add(EnumTag.ROMANTIC.getTag());

        LitePalQuote litePalQuote = new LitePalQuote("Do what you want.", false, true, litePalLanguage, litePalAuthor,new ArrayList<LitePalTag>(){{
            add(EnumTag.INSPIRATIONAL.getTag());
            add(EnumTag.MOTIVATIONAL.getTag());
            add(EnumTag.ROMANTIC.getTag());
        }});

        LitePalQuote litePalQuote2 = new LitePalQuote("yes.", false, true, litePalLanguage, litePalAuthor,new ArrayList<LitePalTag>(){{
            add(EnumTag.INSPIRATIONAL.getTag());
        }});

        LitePalQuote litePalQuote3 = new LitePalQuote("no.", false, true, litePalLanguage, litePalAuthor2,new ArrayList<LitePalTag>(){{
            add(EnumTag.INSPIRATIONAL.getTag());
            add(EnumTag.MOTIVATIONAL.getTag());
        }});


        Log.d(TAG, "LitePalQuote " + " is: " + insertQuote(litePalQuote));
        Log.d(TAG, "LitePalQuote " + " is: " + insertQuote(litePalQuote2));
        Log.d(TAG, "LitePalQuote " + " is: " + insertQuote(litePalQuote3));
//        for (int i = 0; i < litePalQuotes.size(); i++) {
//            Log.d(TAG, "LitePalQuote " + i + " is: " + litePalQuotes.get(i).toString());
//        }
    }
}
