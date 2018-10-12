package com.ins.linphone.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Utils {
    public static void startActivityOnMainThread(final Activity activity, final Intent intent){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.startActivity(intent);
            }
        });

    }

    public static String secondsToString(int seconds) {
        return String.format("%02d:%02d", seconds / 60, seconds % 60);
    }

    public static String getUsernameFromSipAddress(String address){
        //sip:1234@asd:13
        Pattern pattern = Pattern.compile(":(.*?)@");
        Matcher matcher = pattern.matcher(address);
        if (matcher.find())
        {
           return matcher.group(1);
        }
        return address;
    }

    public static void showToast(final Activity activity, final String message, final int duration){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, message, duration).show();
            }
        });
    }

    /**
     * Hides the soft keyboard
     */
    public static void hideSoftKeyboard(Activity activity) {
        if(activity.getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
