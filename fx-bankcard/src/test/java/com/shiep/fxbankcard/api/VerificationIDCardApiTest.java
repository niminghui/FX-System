package com.shiep.fxbankcard.api;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author: 倪明辉
 * @date: 2019/3/14 17:19
 * @description: VerificationIDCardApi测试类
 */
public class VerificationIDCardApiTest {
    private String testData = "350181199608281715";

    @Test
    public void verify() {
        System.out.println(VerificationIDCardApi.verify(testData));
    }
}