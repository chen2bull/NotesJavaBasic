package corejava.corejava1.ch05.p07;

import java.lang.reflect.*;

/**
 * 如果只想让数组增长,不需要使用这个类中的代码,只需要与以下代码就可以了: Employee a = new Employee[100];
 * ....//经过一些操作以后,a已经满了 a = Arrays.copyOf(a, a.lenght*11/10 + 10);
 * 这个类只是用来熟悉反射的使用 This program demonstrates the use of reflection for
 * manipulating arrays.
 * 
 * @version 1.01 2004-02-21
 * @author Cay Horstmann
 */
public class Ch5_07_ArrayGrowTest {
    public static void main(String[] args) {
        int[] a = { 1, 2, 3 };
        a = (int[]) goodArrayGrow(a);
        arrayPrint(a);

        String[] b = { "Tom", "Dick", "Harry" };
        b = (String[]) goodArrayGrow(b);
        arrayPrint(b);

        System.out.println("The following call will generate an exception.");
        b = (String[]) badArrayGrow(b);
    }

    /**
     * This method attempts to grow an array by allocating a new array and
     * copying all elements.
     * 
     * @param a
     *            the array to grow
     * @return a larger array that contains all elements of a. However, the
     *         returned array has type Object[], not the same type as a
     */
    static Object[] badArrayGrow(Object[] a)     // 不用反射,所以得不到a的实际类型,因此有错
    {
        int newLength = a.length * 11 / 10 + 10;
        Object[] newArray = new Object[newLength];
        System.arraycopy(a, 0, newArray, 0, a.length);
        return newArray;
    }

    /**
     * This method grows an array by allocating a new array of the same type and
     * copying all elements.
     * 
     * @param a
     *            the array to grow. This can be an object array or a primitive
     *            type array
     * @return a larger array that contains all elements of a.
     */
    static Object goodArrayGrow(Object a) {
        Class cl = a.getClass();
        if (!cl.isArray())
            return null;
        Class componentType = cl.getComponentType();
        int length = Array.getLength(a);
        int newLength = length * 11 / 10 + 10;

        Object newArray = Array.newInstance(componentType, newLength);
        System.arraycopy(a, 0, newArray, 0, length);
        return newArray;
    }

    /**
     * A convenience method to print all elements in an array
     * 
     * @param a
     *            the array to print. It can be an object array or a primitive
     *            type array
     */
    static void arrayPrint(Object a) {
        Class cl = a.getClass();
        if (!cl.isArray())
            return;
        Class componentType = cl.getComponentType();
        int length = Array.getLength(a);
        System.out.print(componentType.getName() + "[" + length + "] = { ");
        for (int i = 0; i < Array.getLength(a); i++)
            System.out.print(Array.get(a, i) + " ");
        System.out.println("}");
    }
}
