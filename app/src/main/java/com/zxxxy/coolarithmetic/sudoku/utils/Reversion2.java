package com.zxxxy.coolarithmetic.sudoku.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Reversion2 {
    /**
     * 打印二维数组，数独矩阵;
     */
    public static void printArray(int a[][]) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (0 == ((j + 1) % 3)) {
                }
            }
            if (0 == ((i + 1) % 3)) {
                System.out.println();
            }
        }
    }

    /**
     * 产生一个1-9的不重复长度为9的一维数组
     */
    public static ArrayList<Integer> creatNineRondomArray() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        Random random = new Random();
        for (int i = 0; i < 9;
             i++) {
            int randomNum = random.nextInt(9) + 1;
            while (true) {
                if (!list.contains(randomNum)) {
                    list.add(randomNum);
                    break;
                }
                randomNum = random.nextInt(9) + 1;
            }
        }
        return list;
    }

    /**
     * 通过一维数组和原数组生成随机的数独矩阵,遍历二维数组里的数据，
     * 在一维数组找到当前值的位置，并把一维数组
     * 当前位置加一处位置的值赋到当前二维数组中。目的就是将一维数组为依据，
     * 按照随机产生的顺序，将这个9个数据进行循环交换，生成一个随机的数独矩阵。
     */
    public static void creatReversion2Array(int[][] seedArray, ArrayList<Integer> randomList) {
        int flag = 1;
        for (int i = 0; i < 9;
             i++) {
            for (int j = 0; j < 9;
                 j++) {
                for (int k = 0; k < 9;
                     k++) {
                    if (seedArray[i][j] == randomList.get(k)) {
                        seedArray[i][j] = randomList.get((k + 1) % 9);
                        break;
                    }
                }
            }
        }
    }

    public int[] getOneW(int[][] start) {
        int size = start.length * start[2].length;
        int[] newSu = new int[size];
        int temp = 0;
        for (int i = 0; i < start[2].length; i++) {
            for (int j = 0; j < start.length; j++) {
                newSu[temp] = start[i][j];
                temp++;
            }
        }
        return newSu;
    }

    public String oneW2String(int[] start) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < start.length; i++) {
            stringBuilder.append(start[i]);
        }
        return stringBuilder.toString();
    }

    public String stringExcept(int[] start, int count) {
        List<Integer> arr = new ArrayList<Integer>();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            while (true) {
                int temp = random.nextInt(81);
                if (!arr.contains(temp)) {
                    arr.add(temp);
                    break;
                }
            }
        }

        for (int j = 0; j < count; j++) {
            start[arr.get(j)] = 0;
        }

        return oneW2String(start);
    }
}