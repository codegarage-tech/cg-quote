package com.reversecoder.gcm.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.reversecoder.gcm.R;
import com.reversecoder.gcm.broadcast.GcmBroadcastReceiver;
import com.reversecoder.gcm.model.GcmData;
import com.reversecoder.gcm.util.GcmUtils;

import static com.reversecoder.gcm.util.GcmConfig.INTENT_KEY_GCM_DATA_CONTENT_INTENT;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class GcmIntentService extends IntentService {

    private static final String TAG = GcmIntentService.class.getSimpleName();

    public GcmIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
//                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
//                sendNotification("Deleted messages on server: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                for (int i = 0; i < 5; i++) {
                    Log.d(TAG, "Working... " + (i + 1) + "/5 @ " + SystemClock.elapsedRealtime());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                    }
                }
                Log.d(TAG, "Completed work @ " + SystemClock.elapsedRealtime());

                //Parse notification data
                String title = extras.getString("title");
                String subtitle = extras.getString("subtitle");
                String message = extras.getString("message");
                String tickerText = extras.getString("tickerText");
                String from = extras.getString("from");
                String sound = extras.getString("sound");
                String vibrate = extras.getString("vibrate");

                GcmData gcmData = new GcmData(title, subtitle, message, tickerText, from, sound, vibrate);

                //Send notification
                sendNotification(this, gcmData);

                Log.d(TAG, "Received:1 " + gcmData.toString());
                Log.d(TAG, "Received:2 " + extras.toString());
            }
        }

        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(Context context, GcmData gcmData) {

        Intent intentGcmDetail;

        //Open other app from google play store on notification click
//        GcmUtils.openPublisher(context, GcmUtils.BuildType.GOOGLE,
//                context.getString(R.string.gcm_app_publisher_id), context.getApplicationContext().getPackageName());

        //Leave review in google play store on notification click
        intentGcmDetail = new Intent(Intent.ACTION_VIEW);
        intentGcmDetail.setData(GcmUtils.getPlayStoreUri(context));

        //Open new activity on notification click
//        intentGcmDetail = new Intent(context, GcmManager.getContentClass());
//        intentGcmDetail.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        intentGcmDetail.putExtra(INTENT_KEY_GCM_DATA_CONTENT_INTENT, gcmData);

        PendingIntent pendingIntentGcm = PendingIntent.getActivity(context, gcmData.getId(), intentGcmDetail, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();
        bigStyle.setBigContentTitle(gcmData.getTitle());
        bigStyle.bigText(getString(R.string.txt_tap_for_more_details));
        Notification n = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notification_gcm)
                .setContentTitle(gcmData.getTitle())
                .setContentText(getString(R.string.txt_tap_for_more_details))
                .setPriority(Notification.PRIORITY_MAX)
                .setWhen(0)
                .setStyle(bigStyle)
                .setContentIntent(pendingIntentGcm)
                .setAutoCancel(true)
                .build();

        n.defaults |= Notification.DEFAULT_VIBRATE;
        n.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        n.defaults |= Notification.DEFAULT_SOUND;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(gcmData.getId(), n);
    }
}