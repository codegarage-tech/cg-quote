package com.eggheadgames.aboutbox;

public class AboutConfig {

    public enum BuildType {AMAZON, GOOGLE}

    //    general info
    public String appName;
    public int appIcon;
    public String version;
    public String author;
    public String extra;
    public String extraTitle;
    public String aboutLabelTitle;
    public String logUiEventName;
    public String facebookUserName;
    public String twitterUserName;
    public String webHomePage;
    public String guideHtmlPath;
    public String appPublisherId;
    public String companyHtmlPath;
    public String privacyHtmlPath;
    public String acknowledgmentHtmlPath;
    public BuildType buildType;
    public String packageName;

    //    custom analytics, dialog and share
    public IAnalytic analytics;
    public IDialog dialog;
    public IShare share;

    //    email
    public String emailAddress;
    public String emailSubject;
    public String emailBody;

    //    share
    public String shareMessage;
    public String sharingTitle;

    public static class SingletonHolder {
        public static final AboutConfig HOLDER_INSTANCE = new AboutConfig();
    }

    public static AboutConfig getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

}
