package com.programlearning.learning.webCrawler.cloudfactory;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.math.BigDecimal;
import java.util.List;

public class PipeLine implements Pipeline{

    private BigDecimal billSum = BigDecimal.ZERO;
    private BigDecimal priceSum = BigDecimal.ZERO;

    private final Object lock = new Object();

    private int count = 0;

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<String> bill = resultItems.get("bill");
        List<String> price = resultItems.get("price");
        synchronized (lock){
            billSum = bill.stream().map(BigDecimal::new).reduce(billSum, BigDecimal::add);
            priceSum = price.stream().map(item -> {
                BigDecimal bigDecimal;
                if(item.contains("kw")){
                    Double d = Double.valueOf(item.replaceAll("[a-zA-Z]", ""));
                    bigDecimal = BigDecimal.valueOf(d * 10000000);
                }else if(item.contains("w")){
                    Double d = Double.valueOf(item.replaceAll("[a-zA-Z]", ""));
                    bigDecimal = BigDecimal.valueOf(d * 10000);
                }else if(item.contains("k")){
                    Double d = Double.valueOf(item.replaceAll("[a-zA-Z]", ""));
                    bigDecimal = BigDecimal.valueOf(d * 1000);
                }else{
                    bigDecimal = new BigDecimal(item.replaceAll("[a-zA-Z]", ""));
                }
                return bigDecimal;
            }).reduce(priceSum, BigDecimal::add);
            count++;
        }
        System.out.println("订单总成交数：" + billSum.toString());
        System.out.println("订单总金额：" + priceSum.toString());
        System.out.println("次数：" + count);
    }
}
