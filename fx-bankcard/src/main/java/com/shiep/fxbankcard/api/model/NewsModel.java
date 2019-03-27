package com.shiep.fxbankcard.api.model;

import lombok.Data;

import java.util.Date;

/**
 * @author: 倪明辉
 * @date: 2019/3/27 17:42
 * @description: 新闻model类
 */
@Data
public class NewsModel {
    /**
     * description: 新闻的唯一标识符
     */
    private String uniqueKey;
    /**
     * description: 新闻的发布时间
     */
    private String releaseTime;
    /**
     * description: 新闻的标题
     */
    private String title;
    /**
     * description: 新闻的地址
     */
    private String newsURL;
    /**
     * description: 新闻的作者
     */
    private String authorName;
    /**
     * description: 新闻的图片地址
     */
    private String imgURL;
    /**
     * description: 新闻的类别
     */
    private String category;
}
