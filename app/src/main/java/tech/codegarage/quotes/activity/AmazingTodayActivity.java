package tech.codegarage.quotes.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.reversecoder.library.random.RandomManager;
import com.reversecoder.library.storage.SessionManager;
import com.reversecoder.library.util.AllSettingsManager;

import java.util.ArrayList;
import java.util.Date;

import io.armcha.ribble.presentation.widget.AnimatedImageView;
import io.armcha.ribble.presentation.widget.AnimatedTextView;
import io.armcha.ribble.presentation.widget.ArcView;
import tech.codegarage.quotes.R;
import tech.codegarage.quotes.model.database.LitePalDataBuilder;
import tech.codegarage.quotes.model.database.QuoteOfTheDay;
import tech.codegarage.quotes.view.CanaroTextView;

import static tech.codegarage.quotes.model.database.LitePalDataHandler.getAllQuotes;
import static tech.codegarage.quotes.util.AllConstants.SESSION_QUOTE_OF_THE_DAY;
import static tech.codegarage.quotes.util.AppUtils.isDateEqual;
import static tech.codegarage.scheduler.util.AllConstants.DATE_FORMAT;
import static tech.codegarage.scheduler.util.AllConstants.DATE_FORMAT_STRING;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class AmazingTodayActivity extends BaseActivity {

    //toolbar
    ArcView arcMenuView;
    AnimatedImageView arcMenuImage;
    AnimatedTextView toolbarTitle;
    Toolbar toolbar;

    CanaroTextView tvQuote;
    private String TAG = AmazingTodayActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amazing_today);

        initView();
        initActions();
    }

    public void initView() {
        initToolBar();

        tvQuote = (CanaroTextView) findViewById(R.id.tv_quote);

        new GetTodayData().execute();
    }

    private void initActions() {

    }

    private void initToolBar() {
        toolbarTitle = (AnimatedTextView) findViewById(R.id.toolbarTitle);
        arcMenuImage = (AnimatedImageView) findViewById(R.id.arcImage);
        arcMenuView = (ArcView) findViewById(R.id.arcView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbarTitle.setAnimatedText(getString(R.string.title_activity_amazing_today), 0L);

        arcMenuImage.setAnimatedImage(R.drawable.arrow_left, 0L);
        arcMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public class GetTodayData extends AsyncTask<String, String, LitePalDataBuilder> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected LitePalDataBuilder doInBackground(String... params) {
            ArrayList<LitePalDataBuilder> litePalDataBuilders = getAllQuotes();
            if (litePalDataBuilders.size() > 0) {
                return litePalDataBuilders.get(RandomManager.getRandom(litePalDataBuilders.size()));
            }
            return null;
        }

        @Override
        protected void onPostExecute(LitePalDataBuilder result) {
            if (result != null) {
                String today = DATE_FORMAT.format(new Date());
                QuoteOfTheDay quoteOfTheDay;

                if (!AllSettingsManager.isNullOrEmpty(SessionManager.getStringSetting(AmazingTodayActivity.this, SESSION_QUOTE_OF_THE_DAY))) {
                    Log.d(TAG, "Session data exist");
                    quoteOfTheDay = QuoteOfTheDay.convertFromStringToObject(SessionManager.getStringSetting(AmazingTodayActivity.this, SESSION_QUOTE_OF_THE_DAY), QuoteOfTheDay.class);
                    Log.d(TAG, "Session data(today): " + today);
                    Log.d(TAG, "Session data(quoteOfTheDay.getToday()): " + quoteOfTheDay.getToday());

                    if (!isDateEqual(today, quoteOfTheDay.getToday(), DATE_FORMAT_STRING)) {
                        Log.d(TAG, "Session data didn't match");
                        LitePalDataBuilder.LitePalQuoteBuilder litePalQuoteBuilder = result.getLitePalQuoteBuilders().get(RandomManager.getRandom(result.getLitePalQuoteBuilders().size()));
                        quoteOfTheDay = new QuoteOfTheDay(result.getLitePalLanguage(), result.getLitePalAuthor(), litePalQuoteBuilder, today);
                        SessionManager.setStringSetting(AmazingTodayActivity.this, SESSION_QUOTE_OF_THE_DAY, QuoteOfTheDay.convertFromObjectToString(quoteOfTheDay));
                        Log.d(TAG, "Session data: " + quoteOfTheDay.toString());
                    } else {
                        Log.d(TAG, "Session data didn't match");
                        Log.d(TAG, "Session data: " + quoteOfTheDay.toString());
                    }
                } else {
                    Log.d(TAG, "Session data is not found");
                    LitePalDataBuilder.LitePalQuoteBuilder litePalQuoteBuilder = result.getLitePalQuoteBuilders().get(RandomManager.getRandom(result.getLitePalQuoteBuilders().size()));
                    quoteOfTheDay = new QuoteOfTheDay(result.getLitePalLanguage(), result.getLitePalAuthor(), litePalQuoteBuilder, today);
                    SessionManager.setStringSetting(AmazingTodayActivity.this, SESSION_QUOTE_OF_THE_DAY, QuoteOfTheDay.convertFromObjectToString(quoteOfTheDay));
                    Log.d(TAG, "Session data: " + quoteOfTheDay.toString());
                }

                tvQuote.setText(quoteOfTheDay.getLitePalQuoteBuilder().getLitePalQuote().getQuoteDescription());
            }
        }
    }
}