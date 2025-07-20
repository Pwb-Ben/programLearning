package com.programlearning.learning.war3connection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class IPPool {

    private final BlockingQueue<String> availableIPs;

    public IPPool() {
        this.availableIPs = new LinkedBlockingQueue<>();
        initializeIPs();
    }

    private void initializeIPs() {
        // 初始化IP范围：127.0.1.1 到 127.0.1.255
        for (int i = 1; i <= 255; i++) {
            availableIPs.add("127.0.1." + i);
        }
    }

    /**
     * 获取一个可用IP
     *
     * @return IP地址字符串
     * @throws InterruptedException 如果等待时被中断
     */
    public String acquireIP() throws InterruptedException {
        return availableIPs.take(); // 阻塞直到有可用IP
    }

    /**
     * 释放IP地址，将其返回到池中
     *
     * @param ip 要释放的IP地址
     */
    public void releaseIP(String ip) {
        if (ip != null && ip.matches("^127\\.0\\.1\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$")) {
            availableIPs.add(ip);
        }
    }

    /**
     * 尝试获取IP（非阻塞）
     *
     * @return 可用IP或null（如果没有可用IP）
     */
    public String tryAcquireIP() {
        return availableIPs.poll();
    }

    /**
     * 获取当前可用IP数量
     *
     * @return 可用IP数量
     */
    public int availableIPCount() {
        return availableIPs.size();
    }
}
