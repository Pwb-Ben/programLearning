package com.programlearning.learning.base;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二叉树遍历
 *
 * 深度遍历的运行过程是先进后出的，自然的方法是栈和递归
 * 广度遍历的运行过程是先进先出的，自然的方法是队列
 */
public class BinaryTreeTraversal {

    private class TreeNode{
        public TreeNode left;
        public TreeNode right;
        public String value;
    }

    // 前序遍历 递归
    public static void preorderTraversalByRecursion(TreeNode treeNode){
        if (treeNode==null) {
            return;
        }else {
            System.out.println(treeNode.value);
            preorderTraversalByRecursion(treeNode.left);
            preorderTraversalByRecursion(treeNode.right);
        }
    }

    // 前序遍历 非递归借助栈
    public static void preorderTraversalByStack(TreeNode treeNode){
        if (treeNode==null) {
            return;
        }else{
            Stack<TreeNode> stack = new Stack<>();
            while (treeNode!=null || !stack.isEmpty()){
                while (treeNode!=null){
                    System.out.println(treeNode.value);
                    stack.push(treeNode);
                    treeNode = treeNode.left;
                }
                if (!stack.isEmpty()){
                    treeNode = stack.pop();
                    treeNode = treeNode.right;
                }
            }
        }
    }

    // 中序遍历 递归
    public static void inorderTraversalByRecursion(TreeNode treeNode){
        if (treeNode==null){
            return;
        }else{
            inorderTraversalByRecursion(treeNode.left);
            System.out.println(treeNode.value);
            inorderTraversalByRecursion(treeNode.right);
        }
    }

    // 中序遍历 非递归
    public static void inorderTraversalByStack(TreeNode treeNode){
        if (treeNode==null){
            return;
        }else{
            Stack<TreeNode> stack = new Stack<>();
            while (treeNode!=null || !stack.isEmpty()){
                while (treeNode!=null){
                    stack.push(treeNode);
                    treeNode = treeNode.left;
                }
                if (!stack.isEmpty()){
                    treeNode = stack.pop();
                    System.out.println(treeNode.value);
                    treeNode = treeNode.right;
                }
            }
        }
    }

    // 后序遍历 递归
    public static void postorderTraversalByRecursion(TreeNode treeNode){
        if (treeNode==null){
            return;
        }else {
            postorderTraversalByRecursion(treeNode.left);
            postorderTraversalByRecursion(treeNode.right);
            System.out.println(treeNode.value);
        }
    }

    // 后序遍历-非递归
    // 后序遍历递归定义：先左子树，后右子树，再根节点。
    // 后序遍历的难点在于：需要判断上次访问的节点是位于左子树，还是右子树。
    public static void postorderTraversalByStack(TreeNode treeNode){
        if (treeNode==null){
            return;
        }else {
            Stack<TreeNode> stack = new Stack<>();
            // 用来记录当前访问结点
            TreeNode cur = treeNode;
            // 记录上次访问结点
            TreeNode pre = null;

            // 先将左子树入栈
            while (cur != null){
                stack.push(cur);
                cur = cur.left;
            }

            while (!stack.isEmpty()){
                cur = stack.pop();
                // 如果当前访问结点的右孩子为空 或 与上次访问结点相同（说明顺序为：右孩子->根）
                if (cur.right == null || cur.right == pre){
                    System.out.println(cur.value);
                    // 更新上次访问结点
                    pre = cur;
                }else {
                    // 如果为根结点，再次入栈
                    stack.push(cur);
                    // 再将其右孩子入栈
                    cur = cur.right;
                    // 继续放入右孩子的左枝
                    while (cur != null){
                        stack.push(cur);
                        cur = cur.left;
                    }
                }
            }
        }
    }

    // 层次遍历 队列实现
    public static void levelTraversalByQueue(TreeNode treeNode){
        if (treeNode == null){
            return;
        }else {
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(treeNode);
            while (!queue.isEmpty()) {
                TreeNode cur = queue.poll();
                System.out.print(cur.value);
                if (cur.left != null) {
                    queue.add(cur.left);
                }
                if (cur.right != null) {
                    queue.add(cur.right);
                }
            }
        }
    }

    // 层次遍历 队列 单行打印
    public static ArrayList<ArrayList<String>> levelTraversalOneLayerPrintByQueue(TreeNode treeNode) {
        ArrayList<String> layer = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        ArrayList<ArrayList<String>> res = new ArrayList<>();
        int start = 0,end = 1;

        if (treeNode == null) {
            return res;
        }

        queue.add(treeNode);
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            layer.add(cur.value);
            start++;

            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }

            if (start == end) {
                // 下一层数量
                end = queue.size();
                // start清零
                start = 0;
                // 保存当前层的list
                res.add(layer);
                layer = new ArrayList<>();
            }
        }
        return res;
    }
}
