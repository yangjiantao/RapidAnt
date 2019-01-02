package io.jiantao.utils.android;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * 存储器相关工具类
 * Created by jiantaoyang on 2018/10/25.
 */
public class StorageUtils {


    /**
     * Return whether sdcard is enabled by environment.
     *
     * @return true : enabled<br>false : disabled
     */
    public static boolean isSDCardEnableByEnvironment() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * Return the path of sdcard by environment.
     *
     * @return the path of sdcard by environment
     */
    public static String getSDCardPathByEnvironment() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return "";
    }

    /**
     * Return whether sdcard is enabled.
     *
     * @return true : enabled<br>false : disabled
     */
    public static boolean isSDCardEnable(@NonNull Context context) {
        return !getSDCardPaths(context).isEmpty();
    }

    /**
     * 该文件所在存储器的总容量
     *
     * @param path filepath
     * @return
     */
    public static long getStorageTotalSpace(String path) {
        StatFs statFs = new StatFs(path);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return statFs.getTotalBytes();
        } else {
            return statFs.getBlockSizeLong() * statFs.getBlockCountLong();
        }
    }

    /**
     * 该文件所在存储器的可用容量
     *
     * @param path filepath
     * @return
     */
    public static long getStorageAvailableSpace(String path) {
        StatFs statFs = new StatFs(path);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            // getAvailableBytes 比 getFreeBytes 准确
            return statFs.getAvailableBytes();
        } else {
            // getAvailableBlocksLong 比 getFreeBlocksLong 准确
            return statFs.getBlockSizeLong() * statFs.getAvailableBlocksLong();
        }
    }

    /**
     * 获取外部sdcard可用容量
     * @return
     */
    public static long getSDCardAvailableSpace() {
        return getStorageAvailableSpace(getSDCardPathByEnvironment());
    }

    /**
     * Return the paths of sdcard.
     *
     * @return the paths of sdcard
     */
    public static List<String> getSDCardPaths(@NonNull Context context) {
        StorageManager storageManager = (StorageManager) context.getApplicationContext()
                .getSystemService(Context.STORAGE_SERVICE);
        List<String> paths = new ArrayList<>();
        try {
            //noinspection JavaReflectionMemberAccess
            Method getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths");
            getVolumePathsMethod.setAccessible(true);
            Object invoke = getVolumePathsMethod.invoke(storageManager);
            paths = Arrays.asList((String[]) invoke);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return paths;
    }
}
