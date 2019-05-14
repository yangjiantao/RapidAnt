package io.jiantao.utils.android;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * 具有生命周期感知的Handler
 *
 * @author Created by jiantaoyang
 * @date 2019-05-14
 */
public class LifecycleHandler extends Handler implements LifecycleObserver {

    private @Nullable
    LifecycleOwner mOwner;

    public LifecycleHandler() {
    }

    public LifecycleHandler(Callback callback) {
        super(callback);
    }

    public LifecycleHandler(Looper looper) {
        super(looper);
    }

    public LifecycleHandler(Looper looper, Callback callback) {
        super(looper, callback);
    }

    /**
     * bind lifecycleOwner for handler that can remove all pending messages when ON_DESTROY Event occur
     * @param owner
     */
    public void bindLifecycleOwner(LifecycleOwner owner) {
        if (owner != null) {
            owner.getLifecycle().addObserver(this);
            mOwner = owner;
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onDestroy() {
        Log.d("LifecycleHandler", "onDestroy method called.");
        // 移除队列中所有未执行的消息
        removeCallbacksAndMessages(null);
        if (mOwner != null) {
            mOwner.getLifecycle().removeObserver(this);
        }
    }
}
