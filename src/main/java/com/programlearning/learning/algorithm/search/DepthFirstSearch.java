package com.programlearning.learning.algorithm.search;

import java.util.*;

/**
 * 回溯算法
 * 回溯算法通常采用深度优先搜索来遍历解空间
 * 深度优先搜索是一种优先走到底、无路可走再回头的遍历方式，通常基于递归来实现
 *
 * 经典例题
 * 一、搜索问题：这类问题的目标是找到满足特定条件的解决方案。
 * （1）全排列问题：给定一个集合，求出其所有可能的排列组合。
 * （2）子集和问题：给定一个集合和一个目标和，找到集合中所有和为目标和的子集。
 * （3）汉诺塔问题：给定三个柱子和一系列大小不同的圆盘，要求将所有圆盘从一个柱子移动到另一个柱子，每次只能移动一个圆盘，且不能将大圆盘放在小圆盘上。
 *
 * 二、约束满足问题：这类问题的目标是找到满足所有约束条件的解。
 * （1）n皇后：在 n * n 的棋盘上放置n个皇后，使得它们互不攻击。
 * （2）数独：在 9 * 9 的网格中填入数字 1 ~ 9，使得每行、每列和每个 3 * 3 子网格中的数字不重复。
 * （3）图着色问题：给定一个无向图，用最少的颜色给图的每个顶点着色，使得相邻顶点颜色不同。
 *
 * 三、组合优化问题：这类问题的目标是在一个组合空间中找到满足某些条件的最优解。
 * （1）0-1 背包问题：给定一组物品和一个背包，每个物品有一定的价值和重量，要求在背包容量限制内，选择物品使得总价值最大。
 * （2）旅行商问题：在一个图中，从一个点出发，访问所有其他点恰好一次后返回起点，求最短路径。
 * （3）最大团问题：给定一个无向图，找到最大的完全子图，即子图中的任意两个顶点之间都有边相连。
 */
public class DepthFirstSearch {

    public static void main(String[] args) {
        // 全排列
        for (List<Integer> list : DepthFirstSearch.permutations(new int[]{1,2,3})) {
            list.forEach(System.out::print);
            System.out.println();
        }
        System.out.println();

        // N皇后问题
        int n = 5;
        for (char[][] chars : nQueens(n)) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    System.out.print(chars[i][j]);
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    /**
     * 回溯算法：全排列
     */
    private static void backtrack(List<Integer> state, int[] choices, boolean[] selected, List<List<Integer>> res) {
        // 当状态长度等于元素数量时，记录解
        if (state.size() == choices.length) {
            res.add(new ArrayList<>(state));
            return;
        }
        // 遍历所有选择
        Set<Integer> duplicated = new HashSet<>();
        for (int i = 0; i < choices.length; i++) {
            int choice = choices[i];
            // 剪枝：不允许重复选择元素 且 不允许重复选择相等元素
            if (!selected[i] && !duplicated.contains(choice)) {
                // 尝试：做出选择，更新状态
                duplicated.add(choice); // 记录选择过的元素值
                selected[i] = true;
                state.add(choice);
                // 进行下一轮选择
                backtrack(state, choices, selected, res);
                // 回退：撤销选择，恢复到之前的状态
                selected[i] = false;
                state.remove(state.size() - 1);
            }
        }
    }
    private static List<List<Integer>> permutations(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        backtrack(new ArrayList<>(), nums, new boolean[nums.length], res);
        return res;
    }

    /**
     * 回溯算法：N皇后问题
     */
    private static void backtrack(int row, int n, char[][] state, List<char[][]> res,
                                  boolean[] cols, boolean[] diags1, boolean[] diags2) {
        // 当放置完所有行时，记录解
        if (row == n) {
            char[][] copyState = new char[n][n];
            for (int i = 0; i < n; i++) {
                System.arraycopy(state[i], 0, copyState[i], 0, n);
            }
            res.add(copyState);
            return;
        }
        // 遍历所有列
        for (int col = 0; col < n; col++) {
            // 计算该格子对应的主对角线和副对角线
            int diag1 = row - col + n - 1;
            int diag2 = row + col;
            // 剪枝：不允许该格子所在 (列 或 主对角线 或 副对角线) 包含皇后
            if (!(cols[col] || diags1[diag1] || diags2[diag2])) {
                // 尝试：将皇后放置在该格子
                state[row][col] = 'Q';
                cols[col] = diags1[diag1] = diags2[diag2] = true;
                // 放置下一行
                backtrack(row + 1, n, state, res, cols, diags1, diags2);
                // 回退：将该格子恢复为空位
                state[row][col] = '#';
                cols[col] = diags1[diag1] = diags2[diag2] = false;
            }
        }
    }
    private static List<char[][]> nQueens(int n) {
        // 初始化 n*n 大小的棋盘，其中 'Q' 代表皇后，'#' 代表空位
        char[][] state = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                state[i][j] = '#';
            }
        }
        // 记录列是否有皇后
        boolean[] cols = new boolean[n];
        // 记录主对角线是否有皇后
        boolean[] diags1 = new boolean[2 * n - 1];
        // 记录副对角线是否有皇后
        boolean[] diags2 = new boolean[2 * n - 1];
        List<char[][]> res = new ArrayList<>();
        backtrack(0, n, state, res, cols, diags1, diags2);
        return res;
    }

    /**
     * 相较于基于前序遍历的实现代码，基于回溯算法框架的实现代码虽然显得啰嗦，但通用性更好。
     * 实际上，所有回溯问题都可以在该框架下解决。我们需要根据具体问题来定义 state 和 choices，并实现框架中的各个方法。
     */
    private static class BackTrackTemplate {
        /**
         * 判断当前状态是否为解
         */
        private static boolean isSolution(List<TreeNode> state) {
            return !state.isEmpty() && state.get(state.size() - 1).getValue() == 7;
        }

        /**
         * 记录解
         */
        private static void recordSolution(List<TreeNode> state, List<List<TreeNode>> res) {
            res.add(new ArrayList<>(state));
        }

        /**
         * 判断在当前状态下，该选择是否合法
         */
        private static boolean isValid(List<TreeNode> state, TreeNode choice) {
            return choice != null && choice.getValue() != 3;
        }

        /**
         * 更新状态
         */
        private static void makeChoice(List<TreeNode> state, TreeNode choice) {
            state.add(choice);
        }

        /**
         * 恢复状态
         */
        private static void undoChoice(List<TreeNode> state, TreeNode choice) {
            state.remove(state.size() - 1);
        }

        /**
         * 回溯算法
         */
        private static void backtrack(List<TreeNode> state, List<TreeNode> choices, List<List<TreeNode>> res) {
            // 检查是否为解
            if (isSolution(state)) {
                // 记录解
                recordSolution(state, res);
                return;
            }
            // 遍历所有选择
            for (TreeNode choice : choices) {
                // 剪枝：检查选择是否合法
                if (isValid(state, choice)) {
                    // 尝试：做出选择，更新状态
                    makeChoice(state, choice);
                    // 进行下一轮选择
                    backtrack(state, Arrays.asList(choice.left, choice.right), res);
                    // 回退：撤销选择，恢复到之前的状态
                    undoChoice(state, choice);
                }
            }
        }
    }

    private static class TreeNode {
        TreeNode left;
        TreeNode right;
        int value;

        public TreeNode getLeft() {
            return left;
        }

        public void setLeft(TreeNode left) {
            this.left = left;
        }

        public TreeNode getRight() {
            return right;
        }

        public void setRight(TreeNode right) {
            this.right = right;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
