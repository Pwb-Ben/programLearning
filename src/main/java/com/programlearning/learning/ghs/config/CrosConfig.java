package com.programlearning.learning.ghs.config;

import com.programlearning.learning.ghs.common.FileProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CrosConfig implements WebMvcConfigurer {

    private FileProperties fileProperties;

    public CrosConfig(FileProperties fileProperties) {
        this.fileProperties = fileProperties;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/h5/123.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        FileProperties.FilePath path = fileProperties.getPath();
        String pathUtl = "file:" + path.getPath().replace("\\","/");
        registry.addResourceHandler("/file/**").addResourceLocations(pathUtl).setCachePeriod(0);
        registry.addResourceHandler("/h5/**").addResourceLocations(fileProperties.getHtml()).setCachePeriod(0);
    }
}
