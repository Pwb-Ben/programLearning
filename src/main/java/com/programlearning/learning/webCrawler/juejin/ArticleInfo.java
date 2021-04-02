package com.programlearning.learning.webCrawler.juejin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class ArticleInfo implements Serializable {

    private static final long serialVersionUID = -1554853061240658449L;

    @JsonProperty("article_id")
    private String articleId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("brief_content")
    private String briefContent;

    @JsonProperty("category_id")
    private String categoryId;

    @JsonProperty("tag_ids")
    private List<String> tagIds;

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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public List<String> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<String> tagIds) {
        this.tagIds = tagIds;
    }

    @Override
    public String toString() {
        return "ArticleInfo{" +
                "articleId='" + articleId + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
