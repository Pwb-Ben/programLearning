package com.programlearning.learning.webCrawler.zylsj;

import cn.hutool.core.io.file.FileWriter;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

import java.util.*;

/**
 * @author Ben
 * @date 2021-04-01 11:50:07
 */
@HelpUrl("https://www.zylsj.com/forum-42-1.html")
@TargetUrl("https://www.zylsj.com/forum-42-*.html")
@ExtractBy(value = "//table/tbody[contains(@id,'normalthread_')]/tr/th", multi = true)
public class ForumPost implements AfterExtractor {

    private static final String BASIC_URL = "https://www.zylsj.com/";

    /**
     * 使用TreeMap将每一页的页数作为key存储实现排序，
     * 使用synchronizedMap装饰对象实现多线程同步
     */
    private static Map<Integer, List<String>> dataMap = Collections.synchronizedMap(new TreeMap<>());

    /**
     * 爬取帖子标题
     */
    @ExtractBy(value = "//a[@class='xst']/text()")
    private String title;

    /**
     * 爬取帖子链接
     */
    @ExtractBy(value = "//a[@class='xst']/@href")
    private String href;

    /**
     * 爬取帖子所在页数
     */
    @ExtractBy(value = "//span[@id='fd_page_top']/div[@class='pg']/strong/text()", source = ExtractBy.Source.RawHtml)
    private String page;

    /**
     * 通过AfterExtractor接口，当数据抽取结束，字段都初始化完毕之后被调用
     * 将爬取到的数据存入dataMap中
     * @param page
     */
    @Override
    public void afterProcess(Page page) {
        dataMap.compute(Integer.parseInt(this.page), (k, v) -> {
            if (v==null) {
                v = new LinkedList<>();
                v.add(this.title + "  " + BASIC_URL + this.href);
            }
            v.add(this.title + "  " + BASIC_URL + this.href);
            return v;
        });
    }

    public static void main(String[] args) {
        // 启动爬虫
        OOSpider.create(Site.me().setSleepTime(1000), new ConsolePageModelPipeline(), ForumPost.class)
                .addUrl("https://www.zylsj.com/forum-42-1.html")
                .thread(Runtime.getRuntime().availableProcessors())
                .run();
        // 顺序收集每页的帖子数据
        List<String> dataList = new LinkedList<>();
        for(Map.Entry<Integer, List<String>> entry : dataMap.entrySet()){
            dataList.addAll(entry.getValue());
        }
        // 写到文件
        FileWriter writer = new FileWriter("C:\\Users\\pwbco\\Desktop\\data.txt");
        writer.appendLines(dataList);
    }
}
