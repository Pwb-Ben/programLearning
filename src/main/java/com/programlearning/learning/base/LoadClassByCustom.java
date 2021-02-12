package com.programlearning.learning.base;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Administrator
 */
public class LoadClassByCustom extends ClassLoader{

    @Override
    protected Class findClass(String name, String path){
        byte[] cLassBytes;
        try {
            cLassBytes = Files.readAllBytes(Paths.get(path));
            return defineClass(name, cLassBytes, 0, cLassBytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        LoadClassByCustom loadClassByCustom = new LoadClassByCustom();
        System.out.println(LoadClassByCustom.class.getClassLoader());
        Class clazz = loadClassByCustom.findClass("cn.testForLoadClass.TestForLoadClass", "src\\main\\resources\\cn\\testForLoadClass\\TestForLoadClass.class");

        try {
            System.out.println(clazz.getClassLoader());
            Object object = clazz.newInstance();
            Method method = clazz.getMethod("helloWorld");
            method.invoke(object);
        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

