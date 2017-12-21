package com.reversecoder.quote.util;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import com.reversecoder.quote.R;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public class ClipboardHandler {

    /**
     * Copy Data to Clipboard Methods
     **/

    //First Check SDK version because SDK below honeycomb has different method and above honeycomb is different
    public static void copyToClipboard(Context mContext,
                                       String data) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            copyToClipboardBelowHoneyComb(mContext, data);
        } else {
            copyToClipboardAboveHoneyComb(mContext, data);
        }
        Toast.makeText(mContext, mContext.getString(R.string.txt_copied_to_clipboard), Toast.LENGTH_SHORT).show();
    }

    //For Honeycomb and above devices
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static void copyToClipboardAboveHoneyComb(Context mContext,
                                                      String data) {
        ClipboardManager clipboard = (ClipboardManager) mContext
                .getSystemService(Context.CLIPBOARD_SERVICE);//Get Clipboard Manager
        ClipData clip = ClipData.newPlainText(
                "clipboard data ", data);//Save plain text data to clip data
        clipboard.setPrimaryClip(clip);//set clip data as primary clip
    }

    //For below Honeycomb devices
    @SuppressWarnings("deprecation")
    private static void copyToClipboardBelowHoneyComb(Context mContext,
                                                      String data) {
        android.text.ClipboardManager clipboard = (android.text.ClipboardManager) mContext
                .getSystemService(Context.CLIPBOARD_SERVICE);//get Clipboard manager
        clipboard.setText(data);//set text to clipboard
    }

    /**
     * Get Data from Clipboard Methods
     **/

    //First Check SDK version because SDK below honeycomb has different method and above honeycomb is different
    public static String getDataFromClipboard(Context mContext) {
        String text = "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            text = getClipboardDataBelowHoneyComb(mContext);
        } else {
            text = getClipboardDataAboveHoneyComb(mContext);
        }
        Toast.makeText(mContext, mContext.getString(R.string.txt_pasted_from_clipboard), Toast.LENGTH_SHORT).show();
        return text;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static String getClipboardDataAboveHoneyComb(Context mContext) {
        ClipboardManager clipboard = (ClipboardManager) mContext
                .getSystemService(Context.CLIPBOARD_SERVICE);//get Clipboard manager
        ClipData abc = clipboard.getPrimaryClip();//Get Primary clip
        ClipData.Item item = abc.getItemAt(0);//Get item from clip data

        return item.getText().toString();
    }

    @SuppressWarnings("deprecation")
    private static String getClipboardDataBelowHoneyComb(Context mContext) {
        android.text.ClipboardManager clipboard = (android.text.ClipboardManager) mContext
                .getSystemService(Context.CLIPBOARD_SERVICE);//get Clipboard manager
        return clipboard.getText().toString();

    }
}
