package tech.codegarage.quotes.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cleveroad.cyclemenuwidget.CycleMenuItem;
import com.cleveroad.cyclemenuwidget.CycleMenuWidget;
import com.cleveroad.cyclemenuwidget.OnMenuItemClickListener;
import com.cleveroad.cyclemenuwidget.OnStateChangedListener;
import com.reversecoder.library.random.RandomManager;
import com.reversecoder.library.storage.SessionManager;
import com.reversecoder.library.util.AllSettingsManager;

import java.util.ArrayList;
import java.util.Date;

import io.armcha.ribble.presentation.widget.AnimatedImageView;
import io.armcha.ribble.presentation.widget.AnimatedTextView;
import io.armcha.ribble.presentation.widget.ArcView;
import spencerstudios.com.bungeelib.Bungee;
import tech.codegarage.quotes.R;
import tech.codegarage.quotes.model.database.LitePalDataBuilder;
import tech.codegarage.quotes.model.database.LitePalDataHandler;
import tech.codegarage.quotes.model.database.QuoteOfTheDay;
import tech.codegarage.quotes.util.AppUtils;
import tech.codegarage.quotes.util.ClipboardHandler;
import tech.codegarage.quotes.util.IntentManager;
import tech.codegarage.quotes.view.CanaroTextView;

import static tech.codegarage.quotes.model.database.LitePalDataHandler.getAllQuotes;
import static tech.codegarage.quotes.model.database.LitePalDataHandler.getQuotePosition;
import static tech.codegarage.quotes.util.AllConstants.SESSION_QUOTE_OF_THE_DAY;
import static tech.codegarage.quotes.util.AppUtils.isDateEqual;
import static tech.codegarage.scheduler.util.AllConstants.DATE_FORMAT_DD_MM_YY;
import static tech.codegarage.scheduler.util.AllConstants.DATE_FORMAT_YYYY_MM_DD_STRING;

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

    CycleMenuWidget cycleMenuWidget;
    CanaroTextView tvQuote, tvAuthor;
    QuoteOfTheDay mQuoteOfTheDay;
    //    ValueAnimator mValueAnimator;
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
        tvAuthor = (CanaroTextView) findViewById(R.id.tv_author);
        cycleMenuWidget = (CycleMenuWidget) findViewById(R.id.itemCycleMenuWidget);
