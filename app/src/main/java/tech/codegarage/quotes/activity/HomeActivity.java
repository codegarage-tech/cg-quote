package tech.codegarage.quotes.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.eggheadgames.aboutbox.AboutBoxUtils;
import com.eggheadgames.aboutbox.AboutConfig;
import com.eggheadgames.aboutbox.IAnalytic;
import com.eggheadgames.aboutbox.IDialog;
import com.eggheadgames.aboutbox.activity.AboutActivity;
import com.eggheadgames.aboutbox.listener.LicenseClickListener;
import com.reversecoder.attributionpresenter.activity.LicenseActivity;
import com.reversecoder.gcm.task.RegisterApp;
import com.reversecoder.library.network.NetworkManager;
import com.reversecoder.library.storage.SessionManager;
import com.reversecoder.library.util.AllSettingsManager;

import java.util.Calendar;
import java.util.List;

import io.armcha.ribble.presentation.navigation.NavigationState;
import io.armcha.ribble.presentation.utils.extensions.ViewExKt;
import io.armcha.ribble.presentation.widget.AnimatedImageView;
import io.armcha.ribble.presentation.widget.AnimatedTextView;
import io.armcha.ribble.presentation.widget.ArcView;
import io.armcha.ribble.presentation.widget.navigation_view.NavigationDrawerView;
import io.armcha.ribble.presentation.widget.navigation_view.NavigationId;
import io.armcha.ribble.presentation.widget.navigation_view.NavigationItem;
import io.armcha.ribble.presentation.widget.navigation_view.NavigationItemSelectedListener;
import spencerstudios.com.bungeelib.Bungee;
import tech.codegarage.quotes.R;
import tech.codegarage.quotes.fragment.AuthorFragment;
import tech.codegarage.quotes.fragment.FavouriteFragment;
import tech.codegarage.quotes.interfaces.OnFragmentBackPressedListener;
import tech.codegarage.quotes.util.AllConstants;
import tech.codegarage.quotes.util.AppUtils;
import tech.codegarage.quotes.util.FragmentUtilsManager;
import tech.codegarage.scheduler.enumeration.REPEAT_TYPE;
import tech.codegarage.scheduler.model.ScheduleItem;
import tech.codegarage.scheduler.service.AlarmService;

import static tech.codegarage.scheduler.util.AllConstants.DATE_FORMAT;
import static tech.codegarage.scheduler.util.AllConstants.INTENT_ACTION_CREATE;
import static tech.codegarage.scheduler.util.AllConstants.INTENT_KEY_SCHEDULE_DATA_ALARM_SERVICE;
import static tech.codegarage.scheduler.util.AllConstants.SESSION_DEFAULT_VALUE_STRING;
import static tech.codegarage.scheduler.util.AllConstants.SESSION_KEY_SCHEDULE_DATA;

public class HomeActivity extends BaseActivity {

    private String TRANSLATION_X_KEY = "TRANSLATION_X_KEY";
    private String CARD_ELEVATION_KEY = "CARD_ELEVATION_KEY";
    private String SCALE_KEY = "SCALE_KEY";

    Toolbar toolbar;
    NavigationDrawerView navDrawerView;
    DrawerLayout drawerLayout;
    CardView contentHome;
    public ArcView arcMenuView;
    public AnimatedImageView arcMenuImage;
    AnimatedTextView toolbarTitle;
    public Button btnContextMenu;
    ImageView userAvatar;
    TextView userName;
    TextView userInfo;

    private boolean isArcIcon = false;
    private boolean isDrawerOpened = false;
    private String activeTitle = "";
    private NavigationState navigationState = null;
    private int currentNavigationSelectedItem = 0;

    private String TAG = HomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initQuoteOfTheDayAlarm();

        initAboutPage();

        initViews();

        if (savedInstanceState == null) {
            SessionManager.setStringSetting(HomeActivity.this, AllConstants.SESSION_SELECTED_RIBBLE_MENU, getString(R.string.ribble_menu_item_home));
            handleFragmentChanges(HomeActivity.this, getString(R.string.ribble_menu_item_home), new AuthorFragment());
        }

