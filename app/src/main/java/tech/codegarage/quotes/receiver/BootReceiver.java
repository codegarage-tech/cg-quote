package tech.codegarage.quotes.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.os.UserManagerCompat;
import android.util.Log;

/**
 * @author Md. Rashadul Alam
 */
public class BootReceiver extends BroadcastReceiver {

    private static final String TAG = BootReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        boolean bootCompleted = false;

        //Check boot completed
        if (intent != null) {
            String action = intent.getAction();

            if (action != null) {
                Log.d(TAG, "Received action: " + action + ", user unlocked: " + UserManagerCompat.isUserUnlocked(context));

                //Check boot type
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    bootCompleted = Intent.ACTION_LOCKED_BOOT_COMPLETED.equals(action);
                } else {
                    bootCompleted = Intent.ACTION_BOOT_COMPLETED.equals(action);
                }
            }
        }
        if (!bootCompleted) {
            return;
        }

        /*************************
         * Do further tasks here *
         *************************/
    }
}