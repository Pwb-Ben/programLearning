package com.programlearning.learning.webCrawler;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class myPipeLine implements Pipeline {

    private int count = 1;

    private boolean isCount = false;

    private String fileName;

    public myPipeLine(String fileName, boolean isCount){
        this.fileName = fileName;
        this.isCount = isCount;
    }

    public void process(ResultItems resultItems, Task task) {
        FileWriter fileWriter = null;
        List<String> title = resultItems.get("title");
        List<String> time = resultItems.get("time");
        List<String> address = resultItems.get("address");
        String context = null;

        try {
            fileWriter = new FileWriter(fileName,true);
            for (int i = 0; i<title.size(); i++){
                if (isCount){
                    context = count+"  "+time.get(i)+"  "+title.get(i)+"  "+address.get(i)+"\r\n";
                    count++;
                }else{
                    context = time.get(i)+"  "+title.get(i)+"  "+address.get(i)+"\r\n";
                }
                fileWriter.write(context);
            }
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
