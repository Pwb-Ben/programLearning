package com.programlearning.learning.proxy.dynamicProxy;

import com.programlearning.learning.proxy.staticProxy.ProductService;
import com.programlearning.learning.proxy.staticProxy.ProductServiceImpl;

public class DynamicProxy {

    public static void main(String[] args) {
        ProductService impl = new ProductServiceImpl();
        ProductInvocationHandler productInvocationHandler = new ProductInvocationHandler(impl);
        ProductService proxy = (ProductService) productInvocationHandler.getProxy();
        proxy.addProduct("book");
    }
}
