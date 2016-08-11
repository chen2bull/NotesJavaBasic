/**
 *  @author Chen Mingjian  creat on 2012-3-26
 *
 */
package corejava.corejava1.ch01;

/**
 * 基本类型
 */
public class Ch1_01_Types {

    public byte  value_8; // 一个字节
    public short value_16;  // 二个字节
    public int   value_32;    // 三个字节
    public long  value_64;   // 四个字节

    /**
     * @param args
     */
    public static void main(String[] args) {

        int a = 39;
        int b = 0x4b;
        int c = 012;
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);

        double d1 = 3.0 / 0.0;    // Infinity
        double d2 = -2.0 / 0.0;   // -Infinity
        double d3 = 0.0 / 0.0;    // NaN
        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d3);

        char c1 = 'A';
        char c2 = '\u0061';
        char c3 = '\n';

        System.out.println(c1);
        System.out.println(c2);
        System.out.println(c3);

        boolean b1 = true; /* 布尔值只能是true或者false，不能是0或其它值 */
        System.out.println(b1);
    }
}
