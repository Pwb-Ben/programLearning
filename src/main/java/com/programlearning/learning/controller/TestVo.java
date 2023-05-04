package com.programlearning.learning.controller;

import java.util.*;

public class TestVo {

    private String param;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    /**
     * 1. 汽水瓶
     * 某商店规定：三个空汽水瓶可以换一瓶汽水，允许向老板借空汽水瓶（但是必须要归还）。
     * 小张手上有n个空汽水瓶，她想知道自己最多可以喝到多少瓶汽水。
     * 数据范围：输入的正整数满足
     * 1 ≤n ≤ 100
     */
//    public static void main(String[] args) {
//        List<Integer> list = new ArrayList<>();
//        Scanner scanner = new Scanner(System.in);
//        while (scanner.hasNextLine()) {
//            int n = scanner.nextInt();
//            if (n == 0) break;
//            if (n < 1 || n > 100) {
//                System.out.println("请重新输入！");
//                continue;
//            }
//            list.add(canDrink(n));
//        }
//        list.forEach(System.out::println);
//    }
//
//    // 输入瓶子数量，返回能兑换的数量
//    public static int canDrink(int bottleCount) {
//        if (bottleCount == 2) return 1;
//        int count = bottleCount / 3;
//        int left = bottleCount % 3;
//        if (count == 1 && left == 0) return count;
//        return count + canDrink(count + left);
//    }

    /**
     * 2. 明明的随机数
     * 明明生成了N个1到500之间的随机整数。请你删去其中重复的数字，即相同的数字只保留一个，把其余相同的数去掉，然后再把这些数从小到大排序，按照排好的顺序输出。
     * 数据范围：  1≤n≤1000  ，输入的数字大小满足  1≤val≤500
     */
//    public static void main(String[] args) {
//        int n = 0;
//        Set<Integer> set = new TreeSet<>();
//        Scanner in = new Scanner(System.in);
//        while (true) {
//            String nStr = in.nextLine();
//            n = Integer.parseInt(nStr);
//            if (n < 1 || n > 1000) {
//                System.out.println("请重新输入!");
//                continue;
//            }
//            break;
//        }
//
//        for (int i = 0; i < n; i++) {
//            int val = in.nextInt();
//            if (val < 1 || val > 500) {
//                System.out.println("请重新输入!");
//                continue;
//            }
//            set.add(val);
//        }
//
//        set.forEach(System.out::println);
//    }


//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        while (in.hasNextLine()) {
//            String s = in.nextLine();
//            if (s != null && ( s.startsWith("0x") || s.startsWith("0X") )) {
//                s = s.substring(2);
//            }
//            try {
//                System.out.println(Integer.parseInt(s, 16));
//            } catch (Exception e) {
//                System.out.println("请重新输入");
//                continue;
//            }
//        }
//    }

    /**
     * 约瑟夫环问题
     * 出圈人满足式子 f(n)=(f(n-1)+m)%n
     */
//    public static void main(String[] args) {
//        Scanner in=new Scanner(System.in);
//        while (in.hasNextLine()) {
//            int n = in.nextInt();
//            if (n == 0) break;
//            if (n == 1) {
//                System.out.println(0);
//                continue;
//            }
//            if (n == 2) {
//                System.out.println(1);
//                continue;
//            }
//            int index = 0;
//            for (int i = 2; i <= n; i++) {
//                index = (index + 3) % i;
//            }
//            System.out.println(index);
//        }
//        in.close();
//    }

//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        while (in.hasNextLine()) {
//            String s = in.nextLine();
//            if (s == null || s.length() > 100) {
//                continue;
//            }
//            Set<Character> set = new LinkedHashSet<>();
//            for(int i = 0; i < s.length(); i++) {
//                char c = s.charAt(i);
//                set.add(c);
//            }
//            set.forEach(System.out::print);
//            System.out.println();
//        }
//    }

