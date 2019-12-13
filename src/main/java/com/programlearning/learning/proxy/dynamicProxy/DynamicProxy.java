package com.programlearning.learning.proxy.dynamicProxy;

import com.programlearning.learning.proxy.staticProxy.ProductService;
import com.programlearning.learning.proxy.staticProxy.ProductServiceImpl;

public class DynamicProxy {

    public static void main(String[] args) {

        // 把生成的代理类写入磁盘
        // System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles","true");

        ProductService impl = new ProductServiceImpl();
        ProductInvocationHandler productInvocationHandler = new ProductInvocationHandler(impl);
        ProductService proxy = (ProductService) productInvocationHandler.getProxy(ProductService.class);
        proxy.addProduct("book");
    }
}
