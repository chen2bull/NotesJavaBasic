package leetcode;

/**
 * Calculate the sum of two integers a and b, but you are not allowed to use the operator + and -.
 Example:
 Given a = 1 and b = 2, return 3.
 * Created by Administrator on 2016/7/3.
 */
public class SumOfTwoIntegers {
    public static void main(String args[]) {
        System.out.println(new SumOfTwoIntegers().getSum(1,100));
        System.out.println(new SumOfTwoIntegers().getSum(4,7));
    }
    public int getSum(int a, int b) {
        while(b != 0) {
            int carry = a & b;
            a = a ^ b;
            b = carry << 1;
        }
        return a;
    }
}
