package com.programlearning.learning.webCrawler.juejin;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class BackEndPart implements PageProcessor {


    @Override
    public void process(Page page) {

    }

    @Override
    public Site getSite() {
        return Site.me();
    }
}
