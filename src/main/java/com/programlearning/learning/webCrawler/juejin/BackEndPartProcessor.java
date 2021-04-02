package com.programlearning.learning.webCrawler.juejin;

import com.alibaba.fastjson.JSONObject;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BackEndPartProcessor implements PageProcessor {

    private static volatile AtomicInteger pageNum = new AtomicInteger(0);

    @Override
    public void process(Page page) {
        String responseBody = page.getRawText();
        boolean hasMore = Boolean.parseBoolean(new JsonPathSelector("$.has_more").select(responseBody));
        if (hasMore && pageNum.incrementAndGet() < StartUp.MAX_PAGE_NUM) {
            String cursor = new JsonPathSelector("$.cursor").select(responseBody);
            String requestBody = "{\"id_type\":2,\"sort_type\":200,\"cate_id\":\"6809637769959178254\",\"cursor\":\"" + cursor + "\",\"limit\":20}";
            Request request = new Request(StartUp.REQUEST_URL);
            request.setMethod(HttpConstant.Method.POST);
            request.setRequestBody(HttpRequestBody.json(requestBody, StartUp.ENCODING));
            page.addTargetRequest(request);
        }

        List<String> dataList = new JsonPathSelector("$.data").selectList(responseBody);
        for (String item : dataList) {
            List<String> articleInfo = new JsonPathSelector("$.article_info").selectList(item);
            ArticleInfo a = JSONObject.parseObject(articleInfo.get(0), ArticleInfo.class);
            page.putField(a.getArticleId(), a);
        }
    }

    @Override
    public Site getSite() {
        return Site.me().setSleepTime(100).setRetryTimes(3);
    }
}
