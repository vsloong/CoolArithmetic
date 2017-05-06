package com.zxxxy.coolarithmetic.sudoku.logics;


import com.zxxxy.coolarithmetic.sudoku.utils.MyContant;
import com.zxxxy.coolarithmetic.sudoku.utils.Reversion2;
import com.zxxxy.coolarithmetic.sudoku.utils.SharedPreferencesUtils;
import com.zxxxy.coolarithmetic.sudoku.utils.UIUtils;

import java.util.ArrayList;

/**
 * Author:ZengYinan zengyinanos@qq.com
 * Date:2016/6/29
 * Time:21:15
 * Desc:
 */
public class Game {
    //数独初始化数据的基础
    private String str = "";

    private int sudoku[] = new int[9 * 9];
    private int use[][][] = new int[9][9][];//用于存储每个单元格已经不可用你的数据

    //难度设定 简单 30 中等 50 困难70
    public static int count = 0;

    public Game(String continueGame) {
        /*str += "650000070";
        str += "000506000";
        str += "014000005";
        str += "007009000";
        str += "002314700";
        str += "000700800";
        str += "500000630";
        str += "000201000";
        str += "030000097";*/

        if (continueGame == null || continueGame.isEmpty()) {
            reStart();
        } else {
            sudoku = fromPuzzleString(continueGame);
        }

    }

    public void reStart() {

        int getCount = SharedPreferencesUtils.getInt(UIUtils.getContext(), MyContant.COUNT, 0);
        if (getCount == 0) {
            //表示没有设置 默认为简单
            SharedPreferencesUtils.saveInt(UIUtils.getContext(), MyContant.COUNT, 30);
            count = 30;
        } else {
            count = getCount;
        }

        /*Reversion re = new Reversion();
        int num[][] = re.reversionInit();
        //初始化第一行
        re.arrayRowOne(num);
        //得到一个正确的数独数据
        re.reversion(num, 1, 0);
        int[] oneW = re.getOneW(num);
        str = re.stringExcept(oneW, count);*/

        Reversion2 re = new Reversion2();
        //初始数据
        int seedArray[][] = {{9, 7, 8, 3, 1, 2, 6, 4, 5}, {3, 1, 2, 6, 4, 5, 9, 7, 8}, {6, 4, 5, 9, 7, 8, 3, 1, 2}, {7, 8, 9, 1, 2, 3, 4, 5, 6}, {1, 2, 3, 4, 5, 6, 7, 8, 9}, {4, 5, 6, 7, 8, 9, 1, 2, 3}, {8, 9, 7, 2, 3, 1, 5, 6, 4}, {2, 3, 1, 5, 6, 4, 8, 9, 7}, {5, 6, 4, 8, 9, 7, 2, 3, 1}};
        ArrayList<Integer> randomList = Reversion2.creatNineRondomArray();
        Reversion2.creatReversion2Array(seedArray, randomList);
        int[] oneW = re.getOneW(seedArray);
        str = re.stringExcept(oneW, count);

        sudoku = fromPuzzleString(str);
    }

    public int[] getSudoku() {
        return sudoku;
    }

    private int getTile(int x, int y) {
        return sudoku[y * 9 + x];
    }

    public void setTile(int x, int y, int value) {
        sudoku[y * 9 + x] = value;
    }

    public String getTilesString(int x, int y) {
        int v = getTile(x, y);
        if (v == 0) {
            return "";
        } else {
            return String.valueOf(v);
        }
    }

    /**
     * 根据一个字符串数据，生成一个整型数组，所谓数独游戏的初始化数据
     *
     * @param src
     * @return
     */
    public int[] fromPuzzleString(String src) {
        int[] sudo = new int[src.length()];
        for (int i = 0; i < src.length(); i++) {
            sudo[i] = Character.getNumericValue(src.charAt(i));//注意此处要把char型转成int型，否则存储的是ASCII码
        }
        return sudo;
    }

    /**
     * 计算某一单元格当中已经不可用的数据
     *
     * @param x
     * @param y
     * @return
     */
    public int[] calculateUsedTiles(int x, int y) {
        int c[] = new int[9];
        //一竖排中不可用的数据
        for (int i = 0; i < 9; i++) {
            if (i == y) {
                continue;
            }
            int t = getTile(x, i);
            if (t != 0) {
                c[t - 1] = t;
            }
        }
        //一横排中不可用的数据
        for (int i = 0; i < 9; i++) {
            if (i == x) {
                continue;
            }
            int t = getTile(i, y);
            if (t != 0) {
                c[t - 1] = t;
            }
        }
        //周围不可用的数据
        int startX = (x / 3) * 3;
        int startY = (y / 3) * 3;
        for (int i = startX; i < startX + 3; i++) {
            for (int j = startY; j < startY + 3; j++) {
                if (i == x && j == y) {
                    continue;
                }
                int t = getTile(i, j);
                if (t != 0) {
                    c[t - 1] = t;
                }
            }
        }

        //去掉其中为0的数据
        int nused = 0;
        for (int t : c) {
            if (t != 0) {
                nused++;
            }
        }
        int c1[] = new int[nused];
        nused = 0;
        for (int t : c) {
            if (t != 0) {
                c1[nused++] = t;
            }
        }
        return c1;
    }

    /**
     * 用于计算所有单元格对应的不可用的数据
     */
    public void calculateAllUsedTiles() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                use[i][j] = calculateUsedTiles(i, j);
            }
        }
    }

    /**
     * 取出某一个单元格当中已经不可用的数据
     *
     * @param x
     * @param y
     * @return
     */
    public int[] getUsedTilesByCoor(int x, int y) {
        return use[x][y];
    }

    /**
     * 指定位置上是否已经填了有数字
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isSet(int x, int y) {
        if (getTile(x, y) != 0) {
            return true;
        } else {
            return false;
        }
    }
}