        initPushNotification();
    }

    private void initViews() {

        initToolBar();

        navDrawerView = (NavigationDrawerView) findViewById(R.id.navigation_drawer_view);
        userAvatar = (ImageView) navDrawerView.getHeader().findViewById(R.id.userAvatar);
        userName = (TextView) navDrawerView.getHeader().findViewById(R.id.userName);
        userInfo = (TextView) navDrawerView.getHeader().findViewById(R.id.userInfo);

        contentHome = (CardView) findViewById(R.id.mainView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navDrawerView.setNavigationItemSelectListener(new NavigationItemSelectedListener() {
            @Override
            public void onNavigationItemSelected(final NavigationItem item) {
                if (!getNavigatorState().getActiveTag().equalsIgnoreCase(item.getId().getName())) {

                    //Wait for few second and close drawer
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Store current ribble menu in session
                            SessionManager.setStringSetting(HomeActivity.this, AllConstants.SESSION_SELECTED_RIBBLE_MENU, item.getId().getName());

                            //Set menu action
                            if (item.getId().getName().equalsIgnoreCase(NavigationId.HOME.INSTANCE.getName())) {
                                handleFragmentChanges(HomeActivity.this, getString(R.string.ribble_menu_item_home), new AuthorFragment());
                            } else if (item.getId().getName().equalsIgnoreCase(NavigationId.FAVOURITE.INSTANCE.getName())) {
                                handleFragmentChanges(HomeActivity.this, getString(R.string.ribble_menu_item_favourite), new FavouriteFragment());
                            } else if (item.getId().getName().equalsIgnoreCase(NavigationId.AMAZING_TODAY.INSTANCE.getName())) {
                                Intent intentAmazingToday = new Intent(HomeActivity.this, AmazingTodayActivity.class);
                                startActivity(intentAmazingToday);
                            } else if (item.getId().getName().equalsIgnoreCase(NavigationId.RATE_US.INSTANCE.getName())) {
                                AboutBoxUtils.openApp(HomeActivity.this, AboutConfig.BuildType.GOOGLE, getApplicationContext().getPackageName());
                            } else if (item.getId().getName().equalsIgnoreCase(NavigationId.ABOUT.INSTANCE.getName())) {
                                AboutActivity.launch(HomeActivity.this);
                                AboutActivity.setLicenseListener(new LicenseClickListener() {
                                    @Override
                                    public void onLicenseClick() {
                                        Intent intentLicense = new Intent(HomeActivity.this, LicenseActivity.class);
                                        startActivity(intentLicense);
                                    }
                                });
                            }
                        }
                    }, AllConstants.NAVIGATION_DRAWER_CLOSE_DELAY);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        drawerLayout.setDrawerElevation(0f);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                float moveFactor = navDrawerView.getWidth() * slideOffset;
                contentHome.setTranslationX(moveFactor);
                ViewExKt.setScale(contentHome, 1 - slideOffset / 4);
                contentHome.setCardElevation(slideOffset * AppUtils.toPx(HomeActivity.this, 10));
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                handleDrawerOpen();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                handleDrawerClose();
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        });

        drawerLayout.setScrimColor(Color.TRANSPARENT);

        if (isArcIcon || isDrawerOpened) {
            setArcArrowState();
        } else {
            setArcHamburgerIconState();
        }

        updateUserInfo();
    }

    private void initToolBar() {
        toolbarTitle = (AnimatedTextView) findViewById(R.id.toolbarTitle);
        arcMenuImage = (AnimatedImageView) findViewById(R.id.arcImage);
        arcMenuView = (ArcView) findViewById(R.id.arcView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnContextMenu = (Button) findViewById(R.id.btn_context_menu);

        toolbarTitle.setAnimatedText(getString(R.string.title_activity_author), 0L);
        arcMenuImage.setAnimatedImage(R.drawable.arrow_left, 0L);
        arcMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void handleFragmentChanges(AppCompatActivity activity, String currentTag, Fragment fragment) {
        saveNavigatorState(new NavigationState(currentTag, toolbarTitle.getText().toString(), false));

        setToolBarTitle(currentTag);
        activeTitle = currentTag;
        if (isArcIcon) {
            isArcIcon = false;
            setArcHamburgerIconState();
        }

        int checkPosition = -1;
        if (currentTag.equalsIgnoreCase(NavigationId.HOME.INSTANCE.getName())) {
            checkPosition = 0;
            goScreen(checkPosition, currentTag, fragment);
        } else if (currentTag.equalsIgnoreCase(NavigationId.FAVOURITE.INSTANCE.getName())) {
            checkPosition = 1;
            goScreen(checkPosition, currentTag, fragment);
        } else if (currentTag.equalsIgnoreCase(NavigationId.AMAZING_TODAY.INSTANCE.getName())) {
            checkPosition = 2;
        } else if (currentTag.equalsIgnoreCase(NavigationId.RATE_US.INSTANCE.getName())) {
            checkPosition = 3;
        } else if (currentTag.equalsIgnoreCase(NavigationId.ABOUT.INSTANCE.getName())) {
            checkPosition = 4;
        } else {
            checkPosition = currentNavigationSelectedItem;
            goScreen(checkPosition, currentTag, fragment);
        }

    }

    private void goScreen(int checkPosition, String currentTag, Fragment fragment) {
        if (currentNavigationSelectedItem != checkPosition) {
            currentNavigationSelectedItem = checkPosition;
            checkNavigationItem(currentNavigationSelectedItem);
        }

        FragmentUtilsManager.changeSupportFragment(HomeActivity.this, fragment, currentTag);
    }

    public void setArcArrowState() {
        arcMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        arcMenuImage.setAnimatedImage(R.drawable.arrow_left, 0L);
    }

    public void setArcHamburgerIconState() {
        arcMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        arcMenuImage.setAnimatedImage(R.drawable.hamb, 0L);
    }

    public String getToolbarTitle() {
        return toolbarTitle.getText().toString();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            //send backpress event to the fragment and finish activity from there
            List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
            if (fragmentList != null) {
                for (Fragment fragment : fragmentList) {
                    if (fragment instanceof OnFragmentBackPressedListener) {
                        ((OnFragmentBackPressedListener) fragment).onFragmentBackPressed();
                    }
                }
            }
        }
    }

    public void checkNavigationItem(int position) {
        navDrawerView.setChecked(position);
    }

    public void setToolBarTitle(String title) {
        toolbarTitle.setAnimatedText(title, 0L);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            outState.putFloat(TRANSLATION_X_KEY, contentHome.getTranslationX());
            outState.putFloat(CARD_ELEVATION_KEY, ViewExKt.getScale(contentHome));
            outState.putFloat(SCALE_KEY, contentHome.getCardElevation());
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);
        if (savedState != null) {
            contentHome.setTranslationX(savedState.getFloat(TRANSLATION_X_KEY));
            ViewExKt.setScale(contentHome, savedState.getFloat(CARD_ELEVATION_KEY));
            contentHome.setCardElevation(savedState.getFloat(SCALE_KEY));
        }
    }

    public void saveNavigatorState(NavigationState state) {
        this.navigationState = state;
    }

    public NavigationState getNavigatorState() {
        return navigationState;
    }

    public void handleDrawerOpen() {
        if (!isArcIcon)
            setArcArrowState();
        isDrawerOpened = true;
    }

    public void handleDrawerClose() {
        if (!isArcIcon && isDrawerOpened)
            setArcHamburgerIconState();
        isDrawerOpened = false;
    }

    public void updateUserInfo() {
        Glide
                .with(HomeActivity.this)
                .load(R.mipmap.ic_launcher_round)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                .apply(new RequestOptions().circleCropTransform())
                .into(userAvatar);
        userName.setText(getString(R.string.app_name));
        userInfo.setText(getString(R.string.app_version_name));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult");

        switch (requestCode) {
            case AllConstants.REQUEST_CODE_FAVOURITE_FRAGMENT: {
                if (data != null && resultCode == RESULT_OK) {
                    Log.d(TAG, "RESULT_OK");

                    FavouriteFragment favouriteFragment = (FavouriteFragment) FragmentUtilsManager.getVisibleSupportFragment(HomeActivity.this, NavigationId.FAVOURITE.INSTANCE.getName());
                    if (favouriteFragment != null) {
                        Log.d(TAG, "onActivityResult sending to favourite fragment");
                        favouriteFragment.onActivityResult(requestCode, resultCode, data);
                    }
                }
                break;
            }
            default:
                break;
        }
    }

    /************************
     * Methods for schedule *
     ************************/
    private void initQuoteOfTheDayAlarm() {

        Calendar mAlertTime = Calendar.getInstance();
//        Calendar currentTime = Calendar.getInstance();
//        currentTime.add(Calendar.MINUTE, 1);
//        mAlertTime.set(Calendar.HOUR_OF_DAY, currentTime.get(Calendar.HOUR_OF_DAY));
//        mAlertTime.set(Calendar.MINUTE, currentTime.get(Calendar.MINUTE));
//        mAlertTime.set(Calendar.SECOND, 0);
//        String mDate = DATE_FORMAT.format(mAlertTime.getTime());

        mAlertTime.set(Calendar.HOUR_OF_DAY, 8);
        mAlertTime.set(Calendar.MINUTE, 0);
        mAlertTime.set(Calendar.SECOND, 0);
        String mDate = DATE_FORMAT.format(mAlertTime.getTime());

        if (AllSettingsManager.isNullOrEmpty(SessionManager.getStringSetting(HomeActivity.this, SESSION_KEY_SCHEDULE_DATA, SESSION_DEFAULT_VALUE_STRING))) {
            ScheduleItem scheduleItem = new ScheduleItem(1, getString(R.string.txt_quote_of_the_day_title), getString(R.string.txt_quote_of_the_day_content), mDate, mAlertTime.getTimeInMillis(), REPEAT_TYPE.DAILY);

            Intent service = new Intent(HomeActivity.this, AlarmService.class);
            service.putExtra(INTENT_KEY_SCHEDULE_DATA_ALARM_SERVICE, scheduleItem);
            service.setAction(INTENT_ACTION_CREATE);
            startService(service);
        }
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

    /**************************************
     * Register app for push notification *
     **************************************/
    private void initPushNotification() {
        if (NetworkManager.isConnected(HomeActivity.this)) {
            new RegisterApp(HomeActivity.this).execute();
        }
    }
}