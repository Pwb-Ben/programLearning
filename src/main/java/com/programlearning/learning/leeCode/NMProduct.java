package com.programlearning.learning.leeCode;

import java.util.Scanner;

/**
 * 题目
 * 流水线N个产品每个产品有M个零件，Aij表示生产该零件的生产时间
 * 需满足以下2个条件
 * 1. Aij需要生产出来必须保证Ai-1 j先生产出来
 * 2. Aij需要生产出来必须保证Ai j-1先生产出来
 * 问生产完N个产品需要多少时间
 *
 * 测试用例有
 * 1 3
 * 3.0
 * 2.0
 * 1.0
 * 输出6.000000
 *
 * 3 1
 * 3.0 2.0 1.0
 * 输出6.000000
 *
 * 用例3：
 * 2 4
 * 10.000000 5.000000
 * 4.500000 3.000000
 * 4.499999 2.000000
 * 2.000000 1.000000
 * 21.999999
 *
 */
public class NMProduct {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            String s = scanner.nextLine();
            String[] sl = s.split(" ");
            int m = Integer.parseInt(sl[0]), n = Integer.parseInt(sl[1]);
            double[][] doubles = new double[n][m];
            for (int i = 0; i<n; i++){
                s = scanner.nextLine();
                sl = s.split(" ");
                for (int j = 0; j<m; j++){
                    doubles[i][j] = Double.parseDouble(sl[j]);
                }
            }
            double res = dp(doubles,n-1,m-1);
            System.out.println(String.format("%.6f", res));
        }
    }

    /**
     * 动态规划
     * @param A
     * @param i
     * @param j
     * @return
     */
    static double dp(double[][] A, int i, int j){
        if (i == 0 && j == 0){
            return A[0][0];
        }else if (i == 0){
            return dp(A, i, j-1) + A[i][j];
        }else if (j == 0){
            return dp(A, i-1, j) + A[i][j];
        }else{
            double d1 = dp(A, i-1, j);
            double d2 = dp(A, i, j-1);
            return d1-d2>=0.000001?d1+A[i][j]:d2+A[i][j];
        }
    }
}
