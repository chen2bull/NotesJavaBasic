package corejava.corejava1.ch11;

import java.util.*;

/**
 * 一个显示递归函数堆栈跟踪信息的程序.
 * 
 * @version 1.01 2004-05-10
 * @author Cay Horstmann
 */
public class Ch11_02_StackTraceTest {
    /**
     * Computes the factorial of a number
     * 
     * @param n
     *            a nonnegative integer
     * @return n! = 1 * 2 * . . . * n
     */
    public static int factorial(int n) {
        System.out.println("factorial(" + n + "):");
        Throwable t = new Throwable();
        // 堆栈跟踪是方法调用过程的列表
        // 调用Throwable对象的getStackTrace方法可以得到StackTraceElement类型的数组.
        // 可以用它来分析方法调用的过程及位置
        StackTraceElement[] frames = t.getStackTrace();
        // StackTraceElement有getFileName,getLineNumber,getClassName,getMethodName等方法
        // 用来在程序中对堆栈跟踪进行分析
        for (StackTraceElement f : frames)
            System.out.println(f);
        int r;
        if (n <= 1)
            r = 1;
        else
            r = n * factorial(n - 1);
        System.out.println("return " + r);
        return r;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter n: ");
        int n = in.nextInt();
        factorial(n);
    }
}
