package com.programlearning.learning.ghs.controller;

import com.programlearning.learning.ghs.common.GhsConstant;
import com.programlearning.learning.ghs.spider.SpiderPipeline;
import com.programlearning.learning.ghs.spider.SpiderProcessor;
import com.programlearning.learning.ghs.spider.pipeline.SearchVideoPipeline;
import com.programlearning.learning.ghs.spider.pojo.PageNode;
import com.programlearning.learning.ghs.spider.processor.SearchVideoProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

import java.util.Objects;

/**
 * @author Ben
 */
@RestController
@RequestMapping("search")
public class SearchController {

    private static final String NO_RESOURCE = "no resource";

    private SpiderPipeline spider(SpiderProcessor spiderProcessor, SpiderPipeline spiderPipeline, String url){
        Spider.create(spiderProcessor)
                .addPipeline(spiderPipeline)
                .addUrl(url)
                .thread(5)
                .run();
        return spiderPipeline;
    }

    @GetMapping("search")
    public ResponseEntity<Object> searchDefault(){
        SearchVideoProcessor spiderProcessor = new SearchVideoProcessor();
        SearchVideoPipeline searchVideoPipeline = new SearchVideoPipeline();
        spider(spiderProcessor, searchVideoPipeline, GhsConstant.JAVBUS);
        PageNode pageNode = searchVideoPipeline.getPageNode();
        if (Objects.isNull(pageNode)){
            return ResponseEntity.badRequest().body(NO_RESOURCE);
        }
        return ResponseEntity.ok(pageNode);
    }

    @GetMapping("search/{key}")
    public ResponseEntity<Object> searchResult(@PathVariable String key){
        SpiderProcessor spiderProcessor = new SearchVideoProcessor();
        SearchVideoPipeline searchVideoPipeline = new SearchVideoPipeline();
        spider(spiderProcessor, searchVideoPipeline, GhsConstant.JAVBUS + "/search/" + key + "/");
        PageNode pageNode = searchVideoPipeline.getPageNode();
        if (Objects.isNull(pageNode)){
            return ResponseEntity.badRequest().body(NO_RESOURCE);
        }
        return ResponseEntity.ok(pageNode);
    }

    @GetMapping("searchNextPage")
    public ResponseEntity<Object> searchNextPage(String nextPageUrl){
        SpiderProcessor spiderProcessor = new SearchVideoProcessor();
        SearchVideoPipeline searchVideoPipeline = new SearchVideoPipeline();
        spider(spiderProcessor, searchVideoPipeline, GhsConstant.JAVBUS + nextPageUrl);
        PageNode pageNode = searchVideoPipeline.getPageNode();
        if (Objects.isNull(pageNode)){
            return ResponseEntity.badRequest().body(NO_RESOURCE);
        }
        return ResponseEntity.ok(pageNode);
    }
}
