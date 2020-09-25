package com.programlearning.learning.ghs.spider.processor;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.programlearning.learning.ghs.common.GhsConstant;
import com.programlearning.learning.ghs.spider.SpiderProcessor;
import com.programlearning.learning.ghs.spider.pojo.MagnetNode;
import com.programlearning.learning.ghs.spider.pojo.VideoDetailNode;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class VideoDetailProcessor extends SpiderProcessor {

    private static final String GET_MAGNET_URL = "https://www.javbus.com/ajax/uncledatoolsbyajax.php";

    @Override
    public void process(Page page) {
        super.process(page);
        if (page.getUrl().regex(ReUtil.escape(VideoDetailProcessor.GET_MAGNET_URL) + ".*").match()){
            // 如果是获取磁链的ajax请求
            List<String> magnetUrlList = page.getHtml().xpath("a/@href").all();
            if (Objects.nonNull(magnetUrlList) && magnetUrlList.size() > 0){
                magnetUrlList = magnetUrlList.stream().distinct().filter(StrUtil::isNotBlank).collect(Collectors.toList());
                List<MagnetNode> magnetNodeList = magnetUrlList.stream().map(magnetUrl -> {
                    List<String> l = page.getHtml().xpath("//a[contains(@href,'" + magnetUrl + "')]").xpath("a/text()").all();
                    MagnetNode magnetNode = new MagnetNode();
                    magnetNode.setName(l.get(0).strip());
                    magnetNode.setSize(l.get(1).strip());
                    magnetNode.setPublishDate(l.get(2).strip());
                    magnetNode.setUrl(magnetUrl);
                    return magnetNode;
                }).collect(Collectors.toList());
                page.putField("magnetNodeList", magnetNodeList);
            }else{
                page.putField("magnetNodeList", null);
            }
        }else{
            VideoDetailNode videoDetailNode = new VideoDetailNode();
            // 获取标题
            videoDetailNode.setTitle(page.getHtml().xpath("h3/text()").get());
            // 获取封面图像
            videoDetailNode.setBigImage(page.getHtml().$("a.bigImage").xpath("a/@href").get());
            // 获取样品图像
            videoDetailNode.setSampleList(page.getHtml().xpath("//div[contains(@id,'sample-waterfall')]").xpath("a/@href").all());
            // info信息节点
            List<Selectable> list = page.getHtml().$("div.info").xpath("p").nodes();
            list.stream().parallel().forEach(item -> {
                // 利用header类选择器筛选出标题
                String spanHeader = item.$("span.header").get();

                if(StrUtil.isNotBlank(spanHeader)){
                    // 如果有内容获取标题文本
                    String headerText = item.$("span.header").xpath("span/text()").get();
                    switch (headerText){
                        case "識別碼:":
                            videoDetailNode.setUid(item.xpath("span").nodes().get(1).xpath("span/text()").get().strip());
                            break;
                        case "發行日期:":
                            videoDetailNode.setPublishDate(item.xpath("p/text()").get().strip());
                            break;
                        case "長度:":
                            videoDetailNode.setLength(item.xpath("p/text()").get().strip());
                            break;
                        case "導演:":
                            videoDetailNode.setDirector(item.xpath("a/text()").get().strip());
                            break;
                        case "製作商:":
                            videoDetailNode.setManufacturer(item.xpath("a/text()").get().strip());
                            break;
                        case "發行商:":
                            videoDetailNode.setPublisher(item.xpath("a/text()").get().strip());
                            break;
                        case "系列:":
                            videoDetailNode.setSeries(item.xpath("a/text()").get().strip());
                            break;
                        case "演員":
                            int index = list.indexOf(item);
                            if (index + 1 < list.size()){
                                videoDetailNode.setActors(list.get(index + 1).xpath("a/text()").all());
                            }else{
                                videoDetailNode.setActors(null);
                            }
                            break;
                        default:
                            break;
                    }
                }else{
                    switch (item.xpath("p/text()").get()){
                        case "類別:":
                            int index = list.indexOf(item);
                            videoDetailNode.setCategories(list.get(index + 1).xpath("a/text()").all());
                            break;
                        default:
                            break;
                    }
                }
            });
            // 获取磁力链接
            List<String> scriptList = page.getHtml().xpath("script").all();
            String gid = "";
            for (String script: scriptList) {
                if (ReUtil.isMatch(".*gid.*", script)){
                    List<String> g = ReUtil.findAllGroup0("[+-]?\\d+(\\.\\d*)?|[+-]?\\.\\d+", script);
                    gid = g.get(0);
                    break;
                }
            }
            String d = String.valueOf(Math.floor(Math.random()*1e3+1));
            Request request = new Request(VideoDetailProcessor.GET_MAGNET_URL + "?gid=" + gid + "&lang=zh&uc=0&floor=" + d.substring(0, d.indexOf(".")))
                    .setMethod("GET")
                    .addCookie("__cfduid", "de7d4bcd91b009840963f1037b878637e1601010616")
                    .addCookie("existmag", "mag")
                    .addCookie("PHPSESSID", "f305c7ff4gblffqqkojv7cbhg7")
                    .addHeader("referer", GhsConstant.JAVBUS + "/" + videoDetailNode.getUid())
                    .addHeader("sec-fetch-dest", "empty")
                    .addHeader("sec-fetch-mode", "cors")
                    .addHeader("sec-fetch-site", "same-origin")
                    .addHeader("Accept", "*/*")
                    .addHeader("x-requested-with", "XMLHttpRequest")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36");
            page.addTargetRequest(request);
            page.putField("videoDetailNode", videoDetailNode);
        }
    }
}
