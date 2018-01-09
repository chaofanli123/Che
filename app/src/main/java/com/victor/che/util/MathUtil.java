/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.victor.che.util;

import java.math.BigDecimal;

/**
 * 数学处理类
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/10 0010 11:13
 */
public class MathUtil {

    // 默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;

    /**
     * 财务数据的格式（保留小数点后两位）
     */
    public static final String FORMAT_FINANCE = "%.2f";

    /**
     * 转成财务数据（1.00元）
     *
     * @param value
     * @return
     */
    public static String getMoneyText(double value) {
        return String.format(FORMAT_FINANCE + "元", value);
    }

    /**
     * 转成财务数据（￥1.00）
     *
     * @param value
     * @return
     */
    public static String getMoneyFormat(double value) {
        return String.format("￥" + FORMAT_FINANCE, value);
    }

    /**
     * 转成财务数据格式（保留小数点后两位）
     *
     * @param value
     * @return
     */
    public static String getFinanceValue(double value) {
        return String.format(FORMAT_FINANCE, value);
    }

    /**
     * 计算增长率
     *
     * @param current
     * @param last
     * @return
     */
    public static String calcIncreaseRate(double current, double last) {
        // last = 0, curent = 0;
        // last = 0, current > 0;
        double offset = sub(current, last);
        double result = 0;
        if (last == 0) {
            return current > 0 ? "100.00%" : "0.00%";
            //            result = mul(offset, 100);
        } else {
            result = div(mul(offset, 100), last, 2);
        }
        return MathUtil.getFinanceValue(result) + "%";
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 描述：字节数组转换成16进制串.
     *
     * @param b      the b
     * @param length the length
     * @return the string
     */
    public static String byte2HexStr(byte[] b, int length) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < length; ++n) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else {
                hs = hs + stmp;
            }
            hs = hs + ",";
        }
        return hs.toUpperCase();
    }

    /**
     * 二进制转为十六进制.
     *
     * @param binary the binary
     * @return char hex
     */
    public static char binaryToHex(int binary) {
        char ch = ' ';
        switch (binary) {
            case 0:
                ch = '0';
                break;
            case 1:
                ch = '1';
                break;
            case 2:
                ch = '2';
                break;
            case 3:
                ch = '3';
                break;
            case 4:
                ch = '4';
                break;
            case 5:
                ch = '5';
                break;
            case 6:
                ch = '6';
                break;
            case 7:
                ch = '7';
                break;
            case 8:
                ch = '8';
                break;
            case 9:
                ch = '9';
                break;
            case 10:
                ch = 'a';
                break;
            case 11:
                ch = 'b';
                break;
            case 12:
                ch = 'c';
                break;
            case 13:
                ch = 'd';
                break;
            case 14:
                ch = 'e';
                break;
            case 15:
                ch = 'f';
                break;
            default:
                ch = ' ';
        }
        return ch;
    }

    /**
     * 一维数组转为二维数组
     *
     * @param m      the m
     * @param width  the width
     * @param height the height
     * @return the int[][]
     */
    public static int[][] arrayToMatrix(int[] m, int width, int height) {
        int[][] result = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int p = j * height + i;
                result[i][j] = m[p];
            }
        }
        return result;
    }

    /**
     * 二维数组转为一维数组
     *
     * @param m the m
     * @return the double[]
     */
    public static double[] matrixToArray(double[][] m) {
        int p = m.length * m[0].length;
        double[] result = new double[p];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                int q = j * m.length + i;
                result[q] = m[i][j];
            }
        }
        return result;
    }

    /**
     * 描述：int数组转换为double数组.
     *
     * @param input the input
     * @return the double[]
     */
    public static double[] intToDoubleArray(int[] input) {
        int length = input.length;
        double[] output = new double[length];
        for (int i = 0; i < length; i++) {
            output[i] = Double.valueOf(String.valueOf(input[i]));
        }
        return output;
    }

    /**
     * 描述：int二维数组转换为double二维数组.
     *
     * @param input the input
     * @return the double[][]
     */
    public static double[][] intToDoubleMatrix(int[][] input) {
        int height = input.length;
        int width = input[0].length;
        double[][] output = new double[height][width];
        for (int i = 0; i < height; i++) {
            // 列
            for (int j = 0; j < width; j++) {
                // 行
                output[i][j] = Double.valueOf(String.valueOf(input[i][j]));
            }
        }
        return output;
    }

    /**
     * 计算数组的平均值.
     *
     * @param pixels 数组
     * @return int 平均值
     */
    public static int average(int[] pixels) {
        float m = 0;
        for (int i = 0; i < pixels.length; ++i) {
            m += pixels[i];
        }
        m = m / pixels.length;
        return (int) m;
    }

    /**
     * 计算数组的平均值.
     *
     * @param pixels 数组
     * @return int 平均值
     */
    public static int average(double[] pixels) {
        float m = 0;
        for (int i = 0; i < pixels.length; ++i) {
            m += pixels[i];
        }
        m = m / pixels.length;
        return (int) m;
    }

    /**
     * 描述：计算对数
     *
     * @param value value的对数
     * @param base  以base为底
     * @return
     */
    public static double log(double value, double base) {
        return Math.log(value) / Math.log(base);
    }

    /**
     * 描述：点在直线上. 点A（x，y）,B(x1,y1),C(x2,y2) 点A在直线BC上吗?
     *
     * @param x
     * @param y
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public boolean pointOnLine(double x, double y, double x1, double y1, double x2, double y2) {
        double result = (x - x1) * (y2 - y1) - (y - y1) * (x2 - x1);
        if (result == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 描述：点在线段上. 点A（x，y）,B(x1,y1),C(x2,y2) 点A在线段BC上吗?
     *
     * @param x
     * @param y
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static boolean pointAtELine(double x, double y, double x1, double y1, double x2, double y2) {
        double result = (x - x1) * (y2 - y1) - (y - y1) * (x2 - x1);
        if (result == 0) {
            if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 描述：两条直线相交. 点A（x1，y1）,B(x2,y2),C(x3,y3),D(x4,y4) 直线AB与直线CD相交吗?
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param x3
     * @param y3
     * @param x4
     * @param y4
     * @return
     */
    public static boolean LineOnLine(double x1, double y1, double x2, double y2, double x3, double y3, double x4,
                                     double y4) {
        double k1 = (y2 - y1) / (x2 - x1);
        double k2 = (y4 - y3) / (x4 - x3);
        if (k1 == k2) {
            // System.out.println("平行线");
            return false;
        } else {
            double x = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x3 * y4 - y3 * x4) * (x1 - x2))
                    / ((y2 - y1) * (x3 - x4) - (y4 - y3) * (x1 - x2));
            double y = (x1 * y2 - y1 * x2 - x * (y2 - y1)) / (x1 - x2);
            // System.out.println("直线的交点("+x+","+y+")");
            return true;
        }
    }

    /**
     * 描述：线段与线段相交. 点A（x1，y1）,B(x2,y2),C(x3,y3),D(x4,y4) 线段AB与线段CD相交吗?
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param x3
     * @param y3
     * @param x4
     * @param y4
     * @return
     */
    public static boolean eLineOnELine(double x1, double y1, double x2, double y2, double x3, double y3, double x4,
                                       double y4) {
        double k1 = (y2 - y1) / (x2 - x1);
        double k2 = (y4 - y3) / (x4 - x3);
        if (k1 == k2) {
            // System.out.println("平行线");
            return false;
        } else {
            double x = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x3 * y4 - y3 * x4) * (x1 - x2))
                    / ((y2 - y1) * (x3 - x4) - (y4 - y3) * (x1 - x2));
            double y = (x1 * y2 - y1 * x2 - x * (y2 - y1)) / (x1 - x2);
            // System.out.println("直线的交点("+x+","+y+")");
            if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2)
                    && x >= Math.min(x3, x4) && x <= Math.max(x3, x4) && y >= Math.min(y3, y4)
                    && y <= Math.max(y3, y4)) {
                // System.out.println("交点（"+x+","+y+"）在线段上");
                return true;
            } else {
                // System.out.println("交点（"+x+","+y+"）不在线段上");
                return false;
            }
        }
    }

    /**
     * 描述：线段直线相交. 点A（x1，y1）,B(x2,y2),C(x3,y3),D(x4,y4) 线段AB与直线CD相交吗?
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param x3
     * @param y3
     * @param x4
     * @param y4
     * @return
     */
    public static boolean eLineOnLine(double x1, double y1, double x2, double y2, double x3, double y3, double x4,
                                      double y4) {
        double k1 = (y2 - y1) / (x2 - x1);
        double k2 = (y4 - y3) / (x4 - x3);
        if (k1 == k2) {
            // System.out.println("平行线");
            return false;
        } else {
            double x = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x3 * y4 - y3 * x4) * (x1 - x2))
                    / ((y2 - y1) * (x3 - x4) - (y4 - y3) * (x1 - x2));
            double y = (x1 * y2 - y1 * x2 - x * (y2 - y1)) / (x1 - x2);
            // System.out.println("交点("+x+","+y+")");
            if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2)) {
                // System.out.println("交点（"+x+","+y+"）在线段上");
                return true;
            } else {
                // System.out.println("交点（"+x+","+y+"）不在线段上");
                return false;
            }
        }
    }

    /**
     * 描述：点在矩形内. 矩形的边都是与坐标系平行或垂直的。 只要判断该点的横坐标和纵坐标是否夹在矩形的左右边和上下边之间。
     * 点A（x，y）,B(x1,y1),C(x2,y2) 点A在以直线BC为对角线的矩形中吗?
     *
     * @param x
     * @param y
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static boolean pointOnRect(double x, double y, double x1, double y1, double x2, double y2) {
        if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2)) {
            // System.out.println("点（"+x+","+y+"）在矩形内上");
            return true;
        } else {
            // System.out.println("点（"+x+","+y+"）不在矩形内上");
            return false;
        }
    }

    /**
     * 描述：矩形在矩形内. 只要对角线的两点都在另一个矩形中就可以了. 点A(x1,y1),B(x2,y2)，C(x1,y1),D(x2,y2)
     * 以直线AB为对角线的矩形在以直线BC为对角线的矩形中吗?
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param x3
     * @param y3
     * @param x4
     * @param y4
     * @return
     */
    public static boolean rectOnRect(double x1, double y1, double x2, double y2, double x3, double y3, double x4,
                                     double y4) {
        if (x1 >= Math.min(x3, x4) && x1 <= Math.max(x3, x4) && y1 >= Math.min(y3, y4) && y1 <= Math.max(y3, y4)
                && x2 >= Math.min(x3, x4) && x2 <= Math.max(x3, x4) && y2 >= Math.min(y3, y4)
                && y2 <= Math.max(y3, y4)) {
            // System.out.println("矩形在矩形内");
            return true;
        } else {
            // System.out.println("矩形不在矩形内");
            return false;
        }
    }

    /**
     * 描述：圆心在矩形内 . 圆心在矩形中且圆的半径小于等于圆心到矩形四边的距离的最小值。 圆心(x,y) 半径r
     * 矩形对角点A（x1，y1），B(x2，y2)
     *
     * @param x
     * @param y
     * @param r
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static boolean circleOnRect(double x, double y, double r, double x1, double y1, double x2, double y2) {
        // 圆心在矩形内
        if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2)) {
            // 圆心到4条边的距离
            double l1 = Math.abs(x - x1);
            double l2 = Math.abs(y - y2);
            double l3 = Math.abs(x - x2);
            double l4 = Math.abs(y - y2);
            if (r <= l1 && r <= l2 && r <= l3 && r <= l4) {
                // System.out.println("圆在矩形内");
                return true;
            } else {
                // System.out.println("圆不在矩形内");
                return false;
            }

        } else {
            // System.out.println("圆不在矩形内");
            return false;
        }
    }

    /**
     * 描述：获取两点间的距离.
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static double getDistance(double x1, double y1, double x2, double y2) {
        double x = x1 - x2;
        double y = y1 - y2;
        return Math.sqrt(x * x + y * y);
    }

    /**
     * 矩形碰撞检测 参数为x,y,width,height
     *
     * @param x1 第一个矩形的x
     * @param y1 第一个矩形的y
     * @param w1 第一个矩形的w
     * @param h1 第一个矩形的h
     * @param x2 第二个矩形的x
     * @param y2 第二个矩形的y
     * @param w2 第二个矩形的w
     * @param h2 第二个矩形的h
     * @return 是否碰撞
     */
    public static boolean isRectCollision(float x1, float y1, float w1, float h1, float x2, float y2, float w2,
                                          float h2) {
        if (x2 > x1 && x2 > x1 + w1) {
            return false;
        } else if (x2 < x1 && x2 < x1 - w2) {
            return false;
        } else if (y2 > y1 && y2 > y1 + h1) {
            return false;
        } else if (y2 < y1 && y2 < y1 - h2) {
            return false;
        } else {
            return true;
        }
    }

    public static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米.
     *
     * @param longitude1
     * @param latitude1
     * @param longitude2
     * @param latitude2
     * @return
     */
    public static double getGeoDistance(double longitude1, double latitude1, double longitude2, double latitude2) {
        double EARTH_RADIUS = 6378137;
        double radLat1 = rad(latitude1);
        double radLat2 = rad(latitude2);
        double a = radLat1 - radLat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(
                Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

}
