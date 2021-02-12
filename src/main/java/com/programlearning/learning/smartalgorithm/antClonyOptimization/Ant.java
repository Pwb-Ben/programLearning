package com.programlearning.learning.smartalgorithm.antClonyOptimization;

import java.util.Random;
import java.util.Vector;

public class Ant {

    //禁忌表
    private Vector<Integer> tabu;
    //允许搜索的城市
    private Vector<Integer> allowedCities;
    //信息数变化矩阵
    private float[][] delta;
    //距离矩阵
    private int[][] distance;

    private float alpha;
    private float beta;

    //路径长度
    private int tourLength;
    //城市数量
    private int cityNum;
    //起始城市
    private int firstCity;
    //当前城市
    private int currentCity;

    public Ant(){
        cityNum = 30;
        tourLength = 0;
    }
    /**
     * Constructor of Ant
     * @param num 蚂蚁数量
     */
    public Ant(int num){
        cityNum = num;
        tourLength = 0;
    }

    /**
     * 初始化蚂蚁，随机选择起始位置
     * @param distance 距离矩阵
     * @param a alpha
     * @param b beta
     */
    public void init(int[][] distance, float a, float b){
        alpha = a;
        beta = b;
        allowedCities = new Vector<>();
        tabu = new Vector<>();
        this.distance = distance;
        delta = new float[cityNum][cityNum];
        for (int i = 0; i < cityNum; i++) {
            allowedCities.add(i);
            for (int j = 0; j < cityNum; j++) {
                delta[i][j] = 0.f;
            }
        }

        Random random = new Random(System.currentTimeMillis());
            firstCity = random.nextInt(cityNum);
             for (Integer i:allowedCities) {
                 if (i == firstCity) {
                     allowedCities.remove(i);
                     break;
                 }
             }
             tabu.add(firstCity);
             currentCity = firstCity;
        }
        /**
         * 选择下一个城市
         * @param pheromone 信息素矩阵
        */
        public void selectNextCity(float[][] pheromone){
            float[] p = new float[cityNum];
            float sum = 0.0f;
            //计算分母部分
            for (Integer i:allowedCities) {
                sum += Math.pow(pheromone[currentCity][i], alpha)*Math.pow(1.0/distance[currentCity][i.intValue()], beta);
            }
            //计算概率矩阵
            for (int i = 0; i < cityNum; i++) {
                boolean flag = false;
                for (Integer j:allowedCities) {
                    if (i == j) {
                        p[i] = (float) (Math.pow(pheromone[currentCity][i], alpha)*Math.pow(1.0/distance[currentCity][i], beta))/sum;
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    p[i] = 0.f;
                }
            }

            //轮盘赌选择下一个城市
            Random random = new Random(System.currentTimeMillis());
            float sleectP = random.nextFloat();
            int selectCity = 0;
            float sum1 = 0.f;
            for (int i = 0; i < cityNum; i++) {
                sum1 += p[i];
                if (sum1 >= sleectP) {
                    selectCity = i;
                    break;
                }
            }

            //从允许选择的城市中去除select city
            for (Integer i:allowedCities) {
                if (i == selectCity) {
                    allowedCities.remove(i);
                    break;
                }
            }
            //在禁忌表中添加select city
            tabu.add(selectCity);
            //将当前城市改为选择的城市
            currentCity = selectCity;
        }

        /**
         * 计算路径长度
         * @return 路径长度
         */
        private int calculateTourLength(){
            int len = 0;
            for (int i = 0; i < cityNum; i++) {
                len += distance[this.tabu.get(i)][this.tabu.get(i + 1)];
            }
            return len;
        }

        public Vector<Integer> getAllowedCities() {
            return allowedCities;
        }

        public void setAllowedCities(Vector<Integer> allowedCities) {
            this.allowedCities = allowedCities;
        }

        public int getTourLength() {
            tourLength = calculateTourLength();
            return tourLength;
        }

        public void setTourLength(int tourLength) {
            this.tourLength = tourLength;
        }

        public int getCityNum() {
            return cityNum;
        }
        public void setCityNum(int cityNum) {
            this.cityNum = cityNum;
        }

        public Vector<Integer> getTabu() {
            return tabu;
        }

        public void setTabu(Vector<Integer> tabu) {
            this.tabu = tabu;
        }

        public float[][] getDelta() {
            return delta;
        }

        public void setDelta(float[][] delta) {
            this.delta = delta;
        }

        public int getFirstCity() {
            return firstCity;
        }

        public void setFirstCity(int firstCity) {
            this.firstCity = firstCity;
        }
}
