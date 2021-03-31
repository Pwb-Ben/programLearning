package com.programlearning.learning.webCrawler.zylsj;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

public class Crawler implements PageProcessor {

    private List<String> requests;

    private Crawler(List<String> requests){
        this.requests = requests;
    }

    @Override
    public void process(Page page) {
        if (requests!=null){
            page.addTargetRequests(requests);

            Selectable selectableList = page.getHtml().xpath("table/tbody[contains(@id,'normalthread_')]");

            List<String> titleList = selectableList.$("a.xst").xpath("a/text()").all();
            List<String> hrefList = selectableList.$("a.xst").xpath("a/@href").all();
//            page.putField("list",list);
        }
    }

    @Override
    public Site getSite() {
        return Site.me().setSleepTime(100).setRetryTimes(3);
    }

    public static void main(String[] args) {
        List<String> requests = new ArrayList<>(81);
        for (int i = 2; i<=2; i++){
            requests.add("https://www.zylsj.com/forum-42-" + i + ".html");
        }
        Crawler crawler = new Crawler(requests);
        Spider.create(crawler)
                .thread(Runtime.getRuntime().availableProcessors())
                .addUrl("https://www.zylsj.com/forum-42-1.html")
                .addPipeline(new PipeLine())
                .run();
    }
}
