package com.programlearning.learning.webCrawler.juejin;

import cn.hutool.core.io.file.FileWriter;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class BackEndPartFileTxtPipeLine implements Pipeline {

    private PrintWriter printWriter;

    private final Object mutex;

    BackEndPartFileTxtPipeLine(String path) {
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
                this.printWriter.print("分类：" + a.getCategoryName() + "\t");

                List<String> tags = a.getTags();
                StringBuilder stringBuilder = new StringBuilder("标签：");
                for (String tag : tags) {
                    stringBuilder.append(tag);
                    stringBuilder.append(" ");
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
