package com.programlearning.learning.webCrawler;

import com.programlearning.learning.threadPool.ExecutorsExample;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class HttpClient {

    public static void main(String[] args) throws IOException, InterruptedException {

        List<String> list = Collections.synchronizedList(new ArrayList<>());

        ExecutorsExample executorsExample = new ExecutorsExample();
        ExecutorService executorService = executorsExample.getExecutorService();
        ArrayList<Runnable> runnableTasks = new ArrayList<>();
        int start = 21033;
        int end = start + 1000;
        CountDownLatch latch = new CountDownLatch((30000 - 21033)/1000);
        while(end < 30000) {
            int finalStart = start;
            int finalEnd = end;
            runnableTasks.add(() -> {
                CloseableHttpClient httpClient = HttpClients.createDefault();
                try {
                    for (int i = finalStart; i < finalEnd; i++) {
                        HttpGet request = new HttpGet("https://v.leleketang.com/dat/hs/ma/k/video/" + i + ".mp4");
                        CloseableHttpResponse response = httpClient.execute(request);
                        try {
                            System.out.println(response.getStatusLine().getStatusCode());   // 200

                            if (response.getStatusLine().getStatusCode() == 200) {
                                list.add(i + "： " + response.getStatusLine().getStatusCode());
                            }
                        } finally {
                            response.close();
                        }
                    }
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        httpClient.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    latch.countDown();
                }
            });
            start = end;
            end = end + 1000;
            if (end >= 30000) {
                end = 30000;
            }
        }

        for(Runnable r:runnableTasks) {
            executorService.execute(r);
        }

        FileWriter fileWriter = new FileWriter("C:\\Users\\Administrator\\Desktop\\abc.txt",true);
        latch.await();
        try {
            for (String item : list) {
                fileWriter.write(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fileWriter.flush();
            fileWriter.close();
        }
    }
}
