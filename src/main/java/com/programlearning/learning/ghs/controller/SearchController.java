package com.programlearning.learning.ghs.controller;

import com.programlearning.learning.ghs.common.GhsConstant;
import com.programlearning.learning.ghs.spider.pipeline.ControllerPipeline;
import com.programlearning.learning.ghs.spider.pojo.VideoNode;
import com.programlearning.learning.ghs.spider.processor.SearchVideoProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import us.codecraft.webmagic.Spider;

import java.util.List;
import java.util.Objects;

/**
 * @author Ben
 */
@Controller
public class SearchController {

    @ResponseBody
    @GetMapping("/search/{key}")
    public ResponseEntity<Object> searchResult(@PathVariable String key){
        SearchVideoProcessor searchVideoProcessor = new SearchVideoProcessor();
        ControllerPipeline controllerPipeline = new ControllerPipeline();
        Spider.create(searchVideoProcessor)
                .addPipeline(controllerPipeline)
                .addUrl(GhsConstant.JAVBUS + "/search/" + key + "/")
                .thread(10)
                .run();
        List<VideoNode> videoNodeList = controllerPipeline.getVideoNodeList();
        if (Objects.isNull(videoNodeList) || videoNodeList.isEmpty()){
            return ResponseEntity.badRequest().body("没有找到资源");
        }
        return ResponseEntity.ok(videoNodeList);
    }
}
