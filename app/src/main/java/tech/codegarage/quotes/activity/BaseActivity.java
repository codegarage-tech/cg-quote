package tech.codegarage.quotes.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.eggheadgames.aboutbox.AboutConfig;
import com.eggheadgames.aboutbox.IAnalytic;
import com.eggheadgames.aboutbox.IDialog;
import com.reversecoder.localechanger.LocaleChanger;
import com.reversecoder.localechanger.utils.ActivityRecreationHelper;
import tech.codegarage.quotes.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initAboutPage();
    }

    /**************************
     * Methods for about page *
     **************************/
    private void initAboutPage() {
        AboutConfig aboutConfig = AboutConfig.getInstance();
        aboutConfig.appName = getString(R.string.app_name);
        aboutConfig.appIcon = R.mipmap.ic_launcher;
        aboutConfig.version = getString(R.string.app_version_name);
        aboutConfig.author = getString(R.string.app_author);
        aboutConfig.aboutLabelTitle = getString(R.string.mal_title_about);
        aboutConfig.packageName = getApplicationContext().getPackageName();
        aboutConfig.buildType = AboutConfig.BuildType.GOOGLE;

        aboutConfig.facebookUserName = getString(R.string.app_publisher_facebook_id);
        aboutConfig.twitterUserName = getString(R.string.app_publisher_twitter_id);
        aboutConfig.webHomePage = getString(R.string.app_publisher_profile_website);

        // app publisher for "Try Other Apps" item
        aboutConfig.appPublisher = getString(R.string.app_publisher);

        // if pages are stored locally, then you need to override aboutConfig.dialog to be able use custom WebView
        aboutConfig.companyHtmlPath = getString(R.string.app_publisher_company_html_path);
        aboutConfig.privacyHtmlPath = getString(R.string.app_publisher_privacy_html_path);
        aboutConfig.acknowledgmentHtmlPath = getString(R.string.app_publisher_acknowledgment_html_path);

        aboutConfig.dialog = new IDialog() {
            @Override
            public void open(AppCompatActivity appCompatActivity, String url, String tag) {
                // handle custom implementations of WebView. It will be called when user click to web items. (Example: "Privacy", "Acknowledgments" and "About")
            }
        };

        aboutConfig.analytics = new IAnalytic() {
            @Override
            public void logUiEvent(String s, String s1) {
                // handle log events.
            }

            @Override
            public void logException(Exception e, boolean b) {
                // handle exception events.
            }
        };
        // set it only if aboutConfig.analytics is defined.
        aboutConfig.logUiEventName = "Log";

        // Contact Support email details
        aboutConfig.emailAddress = getString(R.string.app_author_email);
        aboutConfig.emailSubject = "[" + getString(R.string.app_name) + "]" + "[" + getString(R.string.app_version_name) + "]" + " " + getString(R.string.app_contact_subject);
        aboutConfig.emailBody = getString(R.string.app_contact_body);
    }

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

        ActivityRecreationHelper.recreate(BaseActivity.this, false);
    }

    @Override
    protected void onDestroy() {
        ActivityRecreationHelper.onDestroy(this);
        super.onDestroy();
    }


    @Override
    protected void onResume() {
        super.onResume();
        ActivityRecreationHelper.onResume(this);
    }
}
