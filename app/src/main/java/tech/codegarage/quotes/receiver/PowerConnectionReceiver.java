package tech.codegarage.quotes.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class PowerConnectionReceiver extends BroadcastReceiver {

    private static final String TAG = PowerConnectionReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent != null) {
            String action = intent.getAction();
            if (action != null) {
                if (action.equalsIgnoreCase(Intent.ACTION_POWER_CONNECTED)) {
                    Log.d(TAG, "Power Connected");
                }
                if (action.equalsIgnoreCase(Intent.ACTION_POWER_DISCONNECTED)) {
                    Log.d(TAG, "Power Disconnected");
                }
            }
        }
    }
}
