package com.programlearning.learning.webCrawler.zylsj;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

public class PipeLine implements Pipeline{

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<String> list = resultItems.get("list");

    }
}
