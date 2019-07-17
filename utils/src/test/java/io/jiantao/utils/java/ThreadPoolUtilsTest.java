package io.jiantao.utils.java;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicLong;

import io.jiantao.utils.android.ThreadPoolUtils;

/**
 * description
 *
 * @author Created by jiantaoyang
 * @date 2019-07-17
 */
public class ThreadPoolUtilsTest {

    static AtomicLong atomicLong = new AtomicLong(1);

    class Task implements Runnable {

        @Override
        public void run() {
            long count = atomicLong.incrementAndGet();
            System.out.println(" running >>>>> " + count);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" running <<<<< " + count);
        }
    }

    @Test
    public void testSinglePool() {

        System.out.println("testSinglePool start. ");
        final CountDownLatch latch = new CountDownLatch(1);
        ExecutorService singlePool = ThreadPoolUtils.getSinglePool();
        Task task = new Task();
        int count = 100;
        for (int i = 0; i < count; i++) {
            singlePool.execute(task);
        }

        if (atomicLong.get() == count) {
            latch.countDown();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("testSinglePool end. ");
    }

}