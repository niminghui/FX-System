package com.shiep.fxbankcard.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shiep.fxbankcard.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 倪明辉
 * @date: 2019/3/14 14:36
 * @description: 校验银行卡是否有效，并返回发证省份、城市、地区、出生年月日、性别、年龄等。
 */
public class VerificationIDCardApi {
    private static final String HOST = "http://idquery.market.alicloudapi.com";
    private static final String PATH = "/idcard/query";
    private static final String METHOD = "GET";
    private static final String APPCODE = "56a6ee55efe6481db33561c026602866";
    private static final String HEADER = "Authorization";
    private static final String APPCODE_HEADER = "APPCODE ";
    private static final String QUERYS_HEADER = "number";

    private static Map<String, String> headers;

    static {
        headers = new HashMap<String, String>();
        //格式：Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put(HEADER, APPCODE_HEADER + APPCODE);
    }

    /**
     * description: 进行身份证校验
     *
     * @param idCardNum
     * @return java.util.Map<java.lang.String , java.lang.Object>
     */
    public static Map<String, Object> verify(String idCardNum) {
        Map<String, String> querys = new HashMap<String, String>();
        querys.put(QUERYS_HEADER, idCardNum);
        try {
            HttpResponse response = HttpUtils.doGet(HOST, PATH, METHOD, headers, querys);
            Map<String, Object> result = new HashMap<String, Object>();
            StatusCodeEnum statusCodeEnum = StatusCodeEnum.parse(response.getStatusLine().getStatusCode());
            result.put("code", statusCodeEnum.getCode());
            result.put("message", statusCodeEnum.getMessage());
            // 当查询成功时，添加数据到result中
            if (statusCodeEnum.getCode().equals(StatusCodeEnum.SUCCESS.getCode())) {
                result.put("data", getDatas(EntityUtils.toString(response.getEntity())));
                return result;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * description: 取出数据
     *
     * @param str
     * @return java.util.Map<java.lang.String , java.lang.String>
     */
    private static Map<String, String> getDatas(String str) {
        Map<String, String> data = new HashMap<>();
        JSONObject alljsonObject = (JSONObject) JSON.parse(str);
        JSONObject jsonObject = (JSONObject) alljsonObject.get("data");
        data.put("prov", (String) jsonObject.get("prov"));
        data.put("city", (String) jsonObject.get("city"));
        data.put("region", (String) jsonObject.get("region"));
        data.put("sex", (String) jsonObject.get("sex"));
        data.put("age", jsonObject.getInteger("age").toString());
        data.put("birthday", (String) jsonObject.get("birthday"));
        return data;
    }
}