    /**
     * 数独求解
     */
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        while (sc.hasNext()) {
//            int[][] a = new int[9][9];
//            for (int i = 0; i < 9; i++) {
//                for (int j = 0; j < 9; j++){
//                    a[i][j] = sc.nextInt();
//                }
//            }
//
//            dfs(a, 0);
//
//            for (int[] b : a) {
//                for (int c : b) {
//                    System.out.print(c + " ");
//                }
//                System.out.println();
//            }
//        }
//    }
//
//    public static boolean dfs(int[][] a, int id) {
//        if (id == 81) return true;
//        int m = id / 9;
//        int n = id % 9;
//        if(a[m][n]==0){
//            for (int i = 1; i < 10; i++) {
//                if (!numberIsOK(a, m, n, i)) continue;
//                a[m][n] = i;
//                if (dfs(a, id + 1)) return true;
//                a[m][n] = 0;
//            }
//        }
//        else return dfs(a, id+1);
//        return false;
//    }
//
//    public static boolean numberIsOK(int[][] a, int m, int n, int t) {
//        //横竖都不存在重复
//        for (int i = 0; i < 9; i++) {
//            if (a[m][i] == t || a[i][n] == t)
//                return false;
//        }
//        //小九宫格也不存在重复
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                if (a[m / 3 * 3 + i][n / 3 * 3 + j] == t){
//                    return false;
//                }
//            }
//        }
//        return true;
//    }

//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        while (sc.hasNextLine()) {
//            String s = sc.nextLine();
//            if (s == null || s.length() < 1 || s.length() > 20) continue;
//            char[] chars = s.toCharArray();
//            HashMap<Character, Integer> map = new HashMap<>();
//            for (char c : chars) {
//                int counter = 0;
//                for (char aChar : chars) {
//                    if (c == aChar) {
//                        counter++;
//                    }
//                }
//                map.put(c, counter);
//            }
//
//            int smallest = Integer.MAX_VALUE;
//            ArrayList<Character> list = new ArrayList<>();
//            for (Map.Entry<Character, Integer> entry : map.entrySet()) {
//                if (entry.getValue() < smallest) {
//                    smallest = entry.getValue();
//                    list.clear();
//                    list.add(entry.getKey());
//                }
//                if (entry.getValue() == smallest) {
//                    list.add(entry.getKey());
//                }
//            }
//
//            for (Character c : list) {
//                s = s.replaceAll(c.toString(), "");
//            }
//            System.out.println(s);
//        }
//    }

//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        int n = 0;
//        while (in.hasNextLine()) {
//            n = in.nextInt();
//            if (n >= 1 && n <= 1000) break;
//        }
//        ArrayList<Integer> list = new ArrayList<>();
//        while (in.hasNextLine()) {
//            for (int i=0;i<n;i++) {
//                int num = in.nextInt();
//                if(num >= 0 && num <= 100000) {
//                    list.add(num);
//                } else {
//                    list.clear();
//                    break;
//                }
//            }
//            if (list.size() == n) break;
//        }
//        int orderBy = 0;
//        while (in.hasNextLine()) {
//            orderBy = in.nextInt();
//            if (orderBy == 0 || orderBy == 1) break;
//        }
//        if (orderBy == 0) {
//            list.stream().sorted(Comparator.comparingInt(c -> c)).forEach(item -> {
//                System.out.print(item + " ");
//            });
//        } else {
//            list.stream().sorted(Comparator.comparingInt(c -> (int) c).reversed()).forEach(item -> {
//                System.out.print(item + " ");
//            });
//        }
//
//    }


//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        int n = 0;
//        while (in.hasNextLine()) {
//            n = Integer.parseInt(in.nextLine());
//            if (n < 1 || n > 500) continue;
//            break;
//        }
//        int i = 0;
//        TreeMap<Integer, Integer> map = new TreeMap<>();
//        while (i<n && in.hasNextLine()){
//            String s = in.nextLine();
//            String[] strings = s.split(" ");
//            int[] ints = new int[2];
//            ints[0] = Integer.parseInt(strings[0]);
//            ints[1] = Integer.parseInt(strings[1]);
//            if (ints[0] < 0 || ints[0] > 11111111 || ints[1] < 1 || ints[1] > 100000) continue;
//            map.compute(ints[0], (key, value) -> {
//                if (value == null) {
//                    return ints[1];
//                }
//                return value + ints[1];
//            });
//            i++;
//        }
//        map.forEach((k, v) -> {
//            System.out.println(k + " " + v);
//        });
//    }

//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        while (in.hasNextLine()) {
//            String list = in.nextLine();
//            if (list == null || list.length() == 0) continue;
//            String[] strings = list.split(" ");
//
//            // 单词个数
//            int n = Integer.parseInt(strings[0]);
//            if (n < 1 || n > 1000) continue;
//
//            int i = 0;
//            // 单词列表
//            String[] words = new String[n];
//            boolean notMatch = false;
//            while (i < n) {
//                words[i] = strings[i + 1];
//                if (words[i].length() < 1 || words[i].length() > 10) {
//                    notMatch = true;
//                    break;
//                }
//                i++;
//            }
//            if (notMatch) continue;
//
//            // 提供单词x
//            String findWord = strings[n + 1];
//            if (findWord.length() < 1 || findWord.length() > 10) continue;
//
//            // 按字典序排列后的第 k 个的 k
//            int k = Integer.parseInt(strings[n + 2]);
//            if (k < 1 || k > n) continue;
//
//            // 存结果的Set
//            TreeSet<String> set = new TreeSet<>();
//            // 存findword每个字符的数量的Map
//            TreeMap<Character, Integer> map = new TreeMap<>();
//            char[] chars = findWord.toCharArray();
//            for (char c : chars) {
//                map.compute(c, (key, value) -> value==null ? 1 : ++value);
//            }
//
//            for (String word : words) {
//                // 字数要相等
//                if (word.length() != findWord.length()) continue;
//
//                // 不能等同
//                if (word.equals(findWord)) continue;
//
//                // 每个字符出现的数量跟findword的一样，两个Map的key和value应该是全等的
//                TreeMap<Character, Integer> wordMap = new TreeMap<>();
//                char[] wordChars = word.toCharArray();
//                for (char c : wordChars) {
//                    wordMap.compute(c, (key, value) -> value==null ? 1 : ++value);
//                }
//                boolean isMatch = true;
//                if (wordMap.size() != map.size()) continue;
//                for (Map.Entry<Character, Integer> entry : map.entrySet()) {
//                    char key = entry.getKey();
//                    int value = entry.getValue();
//
//                    int wordValue = wordMap.get(key);
//                    if (value != wordValue) {
//                        isMatch = false;
//                        break;
//                    }
//                }
//
//                if (isMatch) {
//                    set.add(word);
//                }
//            }
//
//            System.out.println(set.size());
//            if (k < set.size()) {
//                for(i=0;i< set.size();i++){
//                    if (i == k-1) {
//                        System.out.println(set.pollFirst());
//                    } else {
//                        set.pollFirst();
//                    }
//                }
//            }
//        }
//    }

//    public static class Person {
//        private String name;
//        private int grade;
//
//        public Person(String name, int grade) {
//            this.name = name;
//            this.grade = grade;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public int getGrade() {
//            return grade;
//        }
//
//        public void setGrade(int grade) {
//            this.grade = grade;
//        }
//    }
//
//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        int n = 0;
//        while (in.hasNextLine()){
//            n = Integer.parseInt(in.nextLine());
//            if (n >= 1 && n <= 500) {
//                break;
//            }
//        }
//
//        int orderBy = 0;
//        while (in.hasNextLine()) {
//            orderBy = Integer.parseInt(in.nextLine());
//            if (orderBy == 0 || orderBy == 1) {
//                break;
//            }
//        }
//
//        int i = 0;
//        List<Person> personList = new ArrayList<>(n);
//        while (i < n && in.hasNextLine()) {
//            String p = in.nextLine();
//            String[] ps = p.split(" ");
//            Person person = new Person(ps[0], Integer.parseInt(ps[1]));
//            personList.add(person);
//            i++;
//        }
//
//        if (orderBy == 0) {
//            personList.stream().sorted(Comparator.comparing(Person::getGrade)).forEach(item -> {
//                System.out.println(item.getName() + " " + item.getGrade());
//            });
//        } else {
//            personList.stream().sorted(Comparator.comparing(Person::getGrade).reversed()).forEach(item -> {
//                System.out.println(item.getName() + " " + item.getGrade());
//            });
//        }
//    }

//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        String s = in.nextLine();
//        System.out.println(maxDepth(s));
//    }
//
//    public static int maxDepth(String s) {
//        if (s == null) {
//            return 0;
//        }
//        int result = 0, size = 0;
//        char[] chars = s.toCharArray();
//        for (char c : chars) {
//            if (c == '(') {
//                size++;
//                result = Math.max(size, result);
//            }
//            if (c == ')') {
//                size--;
//            }
//        }
//        return result;
//    }

//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        String s = in.nextLine();
//        char[] chars = s.toCharArray();
//        dfs(chars, 0);
//    }
//
//    public static void dfs(char[] c, int k){
//        if(k == c.length){
//            list.add(new String(c));
//            return;
//        }
//        HashSet<Character> set = new HashSet<>();
//        for(int i = k; i < c.length; i++){
//            if(!set.contains(c[i])){
//                set.add(c[i]);
//                swap(c, i, k);
//                dfs(c, k+1);
//                swap(c, i, k);
//            }
//        }
//    }
//
//    public static void swap(char[] c, int x, int y){
//        char temp = c[x];
//        c[x] = c[y];
//        c[y] = temp;
//    }

//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        int n = 0;
//        while (in.hasNextLine()) {
//            n = Integer.parseInt(in.nextLine());
//            if (n >= 1 && n <= 10) {
//                break;
//            }
//        }
//
//        String[] famas = new String[1];
//        while (in.hasNextLine()) {
//            String s = in.nextLine();
//            if (s != null) {
//                famas = s.split(" ");
//                break;
//            }
//        }
//
//        String[] famaNums = new String[1];
//        while (in.hasNextLine()) {
//            String s = in.nextLine();
//            if (s != null) {
//                famaNums = s.split(" ");
//                break;
//            }
//        }
//
//        HashSet<Integer> set = new HashSet<Integer>();
//        set.add(0);
//
//        List<Integer> list = new ArrayList<>();
//        for(int j = 0; j < famaNums.length; j++) {
//            int num = Integer.parseInt(famaNums[j]);
//            for(int i = 0; i < num; i++) {
//                list.add(Integer.parseInt(famas[j]));
//            }
//        }
//
//        for (Integer item : list) {
//            Set<Integer> newSet = set.stream().map(i -> i+item).collect(Collectors.toSet());
//            set.addAll(newSet);
//        }
//
//        System.out.println(set.size());
//    }

    /**
     * 华为真题 查找重复代码
     * @param args
     */
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        String text1 = sc.nextLine();
//        String text2 = sc.nextLine();
//
//        if (text1.length() > text2.length()) {
//            String temp = text1;
//            text1 = text2;
//            text2 = temp;
//        }
//
//        String max = "";
//        for (int i = 0;i<text1.length();i++){
//            for (int j = i + 1;j<text1.length()+1;j++){
//                String sub = text1.substring(i, j);
//                if (text2.contains(sub) && max.length() < sub.length()) {
//                    max = sub;
//                }
//            }
//        }
//
//        System.out.println(max);
//    }

//    static int[] dr = new int[]{-1, 0, 1, 0};
//    static int[] dc = new int[]{0, -1, 0, 1};
//    static char[][] chars;
//
//    /**
//     * 华为真题 查找单入口空闲区域
//     * @param args
//     */
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        int m = sc.nextInt();
//        int n = sc.nextInt();
//        sc.nextLine();
//        chars = new char[m][n];
//        for (int i=0; i<m; i++){
//            String[] strings = sc.nextLine().split(" ");
//            for (int j=0; j<n; j++) {
//                chars[i][j] = strings[j].charAt(0);
//            }
//        }
//
//        // 最大区域大小
//        int max = -1;
//        // 最大区域入口坐标
//        int x = -1, y = -1;
//
//        // 判断边界是否有‘O’
//        for(int i=0; i<m; i++) {
//            if (i==0||i==m-1) {
//                // 有效入口个数，有且仅有一个有效入口
//                int counter = 0;
//                for (int j=0; j<n; j++) {
//                    // 发现入口 进行bfs搜索
//                    if (chars[i][j] == 'O') {
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
//
//        for (int j=0; j<n; j++) {
//            if (j==0||j==n-1) {
//                // 有效入口个数，有且仅有一个有效入口
//                int counter = 0;
//                for(int i=0; i<m; i++) {
//                    // 发现入口 进行bfs搜索
//                    if (chars[i][j] == 'O') {
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
//        // 无效区域
//        boolean invalid = false;
//        Queue<int[]> queue = new LinkedList<>();
//        queue.add(new int[]{x,y});
//        while (!queue.isEmpty()) {
//            int[] ints = queue.poll();
//            // 没有搜索过的
//            if (chars[ints[0]][ints[1]] == 'O') {
//                // 遍历过的坐标点标记为‘X’
//                chars[ints[0]][ints[1]] = 'X';
//                // 面积+1
//                counter++;
//                // 查询相邻的坐标点是否为‘O’
//                for(int i=0; i<4; i++) {
//                    int nx = ints[0] + dr[i];
//                    int ny = ints[1] + dc[i];
//                    // 横纵坐标没有越界且坐标点为‘O’
//                    if (nx >= 0 && nx<m && ny>=0 && ny <n && chars[nx][ny] == 'O') {
//                        // 发现第二入口 标记无效区域 并继续将剩余坐标点标记为‘X’
//                        if (nx == 0 || nx == m-1 || ny == 0 || ny == n-1) {
//                            invalid = true;
//                        }
//                        queue.add(new int[]{nx,ny});
//                    }
//                }
//            }
//        }
//        return invalid ? -1 : counter;
//    }

//    public static void main(String[] args) {
//        int target = 64;
//        // 算出最接近k的层级
//        int n = 0;
//        long l = 73709551616L + 1;
//        BigDecimal bk = new BigDecimal(l);
//        BigDecimal bigDecimal;
//        for (int i=0;;i++){
//            bigDecimal = BigDecimal.valueOf(Math.pow(2, i));
//            if (bigDecimal.compareTo(bk) > -1) {
//                // 大于等于
//                System.out.println(i);
//                n = i;
//                break;
//            }
//        }
//
//        // 因为第n层的后半段跟第n-1层一样，所以递归回溯
//        double k = l;
//        boolean res;
//        while (true) {
//            if (n <= 2) {
//                if (k == 0L) {
//                    res = false;
//                    break;
//                }
//                if (k == 1L) {
//                    res = true;
//                    break;
//                }
//            }
//            double len = Math.pow(2, n - 1);
//            // 后半截的偏移量对应的字符 = i-1的偏移量对应的字符
//            k = k - len;
//            n = n - 1;
//        }
//
//        for (int i=n+1;i<=target;i++) {
//            res = !res;
//        }
//        System.out.println(res ? "R" : "B");
//    }

//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        String s = sc.nextLine();
//        String[] ss = s.split(" ");
//        TreeMap<Integer, Integer> map = new TreeMap<>((o1, o2) -> o2 - o1);
//        for (String string : ss) {
//            Integer i = Integer.parseInt(string);
//            map.put(i, map.getOrDefault(i, 0) + 1);
//        }
//
//        List<Integer> zhadan = new LinkedList<>();
//        List<Integer> hulu = new LinkedList<>();
//        List<Integer> sanzhang = new LinkedList<>();
//        List<Integer> duizi = new LinkedList<>();
//        List<Integer> danzhang = new LinkedList<>();
//
//        List<Integer> list = new ArrayList<>(map.keySet());
//        for (int i=0;i<list.size();i++) {
//            Integer card = list.get(i);
//            Integer num = map.get(card);
//
//            if (num == 4) {// 炸弹
//                for (int j=0;j<4;j++) {
//                    zhadan.add(card);
//                }
//            } else if (num == 3) {// 可以组成葫芦或者3张
//                // 向后查找是否有两张
//                for (int j=i+1;j<list.size();j++) {
//                    Integer card1 = list.get(j);
//                    Integer num2 = map.get(card1);
//                    if (num2 >= 2) {
//                        // 有两张以上
//                        map.put(card1, map.get(card1) - 2);
//                        for (int k=0;k<3;k++) {
//                            hulu.add(card);
//                        }
//                        for (int k=0;k<2;k++) {
//                            hulu.add(card1);
//                        }
//                        break;
//                    }
//                    if (j == list.size()-1) {
//                        // 后面都是单张 则只能组成3张
//                        for (int k=0;k<3;k++) {
//                            sanzhang.add(card);
//                        }
//                    }
//                }
//            } else if (num == 2) {
//                // 两张
//                for (int j=0;j<2;j++) {
//                    duizi.add(card);
//                }
//            } else {
//                danzhang.add(card);
//            }
//        }
//
//        zhadan.forEach(item -> {System.out.print(item + " ");});
//        hulu.forEach(item -> {System.out.print(item + " ");});
//        sanzhang.forEach(item -> {System.out.print(item + " ");});
//        duizi.forEach(item -> {System.out.print(item + " ");});
//        danzhang.forEach(item -> {System.out.print(item + " ");});
//    }

    /**
     * 华为真题 查找充电设备组合 背包算法
     */
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        int n = sc.nextInt();
//        int[] p = new int[n];
//        for (int i=0;i<n;i++) {
//            p[i] = sc.nextInt();
//        }
//        int max = sc.nextInt();
//        System.out.println(handleDp(n, p, max));
//    }
//    public static int handleDp(int n, int[] p, int max) {
//        // 用二维数组dp[i][j]表示拿完第i件物品时j背包容量的最大值或方案数
//        int[][] dp = new int[n+1][max+1];
//        for (int i=0;i<=n;i++) {
//            for (int j=0;j<=max;j++) {
//                if (i==0||j==0) {
//                    continue;
//                }
//                if (p[i-1]>j) {
//                    dp[i][j] = dp[i-1][j];
//                } else {
//                    // dp[i-1][j]不选择i物品，dp[i-1][j-p[i-1]]+p[i-1]选择
//                    dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-p[i-1]] + p[i-1]);
//                }
//            }
//        }
//        return dp[n][max];
//    }

    /**
     * 华为真题 查找充电设备组合 背包算法改进
     */
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        int n = sc.nextInt();
//        int[] p = new int[n];
//        for (int i=0;i<n;i++){
//            p[i] = sc.nextInt();
//        }
//        int max = sc.nextInt();
//        System.out.println(handleDp(n, p, max));
//    }
//    public static int handleDp(int n, int[] p, int max) {
//        // 通过滚动数组的方法减少存储空间，只需使用一个一维数组即可完成背包计算
//        int[] dp = new int[max+1];
//        for (int i=0;i<n;i++){
//            for (int j=max;j>=p[i];j--) {
//                dp[j] = Math.max(dp[j], dp[j-p[i]] + p[i]);
//            }
//        }
//        return dp[max];
//    }

    /**
     * 华为真题 工作安排
     */
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        int time = sc.nextInt();
//        int n = sc.nextInt();
//        sc.nextLine();
//        int[][] p = new int[n][2];
//        for (int i=0;i<n;i++){
//            String[] s = sc.nextLine().split(" ");
//            p[i][0] = Integer.parseInt(s[0]);
//            p[i][1] = Integer.parseInt(s[1]);
//        }
//        System.out.println(handleDp(n, p, time));
//    }
//    public static int handleDp(int n, int[][] p, int time) {
//        int[] dp = new int[time+1];
//        for (int i=0;i<n;i++){
//            for (int j=time;j>=p[i][0];j--) {
//                dp[j] = Math.max(dp[j], dp[j-p[i][0]] + p[i][1]);
//            }
//        }
//        return dp[time];
//    }

    /**
     * 华为真题 工作安排 如果变为完全背包问题 （即每件任务的数量不限量）
     */
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        int time = sc.nextInt();
//        int n = sc.nextInt();
//        sc.nextLine();
//        int[][] p = new int[n+1][2];
//        for (int i=1;i<=n;i++){
//            String[] s = sc.nextLine().split(" ");
//            p[i][0] = Integer.parseInt(s[0]);
//            p[i][1] = Integer.parseInt(s[1]);
//        }
//        System.out.println(handleDp(n, p, time));
//    }
//    public static int handleDp(int n, int[][] p, int time) {
//        int[][] dp = new int[n+1][time+1];
//        for (int i=1;i<=n;i++){
//            // 采用二维数组来推导，但是在推导第i件物品的价值时，
//            // 我们需要用0件、1件、2件……[V/c]件依次去更新dp[i][0]......dp[i][V]，
//            // 这样显然效率比较差，采用滚动计算的方式来解决这一问题
//            for (int j=0;j<p[i][0];j++) {
//                // 先获取上一层数据，作为不选择i物品的基础数据
//                dp[i][j] = dp[i-1][j];
//                System.out.println("------" + "取上一层" + "------dp(" + i + "," + j + ")-----------");
//                for (int[] l : dp) {
//                    for (int ii : l){
//                        System.out.print(ii + " ");
//                    }
//                    System.out.println();
//                }
//                System.out.println("------" + "取上一层" + "------dp(" + i + "," + j + ")-----------");
//            }
//            for (int j=p[i][0];j<=time;j++) {
//                // 滚动方式试探拿去i物品是否能获取更大价值
//                dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-p[i][0]] + p[i][1]);
//                System.out.println("------" + "获取更大价值" + "------dp(" + i + "," + j + ")-----------");
//                for (int[] l : dp) {
//                    for (int ii : l){
//                        System.out.print(ii + " ");
//                    }
//                    System.out.println();
//                }
//                System.out.println("------" + "获取更大价值" + "------dp(" + i + "," + j + ")-----------");
//            }
//        }
//        return dp[n][time];
//    }

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
//        int l=sc.nextInt();
//        int r=sc.nextInt();
//        int count=0;
//        for (int i = l; i <=r ; i++) {
//            String s=Integer.toString(i,2);
//            if(!s.matches(".*(101)+.*")) count++;
//        }
//        List<Integer> list = new ArrayList<>();
//        Integer[] ii = list.toArray(new Integer[1]);
//        System.out.println(count);

        System.out.println(Arrays.toString(searchRange(new int[]{2,2}, 3)));
    }
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] ans = new int[n - k + 1];
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < n; ++i) {
            // 将出界的下标删除
            if (i >= k && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }
            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.offerLast(i);

            // 第 k 个元素开始需要计算最大值
            if(i >= k){
                ans[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        return ans;
    }
    public static int[] searchRange(int[] nums, int target) {
        if(nums.length == 0) {
            return new int[]{-1,-1};
        }

        int l = 0, r = nums.length-1;
        while(l<=r){
            int mid = (r-l)/2 + l;
            if(nums[mid] < target){
                l = mid + 1;
            }else{
                r = mid - 1;
            }
        }

        if (l > nums.length-1 || nums[l] != target){
            return new int[]{-1,-1};
        }

        int end = l;
        while(end < nums.length && nums[end] <= target){
            end++;
        }

        return new int[]{l,end-1};
    }
}
