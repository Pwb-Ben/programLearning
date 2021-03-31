package com.programlearning.learning.webCrawler.zylsj;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PipeLine implements Pipeline{

    private Map<String, String> dataMap = new ConcurrentHashMap<>(1620);

    @Override
    public void process(ResultItems resultItems, Task task) {
        Map<String, String> map = resultItems.get("map");
        dataMap.putAll(map);
    }

    public Map<String, String> getDataMap() {
        return dataMap;
    }

}
