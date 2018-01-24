package tech.codegarage.quotes.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.view.View;

import com.luseen.verticalintrolibrary.VerticalIntro;
import com.luseen.verticalintrolibrary.VerticalIntroItem;
import com.reversecoder.library.storage.SessionManager;
import com.reversecoder.localechanger.LocaleChanger;
import com.reversecoder.localechanger.utils.ActivityRecreationHelper;
import com.reversecoder.permission.activity.PermissionListActivity;

import spencerstudios.com.bungeelib.Bungee;
import tech.codegarage.quotes.R;

import static tech.codegarage.quotes.util.AllConstants.SESSION_IS_FIRST_TIME;

public class AppIntroActivity extends VerticalIntro {

    //For single press
    private static final long MIN_DELAY_MS = 3000L;
    private long mLastClickTime;
    private String TAG = AppIntroActivity.class.getSimpleName();

    @Override
    protected void init() {
        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.colorPink)
                .image(R.drawable.ic_vector_intro_author_empty_rose)
                .title(getString(R.string.txt_author_detail_title))
                .text(getString(R.string.txt_author_detail_description))
                .textSize(18)
                .titleSize(22)
                .build());

        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.colorPurple)
                .image(R.drawable.ic_vector_intro_quote_empty_blue)
                .title(getString(R.string.txt_quote_detail_title))
                .text(getString(R.string.txt_quote_detail_description))
                .textSize(18)
                .titleSize(22)
                .build());

        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.colorBlue)
                .image(R.drawable.ic_vector_intro_favourite_quote_empty_rose)
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
            Bungee.slideUp(AppIntroActivity.this);
        } else {
            navigateHomeActivity();
        }
    }

    private void navigateHomeActivity() {
        Intent intent = new Intent(AppIntroActivity.this, HomeActivity.class);
        startActivity(intent);
        Bungee.slideUp(AppIntroActivity.this);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PermissionListActivity.REQUEST_CODE_PERMISSIONS) {
            if (resultCode == RESULT_OK) {
                navigateHomeActivity();
            } else if (resultCode == RESULT_CANCELED) {
                Bungee.slideDown(AppIntroActivity.this);
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
