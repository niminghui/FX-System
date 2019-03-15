package com.shiep.fxbankcard.api;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author: 倪明辉
 * @date: 2019/3/15 16:51
 * @description: 汇率api测试类
 */
public class FxRateApiTest {

    @Test
    public void getRmbRate() {
        Map<String,Object> resultMap=FxRateApi.getRmbRate();
        Assert.assertEquals(200,resultMap.get("code"));
        System.out.println(resultMap);
    }

    @Test
    public void getFxRate() {
        Map<String,Object> resultMap=FxRateApi.getFxRate();
        Assert.assertEquals(200,resultMap.get("code"));
        System.out.println(resultMap);
    }
}