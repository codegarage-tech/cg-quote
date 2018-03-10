package tech.codegarage.quotes.application;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;

import com.reversecoder.gcm.GcmManager;
import com.reversecoder.library.storage.SessionManager;
import com.reversecoder.localechanger.LocaleChanger;
import com.reversecoder.logger.LogType;
import com.reversecoder.logger.Logger;
import com.singhajit.sherlock.core.Sherlock;
import com.singhajit.sherlock.core.investigation.AppInfo;
import com.singhajit.sherlock.core.investigation.AppInfoProvider;
import com.singhajit.sherlock.util.AppInfoUtil;

import org.litepal.LitePal;
import org.litepal.parser.LitePalConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tech.codegarage.quotes.BuildConfig;
import tech.codegarage.quotes.R;
import tech.codegarage.quotes.activity.AmazingTodayActivity;
import tech.codegarage.quotes.activity.GcmDetailActivity;
import tech.codegarage.quotes.model.LitePalAuthor;
import tech.codegarage.quotes.model.LitePalLanguage;
import tech.codegarage.quotes.model.LitePalQuote;
import tech.codegarage.quotes.model.LitePalQuoteLanguageAuthorTag;
import tech.codegarage.quotes.model.LitePalTag;
import tech.codegarage.scheduler.Scheduler;

import static com.reversecoder.localechanger.data.Locales.getAllLocales;
import static tech.codegarage.quotes.util.AllConstants.DB_NAME;
import static tech.codegarage.quotes.util.AllConstants.DB_STORAGE;
import static tech.codegarage.quotes.util.AllConstants.DB_VERSION;
import static tech.codegarage.quotes.util.AllConstants.SESSION_FREE_APP;
import static tech.codegarage.quotes.util.AllConstants.SESSION_SELECTED_LANGUAGE;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class QuoteApp extends Application {

    private static Context mContext;
    private static final String CANARO_EXTRA_BOLD_PATH = "fonts/canaro_extra_bold.otf";
    public static Typeface canaroExtraBold;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        //For using vector drawable
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        //Initialize multidex object
        MultiDex.install(this);

        //Initialize GCM content class
        GcmManager.initGcmManager(mContext, new GcmManager.GcmBuilder().setContentClass(GcmDetailActivity.class).buildGcmManager());

        //Initialize scheduler content class
        Scheduler.initSchedule(mContext, new Scheduler.ScheduleBuilder().setContentClass(AmazingTodayActivity.class).buildSchedule());

        //Initialize litepal database
        LitePal.initialize(mContext, new LitePalConfig(DB_VERSION, DB_NAME, "", DB_STORAGE, new ArrayList<String>() {{
            add(LitePalLanguage.class.getName());
            add(LitePalAuthor.class.getName());
            add(LitePalQuote.class.getName());
            add(LitePalTag.class.getName());
            add(LitePalQuoteLanguageAuthorTag.class.getName());
        }}));

        //initialize crash log handler
        Sherlock.init(this);
        Sherlock.setAppInfoProvider(new AppInfoProvider() {
            @Override
            public AppInfo getAppInfo() {
                return new AppInfo.Builder()
                        .with("Version: ", AppInfoUtil.getAppVersion(QuoteApp.this))
                        .with("App Name: ", getString(R.string.app_name))
                        .build();
            }
        });

//        //initialize database
//        SugarContext.init(this);

        //initialize font
        initTypeface();

        //initialize logger
        Logger.Builder.getInstance(this)
                .isLoggable(BuildConfig.DEBUG)
                .logType(LogType.DEBUG)
//                .tag("MyTag")
                .build();

        //Initialize Locale
//        if (AllSettingsManager.isNullOrEmpty(SessionManager.getStringSetting(getGlobalContext(), SESSION_SELECTED_LANGUAGE))) {
        List<Locale> locales = getAllLocales();
        LocaleChanger.initialize(getApplicationContext(), locales);
        Locale selectedLocale = locales.get(0);
        LocaleChanger.setLocale(selectedLocale);
        SessionManager.setStringSetting(getGlobalContext(), SESSION_SELECTED_LANGUAGE, selectedLocale.getLanguage());
        //Initialize app type(Now the app is in paid mode for testing)
        SessionManager.setBooleanSetting(getGlobalContext(), SESSION_FREE_APP, false);
//        }
    }

//    @Override
//    public void onTerminate() {
//        super.onTerminate();
//        SugarContext.terminate();
//    }

    private void initTypeface() {
        canaroExtraBold = Typeface.createFromAsset(getAssets(), CANARO_EXTRA_BOLD_PATH);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleChanger.onConfigurationChanged();
    }

    public static Context getGlobalContext() {
        return mContext;
    }
}
