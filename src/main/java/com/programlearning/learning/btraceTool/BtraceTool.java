package com.programlearning.learning.btraceTool;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;

import static com.sun.btrace.BTraceUtils.jstack;
import static com.sun.btrace.BTraceUtils.println;


@BTrace
public class BtraceTool {

    /**
     * 拦截时机：
     * 1、Kind.ENTRY：入口拦截，默认值；
     * 2、Kind.RETURN：拦截返回值，只有把拦截位置定义为Kind.RETURN，才能获取方法的返回结果@Return和执行时间@Duration；
     * 3、Kind.THROW：发生异常时拦截；
     * 4、Kind.LINE：拦截某一行，可以监控代码是否执行到指定的位置；
     * 5、Kind.CALL：分析方法中调用其它方法的执行情况，比如在execute方法中，想获取add方法的执行耗时，必须把where设置成Where.AFTER；
     *
     * 技巧：
     * 1、拦截构造函数：
     * 指定method = "<init>"即可拦截指定类的构造函数；
     * 2、拦截同名函数：拦截同名重载方法，只需要在BTrace脚本的方法中声明与之对应的参数即可。
     * 3、拦截返回值：
     * 指定location=@Location(Kind.RETURN)，并且在方法的参数里面加上@Return AnyType result即可接收返回值；
     *
     * 更多详见：https://www.cnblogs.com/laoxia/p/9773319.html
     */
    @OnMethod(clazz = "java.util.concurrent.ArrayBlockingQueue", method = "take", location = @Location(value = Kind.ENTRY))
    public static void onTraceMyTarget() {
        println("on take");
        jstack();
    }
}
