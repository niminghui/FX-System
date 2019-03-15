package com.shiep.fxbankcard.api;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

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
        Map<String, Object> resultMap = VerificationIDCardApi.verify(testData);
        Assert.assertEquals(200, resultMap.get("code"));
        System.out.println(resultMap);
    }
}