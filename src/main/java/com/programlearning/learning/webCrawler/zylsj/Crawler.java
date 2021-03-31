package com.programlearning.learning.webCrawler.zylsj;

import cn.hutool.core.io.file.FileWriter;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Crawler implements PageProcessor {

    private static final String URL = "https://www.zylsj.com/";

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

            Map<String, String> map = new HashMap<>(20);
            for(int i=0, len=titleList.size(); i<len; i++){
                map.put(hrefList.get(i), titleList.get(i));
            }
            page.putField("map", map);
        }
    }

    @Override
    public Site getSite() {
        return Site.me().setSleepTime(100).setRetryTimes(3);
    }

    public static void main(String[] args) {
        List<String> requests = new ArrayList<>(81);
        for (int i = 2; i<=81; i++){
            requests.add("https://www.zylsj.com/forum-42-" + i + ".html");
        }
        Crawler crawler = new Crawler(requests);
        PipeLine pipeLine = new PipeLine();
        Spider.create(crawler)
                .thread(Runtime.getRuntime().availableProcessors())
                .addUrl("https://www.zylsj.com/forum-42-1.html")
                .addPipeline(pipeLine)
                .run();
        Map<String, String> dataMap = pipeLine.getDataMap();
        List<String> list = new ArrayList<>(1620);
        for (Map.Entry<String, String> entry : dataMap.entrySet()){
            list.add(URL + entry.getKey() + "  " + entry.getValue());
        }
        FileWriter writer = new FileWriter("C:\\Users\\Administrator\\Desktop\\data.txt");
        writer.appendLines(list);
    }
}
