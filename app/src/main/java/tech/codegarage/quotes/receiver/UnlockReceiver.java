package tech.codegarage.quotes.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class UnlockReceiver extends BroadcastReceiver {

    private static final String TAG = UnlockReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (action != null) {
                if (intent.getAction().equalsIgnoreCase(Intent.ACTION_SCREEN_ON)) {
                    Log.d(TAG, "Screen is on.");
                }
                if (intent.getAction().equalsIgnoreCase(Intent.ACTION_SCREEN_OFF)) {
                    Log.d(TAG, "Screen is locked.");
                }
                if (intent.getAction().equalsIgnoreCase(Intent.ACTION_USER_PRESENT)) {
                    Log.d(TAG, "Screen is unlocked.");
                }
                if (intent.getAction().equalsIgnoreCase(Intent.ACTION_SHUTDOWN)) {
                    Log.d(TAG, "Phone is shutting down.");
                }
            }
        }
    }
}
