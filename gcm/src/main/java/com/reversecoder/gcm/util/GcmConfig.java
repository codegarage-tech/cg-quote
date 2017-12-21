package com.reversecoder.gcm.util;

import android.util.Log;

import org.json.JSONObject;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class GcmConfig {

    public static final String GCM_SENDER_ID = "744214366832";
    private static final String TAG = GcmConfig.class.getSimpleName();

    public static String getRegisterDeviceUrl() {
        String url = "http://codegarage.website/quote/registration.php";
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
        Log.d(TAG, "getUpdateUserParameters: " + params.toString());
        return params;
    }

    public static boolean isNullOrEmpty(String myString) {
        return myString == null ? true : myString.length() == 0 || myString.equalsIgnoreCase("null") || myString.equalsIgnoreCase("");
    }
}