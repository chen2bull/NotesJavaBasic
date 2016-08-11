/**
 *  @author Chen Mingjian  creat on 2012-3-26
 *
 */
package corejava.corejava1.ch05.p04;

import java.util.ArrayList;

/**
 * 对象包装器,自动打包与拆包 有时，需要将int这样的基本类型转换为对象。
 */
public class Ch5_04_Autoboxing {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        // 尖括号中的类型不允许是int,double等基本类型
        list.add(3);    // 同list.add(new Integer(3));
                     // 从前者到后者(自动)转换的过程叫做自动打包
        list.add(9);
        list.add(13);

        int n = list.get(0);    // 同 int n = list.get(0).intValue();
                             // 从前者到后者(自动)转换的过程叫做自动拆包
        System.out.println(n);

        int x = Integer.parseInt("100");    // 数字字符串 转 int
        System.out.println(x);

        x = Integer.parseInt("1A3", 16);     // 十六进制 数字字符串 转 int
        System.out.printf("%X\n", x);
    }

}
