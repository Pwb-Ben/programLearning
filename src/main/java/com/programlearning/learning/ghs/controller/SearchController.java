package com.programlearning.learning.ghs.controller;

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

    @GetMapping("/search/{key}")
    public ResponseEntity<Object> searchResult(@PathVariable String key){
        SpiderProcessor spiderProcessor = new SearchVideoProcessor();
        ControllerPipeline controllerPipeline = new ControllerPipeline();
        Spider.create(spiderProcessor)
                .addPipeline(controllerPipeline)
                .addUrl(GhsConstant.JAVBUS + "/search/" + key + "/")
                .thread(5)
                .run();
        PageNode pageNode = controllerPipeline.getPageNode();
        if (Objects.isNull(pageNode)){
            return ResponseEntity.badRequest().body("no resource");
        }
        return ResponseEntity.ok(pageNode);
    }

    @GetMapping("/searchNextPage")
    public ResponseEntity<Object> searchNextPage(String nextPageUrl){
        SpiderProcessor spiderProcessor = new SearchVideoProcessor();
        ControllerPipeline controllerPipeline = new ControllerPipeline();
        Spider.create(spiderProcessor)
                .addPipeline(controllerPipeline)
                .addUrl(GhsConstant.JAVBUS + nextPageUrl)
                .thread(5)
                .run();
        PageNode pageNode = controllerPipeline.getPageNode();
        if (Objects.isNull(pageNode)){
            return ResponseEntity.badRequest().body("no resource");
        }
        return ResponseEntity.ok(pageNode);
    }
}
