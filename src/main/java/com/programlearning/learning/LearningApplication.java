package com.programlearning.learning;

import cn.hutool.core.util.PageUtil;
import com.programlearning.learning.spring.initializingBean.TestInitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 包扫描的触发时机在这里：执行 ConfigurationClassPostProcessor 的 postProcessBeanDefinitionRegistry 方法，解析 @ComponentScan 时触发。
 */
@SpringBootApplication
public class LearningApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(LearningApplication.class, args);

//        String[] s1 = new String[]{"1","2","3","4","5","6","7","8","9","10"};
//        String[] s2 = new String[]{"11","12","13"};
//        String[] s3 = new String[]{"21","22","23","24","25","26","27","28","29","30"};
//        List<String[]> list = List.of(s1, s2, s3);
//
//        PageUtil.setFirstPageNo(1);
//        int pageNo = 2, pageSize = 8, sum = 0, index = 0;
//        int startOffset = PageUtil.getStart(pageNo, pageSize);
//
//        List<String> result = new ArrayList<>();
//        for (String[] s : list) {
//            if (result.size() == pageSize) {
//                break;
//            }
//
//            sum += s.length;
//            if (sum > startOffset) {
//                if (result.isEmpty()) {
//                    index = startOffset - (sum - s.length);
//                } else {
//                    // 上一张表不够数
//                    index = 0;
//                }
//
//                for (int i = index; i < s.length; i++) {
//                    result.add(s[i]);
//                    if (result.size() == pageSize) {
//                        break;
//                    }
//                }
//            }
//        }
//
//        result.forEach(System.out::println);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LearningApplication.class);
    }

    @Bean(initMethod = "initMethod")
    public TestInitializingBean testInitializingBean(){
        return new TestInitializingBean();
    }
}
