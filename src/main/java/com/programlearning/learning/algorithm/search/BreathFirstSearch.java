package com.programlearning.learning.algorithm.search;

import java.util.*;

/**
 * 广度优先搜索
 * 广度优先搜索是一种由近及远的遍历方式，从距离最近的顶点开始访问，并一层层向外扩张，通常借助「队列」来实现
 *
 * 经典例题
 * 一、查找单入口空闲区域
 * 给定一个 m * n 的矩阵，由若干1和0构成，1表示该处已被占据，0表示该处空闲，请找到最大的单入口空闲区域
 * 解释: 空闲区域是由连通的0组成的区域，位于边界的0可以构成入口，单入口空闲区域即有且只有一个位于边界的0作为入口的由连通的0组成的区域。如果两个元素在水平或垂直方向相邻，则称它们是“连通”的
 */
public class BreathFirstSearch {

    public static int m;
    public static int n;
    public static String[][] strings;
    public static int[] rukou=new int[2];
    //入口坐标
    public static int count=0;
    //入口个数
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        m=sc.nextInt();
        n=sc.nextInt();
        strings=new String[m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                strings[i][j]=sc.next();
//                System.out.println(strings[i][j]);
            }
        }
        int max=0;
        List<int[]> quyu=new ArrayList<>();
        for(int i = 0; i<m; i++){
            for(int j = 0; j<n; j++){
                if(strings[i][j].equals("O")){
                    //从左上开始遍历寻找第一个为O的坐标
                    strings[i][j]="X";
                    List<int[]> zuobiao=new ArrayList<>();
                    //保存当前空闲区域的所有坐标
                    zuobiao.add(new int[]{i,j});
                    qiuquyu(i,j,zuobiao);
                    //求当前空闲区域的所有坐标
                    if(count==1){
                        //如果入口只有一个则进行比较
                        if(max==zuobiao.size()){
                            //有大小相同的单入口空闲区域，则只需要大小，无需坐标
                            quyu.clear();
                        }else if(max<zuobiao.size()){
                            //若有更大的单入口空闲区域，则清除前面的那个，将当前的单入口空闲区域坐标放进去
                            quyu.clear();
                            quyu.add(new int[]{rukou[0],rukou[1],zuobiao.size()});
                            max=zuobiao.size();
                        }
                    }
                    count=0;
                    rukou=new int[2];
                }
            }
        }
        if(quyu.size()==1){
            //如果单入口空闲区域只有1个，则直接输出
            int[] res=quyu.get(0);
            System.out.println(res[0]+" "+res[1]+" "+res[2]);
        }else if(max!=0){
            //如果最大区域的面积不止为0，则输出最大区域
            System.out.println(max);
        }else {
            //如果没找到，则输出NULL
            System.out.println("NULL");
        }
    }
    //得到单入口区域
    public static void qiuquyu(int x, int y, List<int[]> list){
        if(x==0||x==m-1||y==0||y==n-1) {
            count++;
            rukou[0]=x;
            rukou[1]=y;
        }
        if (x<m-1) {
            if(strings[x+1][y].equals("O")){
                //因为是按顺序来，所以只需要向右或者向下遍历即可
                strings[x+1][y]="X";
                list.add(new int[]{x+1,y});
                qiuquyu(x+1,y,list);
            }
        }
        if (y < n - 1) {
            if(strings[x][y+1].equals("O")) {
                strings[x][y+1] = "X";
                list.add(new int[]{x,y+1});
                qiuquyu(x, y+1, list);
            }
        }
    }

//    static int[] dr = new int[]{-1, 0, 1, 0};
//    static int[] dc = new int[]{0, -1, 0, 1};
//    static int[][] inputMatrix;
//
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        int m = sc.nextInt();
//        int n = sc.nextInt();
//        sc.nextLine();
//        inputMatrix = new int[m][n];
//        for (int i = 0; i < m; i++){
//            String[] strings = sc.nextLine().split(" ");
//            for (int j = 0; j < n; j++) {
//                inputMatrix[i][j] = strings[j].charAt(0);
//            }
//        }
//
//        // 最大区域大小
//        int max = -1;
//        // 最大区域入口坐标
//        int x = -1, y = -1;
//        // 判断边界是否有0
//        for(int i = 0; i < m; i++) {
//            if (i == 0 || i == m - 1) {
//                // 有效入口个数，有且仅有一个有效入口
//                int counter;
//                for (int j = 0; j < n; j++) {
//                    // 发现入口 进行bfs搜索
//                    if (inputMatrix[i][j] == 0) {
//                        counter = bfs(i, j, m, n);
//                        if (counter > max) {
//                            x = i;
//                            y = j;
//                            max = counter;
//                        }
//                        if (counter == max && counter != -1) {
//                            // 有多个相同大小的区域
//                            x = -1;
//                            y = -1;
//                        }
//                    }
//                }
//            }
//        }
//        // 判断边界是否有0
//        for (int j = 0; j < n; j++) {
//            if (j == 0 || j == n - 1) {
//                // 有效入口个数，有且仅有一个有效入口
//                int counter;
//                for(int i = 0; i < m; i++) {
//                    // 发现入口 进行bfs搜索
//                    if (inputMatrix[i][j] == 0) {
//                        counter = bfs(i, j, m, n);
//                        if (counter > max) {
//                            x = i;
//                            y = j;
//                            max = counter;
//                        }
//                        if (counter == max && counter != -1) {
//                            x = -1;
//                            y = -1;
//                        }
//                    }
//                }
//            }
//        }
//
//        if (max == -1) {
//            System.out.println("NULL");
//        } else if (x == -1) {
//            System.out.println(max);
//        } else {
//            System.out.println(x + " " + y + " " + max);
//        }
//    }
//
//    /**
//     * 使用广度优先搜索
//     * @param x y 入口坐标
//     * @param m n m*n的矩阵
//     * @return  最大区域大小，无效区域返回-1
//     */
//    public static int bfs(int x, int y, int m, int n) {
//        // 区域大小计数器
//        int counter = 0;
//        // 无效区域标记
//        boolean invalid = false;
//        Queue<int[]> queue = new LinkedList<>();
//        queue.add(new int[]{x, y});
//        while (!queue.isEmpty()) {
//            int[] ints = queue.poll();
//            // 没有搜索过的
//            if (inputMatrix[ints[0]][ints[1]] == 0) {
//                // 遍历过的坐标点标记为1
//                inputMatrix[ints[0]][ints[1]] = 1;
//                // 面积+1
//                counter++;
//                // 查询相邻的坐标点是否为0
//                for(int i = 0; i < 4; i++) {
//                    int nx = ints[0] + dr[i];
//                    int ny = ints[1] + dc[i];
//                    // 横纵坐标没有越界且坐标点为0
//                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && inputMatrix[nx][ny] == 0) {
//                        // 发现第二入口 标记无效区域 并继续将剩余坐标点标记为1
//                        if (nx == 0 || nx == m - 1 || ny == 0 || ny == n - 1) {
//                            invalid = true;
//                        }
//                        queue.add(new int[]{nx, ny});
//                    }
//                }
//            }
//        }
//        return invalid ? -1 : counter;
//    }
}