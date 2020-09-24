package com.programlearning.learning.ghs.spider.processor;

import com.programlearning.learning.ghs.spider.SpiderProcessor;
import us.codecraft.webmagic.Page;

import java.util.List;

public class VideoDetailProcessor extends SpiderProcessor {

    @Override
    public void process(Page page) {
        super.process(page);
        // 获取样品图像
        List<String> sampleList =  page.getHtml().xpath("//div[contains(@id,'sample-waterfall')]").xpath("a/@href").all();
        System.out.println(sampleList);
    }
}
