package com.shiep.fxbankcard.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shiep.fxbankcard.api.model.NewsModel;
import com.shiep.fxbankcard.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 倪明辉
 * @date: 2019/3/27 16:52
 * @description: 新闻头条Api
 */
public class HeadlinesApi {
    private static final String HOST = "http://toutiao-ali.juheapi.com";
    private static final String PATH = "/toutiao/index";
    private static final String METHOD = "GET";
    private static final String APPCODE = "56a6ee55efe6481db33561c026602866";
    private static final String HEADER = "Authorization";
    private static final String APPCODE_HEADER = "APPCODE ";
    private static final String QUERYS_HEADER = "type";
    private static Map<String, String> headers;

    static {
        headers = new HashMap<String, String>();
        headers.put(HEADER, APPCODE_HEADER + APPCODE);
    }

    public static void main(String[] args) {
        getHeadlines("guoji");
    }

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

    private static List<NewsModel> getDatas(String str) {
        List<NewsModel> dataList = new ArrayList<>();
        JSONObject alljsonObject = (JSONObject) JSON.parse(str);
        JSONObject jsonObject = (JSONObject) alljsonObject.get("result");
        JSONArray jsonArray = (JSONArray) jsonObject.get("data");
        for (Object object : jsonArray){
            JSONObject data = (JSONObject)object;
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
}
