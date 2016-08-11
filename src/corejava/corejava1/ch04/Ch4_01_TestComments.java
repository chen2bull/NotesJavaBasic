/**
 *  @author Chen Mingjian  creat on 2012-3-26
 *
 */
package corejava.corejava1.ch04;

import java.io.IOException;

/**
 * 这是一个用于测试Javadoc使用的类
 * 
 * @author Chen Mingjian
 * @version 1.0.0 （这个标记将产生一个版本条目）
 * @since java1.2
 */
/* 要在Eclipse中使用Javadoc，可以选择Project-->Generator Javadoc */
public class Ch4_01_TestComments {

    /**
     * private属性，注释不会在文档中生成
     */
    private int   age;

    /**
     * public属性，注释会在文档中生成
     */
    public String sex;

    public Ch4_01_TestComments() {
        // TODO Auto-generated constructor stub
    }

    /**
     * 用于求得两个整数中最大的那个
     * 
     * @param x
     *            第一个输入参数的说明 （这个标记说明参数的意义）
     * @param y
     *            第二个输入参数的说明
     * @return 返回两个数中最大的一个（这个标记说明返回值的意义）
     * @throws IOException
     *             (这个标记说明有可能抛出的异常)
     */

    public int FindMaxInt(int x, int y) throws IOException {
        if (x > y) {
            return x;
        } else {
            return y;
        }
    }

    /**
     * 函数的说明放在这个位置
     * 
     * @param args
     *            控制台的输入参数列表
     */
    public static void main(String[] args) {
        System.out.println("Hello world!");

    }

}
