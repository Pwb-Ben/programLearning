package com.programlearning.learning.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class Test {

    @GetMapping("/api/hello")
    public ResponseEntity<Object> getHelloWorld(String param){
        System.out.println("param------------->"+param);
        switch (param){
            case "1":
                return ResponseEntity.ok("hello world");
            case "2":
                return ResponseEntity.badRequest().body("hello world bad request");
            default:
                return ResponseEntity.ok("hello world");
        }
    }

    @PostMapping("/api/hello")
    public ResponseEntity<Object> postHelloWorld(@RequestBody TestVo data){
        System.out.println("data------------->"+data.getParam());
        switch (data.getParam()){
            case "1":
                return ResponseEntity.ok("hello world");
            case "2":
                return ResponseEntity.badRequest().body("hello world bad request");
            default:
                return ResponseEntity.ok("hello world");
        }
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<Object> login(){
        Map<String, String> map = new HashMap<>();
        map.put("token","Bearer 123456");
        return ResponseEntity.ok(map);
    }

    @PostMapping("/api/auth/logout")
    public ResponseEntity<Object> logout(){
        return ResponseEntity.ok("logout success");
    }

    @GetMapping("/api/auth/user")
    public ResponseEntity<Object> user(){
        Map<String, Map<String, String>> map = new HashMap<>(1);
        Map<String, String> map2 = new HashMap<>(2);
        map2.put("name","Ben");
        map2.put("sex","man");
        map.put("user", map2);
        ResponseEntity.badRequest();
        return ResponseEntity.ok(map);
    }

    public static void main(String[] args) {
        System.out.println(ping("baidu.com", 10, 3000));
    }

    public static boolean ping(String ipAddress, int pingTimes, int timeOut) {
        BufferedReader in = null;
        Runtime r = Runtime.getRuntime();
        // 将要执行的ping命令,此命令是windows格式的命令
        String pingCommand = "ping " + ipAddress + " -n " + pingTimes + " -w " + timeOut;
        // Linux命令如下
        // String pingCommand = "ping" -c " + pingTimes    + " -w " + timeOut + ipAddress;
        try {
            // 执行命令并获取输出
            Process p = r.exec(pingCommand);
            if (p == null) {
                return false;
            }
            in = new BufferedReader(new InputStreamReader(p.getInputStream(), "GBK"));
            int connectedCount = 0;
            String line;
            // 逐行检查输出,计算类似出现=23ms TTL=62字样的次数
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                connectedCount += getCheckResult(line);
            }
            // 如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回真
            return connectedCount == pingTimes;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
            }
        }
    }

    private static Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)", Pattern.CASE_INSENSITIVE);
    //若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0.
    private static int getCheckResult(String line) {
        // System.out.println("控制台输出的结果为:"+line);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            return 1;
        }
        return 0;
    }
}
