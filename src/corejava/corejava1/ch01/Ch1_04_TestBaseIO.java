/**
 *  @author Chen Mingjian  creat on 2012-3-26
 *
 */
package corejava.corejava1.ch01;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 测试控制台与文件中，基本IO的使用
 */
public class Ch1_04_TestBaseIO {
    public static void main(String[] args) {
        // TestScanner(); /** 测试基本输入的使用 Scanner */

        TestFileBaseIO();
        /** 文件基本的输入与输出 */
    }

    /** 测试基本输入的使用 Scanner start */
    public static void TestScanner() {
        Scanner in = new Scanner(System.in);

        // get first input
        System.out.print("What is your name? ");
        String name = in.nextLine();

        // get second input
        System.out.print("How old are you? ");
        int age = in.nextInt();

        // display output on console
        System.out.println("Hello, " + name + ". Next year, you'll be " + (age + 1));
    }

    /* Scanner 类的使用法参见java.util.Scanner，以下的为常用的方法 */
    /*
     * Scanner(InputStream source) 构造一个新的 Scanner，它生成的值是从指定的输入流扫描的。 String
     * next() 查找并返回来自此扫描器的下一个完整标记(以空格作为分隔符)。 double nextDouble()
     * 将输入信息的下一个标记扫描为一个 double。 int nextInt() 将输入信息的下一个标记扫描为一个 int。 String
     * nextLine() 此扫描器执行当前行，并返回跳过的输入信息。 boolean hasNext() 如果此扫描器的输入中有另一个标记，则返回
     * true。 boolean hasNextInt(int radix) 检测是否有整数的下一个字符序列。 boolean
     * hasNextDouble() 检测是否有浮点数的下一个字符序列。
     */
    /** 测试基本输入的使用 Scanner end */

    /** 格式化输出使用System.out.printf(...),用法与C语言相同 */

    /** 文件基本的输入与输出 start */
    public static void TestFileBaseIO() {
        try {
            Scanner in = new Scanner(new File("Ch1_04_myfile1.txt"));
            PrintWriter myout = new PrintWriter("Ch1_04_myfile2.txt");

            while (in.hasNextLine()) {
                myout.println(in.next());
            }

            myout.close();      // 必须要close，否则可能输出文件中看不到
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    /** 文件基本的输入与输出 end */

}
