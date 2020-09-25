package com.programlearning.learning.ghs.spider.pipeline;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpUtil;
import com.programlearning.learning.ghs.spider.SpiderPipeline;
import com.programlearning.learning.ghs.spider.pojo.VideoDetailNode;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

import java.util.Objects;
import java.util.stream.Collectors;

public class VideoDetailPipeline extends SpiderPipeline {

    private VideoDetailNode videoDetailNode;

    @Override
    public void process(ResultItems resultItems, Task task) {
        if (Objects.isNull(resultItems.get("videoDetailNode"))){
            this.videoDetailNode.setMagnetList(resultItems.get("magnetNodeList"));
        }else{
            this.videoDetailNode = resultItems.get("videoDetailNode");
            this.videoDetailNode.setBigImage("data:image/jpg;base64," + Base64.encode(HttpUtil.downloadBytes(this.videoDetailNode.getBigImage())));
            this.videoDetailNode.setSampleList(this.videoDetailNode.getSampleList().stream().parallel().map(sample -> "data:image/jpg;base64," + Base64.encode(HttpUtil.downloadBytes(sample))).collect(Collectors.toList()));
        }
    }

    public VideoDetailNode getVideoDetailNode() {
        return videoDetailNode;
    }

    public void setVideoDetailNode(VideoDetailNode videoDetailNode) {
        this.videoDetailNode = videoDetailNode;
    }
}
