package com.programlearning.learning.webCrawler;

import org.bouncycastle.util.Strings;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.*;
import java.util.List;

public class myPipeLine implements Pipeline {

    private int count = 1;

    private boolean isCount = false;

    private String fileName;

    public myPipeLine(String fileName, boolean isCount){
        this.fileName = fileName;
        this.isCount = isCount;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        FileWriter fileWriter = null;
        StringBuilder context;

        List<String> title = resultItems.get("title");
        List<String> time = resultItems.get("time");
        List<String> address = resultItems.get("address");

        try {
            fileWriter = new FileWriter(fileName,true);
            context = new StringBuilder();
            for (int i = 0; i<title.size(); i++){
                if (isCount){
                    context.append(count)
                            .append("    ")
                            .append(time.get(i))
                            .append("    ")
                            .append(title.get(i))
                            .append("    ")
                            .append(address.get(i))
                            .append("\r\n");
                    count++;
                }else{
                    context.append(time.get(i))
                            .append("    ")
                            .append(title.get(i))
                            .append("    ")
                            .append(address.get(i))
                            .append("\r\n");
                }
            }
            fileWriter.write(context.toString());
            //help gc
            context = null;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream("C:\\Users\\pwbco\\Desktop\\Context.txt",true);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        ObjectOutputStream oos = null;
//        try {
//            oos = new ObjectOutputStream(fos);
//            oos.writeObject(context);
//            oos.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                oos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
