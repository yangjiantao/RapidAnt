package com.jiantao.code.samples.java.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jiantaoyang on 2018/11/1.
 */
public class CountDownLatchSample {

    public static void main(String[] args) throws InterruptedException {
        driver1();
    }

    /**
     * 多线程控制一个CountDownLatch对象，实现线程间协作。
     * await: 当前线程等待直到latch count down为0
     * @throws InterruptedException
     */
    public static void driver1() throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        final int N = 10;
        CountDownLatch startSignal = new CountDownLatch(1);
        // attention ： this params is N
        CountDownLatch doneSignal = new CountDownLatch(N);
        for (int i = 0; i < N; i++) {
            executorService.execute(new Worker(i, startSignal, doneSignal));
        }
        // don't let run yet
        System.out.println("main method do Something else  1111 ");
        startSignal.countDown();      // let all threads proceed
        System.out.println("main method do Something else  2222 ");
        doneSignal.await();           // wait for all to finish
        System.out.println("main method worker all done ");
    }

    static class Worker implements Runnable {

        private CountDownLatch startSignal;
        private CountDownLatch doneSignal;
        private int index;

        public Worker(int index, CountDownLatch startSignal, CountDownLatch doneSignal) {
            this.index = index;
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }

        @Override
        public void run() {

            try {
                startSignal.await();
                doWork();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                doneSignal.countDown();
            }
        }

        private void doWork() throws InterruptedException {
            System.out.println(" worker id = " + index + "; doWork >>>>>>> ");
            Thread.sleep(index == 0 ? 10_000 : 1000);
            System.out.println(" worker id = " + index + "; doWork <<<<<<< ");
        }
    }

}
