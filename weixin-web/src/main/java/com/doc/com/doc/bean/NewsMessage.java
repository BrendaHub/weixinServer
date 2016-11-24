package com.doc.com.doc.bean;

import java.util.List;

/**
 * 微信的图文消息实例对象类
 * Created by XYZ on 16/11/24.
 */
public class NewsMessage extends  BaseMessage {

    private int ArticleCount;
    private List<subNews> Articles;

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<subNews> getAriicles() {
        return Articles;
    }

    public void setAriicles(List<subNews> ariicles) {
        Articles = ariicles;
    }
}
