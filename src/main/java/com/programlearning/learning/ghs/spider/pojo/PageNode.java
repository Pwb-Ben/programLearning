package com.programlearning.learning.ghs.spider.pojo;

import java.util.List;

public class PageNode {

    /**
     * 当前页数
     */
    private int currentPage;

    /**
     * 下一页的路径
     */
    private String nextPageUrl;

    /**
     * 视频列表
     */
    private List<VideoNode> videoNodeList;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public List<VideoNode> getVideoNodeList() {
        return videoNodeList;
    }

    public void setVideoNodeList(List<VideoNode> videoNodeList) {
        this.videoNodeList = videoNodeList;
    }
}