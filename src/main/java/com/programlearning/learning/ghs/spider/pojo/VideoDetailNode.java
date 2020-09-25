package com.programlearning.learning.ghs.spider.pojo;

import java.util.List;

public class VideoDetailNode {

    /**
     * 标题
     */
    private String title;

    /**
     * 封面图像
     */
    private String bigImage;

    /**
     * 番号
     */
    private String uid;

    /**
     * 出版日期
     */
    private String publishDate;

    /**
     * 时长
     */
    private String length;

    /**
     * 导演
     */
    private String director;

    /**
     * 制作商
     */
    private String manufacturer;

    /**
     * 发行商
     */
    private String publisher;

    /**
     * 系列
     */
    private String series;

    /**
     * 类别
     */
    private List<String> categories;

    /**
     * 演员
     */
    private List<String> actors;

    /**
     * 样品图像
     */
    private List<String> sampleList;

    /**
     * 磁力链接列表
     */
    private List<MagnetNode> magnetList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBigImage() {
        return bigImage;
    }

    public void setBigImage(String bigImage) {
        this.bigImage = bigImage;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public List<String> getSampleList() {
        return sampleList;
    }

    public void setSampleList(List<String> sampleList) {
        this.sampleList = sampleList;
    }

    public List<MagnetNode> getMagnetList() {
        return magnetList;
    }

    public void setMagnetList(List<MagnetNode> magnetList) {
        this.magnetList = magnetList;
    }

    @Override
    public String toString() {
        return "VideoDetailNode{" +
                "title='" + title + '\'' +
                ", bigImage='" + bigImage + '\'' +
                ", uid='" + uid + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", length='" + length + '\'' +
                ", director='" + director + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", publisher='" + publisher + '\'' +
                ", series='" + series + '\'' +
                ", categories=" + categories +
                ", actors=" + actors +
                ", sampleList=" + sampleList +
                ", magnetList=" + magnetList +
                '}';
    }
}
