package com.programlearning.learning.proxy.staticProxy;

public class ProductEmployee implements ProductService{

    ProductServiceImpl productService;

    public ProductEmployee(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @Override
    public void addProduct(String productName) {
        System.out.println("检查产品"+productName);
        productService.addProduct(productName);
        System.out.println("添加完成");
    }
}
