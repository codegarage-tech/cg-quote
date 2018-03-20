package com.reversecoder.gcm.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONObject;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class GcmConfig {

    public static final String GCM_SENDER_ID = "744214366832";
    private static final String TAG = GcmConfig.class.getSimpleName();
    public static final String SESSION_GCM_REGISTER_DATA = "SESSION_GCM_REGISTER_DATA";
    public static final String INTENT_KEY_GCM_DATA_CONTENT_INTENT = "INTENT_KEY_GCM_DATA_CONTENT_INTENT";

    public static String getRegisterDeviceUrl() {
        String url = "http://codegarage.tech/warehouse/quote/registration.php";
        Log.d(TAG, "getRegisterDeviceUrl: " + url);
        return url;
    }

    public static JSONObject getRegisterDeviceParameters(String uniqueId, String registrationId, String country, String state, String city) {
        JSONObject params = HttpRequestManager.HttpParameter.getInstance()
                .addJSONParam("uniqueId", uniqueId)
                .addJSONParam("pushId", registrationId)
//                .addJSONParam("country", (isNullOrEmpty(country) ? "" : country))
//                .addJSONParam("state", (isNullOrEmpty(state) ? "" : state))
//                .addJSONParam("city", (isNullOrEmpty(city) ? "" : city))
                .getJSONParam();
        Log.d(TAG, "getRegisterDeviceParameters: " + params.toString());
        return params;
    }

    public static boolean isNullOrEmpty(String myString) {
        return myString == null ? true : myString.length() == 0 || myString.equalsIgnoreCase("null") || myString.equalsIgnoreCase("");
    }

    public static void setStringSetting(Context context, String key, String value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getStringSetting(Context context, String key) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(key, "");
    }

    public static String getStringSetting(Context context, String key, String defaultValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(key, defaultValue);
    }
}