package com.programlearning.learning.webCrawler;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class WebMagicCrawler implements PageProcessor {

    private List<String> requests;

    private WebMagicCrawler(List<String> requests){
        this.requests = requests;
    }

    public void process(Page page) {
        // 将当前页 面里的所有链接都添加到目标页面中
        //System.out.println("网站页面包含链接："+page.getHtml().links().all().toString());
        //page.addTargetRequests(page.getHtml().links().all());

        if (requests!=null){
            page.addTargetRequests(requests);

            System.out.println("网站页面包含post-title标签："+page.getHtml().$("h2.post-title").xpath("a/text()").all().toString());
            System.out.println("网站页面包含time标签："+page.getHtml().xpath("//* time/text()").all().toString());
            System.out.println("网站页面包含href标签："+page.getHtml().$("h2.post-title").links().all().toString());

            List<String> title = page.getHtml().$("h2.post-title").xpath("a/text()").all();
            List<String> time = page.getHtml().xpath("//* time/text()").all();
            List<String> address = page.getHtml().$("h2.post-title").links().all();
            if (title!=null && time!=null && title.size()>0 && time.size()>0){
                page.putField("title",title);
                page.putField("time",time);
                page.putField("address",address);
            }else {
                page.setSkip(true);
            }
        }
    }

    public Site getSite() {
        return Site.me().setSleepTime(100).setRetryTimes(3);
    }

    public static StringBuffer readFileByBytes(String fileName){
        File file = new File(fileName);
        InputStream in = null;
        StringBuffer sb = new StringBuffer();
        if (file.isFile() && file.exists()) {   //判断文件是否存在
            // 一次读多个字节
            byte[] tempBytes = new byte[1024];
            int byteRead;
            try {
                in = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 读入多个字节到字节数组中，byteRead为一次读入的字节数
            while (true) {
                try {
                    byteRead = in.read(tempBytes);
                    if (byteRead==-1) break;
                    String str = new String(tempBytes, 0, byteRead);
                    sb.append(str);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else {
            System.out.println("找不到指定的文件，请确认文件路径是否正确");
        }
        return sb;
    }

    public static void main(String[] args) {
        List<String> requests = new LinkedList<>();
//        int startIndex = 0, endIndex = 0;
//        StringBuffer stringBuffer = readFileByBytes("C:\\Users\\pwbco\\Desktop\\address.txt");
//        while (endIndex < stringBuffer.length()){
//            startIndex = stringBuffer.indexOf("http://",endIndex);
//            if (startIndex==-1) break;
//            endIndex = stringBuffer.indexOf(".html",startIndex);
//            if (endIndex==-1) break;
//            String str = stringBuffer.substring(startIndex,endIndex+5);
//            requests.add(str);
//        }

        String address = "http://www.woshipm.com/category/ucd/page/";
        for (int i = 2; i<=267; i++){
            requests.add(address+i);
        }

        WebMagicCrawler webMagicCrawler = new WebMagicCrawler(requests);
        Spider.create(webMagicCrawler)
//                .thread(5)
                .addUrl("http://www.woshipm.com/category/ucd/")
                .addPipeline(new ConsolePipeline())
                .addPipeline(new myPipeLine("C:\\Users\\pwbco\\Desktop\\context.txt",false))
                .run();
    }
}
