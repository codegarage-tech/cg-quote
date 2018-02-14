package com.reversecoder.gcm.util;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class UniqueIdManager {

    public enum UNIQUE_ID {IMEI, ANDROID_ID, WLAN_MAC_ADDRESS, BLUETOOTH_ADDRESS}

    public static String getUniqueId(Context context, UNIQUE_ID uniqueId) {
        String mUniqueId = "";

        switch (uniqueId) {
            case IMEI:
                mUniqueId = getIemiId(context);
                break;
            case ANDROID_ID:
                mUniqueId = getAndroidId(context);
                break;
            case WLAN_MAC_ADDRESS:
                mUniqueId = getWlanMacAddress(context);
                break;
            case BLUETOOTH_ADDRESS:
                mUniqueId = getBluetoothAddress(context);
                break;
        }

        return mUniqueId;
    }

    public static String getIemiId(Context context) {
        // need permission: <uses-permission android:name="android.permission.READ_PHONE_STATE" />
        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        String imeiId = TelephonyMgr.getDeviceId();
        return imeiId;
    }

    public static String getAndroidId(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;
    }

    /***********************************
    *Need to fix for above marshmallow *
    ************************************/
    public static String getWlanMacAddress(Context context) {
        // Auto granted permission
        // need permission: <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
        WifiManager m_wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String wlanMacAddress = m_wm.getConnectionInfo().getMacAddress();
        return wlanMacAddress;
    }


    /***********************************
     *Need to fix for above marshmallow *
     ************************************/
    public static String getBluetoothAddress(Context context) {
        // Auto granted permission
        // need permission: <uses-permission android:name="android.permission.BLUETOOTH" />
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String bluetoothAddress = bluetoothAdapter.getAddress();
        return bluetoothAddress;
    }
}