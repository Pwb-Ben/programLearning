package com.programlearning.learning.ghs.spider.processor;

import com.programlearning.learning.ghs.spider.SpiderProcessor;
import com.programlearning.learning.ghs.spider.pojo.VideoNode;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ben
 * 影片搜索处理类
 */
@Component
public class SearchVideoProcessor extends SpiderProcessor {

    @Override
    public void process(Page page) {
        super.process(page);
        List<Selectable> list = page.getHtml().xpath("//div[contains(@id,'waterfall')]").$("div.item").nodes();
        List<VideoNode> videoNodeList = list.stream().map(selectable -> {
            VideoNode videoNode = new VideoNode();
            videoNode.setHref(selectable.xpath("a/@href").get());
            videoNode.setImage(selectable.xpath("img/@src").get());
            videoNode.setTitle(selectable.xpath("img/@title").get());
            videoNode.setUid(selectable.xpath("date/text()").all().get(0));
            videoNode.setDate(selectable.xpath("date/text()").all().get(1));
            return videoNode;
        }).collect(Collectors.toList());
        if (!videoNodeList.isEmpty()){
            page.putField("videoNodeList",videoNodeList);
        }else{
            page.putField("videoNodeList",null);
        }
    }
}
