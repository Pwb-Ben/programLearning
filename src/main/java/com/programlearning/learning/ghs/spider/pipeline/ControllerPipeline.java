package com.programlearning.learning.ghs.spider.pipeline;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpUtil;
import com.programlearning.learning.ghs.spider.pojo.VideoNode;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ben
 */
public class ControllerPipeline implements Pipeline {

    private List<VideoNode> videoNodeList;

    @Override
    public void process(ResultItems resultItems, Task task) {
        this.videoNodeList = resultItems.get("videoNodeList");
        this.videoNodeList.stream().parallel().forEach(node -> {
            System.out.println(node.getImage());
            node.setImage("data:image/jpg;base64," + Base64.encode(HttpUtil.downloadBytes(node.getImage())));
        });
    }

    public List<VideoNode> getVideoNodeList() {
        return videoNodeList;
    }

    public void setVideoNodeList(List<VideoNode> videoNodeList) {
        this.videoNodeList = videoNodeList;
    }
}