//        mValueAnimator = AppUtils.flashView(cycleMenuWidget, FLASHING_DEFAULT_DELAY);

        new GetTodayData().execute();
    }

    private void setViewData(QuoteOfTheDay quoteOfTheDay) {
        mQuoteOfTheDay = quoteOfTheDay;

        tvQuote.setText("\"" + mQuoteOfTheDay.getLitePalQuoteBuilder().getLitePalQuote().getQuoteDescription() + "\"");
        tvAuthor.setText("--- " + mQuoteOfTheDay.getLitePalDataBuilder().getLitePalAuthor().getAuthorName());

        if (mQuoteOfTheDay.getLitePalQuoteBuilder().getLitePalQuote().isFavourite()) {
            cycleMenuWidget.setMenuRes(R.menu.menu_cycle_favourite);
        } else {
            cycleMenuWidget.setMenuRes(R.menu.menu_cycle_unfavourite);
        }
        cycleMenuWidget.setCurrentPosition(-1);
        cycleMenuWidget.setCurrentItemsAngleOffset(CycleMenuWidget.UNDEFINED_ANGLE_VALUE);
        cycleMenuWidget.close(false);
        cycleMenuWidget.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View view, int itemPosition) {
                switch (itemPosition) {
                    case 0:
                        IntentManager.shareToAllAvailableApps(AmazingTodayActivity.this, "", AppUtils.getSharedQuote(AmazingTodayActivity.this, mQuoteOfTheDay));
                        break;
                    case 1:
                        ClipboardHandler.copyToClipboard(AmazingTodayActivity.this, mQuoteOfTheDay.getLitePalQuoteBuilder().getLitePalQuote().getQuoteDescription());
                        break;
                    case 2:
                        QuoteOfTheDay proposedQuoteOfTheDay = new QuoteOfTheDay(mQuoteOfTheDay.getLitePalDataBuilder(), mQuoteOfTheDay.getLitePalQuoteBuilder(), mQuoteOfTheDay.getToday());
                        if (proposedQuoteOfTheDay.getLitePalQuoteBuilder().getLitePalQuote().isFavourite()) {
                            proposedQuoteOfTheDay.getLitePalQuoteBuilder().getLitePalQuote().setFavourite(false);
                        } else {
                            proposedQuoteOfTheDay.getLitePalQuoteBuilder().getLitePalQuote().setFavourite(true);
                        }
                        new UpdateQuoteIntoDatabase(AmazingTodayActivity.this, proposedQuoteOfTheDay).execute();

                        break;
                }
            }

            @Override
            public void onMenuItemLongClick(View view, int itemPosition) {
            }
        });
        cycleMenuWidget.setStateChangeListener(new OnStateChangedListener() {
            @Override
            public void onStateChanged(CycleMenuWidget.STATE state) {
            }

            @Override
            public void onOpenComplete() {
            }

            @Override
            public void onCloseComplete() {
            }
        });
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
                String today = DATE_FORMAT_DD_MM_YY.format(new Date());
                QuoteOfTheDay quoteOfTheDay;

                if (!AllSettingsManager.isNullOrEmpty(SessionManager.getStringSetting(AmazingTodayActivity.this, SESSION_QUOTE_OF_THE_DAY))) {
                    Log.d(TAG, "Session data exist");
                    quoteOfTheDay = QuoteOfTheDay.convertFromStringToObject(SessionManager.getStringSetting(AmazingTodayActivity.this, SESSION_QUOTE_OF_THE_DAY), QuoteOfTheDay.class);
                    Log.d(TAG, "Session data(today): " + today);
                    Log.d(TAG, "Session data(quoteOfTheDay.getToday()): " + quoteOfTheDay.getToday());
                    if (!isDateEqual(today, quoteOfTheDay.getToday(), DATE_FORMAT_YYYY_MM_DD_STRING)) {
                        Log.d(TAG, "Session data didn't match");
                        LitePalDataBuilder.LitePalQuoteBuilder litePalQuoteBuilder = result.getLitePalQuoteBuilders().get(RandomManager.getRandom(result.getLitePalQuoteBuilders().size()));
                        quoteOfTheDay = new QuoteOfTheDay(result, litePalQuoteBuilder, today);
                        SessionManager.setStringSetting(AmazingTodayActivity.this, SESSION_QUOTE_OF_THE_DAY, QuoteOfTheDay.convertFromObjectToString(quoteOfTheDay));
                        Log.d(TAG, "Session data: " + quoteOfTheDay.toString());
                    } else {
                        Log.d(TAG, "Session data matched");
                        Log.d(TAG, "Session data: " + quoteOfTheDay.toString());
                    }
                } else {
                    Log.d(TAG, "Session data is not found");
                    LitePalDataBuilder.LitePalQuoteBuilder litePalQuoteBuilder = result.getLitePalQuoteBuilders().get(RandomManager.getRandom(result.getLitePalQuoteBuilders().size()));
                    quoteOfTheDay = new QuoteOfTheDay(result, litePalQuoteBuilder, today);
                    SessionManager.setStringSetting(AmazingTodayActivity.this, SESSION_QUOTE_OF_THE_DAY, QuoteOfTheDay.convertFromObjectToString(quoteOfTheDay));
                    Log.d(TAG, "Session data: " + quoteOfTheDay.toString());
                }

                setViewData(quoteOfTheDay);
            }
        }
    }

    private class UpdateQuoteIntoDatabase extends AsyncTask<String, String, LitePalDataBuilder.LitePalQuoteBuilder> {

        private Context mContext;
        private QuoteOfTheDay quoteOfTheDay;

        UpdateQuoteIntoDatabase(Context context, QuoteOfTheDay quoteOfTheDay) {
            mContext = context;
            this.quoteOfTheDay = quoteOfTheDay;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected LitePalDataBuilder.LitePalQuoteBuilder doInBackground(String... params) {
            //Update quote into database and session
            LitePalDataBuilder.LitePalQuoteBuilder updatedQuote = LitePalDataHandler.updateQuote(quoteOfTheDay.getLitePalDataBuilder(), quoteOfTheDay.getLitePalQuoteBuilder());
            if (updatedQuote != null) {
                return updatedQuote;
            }

            return null;
        }

        @Override
        protected void onPostExecute(LitePalDataBuilder.LitePalQuoteBuilder result) {
            if (result != null) {
                //Update cycle menu
                if (result.getLitePalQuote().isFavourite()) {
                    Toast.makeText(AmazingTodayActivity.this, getString(R.string.txt_marked_as_favourite), Toast.LENGTH_SHORT).show();
                    cycleMenuWidget.updateMenuItem(2, new CycleMenuItem(R.id.cm_favourite, ContextCompat.getDrawable(AmazingTodayActivity.this, R.drawable.ic_vector_favourite_fill_white)));
                } else {
                    Toast.makeText(AmazingTodayActivity.this, getString(R.string.txt_marked_as_unfavourite), Toast.LENGTH_SHORT).show();
                    cycleMenuWidget.updateMenuItem(2, new CycleMenuItem(R.id.cm_favourite, ContextCompat.getDrawable(AmazingTodayActivity.this, R.drawable.ic_vector_favourite_empty_white)));
                }

                //Update recycler view
                int quotePosition = getQuotePosition(quoteOfTheDay.getLitePalDataBuilder().getLitePalQuoteBuilders(), result);
                quoteOfTheDay.getLitePalDataBuilder().getLitePalQuoteBuilders().remove(quotePosition);
                quoteOfTheDay.getLitePalDataBuilder().getLitePalQuoteBuilders().add(quotePosition, result);
                quoteOfTheDay.setLitePalQuoteBuilder(result);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (cycleMenuWidget != null && cycleMenuWidget.isOpened()) {
            cycleMenuWidget.close(true);
        } else {
            super.onBackPressed();
            Bungee.slideDown(AmazingTodayActivity.this);
        }
    }
}