package com.programlearning.learning.webCrawler.cloudfactory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

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

            //订单成交数
            List<String> bill = page.getHtml()
                    .$("div.CM_single_box")
                    .$("div.CM_single_show")
                    .$("div.CM_icon_1")
                    .xpath("div/text()")
                    .all();
            //订单金额
            List<String> price = page.getHtml()
                    .$("div.CM_single_box")
                    .$("div.CM_single_show")
                    .$("div.CM_icon_2")
                    .xpath("div/text()")
                    .all();
            page.putField("bill",bill);
            page.putField("price",price);
        }
    }

    @Override
    public Site getSite() {
        return Site.me().setSleepTime(100).setRetryTimes(3);
    }

    public static void main(String[] args) {
        List<String> requests = new ArrayList<>(914);
        for (int i = 2; i<=914; i++){
            requests.add("https://www.yungongchang.com/Store?pageIndex=" + i + "&pageSize=8");
        }
        Crawler crawler = new Crawler(requests);
        Spider.create(crawler)
                .thread(Runtime.getRuntime().availableProcessors())
                .addUrl("https://www.yungongchang.com/Store?pageIndex=1&pageSize=8")
                .addPipeline(new PipeLine())
                .run();
    }
}
