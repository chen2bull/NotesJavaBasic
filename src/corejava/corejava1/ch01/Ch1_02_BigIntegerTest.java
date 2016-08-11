/**
 *  @author Chen Mingjian  creat on 2012-3-26
 *
 */
package corejava.corejava1.ch01;

/** 使用大数运算,
 * BigInteger可以实现任意精度的整数运算,
 * BigDecimal可以实现任意精度的浮点数运算
 * 大数运算不太常用,使用时参考
 * JDK-doc中java.math.BigDecimal和java.math.BigInteger
 * */
import java.math.*;

import java.util.*;

/**
 * This program uses big numbers to compute the odds of winning the grand prize
 * in a lottery.
 * 
 * @version 1.20 2004-02-10
 * @author Cay Horstmann
 */
public class Ch1_02_BigIntegerTest {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("How many numbers do you need to draw? ");
        int k = in.nextInt();

        System.out.print("What is the highest number you can draw? ");
        int n = in.nextInt();

        /*
         * compute binomial coefficient n*(n-1)*(n-2)*...*(n-k+1)/(1*2*3*...*k)
         */

        BigInteger lotteryOdds = BigInteger.valueOf(1);

        for (int i = 1; i <= k; i++) {
            lotteryOdds = lotteryOdds.multiply(BigInteger.valueOf(n - i + 1)).divide(BigInteger.valueOf(i));
        }
        System.out.println("Your odds are 1 in " + lotteryOdds + ". Good luck!");
    }

}
