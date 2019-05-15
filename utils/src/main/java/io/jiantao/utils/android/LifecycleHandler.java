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

    /**
     * @param mOwner a lifecycleOwner for handler that can remove all pending messages when ON_DESTROY Event occur
     */
    public LifecycleHandler(@NonNull LifecycleOwner mOwner) {
        this.mOwner = mOwner;
    }

    /**
     * @param mOwner   a lifecycleOwner for handler that can remove all pending messages when ON_DESTROY Event occur
     * @param callback callback
     */
    public LifecycleHandler(@NonNull LifecycleOwner mOwner, Callback callback) {
        super(callback);
        this.mOwner = mOwner;
    }

    public LifecycleHandler(@NonNull LifecycleOwner mOwner, Looper looper) {
        super(looper);
        this.mOwner = mOwner;
    }

    public LifecycleHandler(@NonNull LifecycleOwner mOwner, Looper looper, Callback callback) {
        super(looper, callback);
        this.mOwner = mOwner;
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
