/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.jiantao.utils.android;


import androidx.annotation.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Global executor pools for the whole application.
 * <p>
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 *
 * @author yangjiantao
 * @date 2019
 */
public class AppExecutors {
    // --------- copy from AsyncTask --------
    /**
     * cpu count
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    /**
     * // We want at least 2 threads and at most 4 threads in the core pool,
     // preferring to have 1 less than the CPU count to avoid saturating
     // the CPU with background work
     */
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    /**
     * max pool size
     */
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    /**
     * keep alive time
     */
    private static final int KEEP_ALIVE_SECONDS = 30;

    /**
     * ThreadFactory
     */
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "AppExecutors #" + mCount.getAndIncrement());
        }
    };

    /**
     * workQueue
     */
    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>(128);

    // --------- copy from AsyncTask --------

    /**
     * io executor
     */
    private final Executor IOExecutor;

    /**
     * mainThread executor
     */
    private final Executor mainExecutor;

    private static AppExecutors sInstance;

    /**
     * constructor
     */
    private AppExecutors(Executor ioExecutor, Executor mainExecutor) {
        this.IOExecutor = ioExecutor;
        this.mainExecutor = mainExecutor;
    }

    /**
     * no params
     */
    private AppExecutors() {
        this(createIOThreadExecutor(), new MainThreadExecutor());
    }

    /**
     * @return single instance
     */
    private static AppExecutors getInstance(){
        if (sInstance == null) {
            synchronized (AppExecutors.class){
                if (sInstance == null) {
                    sInstance = new AppExecutors();
                }
            }
        }
        return sInstance;
    }

    /**
     * run on uiThread
     */
    public static void runOnUiThread(@NonNull Runnable runnable){
        getInstance().mainExecutor.execute(runnable);
    }

    /**
     * run on IOThread
     */
    public static void runOnIOThread(@NonNull Runnable runnable){
        getInstance().IOExecutor.execute(runnable);
    }

    /**
     * @return io executor
     */
    private static Executor createIOThreadExecutor() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                sPoolWorkQueue, sThreadFactory);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        return threadPoolExecutor;
    }

    private static class MainThreadExecutor implements Executor {
        @Override
        public void execute(@NonNull Runnable command) {
            UiThreadUtils.runOnUiThread(command);
        }
    }
}
