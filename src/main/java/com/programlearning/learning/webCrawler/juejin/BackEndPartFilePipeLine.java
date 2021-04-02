package com.programlearning.learning.webCrawler.juejin;

import cn.hutool.core.io.file.FileWriter;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class BackEndPartFilePipeLine implements Pipeline {

    private PrintWriter printWriter;

    private final Object mutex;

    BackEndPartFilePipeLine(String path) {
        FileWriter writer = new FileWriter(path);
        this.printWriter = writer.getPrintWriter(true);

        this.mutex = this;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        synchronized (mutex) {
            Map<String, Object> map = resultItems.getAll();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                ArticleInfo a = (ArticleInfo)entry.getValue();
                this.printWriter.print(entry.getKey() + ":\t" + a.getTitle() + "\t");

                List<String> tagIds = a.getTagIds();
                StringBuilder stringBuilder = new StringBuilder("(");
                for (String tagId : tagIds) {
                    stringBuilder.append(StartUp.tagMap.get(tagId));
                    stringBuilder.append("  ");
                }
                stringBuilder.append(")");
                this.printWriter.println(stringBuilder.toString());
            }
            this.printWriter.flush();
        }
    }

    public void finishProcess() {
        this.printWriter.close();
    }
}
