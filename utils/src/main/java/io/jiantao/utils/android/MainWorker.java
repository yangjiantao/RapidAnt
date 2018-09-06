package io.jiantao.utils.android;

import android.os.Handler;
import android.os.Looper;

/**
 * the all post runnables will run on main thread.
 * @author Jiantao.Yang
 */
public class MainWorker {

    public static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());

    public static void post(Runnable r) {
        if (isMainThread()) {
            r.run();
        } else {
            MAIN_HANDLER.post(r);
        }
    }

    public static void postAtfront(Runnable r) {
        MAIN_HANDLER.postAtFrontOfQueue(r);
    }

    public static boolean isMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    public static void remove(Runnable r) {
        MAIN_HANDLER.removeCallbacks(r);
    }

    public static void postDelay(long delay, Runnable r) {
        MAIN_HANDLER.postDelayed(r, delay);
    }

    public static void removePreviousAndPost(Runnable r) {
        MAIN_HANDLER.removeCallbacks(r);
        MAIN_HANDLER.post(r);
    }

    public static void removePreviousAndPostDelay(long delayMills, Runnable r) {
        MAIN_HANDLER.removeCallbacks(r);
        MAIN_HANDLER.postDelayed(r, delayMills);
    }
}