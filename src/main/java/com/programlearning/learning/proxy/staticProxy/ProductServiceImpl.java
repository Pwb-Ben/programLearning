package com.programlearning.learning.proxy.staticProxy;

public class ProductServiceImpl implements ProductService{

    @Override
    public void addProduct(String productName) {
        System.out.println("正在添加"+productName);
    }


}
