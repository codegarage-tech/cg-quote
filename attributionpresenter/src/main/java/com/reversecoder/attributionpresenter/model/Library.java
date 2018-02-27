package com.reversecoder.attributionpresenter.model;

/**
 * An enumeration of common Android libraries with their Attribution information.
 */
public enum Library {

    // Presentation
    BUTTER_KNIFE(new Attribution.Builder("Butter Knife", "http://jakewharton.github.io/butterknife/")
            .addLicenseInfo(new LicenseInfo("Copyright 2013 Jake Wharton", License.APACHE))
            .build()),
    PICASSO(new Attribution.Builder("Picasso", "http://square.github.io/picasso/")
            .addLicenseInfo(new LicenseInfo("Copyright 2013 Square, Inc.", License.APACHE))
            .build()),
    GLIDE(new Attribution.Builder("Glide", "https://github.com/bumptech/glide")
            .addLicenseInfo(new LicenseInfo("Copyright 2014 Google, Inc. All rights reserved.", License.BSD_3))
            .addLicenseInfo(new LicenseInfo("Copyright 2014 Google, Inc. All rights reserved.", License.MIT))
            .addLicenseInfo(new LicenseInfo("Copyright 2014 Google, Inc. All rights reserved.", License.APACHE))
            .build()),

    // Architecture
    DAGGER(new Attribution.Builder("Dagger", "http://square.github.io/dagger/")
            .addLicenseInfo(new LicenseInfo("Copyright 2013 Square, Inc.", License.APACHE))
            .build()),
    DAGGER_2(new Attribution.Builder("Dagger 2", "https://google.github.io/dagger/")
            .addLicenseInfo(new LicenseInfo("Copyright 2012 The Dagger Authors", License.APACHE))
            .build()),
    EVENT_BUS(new Attribution.Builder("EventBus", "http://greenrobot.org/eventbus/")
            .addLicenseInfo(new LicenseInfo("Copyright (C) 2012-2016 Markus Junginger, greenrobot", License.APACHE))
            .build()),
    RX_JAVA(new Attribution.Builder("RxJava", "https://github.com/ReactiveX/RxJava")
            .addLicenseInfo(new LicenseInfo("Copyright (c) 2016-present, RxJava Contributors", License.APACHE))
            .build()),
    RX_ANDROID(new Attribution.Builder("RxAndroid", "https://github.com/ReactiveX/RxAndroid")
            .addLicenseInfo(new LicenseInfo("Copyright 2015 The RxAndroid authors", License.APACHE))
            .build()),

    // Networking
    OK_HTTP(new Attribution.Builder("OkHttp", "http://square.github.io/okhttp/")
            .addLicenseInfo(new LicenseInfo("Copyright 2016 Square, Inc.", License.APACHE))
            .build()),
    RETROFIT(new Attribution.Builder("Retrofit", "http://square.github.io/retrofit/")
            .addLicenseInfo(new LicenseInfo("Copyright 2013 Square, Inc.", License.APACHE))
            .build()),

    // Parser
    GSON(new Attribution.Builder("Gson", "https://github.com/google/gson")
            .addLicenseInfo(new LicenseInfo("Copyright 2008 Google Inc.", License.APACHE))
            .build()),

    // ORM & Database
    REALM(new Attribution.Builder("Realm", "https://github.com/realm/realm-java")
            .addLicenseInfo(new LicenseInfo("Copyright 2016 Realm Inc.", License.APACHE))
            .build()),
    SUGAR_CHENNAIONE(new Attribution.Builder("Sugar", "https://github.com/chennaione/sugar")
            .addLicenseInfo(new LicenseInfo("Copyright (C) 2012 by Satya Narayan", License.MIT))
            .build()),
    LITEPAL_LITEPALFRAMEWORK(new Attribution.Builder("LitePal", "https://github.com/LitePalFramework/LitePal")
            .addLicenseInfo(new LicenseInfo("Copyright (C)  Tony Green, LitePal Framework Open Source Project", License.APACHE))
            .build()),

