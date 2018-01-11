package tech.codegarage.quotes.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import io.armcha.ribble.presentation.widget.AnimatedImageView;
import io.armcha.ribble.presentation.widget.AnimatedTextView;
import io.armcha.ribble.presentation.widget.ArcView;
import tech.codegarage.quotes.R;
import tech.codegarage.quotes.model.database.LitePalDataBuilder;
import static tech.codegarage.quotes.model.database.LitePalDataHandler.getAllQuotes;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amazing_today);

        initView();
        initActions();
    }

    public void initView() {
        initToolBar();
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

//    public class GetTodaysData extends AsyncTask<String, String, LitePalDataBuilder> {
//
//        @Override
//        protected void onPreExecute() {
//        }
//
//        @Override
//        protected LitePalDataBuilder doInBackground(String... params) {
////            if (AllSettingsManager.isNullOrEmpty(SessionManager.getStringSetting(getGlobalContext(), SESSION_DATA_DATA_BUILDER))) {
////                return initAllQuotes();
////            } else {
////
////            }
//            ArrayList<LitePalDataBuilder> litePalDataBuilders = new ArrayList<LitePalDataBuilder>();
//            ArrayList<LitePalDataBuilder> tempLitePalDataBuilders = getAllQuotes();
//
//            if (tempLitePalDataBuilders.size() > 0) {
//                    ArrayList<LitePalDataBuilder> arrAd = DataHandler.getAllQuoteAdvertises();
//                    int index = 0;
//                    for (Quote quote : tempAll) {
//                        arrAll.add(quote);
//                        Double randomNumber = Math.random();
//                        Log.d("randomnumber: ", randomNumber + "");
////            if (Math.random() < 0.2) {
//                        if (randomNumber > 0.5) {
//                            arrAll.add(arrAd.get(index % arrAd.size()));
//                            index++;
//                        }
//                    }
//
//            }
//
//            return getAllQuotes();
//        }
//
//        @Override
//        protected void onPostExecute(LitePalDataBuilder result) {
//
//            if (result != null && result.size() > 0) {
//                //This checking for avoiding "Fragment not attached to Activity when finish AsyncTask & Fragment"
//                if (getActivity() != null && isAdded() && (!isCancelled())) {
//                    rlLoadingView.setVisibility(View.GONE);
//                    initData(result);
//                }
//            } else {
//                //This checking for avoiding "Fragment not attached to Activity when finish AsyncTask & Fragment"
//                if (getActivity() != null && isAdded() && (!isCancelled())) {
//                    ivLoading.setVisibility(View.GONE);
//                    tvLoadingMessage.setText(getString(R.string.txt_no_data_found));
//                }
//            }
//        }
//    }
}