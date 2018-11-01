package com.jiantao.code.samples.java.concurrent;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by jiantaoyang on 2018/11/1.
 */
public class CountDownLatchSampleTest {

    @Test
    public void testUsage(){
        try {
            CountDownLatchSample.driver1();
            assertEquals(4, 2 + 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