    // About page
    ANDROID_ABOUT_BOX_EGGHEADGAMES(new Attribution.Builder("Android About Box", "https://github.com/eggheadgames/android-about-box")
            .addLicenseInfo(new LicenseInfo("Copyright (c) 2017 Quality Mobile Puzzle Apps", License.MIT))
            .build()),
    MATERIAL_ABOUT_LIBRARY_DANIELSTONEUK(new Attribution.Builder("Material About Library", "https://github.com/daniel-stoneuk/material-about-library")
            .addLicenseInfo(new LicenseInfo("Copyright 2016 Daniel Stone", License.APACHE))
            .build()),

    // Arc view
    ARC_LAYOUT_FLORENT37(new Attribution.Builder("Arc Layout", "https://github.com/florent37/ArcLayout")
            .addLicenseInfo(new LicenseInfo("Copyright 2016 florent37, Inc.", License.APACHE))
            .build()),

    // License page
    ATTRIBUTE_PRESENTER_FRANMONTIEL(new Attribution.Builder("Attribution Presenter", "https://github.com/franmontiel/AttributionPresenter")
            .addLicenseInfo(new LicenseInfo("Copyright 2017 Francisco José Montiel Navarro", License.APACHE))
            .build()),

    // Slider
    CARD_SLIDER_ANDROID_RAMOTION(new Attribution.Builder("Card Slider Android", "https://github.com/Ramotion/cardslider-android")
            .addLicenseInfo(new LicenseInfo("Copyright (c) 2017 Ramotion", License.MIT))
            .build()),

    // Menu
    CONTEXT_MENU_ANDROID_YALANTIS(new Attribution.Builder("Context Menu Android", "https://github.com/Yalantis/Context-Menu.Android")
            .addLicenseInfo(new LicenseInfo("Copyright 2017, Yalantis", License.APACHE))
            .build()),
    RIBBLE_MENU_ARMCHA(new Attribution.Builder("Ribble Menu", "https://github.com/armcha/Ribble")
            .addLicenseInfo(new LicenseInfo("Copyright (c) 2017 Arman Chatikyan.", License.APACHE))
            .build()),
    CYCLE_MENU_CLEVEROAD(new Attribution.Builder("Cycle Menu", "https://github.com/Cleveroad/CycleMenu")
            .addLicenseInfo(new LicenseInfo("Copyright (c) 2016 Cleveroad Inc.", License.MIT))
            .build()),

    // Notification
    COOKIEBAR2_AVIRANABADY(new Attribution.Builder("CookieBar2", "https://github.com/AviranAbady/CookieBar2")
            .addLicenseInfo(new LicenseInfo("Copyright 2017", License.APACHE))
            .build()),

    // RecyclerView
    EASY_RECYCLERVIEW_JUDE95(new Attribution.Builder("Easy RecyclerView", "https://github.com/Jude95/EasyRecyclerView")
            .addLicenseInfo(new LicenseInfo("Copyright 2015 Jude", License.APACHE))
            .build()),
    STICKY_INDEX_EDSILFER(new Attribution.Builder("Sticky Index", "https://github.com/edsilfer/sticky-index")
            .addLicenseInfo(new LicenseInfo("Copyright 2015 Edgar da Silva Fernandes", License.APACHE))
            .build()),

    // FlipView
    ANDROID_FLIPVIEW_EMILSJOLANDER(new Attribution.Builder("Android FlipView", "https://github.com/emilsjolander/android-FlipView")
            .addLicenseInfo(new LicenseInfo("NA", License.APACHE))
            .build()),
    FOLDABLE_LAYOUT_ALEXVASILKOV(new Attribution.Builder("Foldable Layout", "https://github.com/alexvasilkov/FoldableLayout")
            .addLicenseInfo(new LicenseInfo("NA", License.APACHE))
            .build()),

    // ViewPager
    GLAZY_VIEWPAGER_KANNANANBU(new Attribution.Builder("Glazy ViewPager", "https://github.com/kannan-anbu/glazy-viewpager")
            .addLicenseInfo(new LicenseInfo("Copyright 2017 Kannan-anbu.", License.APACHE))
            .build()),

    // ImageView
    KENBURNSVIEW_FLAVIOARFARIA(new Attribution.Builder("KenBurnsView", "https://github.com/flavioarfaria/KenBurnsView")
            .addLicenseInfo(new LicenseInfo("NA", License.APACHE))
            .build()),

