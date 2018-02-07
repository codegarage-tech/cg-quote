package com.reversecoder.gcm.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.reversecoder.gcm.listener.RegisterAppListener;
import com.reversecoder.gcm.util.GcmConfig;
import com.reversecoder.gcm.util.HttpRequestManager;
import com.reversecoder.gcm.util.UniqueIdManager;

import static com.reversecoder.gcm.util.GcmConfig.GCM_SENDER_ID;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class RegisterApp extends AsyncTask<String, String, HttpRequestManager.HttpResponse> {

    private static final String TAG = RegisterApp.class.getSimpleName();
    private Context mContext;
    private RegisterAppListener mRegisterAppListener;

    public RegisterApp(Context context, RegisterAppListener registerAppListener) {
        this.mRegisterAppListener = registerAppListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected HttpRequestManager.HttpResponse doInBackground(String... params) {

        HttpRequestManager.HttpResponse response = null;
        try {

            //Get GCM registration id
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(mContext);
            String mPushId = gcm.register(GCM_SENDER_ID);
            Log.d(TAG, "mPushId: " + mPushId);

            //Get GCM unique id for each device
            String mUniqueId = UniqueIdManager.getWlanMacAddress(mContext);
            Log.d(TAG, "mUniqueId: " + mUniqueId);

            //Send response to the server
            response = HttpRequestManager.doRestPostRequest(GcmConfig.getRegisterDeviceUrl(), GcmConfig.getRegisterDeviceParameters(mUniqueId, mPushId, "", "", ""), null);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return response;
    }

    @Override
    protected void onPostExecute(HttpRequestManager.HttpResponse result) {
        if (result != null) {
            if (result.isSuccess()) {
                //Send response to the parent activity
                mRegisterAppListener.registerApp(result);
                Log.d(TAG, "success response: " + result.getResult().toString());
//            GcmConfig.setStringSetting(mContext, GcmConfig.SESSION_GCM_PUSH_ID, mPushId);
            }
        } else {
            Log.d(TAG, "success response: " + "No response found");
        }
    }
}