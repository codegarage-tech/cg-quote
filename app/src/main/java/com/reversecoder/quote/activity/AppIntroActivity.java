package com.reversecoder.quote.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;

import com.reversecoder.localechanger.LocaleChanger;
import com.reversecoder.localechanger.utils.ActivityRecreationHelper;
import com.luseen.verticalintrolibrary.VerticalIntro;
import com.luseen.verticalintrolibrary.VerticalIntroItem;
import com.reversecoder.library.storage.SessionManager;
import com.reversecoder.permission.activity.PermissionListActivity;
import com.reversecoder.quote.R;
import com.reversecoder.quote.model.MappedQuote;
import com.reversecoder.quote.util.DataHandler;

import java.util.ArrayList;

import static com.reversecoder.quote.util.AllConstants.SESSION_IS_FIRST_TIME;

public class AppIntroActivity extends VerticalIntro {

    //For single press
    private static final long MIN_DELAY_MS = 3000L;
    private long mLastClickTime;
    private String TAG = AppIntroActivity.class.getSimpleName();

    @Override
    protected void init() {
        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.colorPrimaryDark)
                .image(R.drawable.intro_author)
                .title(getString(R.string.txt_author_detail_title))
                .text(getString(R.string.txt_author_detail_description))
                .textSize(18)
                .titleSize(22)
                .build());

        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.colorPurple)
                .image(R.drawable.intro_quote)
                .title(getString(R.string.txt_quote_detail_title))
                .text(getString(R.string.txt_quote_detail_description))
                .textSize(18)
                .titleSize(22)
                .build());

        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.colorBlue)
                .image(R.drawable.intro_favourite)
                .title(getString(R.string.txt_favourite_quote_title))
                .text(getString(R.string.txt_favourite_quote_description))
                .textColor(R.color.white)
                .titleColor(R.color.white)
                .textSize(18)
                .titleSize(22)
                .build());

        setSkipEnabled(true);
        setVibrateEnabled(true);
        setSkipColor(R.color.white);
//        setNextText("OK");
//        setDoneText("FINISH HIM");
//        setSkipText("GO GO");
        setVibrateIntensity(20);
        setCustomTypeFace(Typeface.createFromAsset(getAssets(), "fonts/NotoSans-Regular.ttf"));
    }

    @Override
    protected Integer setLastItemBottomViewColor() {
        return R.color.colorRose;
    }

    @Override
    protected void onSkipPressed(View view) {
        Log.e("onSkipPressed ", "onSkipPressed");
    }

    @Override
    protected void onFragmentChanged(int position) {
        Log.e("onFragmentChanged ", "" + position);
    }

    @Override
    protected void onDonePressed() {
        //Save app into successfully read
        SessionManager.setBooleanSetting(AppIntroActivity.this, SESSION_IS_FIRST_TIME, false);

        //Send user for futher permission
        goToHomeScreen();
    }

    private void goToHomeScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(AppIntroActivity.this, PermissionListActivity.class);
            startActivityForResult(intent, PermissionListActivity.REQUEST_CODE_PERMISSIONS);
        } else {
            navigateHomeActivity();
        }
    }

    private void navigateHomeActivity() {
        Intent intent = new Intent(AppIntroActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PermissionListActivity.REQUEST_CODE_PERMISSIONS) {
            if (resultCode == RESULT_OK) {
                navigateHomeActivity();
            } else if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }

//    /******************************
//     * Methods for database input *
//     ******************************/
//    public class InputData extends AsyncTask<String, String, ArrayList<MappedQuote>> {
//
//        @Override
//        protected void onPreExecute() {
//        }
//
//        @Override
//        protected ArrayList<MappedQuote> doInBackground(String... params) {
//            return DataHandler.initDataBase();
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<MappedQuote> result) {
//
//            if (result != null && result.size() > 0) {
//                navigateHomeActivity();
//            }
//        }
//    }

    /*****************************
     * Methods for locale change *
     *****************************/
    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = LocaleChanger.configureBaseContext(newBase);
        super.attachBaseContext(newBase);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        ActivityRecreationHelper.recreate(AppIntroActivity.this, false);
    }
}
