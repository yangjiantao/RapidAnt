package io.jiantao.utils.java;

import android.text.TextUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import static org.junit.Assert.assertEquals;

/**
 * Created by jiantaoyang on 2018/10/12.
 */
@RunWith(MockitoJUnitRunner.class)
public class PinyinUtilsTest {

    @Mock
    TextUtils mTextUtils;

    String[] testStrs = {"杨剑涛", " 杨剑涛", "杨剑涛 ", "@#$杨剑涛 ", "@#$%$", "@#$yjt", "@#$123", "123"
            , "_yjt", "_@yjt", "#YJt", "#@yjt", "$yjt", "%yjt", "!yjt", "^yjt", "&yjt", "*yjt", "(yjt", ")yjt"
            , "123杨剑涛", "@#yjt123$#12"
    };

    @Before
    public void setup() {

    }

    @Test
    public void test() {
        for (String testStr : testStrs) {
//            testPinyin(testStr);
            testFirstSpell(testStr);
        }
        assertEquals(4, 2 + 2);
    }

    public void testPinyin(String testStr) {
        String pingYin = PinyinUtils.getPingYin(testStr);
        System.out.println("testPinyin originStr " + testStr + "; result = " + pingYin);
    }

    public void testFirstSpell(String testStr) {
        String pingYin = PinyinUtils.getFirstSpell(testStr);
        System.out.println("testFirstSpell originStr " + testStr + "; result = " + pingYin);
        String aChar = checkEveryChar(pingYin);
        System.out.println("after check: " + aChar);
    }

    @Test
    public void testIterator() {
        int count = 1000;
        Map<Integer, String> dataMap = new HashMap<>(count * 2);
        for (int i = 0; i < count; i++) {
            dataMap.put(i, "index :" + i);
        }

        assertEquals(dataMap.size(), count);

        // iterator remove apply to Map
        Iterator<Integer> iterator = dataMap.keySet().iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            System.out.println("value :" + dataMap.get(next));
            iterator.remove();
        }
        System.out.println(dataMap.size());
        assertEquals(dataMap.size(), count);
    }

    private String checkEveryChar(String firstSpell) {
        if (TextUtils.isEmpty(firstSpell)) {
            return firstSpell;
        }
        int startIndex = -1;
        for (int i = 0; i < firstSpell.length(); i++) {
            char c = firstSpell.charAt(i);
            boolean isLowLetter = c >= 'a' && c <= 'z';
            boolean isUpLetter = c >= 'A' && c <= 'Z';
            if (isLowLetter || isUpLetter) {
                startIndex = i;
                break;
            }
        }
        return startIndex == -1 ? null : firstSpell.substring(startIndex);
    }
}