    // Locale
    LOCALE_CHANGER_FRANMONTIEL(new Attribution.Builder("Locale Changer", "https://github.com/franmontiel/LocaleChanger")
            .addLicenseInfo(new LicenseInfo("Copyright (C) 2017 Francisco José Montiel Navarro", License.APACHE))
            .build()),

    // TextView
    MULTI_COLOR_TEXTVIEW_HAYI(new Attribution.Builder("Multi Color TextView", "https://github.com/ha-yi/MultiColorTextView")
            .addLicenseInfo(new LicenseInfo("NA", License.NONE))
            .build()),

    //Ripple
    SHAPE_RIPPLE_POLDZ123(new Attribution.Builder("Shape Ripple", "https://github.com/poldz123/ShapeRipple")
            .addLicenseInfo(new LicenseInfo("Copyright 2016 Rodolfo Navalon", License.APACHE))
            .build()),

    // Crash log
    SHERLOCK_AJITSING(new Attribution.Builder("Sherlock", "https://github.com/ajitsing/Sherlock")
            .addLicenseInfo(new LicenseInfo("Copyright (C) 2017 Ajit Singh", License.APACHE))
            .build()),

    // Tutorial
    TUTORS_POPALAY(new Attribution.Builder("Tutors", "https://github.com/Popalay/Tutors")
            .addLicenseInfo(new LicenseInfo("NA", License.APACHE))
            .build()),

    // Playstore update
    UPDATE_CHECKER_KOBEUMUT(new Attribution.Builder("Update Checker", "https://github.com/kobeumut/UpdateChecker")
            .addLicenseInfo(new LicenseInfo("Copyright 2017 Gri Software Inc.", License.APACHE))
            .build()),

    // App intro
    VERTICAL_INTRO_ARMCHA(new Attribution.Builder("Vertical Intro", "https://github.com/armcha/Vertical-Intro")
            .addLicenseInfo(new LicenseInfo("Copyright (c) 2017 Arman Chatikyan.", License.APACHE))
            .build()),

    // Sidebar Fast Scroll
    WAVE_SIDEBAR_SOLARTISAN(new Attribution.Builder("Wave SideBar", "https://github.com/Solartisan/WaveSideBar")
            .addLicenseInfo(new LicenseInfo("NA", License.NONE))
            .build()),

    //Animation
    BUNGEE_BINARYFINERY(new Attribution.Builder("Bungee", "https://github.com/Binary-Finery/Bungee")
            .addLicenseInfo(new LicenseInfo("NA", License.APACHE))
            .build()),
    ANDROID_VIEW_ANIMATIONS_DAIMAJIA(new Attribution.Builder("Android View Animations", "https://github.com/daimajia/AndroidViewAnimations")
            .addLicenseInfo(new LicenseInfo("Copyright (c) 2014 daimajia", License.MIT))
            .build()),
    LOTTIE_ANDROID_AIRBNB(new Attribution.Builder("Lottie Android", "https://github.com/airbnb/lottie-android")
            .addLicenseInfo(new LicenseInfo("Copyright 2018 Airbnb, Inc.", License.APACHE))
            .build()),

    // ProgressBar
    MASK_PROGRESSVIEW_IAMMERT(new Attribution.Builder("Mask ProgressView", "https://github.com/iammert/MaskProgressView")
            .addLicenseInfo(new LicenseInfo("Copyright 2015 Mert Şimşek.", License.APACHE))
            .build()),
    FILL_ME_PATRYK1007(new Attribution.Builder("Fill Me", "https://github.com/patryk1007/Fillme")
            .addLicenseInfo(new LicenseInfo("Copyright (C) 2018 patryk1007", License.APACHE))
            .build()),
    PROGRESS_LAYOUT_IAMMERT(new Attribution.Builder("Progress Layout", "https://github.com/iammert/ProgressLayout")
            .addLicenseInfo(new LicenseInfo("Copyright 2015 Mert Şimşek.", License.APACHE))
            .build()),

    //Search or filter
    FABULOUS_FILTER_KRUPEN(new Attribution.Builder("Fabulous Filter", "https://github.com/Krupen/FabulousFilter")
            .addLicenseInfo(new LicenseInfo("Copyright 2017 Krupen Ghetiya", License.APACHE))
            .build());


    private Attribution attribution;

    Library(Attribution attribution) {
        this.attribution = attribution;
    }

    public Attribution getAttribution() {
        return attribution;
    }
}
