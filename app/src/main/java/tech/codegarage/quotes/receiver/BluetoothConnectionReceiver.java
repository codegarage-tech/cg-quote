package tech.codegarage.quotes.receiver;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/*
* Permission:
* <uses-permission android:name="android.permission.BLUETOOTH" />
*
* Declaration:
* <receiver
    android:name=".receiver.BluetoothConnectionReceiver">
      <intent-filter>
         <action android:name="android.bluetooth.adapter.action.STATE_CHANGED" />
      </intent-filter>
  </receiver>
* */
public class BluetoothConnectionReceiver extends BroadcastReceiver {

    private static final String TAG = BluetoothConnectionReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent != null) {
            String action = intent.getAction();

            if (action != null) {
                Log.d(TAG, "Received action: " + action);

                if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                    int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                    Log.d(TAG, "BluetoothAdapter.ACTION_STATE_CHANGED");
                    Log.d(TAG, "Bluetooth state: " + state);

                    //check the state of the bluetooth
                    if (state == BluetoothAdapter.STATE_ON) {
                        //the Bluetooth has been turned on
                        Log.d(TAG, "The Bluetooth has been turned on");
                    } else if (state == BluetoothAdapter.STATE_OFF) {
                        //the Bluetooth has been turned off
                        Log.d(TAG, "The Bluetooth has been turned off");
                    }
                }
            }
        }
    }
}
