package io.jiantao.utils.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;

import androidx.annotation.NonNull;

/**
 * 电池相关常用方法
 *
 * @author Created by jiantaoyang on 2018/11/20.
 */
public class BatteryUtils {

    private static final String TAG = "BatteryUtils";

    /**
     * 是否正在充电或是满电状态
     *
     * @param context
     * @return
     */
    public static boolean isChargingOrFull(@NonNull Context context) {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);
        if (batteryStatus == null) {
            Log.e(TAG, "isChagingOrFull batteryStatus intent is null");
            return false;
        }

        // Are we charging / charged?
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        return status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        // How are we charging?
//        int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
//        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
//        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
    }

    /**
     * 获取当前电量百分比。异常情况返回0.0.
     *
     * @param context
     * @return full is 1.0
     */
    public static float currentBatteryPct(@NonNull Context context) {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);
        if (batteryStatus == null) {
            Log.e(TAG, "isChagingOrFull batteryStatus intent is null");
            return 0F;
        }
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        return level / (float) scale;
    }

    /**
     * Register a listener for getting updates of device charging, discharging or completely charged.
     *
     * @param context               the context
     * @param batteryChargeListener the battery charge listener
     */
    public static void registerConnectChangedBroadcastReceiver(Context context, BroadcastReceiver receiver) {
        final IntentFilter theFilter = new IntentFilter();
        /* System Defined Broadcast */
        theFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        theFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);

        context.getApplicationContext().registerReceiver(receiver, theFilter);
    }

    public static void unregisterBroadcastReceiver(@NonNull Context context, BroadcastReceiver receiver) {
        context.getApplicationContext().unregisterReceiver(receiver);
    }
}
