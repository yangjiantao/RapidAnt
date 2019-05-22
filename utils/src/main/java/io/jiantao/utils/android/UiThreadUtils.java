/**
 * Copyright (c) Facebook, Inc. and its affiliates.
 * <p>
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.jiantao.utils.android;


import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;

/**
 * Utility for interacting with the UI thread.
 * @author open source
 */
public class UiThreadUtils {

    @Nullable
    private static Handler sMainHandler;

    /**
     * @return {@code true} if the current thread is the UI thread.
     */
    public static boolean isOnUiThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    /**
     * Throws an {@link AssertionException} if the current thread is not the UI thread.
     */
    public static void assertOnUiThread() {
        SoftAssertions.assertCondition(isOnUiThread(), "Expected to run on UI thread!");
    }

    /**
     * Throws an {@link AssertionException} if the current thread is the UI thread.
     */
    public static void assertNotOnUiThread() {
        SoftAssertions.assertCondition(!isOnUiThread(), "Expected not to run on UI thread!");
    }

    /**
     * Runs the given {@code Runnable} on the UI thread.
     */
    public static void runOnUiThread(Runnable runnable) {
        synchronized (UiThreadUtils.class) {
            if (sMainHandler == null) {
                sMainHandler = new Handler(Looper.getMainLooper());
            }
        }
        sMainHandler.post(runnable);
    }

    /**
     * Runs the given {@code Runnable} on the UI thread.
     */
    public static void runOnUiThread(Runnable runnable, int delayMillis) {
        synchronized (UiThreadUtils.class) {
            if (sMainHandler == null) {
                sMainHandler = new Handler(Looper.getMainLooper());
            }
        }
        sMainHandler.postDelayed(runnable, delayMillis);
    }

    /**
     * remove exist runnable that not handle.
     */
    public static void removeCallbacks(Runnable runnable) {
        synchronized (UiThreadUtils.class) {
            if (sMainHandler == null) {
                sMainHandler = new Handler(Looper.getMainLooper());
            }
        }
        sMainHandler.removeCallbacks(runnable);
    }
}
