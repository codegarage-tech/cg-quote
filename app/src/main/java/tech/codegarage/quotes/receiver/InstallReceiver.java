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
public class InstallReceiver extends BroadcastReceiver {

    private static final String TAG = InstallReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (action != null) {
                if (intent.getAction().equalsIgnoreCase(Intent.ACTION_PACKAGE_REMOVED)) {
                    Log.d(TAG, "PACKAGE_REMOVED");
                }
                if (intent.getAction().equalsIgnoreCase(Intent.ACTION_PACKAGE_ADDED)) {
                    Log.d(TAG, "PACKAGE_ADDED");
                }

                Uri data = intent.getData();
                if (data != null) {
                    String packageName = data.toString().split("\\:")[1];
                    Log.d(TAG, "PackageName: " + packageName);
                }
            }
        }
    }
}
