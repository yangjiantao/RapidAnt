package io.jiantao.utils.android;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
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

    private @NonNull
    LifecycleOwner mOwner;

    public LifecycleHandler(@NonNull LifecycleOwner owner) {
        bindLifecycleOwner(owner);
    }

    public LifecycleHandler(@NonNull LifecycleOwner owner, Callback callback) {
        super(callback);
        bindLifecycleOwner(owner);
    }

    public LifecycleHandler(@NonNull LifecycleOwner owner, Looper looper) {
        super(looper);
        bindLifecycleOwner(owner);
    }

    public LifecycleHandler(@NonNull LifecycleOwner owner, Looper looper, Callback callback) {
        super(looper, callback);
        bindLifecycleOwner(owner);
    }

    /**
     * bind lifecycleOwner for handler that can remove all pending messages when ON_DESTROY Event occur
     */
    private void bindLifecycleOwner(LifecycleOwner owner) {
        if (owner != null) {
            mOwner = owner;
            owner.getLifecycle().addObserver(this);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onDestroy() {
        // 移除队列中所有未执行的消息
        removeCallbacksAndMessages(null);
        if (mOwner != null) {
            mOwner.getLifecycle().removeObserver(this);
        }
    }
}
