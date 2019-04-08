package com.shiep.fxbankcard.api;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * @author: 倪明辉
 * @date: 2019/3/28 15:58
 * @description: HeadlinesApi的测试类
 */
public class HeadlinesApiTest {
    @Test
    public void getHeadlines(){
        Map<String, Object> resultMap=HeadlinesApi.getHeadlines(HeadlinesApi.TYPE_INTERNATIONAL);
        Assert.assertEquals(200, resultMap.get("code"));
        System.out.println(resultMap);
    }

    @Test
    public void getHeadlinesPageable() {
        Map<String, Object> resultMap = HeadlinesApi.getHeadlinesPageable(HeadlinesApi.TYPE_INTERNATIONAL, 3);
        Assert.assertEquals(200, resultMap.get("code"));
        System.out.println(resultMap);
    }
}