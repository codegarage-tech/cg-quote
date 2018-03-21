package com.eggheadgames.aboutbox.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.danielstone.materialaboutlibrary.MaterialAboutActivity;
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.items.MaterialAboutItemOnClickAction;
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.eggheadgames.aboutbox.AboutBoxUtils;
import com.eggheadgames.aboutbox.AboutConfig;
import com.eggheadgames.aboutbox.IAnalytic;
import com.eggheadgames.aboutbox.R;
import com.eggheadgames.aboutbox.listener.LicenseClickListener;
import com.eggheadgames.aboutbox.share.EmailUtil;
import com.eggheadgames.aboutbox.share.ShareUtil;
import com.eggheadgames.aboutbox.view.AnimatedImageView;
import com.eggheadgames.aboutbox.view.AnimatedTextView;
import com.eggheadgames.aboutbox.view.ArcView;

public class AboutActivity extends MaterialAboutActivity {

    private static LicenseClickListener mLicenseClickListener;

    //toolbar
    ArcView arcMenuView;
    AnimatedImageView arcMenuImage;
    AnimatedTextView toolbarTitle;
    Toolbar toolbar;

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, AboutActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivity(intent);
    }

    public static void setLicenseListener(LicenseClickListener licenseClickListener) {
        mLicenseClickListener = licenseClickListener;
    }

    @Override
    public void setToolbar() {
        toolbarTitle = (AnimatedTextView) findViewById(R.id.toolbarTitle);
        arcMenuImage = (AnimatedImageView) findViewById(R.id.arcImage);
        arcMenuView = (ArcView) findViewById(R.id.arcView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbarTitle.setAnimatedText(getString(R.string.mal_title_about), 0L);

        arcMenuImage.setAnimatedImage(R.drawable.arrow_left, 0L);
        arcMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public int setLayoutResource() {
        return R.layout.mal_material_about_activity;
    }

    @Override
    protected MaterialAboutList getMaterialAboutList(Context context) {

        final AboutConfig config = AboutConfig.getInstance();

        return new MaterialAboutList.Builder()
                .addCard(buildGeneralInfoCard(config))
                .addCard(buildSupportCard(config))
                .addCard(buildShareCard(config))
                .addCard(buildAboutCard(config))
                .addCard(buildSocialNetworksCard(config))
                .addCard(buildPrivacyCard(config))
                .build();
    }

    @NonNull
    private MaterialAboutCard buildGeneralInfoCard(AboutConfig config) {
        MaterialAboutCard.Builder generalInfoCardBuilder = new MaterialAboutCard.Builder();

        generalInfoCardBuilder.addItem(new MaterialAboutTitleItem.Builder()
                .text(config.appName)
                .icon(config.appIcon)
                .build());

        generalInfoCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.egab_version)
                .subText(config.version)
                .build());

        if (!TextUtils.isEmpty(config.author)) {
            generalInfoCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                    .text(R.string.egab_author)
                    .subText(config.author)
                    .build());
        }

        if (!TextUtils.isEmpty(config.extra) && !TextUtils.isEmpty(config.extraTitle)) {
            generalInfoCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                    .text(config.extraTitle)
                    .subTextHtml(config.extra)
                    .build());
        }

        return generalInfoCardBuilder.build();
    }

    @NonNull
    private MaterialAboutCard buildSupportCard(final AboutConfig config) {
        MaterialAboutCard.Builder card = new MaterialAboutCard.Builder();

        if (!TextUtils.isEmpty(config.guideHtmlPath)) {
            card.addItem(itemHelper(R.string.egab_guide, R.drawable.ic_help_green,
                    new MaterialAboutItemOnClickAction() {
                        @Override
                        public void onClick() {
                            if (config.dialog == null) {
                                AboutBoxUtils.openHTMLPage(AboutActivity.this, config.guideHtmlPath);
                            } else {
                                config.dialog.open(AboutActivity.this, config.guideHtmlPath, getString(R.string.egab_guide));
                            }
                            logUIEventName(config.analytics, config.logUiEventName, getString(R.string.egab_guide));
                        }
                    })
            );
        }
        card.addItem(itemHelper(R.string.egab_contact_support, R.drawable.ic_email_black,
                new MaterialAboutItemOnClickAction() {
                    @Override
                    public void onClick() {
                        EmailUtil.contactUs(AboutActivity.this);
                        logUIEventName(config.analytics, config.logUiEventName, getString(R.string.egab_contact_log_event));
                    }
                }));

        return card.build();
    }

    @NonNull
    private MaterialAboutCard buildShareCard(final AboutConfig config) {
        MaterialAboutCard.Builder card = new MaterialAboutCard.Builder();
        if (config.buildType != null && !TextUtils.isEmpty(config.packageName)) {
            card.addItem(itemHelper(R.string.egab_leave_review, R.drawable.ic_review,
                    new MaterialAboutItemOnClickAction() {
                        @Override
                        public void onClick() {
                            AboutBoxUtils.openApp(AboutActivity.this, config.buildType, config.packageName);
                            logUIEventName(config.analytics, config.logUiEventName, getString(R.string.egab_review_log_event));
                        }
                    }));
        }
        card.addItem(itemHelper(R.string.egab_share, R.drawable.ic_share_black,
                new MaterialAboutItemOnClickAction() {
                    @Override
                    public void onClick() {
                        if (config.share == null) {
                            ShareUtil.share(AboutActivity.this);
                        } else {
                            config.share.share(AboutActivity.this);
                        }
                        logUIEventName(config.analytics, config.logUiEventName, getString(R.string.egab_share_log_event));
                    }
                }));
        return card.build();
    }

    @NonNull
    private MaterialAboutCard buildAboutCard(final AboutConfig config) {
        MaterialAboutCard.Builder card = new MaterialAboutCard.Builder();
        if (config.buildType != null && !TextUtils.isEmpty(config.appPublisherId) && !TextUtils.isEmpty(config.packageName)) {
            card.addItem(itemHelper(R.string.egab_try_other_apps, R.drawable.ic_try_other_apps,
                    new MaterialAboutItemOnClickAction() {
                        @Override
                        public void onClick() {
                            AboutBoxUtils.openPublisher(AboutActivity.this, config.buildType,
                                    config.appPublisherId, config.packageName);
                            logUIEventName(config.analytics, config.logUiEventName, getString(R.string.egab_try_other_app_log_event));
                        }
                    }));
        }
        if (!TextUtils.isEmpty(config.companyHtmlPath) && !TextUtils.isEmpty(config.aboutLabelTitle)) {
            card.addItem(new MaterialAboutActionItem.Builder()
                    .text(config.aboutLabelTitle)
                    .icon(R.drawable.ic_about_black)
                    .setOnClickAction(new MaterialAboutItemOnClickAction() {
                        @Override
                        public void onClick() {
                            if (config.dialog == null) {
                                AboutBoxUtils.openHTMLPage(AboutActivity.this, config.companyHtmlPath);
                            } else {
                                config.dialog.open(AboutActivity.this, config.companyHtmlPath, config.aboutLabelTitle);
                            }
                            logUIEventName(config.analytics, config.logUiEventName, config.aboutLabelTitle);
                        }
                    })
                    .build());
        }
        return card.build();
    }

    @NonNull
    private MaterialAboutCard buildSocialNetworksCard(final AboutConfig config) {
        MaterialAboutCard.Builder card = new MaterialAboutCard.Builder();
        if (!TextUtils.isEmpty(config.facebookUserName)) {
            card.addItem(new MaterialAboutActionItem.Builder()
                    .text(R.string.egab_facebook_label)
                    .subText(config.facebookUserName)
                    .icon(R.drawable.ic_facebook_24)
                    .setOnClickAction(new MaterialAboutItemOnClickAction() {
                        @Override
                        public void onClick() {
                            AboutBoxUtils.getOpenFacebookIntent(AboutActivity.this, config.facebookUserName);
                            logUIEventName(config.analytics, config.logUiEventName, getString(R.string.egab_facebook_log_event));
                        }
                    })
                    .build());
        }
        if (!TextUtils.isEmpty(config.twitterUserName)) {
            card.addItem(new MaterialAboutActionItem.Builder()
                    .text(R.string.egab_twitter_label)
                    .subText(config.twitterUserName)
                    .icon(R.drawable.ic_twitter_24dp)
                    .setOnClickAction(new MaterialAboutItemOnClickAction() {
                        @Override
                        public void onClick() {
                            AboutBoxUtils.startTwitter(AboutActivity.this, config.twitterUserName);
                            logUIEventName(config.analytics, config.logUiEventName, getString(R.string.egab_twitter_log_event));
                        }
                    })
                    .build());
        }
        if (!TextUtils.isEmpty(config.webHomePage)) {
            card.addItem(new MaterialAboutActionItem.Builder()
                    .text(R.string.egab_web_label)
                    .subText(config.webHomePage.replaceFirst("^https?://", "").replaceAll("/$", ""))
                    .icon(R.drawable.ic_web_black_24dp)
                    .setOnClickAction(new MaterialAboutItemOnClickAction() {
                        @Override
                        public void onClick() {
                            AboutBoxUtils.openHTMLPage(AboutActivity.this, config.webHomePage);
                            logUIEventName(config.analytics, config.logUiEventName, getString(R.string.egab_website_log_event));
                        }
                    })
                    .build());
        }
        return card.build();
    }

    @NonNull
    private MaterialAboutCard buildPrivacyCard(final AboutConfig config) {
        MaterialAboutCard.Builder card = new MaterialAboutCard.Builder();
        if (!TextUtils.isEmpty(config.privacyHtmlPath)) {
            card.addItem(itemHelper(R.string.egab_privacy_policy, R.drawable.ic_privacy,
                    new MaterialAboutItemOnClickAction() {
                        @Override
                        public void onClick() {
//                            if (config.dialog == null) {
                            AboutBoxUtils.openHTMLPage(AboutActivity.this, config.privacyHtmlPath);
//                            } else {
//                                config.dialog.open(AboutActivity.this, config.privacyHtmlPath, getString(R.string.egab_privacy_policy));
//                            }
                            logUIEventName(config.analytics, config.logUiEventName, getString(R.string.egab_privacy_log_event));
                        }
                    }));
        }
        if (!TextUtils.isEmpty(config.acknowledgmentHtmlPath)) {
            card.addItem(itemHelper(R.string.egab_acknowledgements, R.drawable.ic_acknowledgements,
                    new MaterialAboutItemOnClickAction() {
                        @Override
                        public void onClick() {
//                            if (config.dialog == null) {
                            AboutBoxUtils.openHTMLPage(AboutActivity.this, config.acknowledgmentHtmlPath);
//                            } else {
//                                config.dialog.open(AboutActivity.this, config.acknowledgmentHtmlPath, getString(R.string.egab_acknowledgements));
//                            }
                            logUIEventName(config.analytics, config.logUiEventName, getString(R.string.egab_acknowledgements_log_event));
                        }
                    })
            );
        }
        card.addItem(itemHelper(R.string.egab_third_party_notice, R.drawable.ic_license,
                new MaterialAboutItemOnClickAction() {
                    @Override
                    public void onClick() {
                        mLicenseClickListener.onLicenseClick();
                        logUIEventName(config.analytics, config.logUiEventName, getString(R.string.egab_third_party_notice));
                    }
                }));
        return card.build();
    }

    private MaterialAboutActionItem itemHelper(int name, int icon, MaterialAboutItemOnClickAction clickAction) {
        return new MaterialAboutActionItem.Builder()
                .text(name)
                .icon(icon)
                .setOnClickAction(clickAction)
                .build();
    }

    @Override
    protected CharSequence getActivityTitle() {
        return getString(R.string.egab_about_screen_title);
    }

    private void logUIEventName(IAnalytic analytics, String eventType, String eventValue) {
        if (analytics != null) {
            analytics.logUiEvent(eventType, eventValue);
        }
    }
}