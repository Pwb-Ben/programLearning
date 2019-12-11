package com.programlearning.learning.proxy.staticProxy;

public class StaticProxy {

    public static void main(String[] args) {
        ProductServiceImpl impl = new ProductServiceImpl();
        ProductEmployee productEmployee = new ProductEmployee (impl);
        productEmployee .addProduct("book");
    }
}
