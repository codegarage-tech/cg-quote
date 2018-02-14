package tech.codegarage.quotes.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.lombokcyberlab.android.multicolortextview.MultiColorTextView;
import com.patryk1007.fillme.FillMe;
import com.reversecoder.library.storage.SessionManager;
import com.reversecoder.library.util.AllSettingsManager;
import com.reversecoder.permission.activity.PermissionListActivity;
import com.rodolfonavalon.shaperipplelibrary.ShapeRipple;
import com.rodolfonavalon.shaperipplelibrary.model.Circle;

import java.util.ArrayList;

import spencerstudios.com.bungeelib.Bungee;
import tech.codegarage.quotes.R;
import tech.codegarage.quotes.application.QuoteApp;
import tech.codegarage.quotes.model.database.DataInputListener;
import tech.codegarage.quotes.model.database.LitePalDataBuilder;
import tech.codegarage.quotes.model.database.LitePalDataHandler;
import tech.codegarage.quotes.model.database.LitePalQuote;
import tech.codegarage.quotes.model.database.LitePalQuoteLanguageAuthorTag;

import static tech.codegarage.quotes.util.AllConstants.SESSION_DATA_DATA_BUILDER;
import static tech.codegarage.quotes.util.AllConstants.SESSION_IS_FIRST_TIME;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class SplashActivity extends BaseActivity {

    SplashCountDownTimer splashCountDownTimer;
    private final long splashTime = 4 * 1000;
    private final long interval = 1 * 1000;
    ShapeRipple ripple;
    TextView tvAppVersion,tvMessage;
    LinearLayout llTitleAnimationView;
    MultiColorTextView tvStatus;
    //    ImageView ivLoading;
//    MultiColorTextView tvLoadingMessage;
    FillMe fillMeView;

    InputData inputData;
    private String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initSplashUI();
    }

    private void initSplashUI() {

//        YoYo.with(Techniques.Shake)
//                .duration(1000)
//                .playOn(findViewById(R.id.tv_app_name));

        tvStatus = (MultiColorTextView) findViewById(R.id.tv_status);
        tvMessage = (TextView) findViewById(R.id.tv_message);
        llTitleAnimationView = (LinearLayout) findViewById(R.id.ll_title_animation_view);
        llTitleAnimationView.removeAllViews();
        tvAppVersion = (TextView) findViewById(R.id.application_version);
        tvAppVersion.setText(getString(R.string.app_version_text) + " " + getString(R.string.app_version_name));

//        tvLoadingMessage = (MultiColorTextView) findViewById(R.id.tv_loading_message);
//        tvLoadingMessage.setText(getString(R.string.txt_loading_message));

        //loading gif
//        ivLoading = (ImageView) findViewById(R.id.iv_loading);
//        Glide
//                .with(SplashActivity.this)
//                .load(R.drawable.gif_loading)
//                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
//                .into(ivLoading);

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

        //Fill me view
        fillMeView = (FillMe) findViewById(R.id.fill_me_view);

        //Call fill me view
        new PerformLottieTitle().execute(getString(R.string.app_name_capital));

        if (!AllSettingsManager.isNullOrEmpty(SessionManager.getStringSetting(QuoteApp.getGlobalContext(), SESSION_DATA_DATA_BUILDER))) {
            splashCountDownTimer = new SplashCountDownTimer(splashTime, interval);
            splashCountDownTimer.start();
        } else {
            inputData = new InputData();
            inputData.execute();
        }
    }

    public class SplashCountDownTimer extends CountDownTimer {
        public SplashCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {

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
//                    if (inputData == null) {
//                        inputData = new InputData();
//                    } else if (inputData.getStatus() == AsyncTask.Status.RUNNING) {
//                        inputData.cancel(true);
//                        mAllMappedQuotes.clear();
//                    }
//                    inputData.execute();
                    navigateHomeActivity();
                }
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
        }
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

        int counter;

        public InputData() {
            counter = 0;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected ArrayList<LitePalDataBuilder> doInBackground(String... params) {
            return LitePalDataHandler.initAllQuotes(new DataInputListener<Object>() {
                @Override
                public void InputListener(Object insertedData) {
                    publishProgress(insertedData);
                }
            });
        }

        protected void onProgressUpdate(Object... progress) {
            String progressMessage="";
            if (progress[0] instanceof LitePalQuote) {
                Log.d(TAG, "onProgressUpdate(LitePalQuote): " + ((LitePalQuote) progress[0]).toString());
                progressMessage = ((LitePalQuote) progress[0]).getQuoteDescription();
            } else if (progress[0] instanceof LitePalQuoteLanguageAuthorTag) {
                Log.d(TAG, "onProgressUpdate(LitePalQuoteLanguageAuthorTag): " + ((LitePalQuoteLanguageAuthorTag) progress[0]).toString());
            }

            float progressPercentage = ((float) counter / (float) 100);
            fillMeView.setFillPercentHorizontal(progressPercentage);
//            tvMessage.setText(progressMessage);

            counter++;
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
                        Thread.sleep(700);
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
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
                lottieAnimationView.setLayoutParams(layoutParams);
                lottieAnimationView.setComposition(lottieComposition);
                lottieAnimationView.playAnimation();
                llTitleAnimationView.addView(lottieAnimationView);
            }
        }
    }

//    private class PerformFillMeLoading extends AsyncTask<String, Float, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//            for (int counter = 0; counter <= 100; counter++) {
//                try {
//                    float progress = ((float) counter / (float) 100);
//                    Log.d(TAG, "Progress:(i) = " + counter);
//                    Log.d(TAG, "Progress:(i) = " + progress);
//                    publishProgress(progress);
//                    Thread.sleep(30);
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//
//            }
//            return "Executed";
//        }
//
//        @Override
//        protected void onProgressUpdate(Float... progress) {
//            Log.d(TAG, "Progress:(i) found =  " + progress[0]);
////            tvProgress.setText(progress[0] + "%");
//            fillMeView.setFillPercentHorizontal(progress[0]);
//        }
//    }

    @Override
    public void onBackPressed() {
        if (inputData != null && inputData.getStatus() == AsyncTask.Status.RUNNING) {
            Log.d(TAG, "Canceling splash");
            inputData.cancel(true);
//            mAllMappedQuotes.clear();
//            Log.d(TAG, "data size: " + mAllMappedQuotes.size());
        }

        super.onBackPressed();
        Bungee.slideDown(SplashActivity.this);
    }
}
