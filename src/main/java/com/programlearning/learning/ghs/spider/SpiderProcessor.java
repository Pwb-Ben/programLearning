package com.programlearning.learning.ghs.spider;

import cn.hutool.http.HttpUtil;
import com.programlearning.learning.ghs.spider.processor.SearchVideoProcessor;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author Ben
 * 具体的页面处理需要实现不同的页面处理函数
 */
public class SpiderProcessor implements PageProcessor {

    private String url;

    @Override
    public void process(Page page) {
        page.addTargetRequest(this.url);
    }

    @Override
    public Site getSite() {
        return Site.me().setSleepTime(100).setRetryTimes(3);
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return this.url;
    }

    public static void main(String[] args) {
        SearchVideoProcessor searchVideoProcessor = new SearchVideoProcessor();
        searchVideoProcessor.setUrl("https://www.javbus.com/search/123&type=1");
        Spider.create(searchVideoProcessor)
                .thread(5)
                .addUrl("https://www.javbus.com/search/123&type=1")
                .addPipeline(new ConsolePipeline())
                .run();
        System.out.println("完成");
    }
}
