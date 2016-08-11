/** @author Chen Mingjian  creat on 2012-4-30
 */
package corejava.corejava1.ch05.p07;

import java.lang.reflect.*;
import java.util.*;

/**
 * This program uses reflection to spy on objects.
 * 
 * @version 1.11 2004-02-21
 * @author Cay Horstmann
 */
public class Ch5_07_GeneralToString {
    public static void main(String[] args) {
        ArrayList<Integer> squares = new ArrayList<Integer>();
        for (int i = 1; i <= 5; i++)
            squares.add(i * i);
        System.out.println(new ObjectAnalyzer().toString(squares));
        Date nDate = new Date();
        System.out.println(new ObjectAnalyzer().toString(nDate));
    }
}

class ObjectAnalyzer {
    /**
     * 通用的toString方法.在自己定义的类中要重载这个方法,可以采用以下的方式 public String toString() { return
     * new ObjectAnalyzer().toString(this); }
     * 
     * @param obj
     *            an object
     * @return 类名［域的类型＝域的值。。。］
     */
    public String toString(Object obj) {
        if (obj == null)
            return "null";
        if (visited.contains(obj))
            return "...";
        visited.add(obj); // 保存已经访问过的对象,避免循环引用导致的无限递归
        Class cl = obj.getClass();
        if (cl == String.class)
            return (String) obj;

        if (cl.isArray()) { // 如果c1对应的类型是数组
            String r = cl.getComponentType() + "[]{";   // 得到元素的类型
            for (int i = 0; i < Array.getLength(obj); i++) {
                if (i > 0)
                    r += ",";
                Object val = Array.get(obj, i);
                if (cl.getComponentType().isPrimitive())    // 如果是内建类型(int,double....)
                    r += val;                               // 字符串和值相加会自动调用toString
                else
                    r += toString(val);
            }
            return r + "}";
        }

        String r = cl.getName();    // 如果类型不是数组
        // inspect the fields of this class and all superclasses
        // 遍历类的 所有域 和 所有超类中的所有域
        do {
            r += "[";
            Field[] fields = cl.getDeclaredFields();

            // 除非拥有访问权限,否则,否则Java安全机制只允许查看任意对象有哪些域,而不允许读取或改变它们的值
            // !!使Fields中,域的值都可以设置/获取.
            AccessibleObject.setAccessible(fields, true);
            // get the names and values of all fields
            for (Field f : fields) {
                if (!Modifier.isStatic(f.getModifiers())) { // 如果这个域不是静态域
                    if (!r.endsWith("["))                   // 字符串相加时,这个方法很典型,也很常用
                        r += ",";
                    r += f.getName() + "=";
                    try {
                        Class t = f.getType();              // 获得该域的类型
                        Object val = f.get(obj);            // 获得该域的值
                        // f.set(obj,newValue); 也可以用set方法设置obj对象中用f表示的域
                        if (t.isPrimitive())                // 如果是内建类型(int,double....)
                            r += val;                       // 字符串和值相加会自动调用toString
                        else
                            r += toString(val);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            r += "]";
            cl = cl.getSuperclass();    // 得到描述超类的Class对象
        } while (cl != null);

        return r;
    }

    private ArrayList<Object> visited = new ArrayList<Object>();
}
