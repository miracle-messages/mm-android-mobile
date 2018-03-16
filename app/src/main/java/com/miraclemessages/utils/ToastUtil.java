package com.miraclemessages.utils;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by shobhit on 2017-08-06.
 */

public class ToastUtil {
    /**
     * Display the native toast
     *
     * @param activity - Activity from where the request originated
     * @param message  - Message to be displayed
     */
    public static void showToast(final Activity activity, final String message) {
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Display the native toast
     *
     * @param activity - Activity
     * @param stringId - string resource Id
     */
    public static void showToast(Activity activity, int stringId) {
        showToast(activity, activity.getString(stringId));
    }

}
