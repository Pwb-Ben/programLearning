package com.programlearning.learning.ghs.controller;

import com.programlearning.learning.ghs.spider.pipeline.VideoDetailPipeline;
import com.programlearning.learning.ghs.spider.processor.VideoDetailProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

@RestController
@RequestMapping("detail")
public class DetailController {

    @GetMapping("detail")
    public ResponseEntity<Object> getDetail(String url){
        VideoDetailProcessor videoDetailProcessor = new VideoDetailProcessor();
        VideoDetailPipeline videoDetailPipeline = new VideoDetailPipeline();
        Spider.create(videoDetailProcessor)
                .addPipeline(videoDetailPipeline)
                .addUrl(url)
                .thread(5)
                .run();
        return ResponseEntity.ok(videoDetailPipeline.getVideoDetailNode());
    }
}
