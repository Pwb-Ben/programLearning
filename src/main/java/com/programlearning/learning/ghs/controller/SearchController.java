package com.programlearning.learning.ghs.controller;

import cn.hutool.core.util.StrUtil;
import com.programlearning.learning.ghs.common.GhsConstant;
import com.programlearning.learning.ghs.spider.SpiderProcessor;
import com.programlearning.learning.ghs.spider.pipeline.ControllerPipeline;
import com.programlearning.learning.ghs.spider.pojo.PageNode;
import com.programlearning.learning.ghs.spider.processor.SearchVideoProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

import java.util.Objects;

/**
 * @author Ben
 */
@RestController
public class SearchController {

    private static final String NO_RESOURCE = "no resource";

    private PageNode spider(SpiderProcessor spiderProcessor, ControllerPipeline controllerPipeline, String url){
        Spider.create(spiderProcessor)
                .addPipeline(controllerPipeline)
                .addUrl(url)
                .thread(5)
                .run();
        return controllerPipeline.getPageNode();
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchDefault(){
        SpiderProcessor spiderProcessor = new SearchVideoProcessor();
        ControllerPipeline controllerPipeline = new ControllerPipeline();
        PageNode pageNode = spider(spiderProcessor, controllerPipeline, GhsConstant.JAVBUS);
        if (Objects.isNull(pageNode)){
            return ResponseEntity.badRequest().body(NO_RESOURCE);
        }
        return ResponseEntity.ok(pageNode);
    }

    @GetMapping("/search/{key}")
    public ResponseEntity<Object> searchResult(@PathVariable String key){
        SpiderProcessor spiderProcessor = new SearchVideoProcessor();
        ControllerPipeline controllerPipeline = new ControllerPipeline();
        PageNode pageNode = spider(spiderProcessor, controllerPipeline, GhsConstant.JAVBUS + "/search/" + key + "/");
        if (Objects.isNull(pageNode)){
            return ResponseEntity.badRequest().body(NO_RESOURCE);
        }
        return ResponseEntity.ok(pageNode);
    }

    @GetMapping("/searchNextPage")
    public ResponseEntity<Object> searchNextPage(String nextPageUrl){
        SpiderProcessor spiderProcessor = new SearchVideoProcessor();
        ControllerPipeline controllerPipeline = new ControllerPipeline();
        PageNode pageNode = spider(spiderProcessor, controllerPipeline, GhsConstant.JAVBUS + nextPageUrl);
        if (Objects.isNull(pageNode)){
            return ResponseEntity.badRequest().body(NO_RESOURCE);
        }
        return ResponseEntity.ok(pageNode);
    }
}
