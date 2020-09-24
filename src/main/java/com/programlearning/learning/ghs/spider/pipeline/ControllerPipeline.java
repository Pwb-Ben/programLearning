package com.programlearning.learning.ghs.spider.pipeline;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpUtil;
import com.programlearning.learning.ghs.spider.pojo.PageNode;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Objects;

/**
 * @author Ben
 */
public class ControllerPipeline implements Pipeline {

    private PageNode pageNode;

    @Override
    public void process(ResultItems resultItems, Task task) {
        this.pageNode = resultItems.get("pageNode");
        if (Objects.nonNull(pageNode)){
            this.pageNode.getVideoNodeList().stream().parallel().forEach(node -> {
                System.out.println(node.getImage());
                node.setImage("data:image/jpg;base64," + Base64.encode(HttpUtil.downloadBytes(node.getImage())));
            });
        }
    }

    public PageNode getPageNode() {
        return pageNode;
    }

    public void setPageNode(PageNode pageNode) {
        this.pageNode = pageNode;
    }
}
