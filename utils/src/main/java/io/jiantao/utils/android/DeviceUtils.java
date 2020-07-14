package io.jiantao.utils.android;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;

/**
 * Created by Jiantao.Yang on 2020/6/22.
 */
public class DeviceUtils {

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isLowMemoryDevice(@NonNull Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // Explicitly check with an if statement, on some devices both parts of boolean expressions
        // can be evaluated even if we'd normally expect a short circuit.
        //noinspection SimplifiableIfStatement
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return activityManager.isLowRamDevice();
        } else {
            return true;
        }
    }
}
