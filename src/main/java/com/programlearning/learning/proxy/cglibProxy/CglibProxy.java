package com.programlearning.learning.proxy.cglibProxy;

import com.programlearning.learning.proxy.staticProxy.ProductServiceImpl;
import net.sf.cglib.core.DebuggingClassWriter;

public class CglibProxy {

    public static void main(String[] args) {
        // 把动态代理生成的.class文件写到磁盘上
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"C:\\Users\\pwbco\\Desktop\\code");
        ProductMethodInterceptor productMethodInterceptor = new ProductMethodInterceptor();
        ProductServiceImpl productService = productMethodInterceptor.getProxy(ProductServiceImpl.class);
        productService.addProduct(null);
    }
}
