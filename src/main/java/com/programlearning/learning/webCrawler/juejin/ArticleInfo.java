package com.programlearning.learning.webCrawler.juejin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class ArticleInfo implements Serializable {

    private static final long serialVersionUID = -1554853061240658449L;

    private String articleId;

    private String title;

    private String briefContent;

    private String categoryName;

    private List<String> tags;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBriefContent() {
        return briefContent;
    }

    public void setBriefContent(String briefContent) {
        this.briefContent = briefContent;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "ArticleInfo{" +
                "articleId='" + articleId + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
