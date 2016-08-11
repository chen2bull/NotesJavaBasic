package corejava.corejava1.ch12;

import java.io.Serializable;
import java.util.*;

/**
 * @version 1.00 2004-05-10
 * @author Cay Horstmann
 */
public class PairTest2 {
    public static void main(String[] args) {
        GregorianCalendar[] birthdays = {
                new GregorianCalendar(1906, Calendar.DECEMBER, 9), // G. Hopper
                new GregorianCalendar(1815, Calendar.DECEMBER, 10), // A. Lovelace
                new GregorianCalendar(1903, Calendar.DECEMBER, 3), // J. von Neumann
                new GregorianCalendar(1910, Calendar.JUNE, 22), // K. Zuse
        };
        Pair<GregorianCalendar> mm = ArrayAlg2.minmax(birthdays);
        System.out.println("min = " + mm.getFirst().getTime());
        System.out.println("max = " + mm.getSecond().getTime());
    }
}

class ArrayAlg2 {
    /**
     * 获得T类型数组的最小值和最大值组成的值对（Pair）。
     * 
     * @param a
     *            一个T类型的数组
     * @return 最小值和最大值组成的值对,如果a为空或为null则返回null
     */
    public static <T extends Comparable & Serializable> Pair<T> minmax(T[] a) {
        /*
         * 限定T类型必须实现了Comparable接口和Serializable接口。限定中至多有一个类（如果使用一个类作为
         * 限定，它必须是限定列表中的第一个）。
         * 
         * Serializable 是序列化接口。只起标识作用(里面没有任何方法)。
         * 它有一个id 那个是全球唯一标识，用于区分 bean 对象
         */
        if (a == null || a.length == 0)
            return null;
        T min = a[0];
        T max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (min.compareTo(a[i]) > 0)
                min = a[i];
            if (max.compareTo(a[i]) < 0)
                max = a[i];
        }
        return new Pair<T>(min, max);
    }
}

/* 
 * 使用Java泛型时，需要考虑的一些限制
 * 1.不能用基本类型实例化（如，没有Pair<double>,只有Pair<Double>）
 * 2.运行时类型查询只适用于原始类型
 * （如，if(a instanceof Pair<String>)与if(a instanceof Pair<T>)是一样的，类型T被忽略了）
 * 3.不能抛出也不能捕获泛型类实例
 * （如，public class Problem<T> extends Exception{...} 是非法的）
 * （再如，catch(T e) 也是非法的，不能捕获泛型类）
 * 但是类型类型声明中，可以使用类型变量，以下做法是合法的
 *  public static <T extends Throwable>  void doSomethings(T t) throws T {
        try {
            
        } catch (Throwable realCause) {
            t.initCause(realCause);
            throw t;
        }
    }
 * 
 * 4.参数化类型的数组不合法
 * （如：Pair<String>[] table = new Pair<String>[10];是非法的。如果有必要，可以使用ArrayList代替。
 * ArrayList< Pair<String> > table = new ArrayList< Pair<String> > ();
 * ）
 * 5.不能实例化类型变量
 * （如:。。。new T();是非法的）
 * 6.不能在静态域或方法中引用类型变量。
 */



