package tech.codegarage.quotes.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.lombokcyberlab.android.multicolortextview.MultiColorTextView;
import com.reversecoder.library.storage.SessionManager;
import com.reversecoder.library.util.AllSettingsManager;
import com.reversecoder.permission.activity.PermissionListActivity;
import com.rodolfonavalon.shaperipplelibrary.ShapeRipple;
import com.rodolfonavalon.shaperipplelibrary.model.Circle;

import java.util.ArrayList;

import co.mobiwise.library.ProgressLayout;
import spencerstudios.com.bungeelib.Bungee;
import tech.codegarage.quotes.R;
import tech.codegarage.quotes.application.QuoteApp;
import tech.codegarage.quotes.interfaces.DataInputListener;
import tech.codegarage.quotes.model.LitePalAuthor;
import tech.codegarage.quotes.model.LitePalDataBuilder;
import tech.codegarage.quotes.model.LitePalDataHandler;
import tech.codegarage.quotes.model.LitePalLanguage;
import tech.codegarage.quotes.model.LitePalQuote;
import tech.codegarage.quotes.model.LitePalQuoteLanguageAuthorTag;
import tech.codegarage.quotes.model.LitePalTag;

import static tech.codegarage.quotes.util.AllConstants.SESSION_DATA_DATA_BUILDER;
import static tech.codegarage.quotes.util.AllConstants.SESSION_IS_FIRST_TIME;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class SplashActivity extends BaseActivity {

    //    SplashCountDownTimer splashCountDownTimer;
    private final long splashTime = 4 * 1000;
    private final long interval = 1 * 1000;
    ShapeRipple ripple;
    TextView tvAppVersion, tvMessage, tvProgressStatus, tvLeftFirstBrace, tvProgressMessage, tvRightFirstBrace;
    LinearLayout llTitleAnimationView;
    MultiColorTextView tvStatus;
    ProgressLayout progressLayout;

    InputData inputData;
    PerformLottieTitle performLottieTitle;
    private String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initSplashUI();
    }

    private void initSplashUI() {
        tvStatus = (MultiColorTextView) findViewById(R.id.tv_status);
        tvMessage = (TextView) findViewById(R.id.tv_message);
        tvProgressStatus = (TextView) findViewById(R.id.tv_progress_status);
        tvLeftFirstBrace = (TextView) findViewById(R.id.tv_left_first_brace);
        tvProgressMessage = (TextView) findViewById(R.id.tv_progress_message);
        tvRightFirstBrace = (TextView) findViewById(R.id.tv_right_first_brace);
        llTitleAnimationView = (LinearLayout) findViewById(R.id.ll_title_animation_view);
        llTitleAnimationView.removeAllViews();
        tvAppVersion = (TextView) findViewById(R.id.application_version);
        tvAppVersion.setText(getString(R.string.app_version_text) + " " + getString(R.string.app_version_name));

        //shape ripple
        ripple = (ShapeRipple) findViewById(R.id.background_ripple);
        ripple.setRippleShape(new Circle());
        ripple.setEnableColorTransition(true);
        ripple.setEnableSingleRipple(false);
        ripple.setEnableRandomPosition(true);
        ripple.setEnableRandomColor(true);
        ripple.setEnableStrokeStyle(false);
        ripple.setRippleDuration(2500);
        ripple.setRippleCount(10);
        ripple.setRippleMaximumRadius(184);

        //Progress layout view
        progressLayout = (ProgressLayout) findViewById(R.id.progress_layout);

        //Call Lottie view
        performLottieTitle = new PerformLottieTitle();
        performLottieTitle.execute(getString(R.string.app_name_capital));
    }

    private void navigateHomeActivity() {
        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(intent);
        Bungee.slideUp(SplashActivity.this);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PermissionListActivity.REQUEST_CODE_PERMISSIONS) {
            if (resultCode == RESULT_OK) {
                navigateHomeActivity();
            } else if (resultCode == RESULT_CANCELED) {
                Bungee.slideDown(SplashActivity.this);
                finish();
            }
        }
    }

    /******************************
     * Methods for database input *
     ******************************/
    public class InputData extends AsyncTask<String, Object, ArrayList<LitePalDataBuilder>> {

        int mCounter, mProgress;

        public InputData() {
            mCounter = 0;
            mProgress = 0;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected ArrayList<LitePalDataBuilder> doInBackground(String... params) {

            if (!AllSettingsManager.isNullOrEmpty(SessionManager.getStringSetting(QuoteApp.getGlobalContext(), SESSION_DATA_DATA_BUILDER))) {

                try {
                    Thread.sleep(2 * interval);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                Intent intent;
                if (SessionManager.getBooleanSetting(SplashActivity.this, SESSION_IS_FIRST_TIME, true)) {
                    intent = new Intent(SplashActivity.this, AppIntroActivity.class);
                    startActivity(intent);
                    Bungee.slideUp(SplashActivity.this);
                    finish();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        intent = new Intent(SplashActivity.this, PermissionListActivity.class);
                        startActivityForResult(intent, PermissionListActivity.REQUEST_CODE_PERMISSIONS);
                        Bungee.slideUp(SplashActivity.this);
                    } else {
                        navigateHomeActivity();
                    }
                }
            } else {

//                for (int mCounter = 0; mCounter <= 100; mCounter++) {
//                    try {
//                        float progress = ((float) mCounter / (float) 100);
//                        Log.d(TAG, "Progress:(i) = " + mCounter);
//                        Log.d(TAG, "Progress:(i) = " + progress);
//                        publishProgress(progress);
//                        Thread.sleep(30);
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                }

                publishProgress(mCounter);
                ArrayList<LitePalDataBuilder> litePalDataBuilders = LitePalDataHandler.initAllQuotes(new DataInputListener<Object>() {
                    @Override
                    public void InputListener(Object insertedData) {
                        publishProgress(insertedData);
                    }
                });

                return litePalDataBuilders;
            }

            return null;
        }

        protected void onProgressUpdate(Object... progress) {
            //Used this logic as a temporary basis,
            //after getting all input count can be decide the final logic
            if (progress[0] != null) {

                //assigning message
                String progressMessage = "", progressStatus = "";
                if (progress[0] instanceof LitePalQuote) {
                    Log.d(TAG, "input(LitePalQuote): " + ((LitePalQuote) progress[0]).toString());
                    progressStatus = getString(R.string.txt_setting_quote);
                    progressMessage = ((LitePalQuote) progress[0]).getQuoteDescription();
                } else if (progress[0] instanceof LitePalQuoteLanguageAuthorTag) {
                    Log.d(TAG, "input(LitePalQuoteLanguageAuthorTag): " + ((LitePalQuoteLanguageAuthorTag) progress[0]).toString());
                    progressStatus = getString(R.string.txt_setting_tag);
                    progressMessage = getString(R.string.txt_linking_quote_with_tag);
                } else if (progress[0] instanceof LitePalAuthor) {
                    Log.d(TAG, "input(LitePalAuthor): " + ((LitePalAuthor) progress[0]).toString());
                    progressStatus = getString(R.string.txt_setting_author);
                    progressMessage = ((LitePalAuthor) progress[0]).getAuthorName();
                } else if (progress[0] instanceof LitePalTag) {
                    Log.d(TAG, "input(LitePalTag): " + ((LitePalTag) progress[0]).toString());
                    progressStatus = getString(R.string.txt_setting_tag);
                    progressMessage = ((LitePalTag) progress[0]).getTagName();
                } else if (progress[0] instanceof LitePalLanguage) {
                    Log.d(TAG, "input(LitePalLanguage): " + ((LitePalLanguage) progress[0]).toString());
                    progressStatus = getString(R.string.txt_setting_language);
                    progressMessage = ((LitePalLanguage) progress[0]).getLanguageName();
                }

                //setting message
                tvProgressStatus.setText(progressStatus + ",");
                tvLeftFirstBrace.setText("(");
                tvProgressMessage.setText(progressMessage);
                tvRightFirstBrace.setText(")");

                //set progress
                if (mCounter == 20) {
                    mProgress++;
                    float finalProgress = ((float) mProgress / (float) 100);
                    if (!progressLayout.isPlaying()) {
                        progressLayout.start();
                    }

                    progressLayout.setCurrentProgress((int) (finalProgress * 100));
                    tvMessage.setText(getString(R.string.txt_loading_for_the_first_time) + ",\n" + (int) (finalProgress * 100) + "%");
                    mCounter = 0;
                }
                mCounter++;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<LitePalDataBuilder> result) {
            if (result != null && result.size() > 0) {
                if (SessionManager.getBooleanSetting(SplashActivity.this, SESSION_IS_FIRST_TIME, true)) {
                    Intent intentAppIntro = new Intent(SplashActivity.this, AppIntroActivity.class);
                    startActivity(intentAppIntro);
                    Bungee.slideUp(SplashActivity.this);
                    finish();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Intent intentPermission = new Intent(SplashActivity.this, PermissionListActivity.class);
                        startActivityForResult(intentPermission, PermissionListActivity.REQUEST_CODE_PERMISSIONS);
                        Bungee.slideUp(SplashActivity.this);
                    } else {
                        navigateHomeActivity();
                    }
                }
            }
        }
    }

    private class PerformLottieTitle extends AsyncTask<String, LottieComposition, String> {

        @Override
        protected String doInBackground(String... params) {

            if (!AllSettingsManager.isNullOrEmpty(params[0])) {
                llTitleAnimationView.removeAllViews();

                String name = params[0];

                for (int i = 0; i < name.length(); i++) {
                    String fileName = "mobilo/" + name.charAt(i) + ".json";
                    LottieComposition.Factory.fromAssetFileName(SplashActivity.this, fileName, new OnCompositionLoadedListener() {
                        @Override
                        public void onCompositionLoaded(@Nullable LottieComposition composition) {
                            if (composition != null) {
                                publishProgress(composition);
                            }
                        }
                    });

                    try {
                        Thread.sleep(interval);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            return "Executed";
        }

        @Override
        protected void onProgressUpdate(LottieComposition... progress) {
            if (progress[0] != null) {
                LottieComposition lottieComposition = progress[0];
                LottieAnimationView lottieAnimationView = new LottieAnimationView(SplashActivity.this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                lottieAnimationView.setLayoutParams(layoutParams);
                lottieAnimationView.setComposition(lottieComposition);
                lottieAnimationView.playAnimation();
                llTitleAnimationView.addView(lottieAnimationView);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            inputData = new InputData();
            inputData.execute();

            if (progressLayout.isPlaying()) {
                progressLayout.stop();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (inputData != null && inputData.getStatus() == AsyncTask.Status.RUNNING) {
            inputData.cancel(true);
        }

        if (performLottieTitle != null && performLottieTitle.getStatus() == AsyncTask.Status.RUNNING) {
            performLottieTitle.cancel(true);
        }

        if (progressLayout.isPlaying()) {
            progressLayout.stop();
        }

        super.onBackPressed();
        Bungee.slideDown(SplashActivity.this);
    }
}
