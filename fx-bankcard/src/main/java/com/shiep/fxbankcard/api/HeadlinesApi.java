package com.shiep.fxbankcard.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shiep.fxbankcard.api.model.NewsModel;
import com.shiep.fxbankcard.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 倪明辉
 * @date: 2019/3/27 16:52
 * @description: 新闻头条Api
 */
@SuppressWarnings("all")
public class HeadlinesApi {
    private static final String HOST = "http://toutiao-ali.juheapi.com";
    private static final String PATH = "/toutiao/index";
    private static final String METHOD = "GET";
    private static final String APPCODE = "56a6ee55efe6481db33561c026602866";
    private static final String HEADER = "Authorization";
    private static final String APPCODE_HEADER = "APPCODE ";
    private static final String QUERYS_HEADER = "type";
    private static Map<String, String> headers;

    /**
     * description: 请求新闻的类型
     */
    public static final String TYPE_TOP="top";
    public static final String TYPE_SOCIETY="shehui";
    public static final String TYPE_DOMESTIC="guonei";
    public static final String TYPE_INTERNATIONAL="guoji";
    public static final String TYPE_RECREATION="yule";
    public static final String TYPE_SPORTS="tiyu";
    public static final String TYPE_MILITARY="junshi";
    public static final String TYPE_SCIENCE="keji";
    public static final String TYPE_FINANCE="caijing";
    public static final String TYPE_FASHION="shishang";

    static {
        headers = new HashMap<String, String>();
        headers.put(HEADER, APPCODE_HEADER + APPCODE);
    }

    /**
     * description: 新闻头条
     *
     * @param type 请求的新闻类型
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    public static Map<String, Object> getHeadlines(String type) {
        Map<String, String> querys = new HashMap<String, String>();
        querys.put(QUERYS_HEADER, type);
        try {
            HttpResponse response = HttpUtils.doGet(HOST, PATH, METHOD, headers, querys);
            Map<String, Object> result = new HashMap<String, Object>();
            StatusCodeEnum statusCodeEnum = StatusCodeEnum.parse(response.getStatusLine().getStatusCode());
            result.put("code", statusCodeEnum.getCode());
            result.put("message", statusCodeEnum.getMessage());
            System.out.println(result);
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
     * description: 新闻头条
     *
     * @param type 请求的新闻类型
     * @return java.util.Map<java.lang.String   ,   java.lang.Object>
     */
    public static Map<String, Object> getHeadlinesPageable(String type, Integer size) {
        Map<String, String> querys = new HashMap<String, String>();
        querys.put(QUERYS_HEADER, type);
        try {
            HttpResponse response = HttpUtils.doGet(HOST, PATH, METHOD, headers, querys);
            Map<String, Object> result = new HashMap<String, Object>();
            StatusCodeEnum statusCodeEnum = StatusCodeEnum.parse(response.getStatusLine().getStatusCode());
            result.put("code", statusCodeEnum.getCode());
            result.put("message", statusCodeEnum.getMessage());
            // 当查询成功时，添加数据到result中
            if (statusCodeEnum.getCode().equals(StatusCodeEnum.SUCCESS.getCode())) {
                result.put("data", getDatasPageable(EntityUtils.toString(response.getEntity()), size));
                return result;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * description: 得到数据
     *
     * @param str json字符串
     * @return java.util.List<com.shiep.fxbankcard.api.model.NewsModel>
     */
    private static List<NewsModel> getDatas(String str) {
        List<NewsModel> dataList = new ArrayList<>();
        JSONObject alljsonObject = (JSONObject) JSON.parse(str);
        JSONObject jsonObject = (JSONObject) alljsonObject.get("result");
        JSONArray jsonArray = (JSONArray) jsonObject.get("data");
        for (Object object : jsonArray) {
            JSONObject data = (JSONObject) object;
            NewsModel newsModel = new NewsModel();
            newsModel.setUniqueKey((String) data.get("uniquekey"));
            newsModel.setTitle((String) data.get("title"));
            newsModel.setReleaseTime((String) data.get("date"));
            newsModel.setNewsURL((String) data.get("url"));
            newsModel.setImgURL((String) data.get("thumbnail_pic_s"));
            newsModel.setCategory((String) data.get("category"));
            newsModel.setAuthorName((String) data.get("author_name"));
            dataList.add(newsModel);
        }
        return dataList;
    }

    /**
     * description: 得到带分页的数据集
     *
     * @param str  json字符串
     * @param size 每页大小
     * @return java.util.Map<java.lang.Integer       ,       java.util.List       <       com.shiep.fxbankcard.api.model.NewsModel>>
     */
    private static Map<Integer, List<NewsModel>> getDatasPageable(String str, Integer size) {
        Map<Integer, List<NewsModel>> dataMap = new HashMap<>();
        JSONObject alljsonObject = (JSONObject) JSON.parse(str);
        JSONObject jsonObject = (JSONObject) alljsonObject.get("result");
        JSONArray jsonArray = (JSONArray) jsonObject.get("data");
        for (int i = 0, j = 0; i <= (jsonArray.size() / size) && j < jsonArray.size(); i++) {
            List<NewsModel> dataList = new ArrayList<>(size);
            for (int k = 0; k < size; k++) {
                JSONObject data = (JSONObject) jsonArray.get(j++);
                NewsModel newsModel = new NewsModel();
                newsModel.setUniqueKey((String) data.get("uniquekey"));
                newsModel.setTitle((String) data.get("title"));
                newsModel.setReleaseTime((String) data.get("date"));
                newsModel.setNewsURL((String) data.get("url"));
                newsModel.setImgURL((String) data.get("thumbnail_pic_s"));
                newsModel.setCategory((String) data.get("category"));
                newsModel.setAuthorName((String) data.get("author_name"));
                dataList.add(newsModel);
            }
            dataMap.put(i, dataList);
        }
        return dataMap;
    }
}
