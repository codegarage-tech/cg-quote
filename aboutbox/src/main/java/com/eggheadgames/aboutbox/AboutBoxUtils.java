package com.eggheadgames.aboutbox;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public final class AboutBoxUtils {

    public final static String playStoreAppURI = "https://play.google.com/store/apps/details?id=";
    public final static String amznStoreAppURI = "https://www.amazon.com/gp/mas/dl/android?p=";

    private AboutBoxUtils() {
        //nothing
    }

    public static void getOpenFacebookIntent(Activity context, String name) {
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/" + name));
            context.startActivity(intent);
        } catch (Exception e) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + name));
                context.startActivity(intent);
            } catch (Exception e1) {
                Toast.makeText(context, R.string.egab_can_not_open, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void startTwitter(Activity context, String name) {
        try {
            context.getPackageManager().getPackageInfo("com.twitter.android", 0);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + name));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + name));
                context.startActivity(intent);
            } catch (Exception e1) {
                Toast.makeText(context, R.string.egab_can_not_open, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void openApp(Activity context, AboutConfig.BuildType buildType, String packageName) {
        String appURI = null;
        String webURI = null;
        switch (buildType) {
            case GOOGLE:
                appURI = "market://details?id=" + packageName;
                webURI = playStoreAppURI + packageName;
                break;
            case AMAZON:
                appURI = "amzn://apps/android?p=" + packageName;
                webURI = amznStoreAppURI + packageName;
                break;
            default:
                //nothing
        }
        openApplication(context, appURI, webURI);
    }

    public static void openPublisher(Activity context, AboutConfig.BuildType buildType, String publisher, String packageName) {
        String appURI = null;
        String webURI = null;
        switch (buildType) {
            case GOOGLE:
                // see:
                // https://developer.android.com/distribute/marketing-tools/linking-to-google-play.html#OpeningPublisher
                // https://stackoverflow.com/questions/32029408/how-to-open-developer-page-on-google-play-store-market
                // https://issuetracker.google.com/65244694
                if (publisher.matches("\\d+")) {
                    webURI = "https://play.google.com/store/apps/dev?id=" + publisher;
                    appURI = webURI;
                } else {
                    appURI = "market://search?q=pub:" + publisher;
                    webURI = "https://play.google.com/store/search?q=pub:" + publisher;
                }
                break;
            case AMAZON:
                appURI = "amzn://apps/android?showAll=1&p=" + packageName;
                webURI = "http://www.amazon.com/gp/mas/dl/android?showAll=1&p=" + packageName;
                break;
            default:
                //nothing
        }
        openApplication(context, appURI, webURI);
    }

    public static void openApplication(Activity context, String appURI, String webURI) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(appURI)));
        } catch (ActivityNotFoundException e1) {
            try {
                openHTMLPage(context, webURI);
            } catch (ActivityNotFoundException e2) {
                Toast.makeText(context, R.string.egab_can_not_open, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void openHTMLPage(Activity context, String htmlPath) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(htmlPath)));
    }
}
