package com.jiantao.code.samples.java.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Created by jiantaoyang on 2018/12/3.
 * 测试ArrayList和CopyOnWriteArrayList在clear和iterator的实现区别。
 * 1. COWArrayList 的clear方法是System.arrayCopy过程，原来的数组没有改变，所有iterator遍历的是之前的数据。
 * 2. ArrayList 的clear方法和Iterator都直接操作的elementData对象，所以会有同步问题。
 */
public class CopyOnWriteArrayListTest {

  public static void main(String[] args) {

  }

  private Collection<String> list;

  @Before
  public void mockData() {
    int count = 1000;
    list = new CopyOnWriteArrayList<>();
    //list = new ArrayList<>(count);
    for (int i = 0; i < count; i++) {
      String data = "mock " + i;
      list.add(data);
    }
  }

  @Test
  public void testClearAndIterator() {

    new Thread(new Runnable() {
      @Override public void run() {
        System.out.println("clear >>>>>> ");
        list.clear();
        System.out.println("clear <<<<<< ");
      }
    }).start();

    new Thread(new Runnable() {
      @Override public void run() {
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
          System.out.println("while .... data = " + iterator.next());
        }
      }
    }).start();

    Assert.assertEquals(1 + 1, 2);
  }
}
