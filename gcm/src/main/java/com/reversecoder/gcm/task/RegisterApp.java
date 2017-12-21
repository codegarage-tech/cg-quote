package com.reversecoder.gcm.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.reversecoder.gcm.util.GcmConfig;
import com.reversecoder.gcm.util.HttpRequestManager;
import com.reversecoder.gcm.util.UniqueIdManager;

import static com.reversecoder.gcm.util.GcmConfig.GCM_SENDER_ID;
import static com.reversecoder.gcm.util.GcmConfig.isNullOrEmpty;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class RegisterApp extends AsyncTask<String, String, HttpRequestManager.HttpResponse> {

    private static final String TAG = RegisterApp.class.getSimpleName();
    Context ctx;
    String regid = "";

    public RegisterApp(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected HttpRequestManager.HttpResponse doInBackground(String... params) {
        String msg = "";
        HttpRequestManager.HttpResponse response = null;
        try {
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(ctx);

            regid = gcm.register(GCM_SENDER_ID);
            msg = "registration ID=" + regid;

            response = sendRegistrationIdToBackend(regid, UniqueIdManager.getWlanMacAddress(ctx));

            storeRegistrationId(ctx, regid);
        } catch (Exception ex) {
            msg = "Error :" + ex.getMessage();
        }
        Log.d(TAG, msg);
        return response;
    }

    private void storeRegistrationId(Context ctx, String regid) {
        final SharedPreferences prefs = ctx.getSharedPreferences("GCM-SENDER", Context.MODE_PRIVATE);
//        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("registration_id", regid);
//        editor.putInt("appVersion", appVersion);
        editor.commit();

    }

    private HttpRequestManager.HttpResponse sendRegistrationIdToBackend(String regId, String uniqueId) {
//        URI url = null;
//	   url = new URI("http://10.0.2.2/GCMDemo/register.php?regId=" + regid);
//            url = new URI("http://www.billsmoneycollection.com/dicosta/myGCM/register.php?regId=" + regid);
//        String url = "http://codegarage.website/quote/registration.php?pushId=" + regId + "&uniqueId=" + uniqueId;

        HttpRequestManager.HttpResponse response = HttpRequestManager.doRestPostRequest(GcmConfig.getRegisterDeviceUrl(), GcmConfig.getRegisterDeviceParameters(uniqueId, regId, "", "", ""), null);
        return response;
    }

    @Override
    protected void onPostExecute(HttpRequestManager.HttpResponse result) {

        if (result.isSuccess() && !isNullOrEmpty(result.getResult().toString())) {
            Log.d(TAG, "success response: " + result.getResult().toString());
        }
    }
}