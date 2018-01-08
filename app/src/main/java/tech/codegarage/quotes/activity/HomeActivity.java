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
import tech.codegarage.quotes.fragment.AuthorFragment;
import tech.codegarage.quotes.fragment.FavouriteFragment;
import tech.codegarage.quotes.interfaces.OnFragmentBackPressedListener;
import tech.codegarage.quotes.util.AllConstants;
import tech.codegarage.quotes.util.AppUtils;
import tech.codegarage.quotes.util.FragmentUtilsManager;
import com.eggheadgames.aboutbox.AboutBoxUtils;
import com.eggheadgames.aboutbox.AboutConfig;
import com.eggheadgames.aboutbox.activity.AboutActivity;
import com.eggheadgames.aboutbox.listener.LicenseClickListener;
import com.reversecoder.attributionpresenter.activity.LicenseActivity;
import com.reversecoder.library.storage.SessionManager;
import tech.codegarage.quotes.R;

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

        initViews();

        if (isArcIcon || isDrawerOpened) {
            setArcArrowState();
        } else {
            setArcHamburgerIconState();
        }
        setToolBarTitle(activeTitle);
        updateUserInfo();

        if (savedInstanceState == null) {
            SessionManager.setStringSetting(HomeActivity.this, AllConstants.SESSION_SELECTED_RIBBLE_MENU, getString(R.string.ribble_menu_item_home));
            handleFragmentChanges(HomeActivity.this, getString(R.string.ribble_menu_item_home), new AuthorFragment());
        }

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
                            } else if (item.getId().getName().equalsIgnoreCase(NavigationId.RATE_US.INSTANCE.getName())) {
                                AboutBoxUtils.openApp(HomeActivity.this, AboutConfig.BuildType.GOOGLE, getApplicationContext().getPackageName());
                            } else if (item.getId().getName().equalsIgnoreCase(NavigationId.OTHER_APPS.INSTANCE.getName())) {
                                AboutBoxUtils.openPublisher(HomeActivity.this, AboutConfig.BuildType.GOOGLE, getString(R.string.txt_publisher), getApplicationContext().getPackageName());
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
        } else if (currentTag.equalsIgnoreCase(NavigationId.RATE_US.INSTANCE.getName())) {
            checkPosition = 2;
        } else if (currentTag.equalsIgnoreCase(NavigationId.OTHER_APPS.INSTANCE.getName())) {
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
                for(Fragment fragment : fragmentList){
                    if(fragment instanceof OnFragmentBackPressedListener){
                        ((OnFragmentBackPressedListener)fragment).onFragmentBackPressed();
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
}