package com.reversecoder.quote.application;

import android.app.Application;
import android.content.res.Configuration;
import android.graphics.Typeface;

import com.franmontiel.localechanger.LocaleChanger;
import com.orm.SugarContext;
import com.reversecoder.logger.LogType;
import com.reversecoder.logger.Logger;
import com.reversecoder.quote.BuildConfig;
import com.reversecoder.quote.R;
import com.singhajit.sherlock.core.Sherlock;
import com.singhajit.sherlock.core.investigation.AppInfo;
import com.singhajit.sherlock.core.investigation.AppInfoProvider;
import com.singhajit.sherlock.util.AppInfoUtil;

import java.util.List;
import java.util.Locale;

import static com.franmontiel.localechanger.data.Locales.getAllLocales;
import static com.reversecoder.quote.util.AllConstants.SELECTED_LANGUAGE;

public class QuoteApp extends Application {

    private static final String CANARO_EXTRA_BOLD_PATH = "fonts/canaro_extra_bold.otf";
    public static Typeface canaroExtraBold;

    @Override
    public void onCreate() {
        super.onCreate();

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

        //initialize database
        SugarContext.init(this);

        //initialize font
        initTypeface();

        //initialize logger
        Logger.Builder.getInstance(this)
                .isLoggable(BuildConfig.DEBUG)
                .logType(LogType.DEBUG)
//                .tag("MyTag")
                .build();

        //Initialize Locale
        List<Locale> locales = getAllLocales();
        LocaleChanger.initialize(getApplicationContext(), locales);
        Locale selectedLocale = locales.get(0);
        LocaleChanger.setLocale(selectedLocale);
        SELECTED_LANGUAGE = selectedLocale.getLanguage();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }

    private void initTypeface() {
        canaroExtraBold = Typeface.createFromAsset(getAssets(), CANARO_EXTRA_BOLD_PATH);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleChanger.onConfigurationChanged();
    }
}
