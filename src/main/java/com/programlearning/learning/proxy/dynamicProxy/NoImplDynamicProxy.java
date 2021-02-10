package com.programlearning.learning.proxy.dynamicProxy;

import com.programlearning.learning.proxy.staticProxy.ProductService;

public class NoImplDynamicProxy {

    public static void main(String[] args) {
        ProductInvocationHandler productInvocationHandler = new ProductInvocationHandler();
        ProductService proxy = productInvocationHandler.getProxy(ProductService.class);
        proxy.addProduct("book");
    }
}
