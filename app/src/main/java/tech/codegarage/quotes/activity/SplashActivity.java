package tech.codegarage.quotes.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.lombokcyberlab.android.multicolortextview.MultiColorTextView;
import com.reversecoder.gcm.task.RegisterApp;
import com.reversecoder.library.network.NetworkManager;
import com.reversecoder.library.storage.SessionManager;
import com.reversecoder.library.util.AllSettingsManager;
import com.reversecoder.permission.activity.PermissionListActivity;
import com.rodolfonavalon.shaperipplelibrary.ShapeRipple;
import com.rodolfonavalon.shaperipplelibrary.model.Circle;

import java.util.ArrayList;
import java.util.Calendar;

import spencerstudios.com.bungeelib.Bungee;
import tech.codegarage.quotes.R;
import tech.codegarage.quotes.application.QuoteApp;
import tech.codegarage.quotes.model.database.LitePalDataBuilder;
import tech.codegarage.quotes.model.database.LitePalDataHandler;
import tech.codegarage.scheduler.enumeration.REPEAT_TYPE;
import tech.codegarage.scheduler.model.ScheduleItem;
import tech.codegarage.scheduler.service.AlarmService;
import tech.codegarage.scheduler.Scheduler;

import static tech.codegarage.quotes.util.AllConstants.SESSION_DATA_DATA_BUILDER;
import static tech.codegarage.quotes.util.AllConstants.SESSION_IS_FIRST_TIME;
import static tech.codegarage.scheduler.util.AllConstants.INTENT_ACTION_CREATE;
import static tech.codegarage.scheduler.util.AllConstants.INTENT_KEY_SCHEDULE_DATA_ALARM_SERVICE;
import static tech.codegarage.scheduler.util.AllConstants.TIME_FORMAT;
import static tech.codegarage.scheduler.util.AllConstants.DATE_FORMAT;
import static tech.codegarage.scheduler.util.AllConstants.FORMATTED_TIME_DATE_FORMAT;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class SplashActivity extends BaseActivity {

    SplashCountDownTimer splashCountDownTimer;
    private final long splashTime = 4 * 1000;
    private final long interval = 1 * 1000;
    ShapeRipple ripple;
    TextView tvAppVersion;
    ImageView ivLoading;
    MultiColorTextView tvLoadingMessage;

    InputData inputData;
    private String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Register app for push notification
        if (NetworkManager.isConnected(SplashActivity.this)) {
            new RegisterApp(SplashActivity.this).execute();
        }

        initSplashUI();

        setQuoteOfTheDayAlarm();
    }

    private void setQuoteOfTheDayAlarm() {

        //Initialize scheduler content class
        new Scheduler.ScheduleBuilder().setContentClass(AmazingTodayActivity.class).buildSchedule();

        Calendar currentTime = Calendar.getInstance();
        currentTime.add(Calendar.MINUTE, 1);
        Calendar mAlertTime = Calendar.getInstance();
        mAlertTime.set(Calendar.HOUR_OF_DAY, currentTime.get(Calendar.HOUR_OF_DAY));
        mAlertTime.set(Calendar.MINUTE, currentTime.get(Calendar.MINUTE));
        mAlertTime.set(Calendar.SECOND, 0);
        String mTime = TIME_FORMAT.format(mAlertTime.getTime());
        String mDate = DATE_FORMAT.format(mAlertTime.getTime());
        String mFormattedDate = FORMATTED_TIME_DATE_FORMAT.format(mAlertTime.getTimeInMillis());

//        if (AppUtils.isNullOrEmpty(AppUtils.getStringSetting(SplashActivity.this, SESSION_KEY_SCHEDULE_DATA, SESSION_DEFAULT_VALUE_STRING))) {
        ScheduleItem scheduleItem = new ScheduleItem(1, "Quote of the day"+"("+mDate+")", "Tap for more details." , mAlertTime.getTimeInMillis(), REPEAT_TYPE.NONE);

        Intent service = new Intent(SplashActivity.this, AlarmService.class);
        service.putExtra(INTENT_KEY_SCHEDULE_DATA_ALARM_SERVICE, scheduleItem);
        service.setAction(INTENT_ACTION_CREATE);
        startService(service);
//        }
    }

    private void initSplashUI() {

        YoYo.with(Techniques.Shake)
                .duration(1000)
                .playOn(findViewById(R.id.tv_app_name));

        tvAppVersion = (TextView) findViewById(R.id.application_version);
        tvAppVersion.setText(getString(R.string.app_version_text) + " " + getString(R.string.app_version_name));

        tvLoadingMessage = (MultiColorTextView) findViewById(R.id.tv_loading_message);
        tvLoadingMessage.setText(getString(R.string.txt_loading_message));
//        tvLoadingMessage.setTextColor(getResources().getColor(R.color.white));

        //loading gif
        ivLoading = (ImageView) findViewById(R.id.iv_loading);
        Glide
                .with(SplashActivity.this)
                .load(R.drawable.gif_loading)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                .into(ivLoading);

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

//        if (mAllMappedQuotes.size() > 0) {
//            splashCountDownTimer = new SplashCountDownTimer(splashTime, interval);
//            splashCountDownTimer.start();
//        } else {
//            if (inputData == null) {
//                inputData = new InputData();
//            } else if (inputData.getStatus() == AsyncTask.Status.RUNNING) {
//                inputData.cancel(true);
//                mAllMappedQuotes.clear();
//            }
//            inputData.execute();
//        }

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
    public class InputData extends AsyncTask<String, String, ArrayList<LitePalDataBuilder>> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected ArrayList<LitePalDataBuilder> doInBackground(String... params) {
            return LitePalDataHandler.initAllQuotes();
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
