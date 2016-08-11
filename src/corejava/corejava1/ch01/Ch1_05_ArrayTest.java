/**
 *  @author Chen Mingjian  creat on 2012-3-26
 *
 */
package corejava.corejava1.ch01;

import java.util.Arrays;

/** 测试数组的使用
 * 
 */

/**
 * @author Chen Mingjian
 */
public class Ch1_05_ArrayTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        BasicUsageTest();
        /** 基本语法 */

        ArrayCopyTest();
        /** 数组拷贝 */

        MutiDArrayTest();
        /** 多维数组（包括不规则多维数组） */
    }

    /** 基本语法 start */
    public static void BasicUsageTest() {
        int[] s;
        s = new int[6];
        for (int i = 0; i < s.length; i++) {
            s[i] = i;
        }
        for (int ele : s) {     // JavaSE5.0 增加的for each循环结构
            System.out.println(ele);
        }
    }

    public static int[] returnArrayInt() {
        int[] a = new int[0];       // 数组长度为0 与 null是不同的
        return a;
    }

    /** 基本语法 end */

    /** 数组拷贝 start */
    public static void ArrayCopyTest() {
        int[] Numbers = { 1001, 1002, 1003, 1004, 1005, 1006, 1007 };
        int[] smallPrimes = Numbers;    // 浅拷贝，两个数组变量引用同一个数组
        Numbers[6] = 1024;
        System.out.println(Arrays.toString(smallPrimes));

        int[] copiedNums = Arrays.copyOf(Numbers, Numbers.length);
        // 深拷贝，Numbers中所有值拷贝到copiedNums
        Numbers[6] = 1006;
        System.out.println(Arrays.toString(copiedNums));

        copiedNums = Arrays.copyOf(copiedNums, 2 * copiedNums.length);
        // copyOf方法也可以用来增加数组大小

        int[] dest = { 2, 3, 5, 7, 11, 14, 19 };
        int[] src = Arrays.copyOf(Numbers, Numbers.length);
        System.arraycopy(src, 2, dest, 3, 4); // 部分拷贝，dest要有足够的长度
        System.out.println(Arrays.toString(dest));

    }

    /** 数组拷贝 end */

    /** 数组常用方法 详细见java.util.Arrays start */
    /*
     * 下面说明中type可以是任何类型 static String toString(type[] a) 返回指定数组内容的字符串表示形式。
     * static type[] copyOf(type[] original, int newLength) 复制指定的数组，截取或用 false
     * 填充（如有必要），以使副本具有指定的长度。 static type[] copyOfRange(type[] original, int
     * from, int to) 将指定数组的指定范围复制到一个新数组。 static void sort(type a)
     * 快速升序排序（数组中的所有元素都必须实现 Comparable 接口。） 在进行二分搜索之前，必须根据元素的自然顺序对范围进行升序排序
     * static int binarySearch(type[] a, type key) 使用二分搜索法来搜索指定数组，以获得指定对象。
     * static int binarySearch(type[] a, int fromIndex, int toIndex, type key)
     * 使用二分搜索法来搜索指定数组的范围，以获得指定对象。 static void fill(type[] a, Object val) 将指定的
     * type 引用分配给指定 type 数组的每个元素。 static void fill(type[] a, int fromIndex, int
     * toIndex, Object val) 将指定的 type 引用分配给指定 type 数组指定范围中的每个元素。 static boolean
     * equals(type[] a, type[] a2) 如果两个指定的 type 数组彼此相等，则返回 true。 static String
     * deepToString(Object[] a) 将多维数组转换为字符串。
     */
    /** 数组常用方法 详细见java.util.Arrays end */

    /** 多维数组（包括不规则多维数组）start */
    public static void MutiDArrayTest() {
        final int NMAX = 10;

        // allocate triangular array
        int[][] odds = new int[NMAX + 1][];
        for (int n = 0; n <= NMAX; n++)
            odds[n] = new int[n + 1];

        // fill triangular array
        for (int n = 0; n < odds.length; n++)
            for (int k = 0; k < odds[n].length; k++) {
                /*
                 * compute binomial coefficient
                 * n*(n-1)*(n-2)*...*(n-k+1)/(1*2*3*...*k)
                 */
                int lotteryOdds = 1;
                for (int i = 1; i <= k; i++)
                    lotteryOdds = lotteryOdds * (n - i + 1) / i;

                odds[n][k] = lotteryOdds;
            }

        // print triangular array
        // System.out.print( Arrays.deepToString(odds) );可以有相似的功能，但输出格式不是很好。
        for (int[] row : odds) {
            for (int odd : row)
                System.out.printf("%4d", odd);
            System.out.println();
        }
    }
    /** 多维数组（包括不规则多维数组）end */
}
