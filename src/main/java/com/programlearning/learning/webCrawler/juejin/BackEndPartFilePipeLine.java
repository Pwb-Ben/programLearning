package com.programlearning.learning.webCrawler.juejin;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BackEndPartFilePipeLine implements Pipeline {

    private PrintWriter printWriter;

    private ExcelWriter writer;

    private final Object mutex;

    BackEndPartFilePipeLine(String path) {
//        FileWriter writer = new FileWriter(path);
//        this.printWriter = writer.getPrintWriter(true);

        this.writer = ExcelUtil.getWriter(path);
        this.setExcelHeaderAlias();

        this.mutex = this;
    }

//    @Override
//    public void process(ResultItems resultItems, Task task) {
//        synchronized (mutex) {
//            Map<String, Object> map = resultItems.getAll();
//            for (Map.Entry<String, Object> entry : map.entrySet()) {
//                ArticleInfo a = (ArticleInfo)entry.getValue();
//                this.printWriter.print(entry.getKey() + ":\t" + a.getTitle() + "\t");
//                this.printWriter.print("分类：" + a.getCategoryName() + "\t");
//
//                List<String> tags = a.getTags();
//                StringBuilder stringBuilder = new StringBuilder("标签：");
//                for (String tag : tags) {
//                    stringBuilder.append(tag);
//                    stringBuilder.append(" ");
//                }
//                stringBuilder.append(")");
//                this.printWriter.println(stringBuilder.toString());
//            }
//            this.printWriter.flush();
//        }
//    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        synchronized (mutex) {
            List<ArticleInfo> articleInfoList = new ArrayList<>();

            Map<String, Object> map = resultItems.getAll();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                articleInfoList.add((ArticleInfo) entry.getValue());
            }

            this.writer.write(articleInfoList, false);
        }
    }

    private void setExcelHeaderAlias() {
        this.writer.addHeaderAlias("articleId", "文章ID");
        this.writer.addHeaderAlias("title", "标题");
        this.writer.addHeaderAlias("briefContent", "概要");
        this.writer.addHeaderAlias("categoryName", "文章分类");
        this.writer.addHeaderAlias("tags", "文章标签");
    }

    public void finishProcess() {
//        this.printWriter.close();
        this.writer.close();
    }
}
