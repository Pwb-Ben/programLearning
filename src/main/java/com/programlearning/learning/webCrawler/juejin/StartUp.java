package com.programlearning.learning.webCrawler.juejin;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.utils.HttpConstant;

public class StartUp {

    static final String REQUEST_URL = "https://api.juejin.cn/recommend_api/v1/article/recommend_cate_feed";

    static final String ENCODING = "utf-8";

    static final int MAX_PAGE_NUM = Integer.MAX_VALUE;

    public static void main(String[] args) {
        // 爬取文章列表
        String requestBody = "{\"id_type\":2,\"sort_type\":200,\"cate_id\":\"6809637769959178254\",\"cursor\":\"0\",\"limit\":20}";
        Request request = new Request(REQUEST_URL);
        request.setMethod(HttpConstant.Method.POST);
        request.setRequestBody(HttpRequestBody.json(requestBody, ENCODING));
        BackEndPartFileExcelPipeLine backEndPartFilePipeLine = new BackEndPartFileExcelPipeLine("C:\\Users\\pwbco\\Desktop\\test\\data.xlsx");
        Spider.create(new BackEndPartProcessor())
                .addPipeline(backEndPartFilePipeLine)
                .addRequest(request)
                .run();
        // 结束回调
        backEndPartFilePipeLine.finishProcess();
    }
}
