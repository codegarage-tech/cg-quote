package com.reversecoder.quote.util;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.reversecoder.quote.R;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class FragmentUtilsManager {

    public static void changeFragment(Activity activity, android.app.Fragment targetFragment, String fragmentName) {
        activity.getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, targetFragment, fragmentName)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    public static void changeSupportFragment(AppCompatActivity activity, Fragment targetFragment, String fragmentName) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, targetFragment, fragmentName)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    public static android.app.Fragment getVisibleFragment(Activity activity, String fragmentTag) {
        android.app.FragmentManager fm = activity.getFragmentManager();
        android.app.Fragment fragment = (android.app.Fragment) fm.findFragmentByTag(fragmentTag);
        return fragment;
    }

    public static android.app.Fragment getVisibleFragment(Activity activity, int containerResID) {
        android.app.FragmentManager fm = activity.getFragmentManager();
        android.app.Fragment fragment = (android.app.Fragment) fm.findFragmentById(containerResID);
        return fragment;
    }

    public static Fragment getVisibleSupportFragment(AppCompatActivity activity, String fragmentTag) {
        FragmentManager fm = activity.getSupportFragmentManager();
        Fragment fragment = (Fragment) fm.findFragmentByTag(fragmentTag);
        return fragment;
    }

    public static Fragment getVisibleSupportFragment(AppCompatActivity activity, int containerResID) {
        FragmentManager fm = activity.getSupportFragmentManager();
        Fragment fragment = (Fragment) fm.findFragmentById(containerResID);
        return fragment;
    }
}
