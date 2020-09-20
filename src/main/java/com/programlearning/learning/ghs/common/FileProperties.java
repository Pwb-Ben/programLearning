package com.programlearning.learning.ghs.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ben
 */
@Configuration
@ConfigurationProperties(prefix = "file")
public class FileProperties {

    private String html;

    private FilePath mac;

    private FilePath linux;

    private FilePath windows;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public FilePath getMac() {
        return mac;
    }

    public void setMac(FilePath mac) {
        this.mac = mac;
    }

    public FilePath getLinux() {
        return linux;
    }

    public void setLinux(FilePath linux) {
        this.linux = linux;
    }

    public FilePath getWindows() {
        return windows;
    }

    public void setWindows(FilePath windows) {
        this.windows = windows;
    }

    public FilePath getPath(){
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith(GhsConstant.WIN)) {
            return windows;
        } else if(os.toLowerCase().startsWith(GhsConstant.MAC)){
            return mac;
        }
        return linux;
    }

    public static class FilePath{

        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
