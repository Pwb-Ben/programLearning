package com.programlearning.learning.webCrawler.juejin;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.util.List;

public class TagListProcessor implements PageProcessor {

    @Override
    public void process(Page page) {
        List<String> data = new JsonPathSelector("$.data").selectList(page.getRawText());
        for (String tagInfo : data) {
            String tagId = new JsonPathSelector("$.tag_id").select(tagInfo);
            String tagName = new JsonPathSelector("$.tag_name").select(tagInfo);
            page.putField(tagId, tagName);
        }
    }

    @Override
    public Site getSite() {
        return Site.me().setSleepTime(100).setRetryTimes(3);
    }
}
