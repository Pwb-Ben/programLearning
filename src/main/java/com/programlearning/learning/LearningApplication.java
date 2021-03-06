package com.programlearning.learning;

import com.programlearning.learning.spring.initializingBean.TestInitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

/**
 * 包扫描的触发时机在这里：执行 ConfigurationClassPostProcessor 的 postProcessBeanDefinitionRegistry 方法，解析 @ComponentScan 时触发。
 */
@SpringBootApplication
public class LearningApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(LearningApplication.class, args);
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
