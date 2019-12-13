package com.programlearning.learning.proxy.cglibProxy.copy;

import net.sf.cglib.beans.BeanCopier;

public class Copy {

    public static void main(String[] args) {
        User u1 = new User();
        u1.setId(1);
        u1.setName("dog");
        u1.setPhone("13600000000");

        User u2 = new User();

        /**
         * set get名字不严格会导致数据出错
         */
        BeanCopier beanCopier = BeanCopier.create(User.class, User.class, true);
        beanCopier.copy(u1, u2, (Object var1, Class var2, Object var3) -> {
            System.out.println("var1 = " + var1);
            System.out.println("var2 = " + var2);
            System.out.println("var3 = " + var3);
            return null;
        });

        System.out.println(u2.toString());
    }
}
