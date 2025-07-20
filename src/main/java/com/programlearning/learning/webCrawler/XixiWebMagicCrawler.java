package com.programlearning.learning.webCrawler;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.lang.Console;
import cn.hutool.core.thread.ConcurrencyTester;
import cn.hutool.core.thread.SyncFinisher;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class XixiWebMagicCrawler implements PageProcessor {

    @Override
    public void process(Page page) {
//        String tit = page.getHtml().css("div.tit").xpath("p/text()").get();
//        String title = page.getHtml().css("div.title2").xpath("h2/text()").get();
//        String context = page.getHtml().css("div.newscont").get();

//        FileWriter writer = new FileWriter("D:\\文章\\" + tit + "\\" + title + ".txt");
//        writer.write(context);


        List<String> titleList = page.getHtml().$("ul.honor-list").xpath("p/text()").all();
        List<String> imageList = page.getHtml().$("ul.honor-list").xpath("img/@src").all();
        for (int i = 0; i < titleList.size(); i++) {
//            String url = StrUtil.startWith(image, "http") ? image : "http://www.tansincn.com" + image;
//            System.out.println(tit + ", " + title + ", " + url);
//            HttpResponse res = HttpRequest.get(url).execute();
//            String[] ss = image.split("/");
//            FileUtil.writeBytes(res.bodyBytes(), "D:\\天诚技术\\文章\\" + tit + "\\" + title + "\\" + ss[ss.length - 1]);

            String title = titleList.get(i);
            String image = imageList.get(i);
            String url = StrUtil.startWith(image, "http") ? image : "http://www.tansincn.com" + image;
            HttpResponse res = HttpRequest.get(url).execute();
            String[] ss = image.split("/");
            FileUtil.writeBytes(res.bodyBytes(), "D:\\天诚技术\\合作伙伴\\" + title + ".png");
        }
    }

    @Override
    public Site getSite() {
        return Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);
    }

    public static void main(String[] args) {
//        List<String> arrayList = new ArrayList<>();
//        List<String> dir = FileUtil.listFileNames("D:\\天诚技术");
//        for (String item : dir) {
//            FileReader fileReader = new FileReader("D:\\天诚技术\\" + item);
//            List<String> lines = fileReader.readLines();
//            for (String line : lines) {
//                String[] s = StrUtil.split(line, ",");
//                arrayList.add("http://www.tansincn.com" + s[1]);
//            }
//        }
//        Spider.create(new XixiWebMagicCrawler())
//                .addUrl(arrayList.toArray(new String[0]))
//                .thread(5)
//                .run();

        /**

        AtomicInteger atomicInteger = new AtomicInteger(0);
        AtomicInteger success = new AtomicInteger(0);
        AtomicInteger fail = new AtomicInteger(0);

        int num = 100000;
        ExecutorService executorService = Executors.newFixedThreadPool(200);
        CountDownLatch countDownLatch = new CountDownLatch(num);
        Runnable runnable = () -> {
            int i = atomicInteger.addAndGet(1);
            try {
                // springboot
                String res = HttpUtil.get("http://localhost:8000/api/hello?param=1", 10000);
                // gin
//                String res = HttpUtil.get("http://localhost:8080/ping");

//                int ran = RandomUtil.randomInt(1, 1000);
//                String res = HttpUtil.get("http://localhost:8080/ping?param=" + ran);
                Console.log("No.{}, {} test finished, res: {}", i, Thread.currentThread().getName(), res);
                success.addAndGet(1);
            } catch (Exception e) {
                Console.log("No.{}, 请求异常：{}", i, e.getMessage());
                Console.error(e);
                fail.addAndGet(1);
            }
            countDownLatch.countDown();
        };

        long start = System.currentTimeMillis();

        for(int i = 0; i < num; ++i) {
            executorService.submit(runnable);
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        executorService.shutdown();

        long end = System.currentTimeMillis();

        // 获取总的执行时间，单位毫秒
        Console.log("完成全部请求耗时：" + (end-start) + "毫秒");
        Console.log("总请求次数：{}, 成功次数：{}，失败次数：{}", atomicInteger, success, fail);

         **/

        // 图片下载
//        List<String> dir = FileUtil.listFileNames("D:\\天诚技术");
//        for (String item : dir) {
//            FileReader fileReader = new FileReader("D:\\天诚技术\\" + item);
//            List<String> lines = fileReader.readLines();
//            for (String line : lines) {
//                String[] s = StrUtil.split(line, ",");
//                HttpResponse res = HttpRequest.get("http://www.tansincn.com" + s[2]).execute();
//                String[] ss = item.split("\\.");
//                FileUtil.writeBytes(res.bodyBytes(), "D:\\天诚技术\\列表图片\\" + ss[0] + "\\" + s[0] + ".jpg");
//            }
//        }

        Spider.create(new XixiWebMagicCrawler())
                .addUrl("http://www.tansincn.com/honor_13.html")
                .thread(5)
                .run();
    }
}