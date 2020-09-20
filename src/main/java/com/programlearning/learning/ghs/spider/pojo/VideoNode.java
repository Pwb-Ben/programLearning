package com.programlearning.learning.ghs.spider.pojo;

/**
 * @author Ben
 */
public class VideoNode {

    /**
     * 番号
     */
    private String uid;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片
     */
    private String image;

    /**
     * 跳转链接
     */
    private String href;

    /**
     * 日期
     */
    private String date;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return "VideoNode{" +
                "uid='" + uid + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", href='" + href + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
