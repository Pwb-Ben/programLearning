package com.programlearning.learning.ghs.spider.processor;

import cn.hutool.core.util.StrUtil;
import com.programlearning.learning.ghs.spider.SpiderProcessor;
import com.programlearning.learning.ghs.spider.pojo.PageNode;
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
            PageNode pageNode = new PageNode();
            String current = page.getHtml().$("ul.pagination").$("li.active").xpath("a/text()").get();
            if (StrUtil.isNotBlank(current)){
                pageNode.setCurrentPage(Integer.valueOf(current));
            }else{
                pageNode.setCurrentPage(0);
            }
            String next = page.getHtml().$("ul.pagination").xpath("//a[contains(@id,'next')]").xpath("a/@href").get();
            if (StrUtil.isNotBlank(next)){
                pageNode.setNextPageUrl(next);
            }else{
                pageNode.setNextPageUrl(null);
            }
            pageNode.setVideoNodeList(videoNodeList);
            page.putField("pageNode",pageNode);
        }else{
            page.putField("pageNode",null);
        }
    }
}
