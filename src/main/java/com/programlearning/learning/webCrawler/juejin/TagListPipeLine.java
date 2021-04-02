package com.programlearning.learning.webCrawler.juejin;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

public class TagListPipeLine implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        Map<String, Object> map = resultItems.getAll();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            StartUp.tagMap.put(entry.getKey(), (String) entry.getValue());
        }
    }
}
