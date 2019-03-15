package com.shiep.fxbankcard.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shiep.fxbankcard.api.model.CNYRateModel;
import com.shiep.fxbankcard.api.model.FxRateModel;
import com.shiep.fxbankcard.model.CurrencyEnum;
import com.shiep.fxbankcard.model.CurrencyPairsEnum;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author: 倪明辉
 * @date: 2019/3/15 11:42
 * @description: 外汇汇率api
 */
public class FxRateApi {
    private static final String DEF_CHATSET = "UTF-8";
    private static final int DEF_CONN_TIMEOUT = 30000;
    private static final int DEF_READ_TIMEOUT = 30000;
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
    private static final String APPKEY = "d9c7022c18df54ecd92bcccfa6ca67ad";
    private static final String DEF_REQUEST_TYPE = "1";
    private static final String RESULT_CODE = "resultcode";

    /**
     * description: 请求人民币牌价接口地址
     */
    private static final String RMB_RATE_URL = "http://web.juhe.cn:8080/finance/exchange/rmbquot";
    /**
     * description: 请求外汇汇率接口地址
     */
    private static final String FX_RATE_URL = "http://web.juhe.cn:8080/finance/exchange/frate";

    /**
     * description: 人民币牌价接口
     *
     * @param
     * @return java.util.Map<java.lang.String , java.lang.Object>
     */
    public static Map<String, Object> getRmbRate() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 请求参数map
        Map<String, String> params = new HashMap<String, String>(2);
        // 设置APP Key
        params.put("key", APPKEY);
        params.put("type", DEF_REQUEST_TYPE);
        try {
            String str = net(RMB_RATE_URL, params, "GET");
            JSONObject object = (JSONObject) JSON.parse(str);
            Integer resultCode = Integer.parseInt(object.getString(RESULT_CODE));
            resultMap.put("code", resultCode);
            resultMap.put("message", StatusCodeEnum.getMsgByCode(resultCode));
            if (resultCode.equals(StatusCodeEnum.SUCCESS.getCode())) {
                JSONObject allJsonObject = object.getJSONArray("result").getJSONObject(0);
                Map<String, CNYRateModel> data = getCNYRateData(allJsonObject);
                resultMap.put("data", data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * description: 外汇汇率接口
     *
     * @param
     * @return java.util.Map<java.lang.String , java.lang.Object>
     */
    public static Map<String, Object> getFxRate() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 请求参数map
        Map<String, String> params = new HashMap<String, String>(2);
        params.put("key", APPKEY);
        params.put("type", DEF_REQUEST_TYPE);
        try {
            String str = net(FX_RATE_URL, params, "GET");
            JSONObject object = (JSONObject) JSON.parse(str);
            Integer resultCode = Integer.parseInt(object.getString(RESULT_CODE));
            resultMap.put("code", resultCode);
            resultMap.put("message", StatusCodeEnum.getMsgByCode(resultCode));
            if (resultCode.equals(StatusCodeEnum.SUCCESS.getCode())) {
                JSONObject allJsonObject = object.getJSONArray("result").getJSONObject(0);
                Map<String, FxRateModel> data = getFxRateData(allJsonObject);
                resultMap.put("data", data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * description: 得到外汇汇率数据map
     *
     * @param allJsonObject
     * @return java.util.Map<java.lang.String , com.shiep.fxbankcard.api.model.FxRateModel>
     */
    private static Map<String, FxRateModel> getFxRateData(JSONObject allJsonObject) {
        Map<String, FxRateModel> models = new HashMap<>();
        List<String> currencyCodes = CurrencyPairsEnum.getAllCode();
        for (Object object : allJsonObject.values()) {
            JSONObject jsonObject = (JSONObject) object;
            String currencyPairsCode = jsonObject.getString("code");
            if (currencyCodes.contains(currencyPairsCode)) {
                FxRateModel model = new FxRateModel();
                model.setCurrencyPairsCode(currencyPairsCode);
                model.setCurrencyPairsName(CurrencyPairsEnum.getNameByCode(currencyPairsCode));
                model.setDate(jsonObject.getString("date"));
                model.setTime(jsonObject.getString("datatime"));
                model.setClosePri(jsonObject.getString("closePri"));
                model.setDiffPer(jsonObject.getString("diffPer"));
                model.setDiffAmo(jsonObject.getString("diffAmo"));
                model.setOpenPri(jsonObject.getString("openPri"));
                model.setHighPic(jsonObject.getString("highPic"));
                model.setLowPic(jsonObject.getString("lowPic"));
                model.setRange(jsonObject.getString("range"));
                model.setBuyPic(jsonObject.getString("buyPic"));
                model.setSellPic(jsonObject.getString("sellPic"));
                model.setColor(jsonObject.getString("color"));
                model.setYesPic(jsonObject.getString("yesPic"));
                models.put(model.getCurrencyPairsCode(), model);
            }
        }
        return models;
    }

    /**
     * description: 得到人民币牌价数据map
     *
     * @param allJsonObject
     * @return java.util.Map<java.lang.String , com.shiep.fxbankcard.api.model.CNYRateModel>
     */
    private static Map<String, CNYRateModel> getCNYRateData(JSONObject allJsonObject) {
        String[] currencyArray = {"美元", "欧元", "港币", "日元", "英镑", "澳大利亚元", "加拿大元", "新加坡元", "新西兰元", "瑞典克朗", "林吉特"};
        List<String> currencys = Arrays.asList(currencyArray);
        Map<String, CNYRateModel> models = new HashMap<String, CNYRateModel>();
        for (Object object : allJsonObject.values()) {
            JSONObject jsonObject = (JSONObject) object;
            String currencyName = jsonObject.getString("name");
            if (currencys.contains(currencyName)) {
                CNYRateModel model = new CNYRateModel();
                model.setDate(jsonObject.getString("date"));
                model.setTime(jsonObject.getString("time"));
                model.setCurrencyName(currencyName);
                model.setCurrencyCode(CurrencyEnum.getCodeByName(currencyName));
                model.setBankConversionPri(jsonObject.getString("bankConversionPri"));
                model.setFBuyPri(jsonObject.getString("fBuyPri"));
                model.setFSellPri(jsonObject.getString("fSellPri"));
                models.put(model.getCurrencyCode(), model);
            }
        }
        return models;
    }

    /**
     * description: 发送请求
     *
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return java.lang.String 网络请求字符串
     */
    private static String net(String strUrl, Map params, String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if (method == null || method.equals("GET")) {
                strUrl = strUrl + "?" + urlEncode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (method == null || method.equals("GET")) {
                conn.setRequestMethod("GET");
            } else {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", USER_AGENT);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params != null && method.equals("POST")) {
                try (DataOutputStream out = new DataOutputStream(conn.getOutputStream())) {
                    out.writeBytes(urlEncode(params));
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    /**
     * description: 将map型数据转为请求参数型(String)
     *
     * @param data
     * @return java.lang.String
     */
    private static String urlEncode(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
