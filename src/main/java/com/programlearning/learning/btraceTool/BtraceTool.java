package com.programlearning.learning.btraceTool;

import com.sun.btrace.annotations.*;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

import static com.sun.btrace.BTraceUtils.*;


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

    static String[] opList = {"+","-","*","/"};
    static String[] cardList = {"0","A","2","3","4","5","6","7","8","9","10","J","Q","K"};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String str = scanner.nextLine();
            String[] strings = str.split("\\s+");
            double[] doubles = new double[4];
            boolean flag = true;
            for (int i = 0; i < 4; i++) {
                if ("joker".equals(strings[i]) || "JOKER".equals(strings[i])) {
                    flag = false;
                    break;
                }else if ("J".equals(strings[i])){
                    doubles[i] = 11;
                }else if ("Q".equals(strings[i])){
                    doubles[i] = 12;
                }else if ("K".equals(strings[i])){
                    doubles[i] = 13;
                }else if ("A".equals(strings[i])){
                    doubles[i] = 1;
                }else {
                    doubles[i] = Double.valueOf(strings[i]);
                }
            }
            if (!flag){
                System.out.println("ERROR");
            }else if (!func(doubles)){
                System.out.println("NONE");
            }
        }
    }

    /**
     * 运算函数
     * @param a
     * @param b
     * @param op
     * @return
     */
    static double op(double a, double b, int op){
        if (op == 0){
            return a+b;
        }else if (op == 1){
            return a-b;
        }else if (op == 2){
            return a*b;
        }else if (op == 3){
            return a/b;
        }
        return 0;
    }

    /**
     * 计算24点
     * @param input     输入4个数
     * @param op        运算符3个
     * @return
     */
    static boolean cal24(double input[], int op[]){
        double[] doubles = new double[4];
        System.arraycopy(input, 0, doubles, 0, 4);
        for (int i = 0; i < input.length - 1; i++) {
            doubles[i+1] = op(doubles[i], doubles[i+1], op[i]);
        }
        return Math.abs(doubles[3] - 24) < 0.01;
    }

    /**
     * 遍历函数
     * @param input
     * @return
     */
    static boolean func(double input[]){
        boolean flag = false;
        int[] op = new int[4];
        for(int i = 0; i < 4 && !flag; i++) {
            op[0] = i;
            for(int j = 0; j < 4 && !flag; j++) {
                op[1] = j;
                for(int k = 0; k < 4 && !flag; k++) {
                    op[2] = k;
                    if (cal24(input, op)) {
                        for(int m = 0; m < 3; m++){
                            int cn = (int)input[m];
                            System.out.print(cardList[cn] + opList[op[m]]);
                        }
                        System.out.print(cardList[(int)input[3]]);
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }
}
