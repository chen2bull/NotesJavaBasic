/**
 *  @author Chen Mingjian  creat on 2012-3-26
 *
 */
package corejava.corejava1.ch05.p05;

/**
 * 参数数量可变的方法
 */
public class Ch5_05_VariableArg {

    public static void main(String[] args) {
        double m = max(1, 23, 40, 15, 6);
        System.out.printf("max = %.2f \n", m);

    }

    public static double max(double... values) {
        double largest = Double.MIN_VALUE;
        for (double v : values) {   // 参数的遍历
            if (v > largest) {
                largest = v;
            }
        }
        return largest;
    }
}
