package com.mmall.test;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.04.02 19:06
 */
public class BigDecimalTest {

    @Test
    public void test1() {
        System.out.println(0.05 + 0.01);
        System.out.println(1.0 - 0.42);
        System.out.println(4.015 * 100);
        System.out.println(123.3 / 100);
    }


    @Test
    public void test2() {
        BigDecimal b1 = new BigDecimal(0.05);
        BigDecimal b2 = new BigDecimal(0.01);
        System.out.println(b1.add(b2));
    }

    @Test
    public void test3() {
        BigDecimal b1 = new BigDecimal("0.05");
        BigDecimal b2 = new BigDecimal("0.01");
        System.out.println(b1.add(b2));

    }

    @Test
    public void test4() {
        long currentTime = System.currentTimeMillis();
        System.out.println(currentTime);
        System.out.println(currentTime % 9);
        System.out.println(currentTime + currentTime % 9);

    }

    @Test
    public void test5() {
        System.out.println(Integer.MAX_VALUE);
        Integer int1 = Integer.valueOf("128");
        Integer int2 = Integer.valueOf("128");
        System.out.println(int1 == int2);
    }
}
