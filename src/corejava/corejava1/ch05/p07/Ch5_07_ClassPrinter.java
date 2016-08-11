/**
 *  @author Chen Mingjian  creat on 2012-4-30
 *
 */
package corejava.corejava1.ch05.p07;

import java.util.*;
import java.lang.reflect.*;

/**
 * 这个程序（使用反射）将一个类中的所有方法和域都打印出来。
 */
public class Ch5_07_ClassPrinter {
    public static void main(String[] args) {
        // 类名使用命令行参数或用户的输入
        String name;
        if (args.length > 0)
            name = args[0];
        else {
            Scanner in = new Scanner(System.in);
            System.out.println("Enter class name (e.g. java.util.Date): ");
            name = in.next();
        }

        try {
            // 返回类名为name的Class对象（另一种得到Class对象的方法是通过Object类的getClass方法）
            Class cl = Class.forName(name);
            Class supercl = cl.getSuperclass();         // 得到类的超类
            String modifiers = Modifier.toString(cl.getModifiers());    // 得到修饰符的字符串形式
            if (modifiers.length() > 0)
                System.out.print(modifiers + " ");
            System.out.print("class " + name);
            // 只有当超类不为Object时才打印类的信息
            if (supercl != null && supercl != Object.class)
                System.out.print(" extends " + supercl.getName());
            System.out.print("\n{\n");
            printConstructors(cl);
            System.out.println();
            printMethods(cl);
            System.out.println();
            printFields(cl);
            System.out.println("}");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    /**
     * Prints all constructors of a class
     * 
     * @param cl
     *            a class
     */
    public static void printConstructors(Class cl) {
        Constructor[] constructors = cl.getDeclaredConstructors();  // 获得类的所有构造器

        for (Constructor c : constructors) {
            String name = c.getName();            // 得到构造器的名字
            System.out.print("   ");
            // 得到修饰符的字符串形式 (f.getModifiers方法返回整形，因些需要用 Modifier.toString方法 转换)
            // Modifier类中还有一系列如 isFinal、isPublic等方法检测对就修饰符在modifier中的位
            String modifiers = Modifier.toString(c.getModifiers());

            if (modifiers.length() > 0)
                System.out.print(modifiers + " ");
            System.out.print(name + "(");

            // 打印构造器中参数的类型
            Class[] paramTypes = c.getParameterTypes();
            for (int j = 0; j < paramTypes.length; j++) {
                if (j > 0)
                    System.out.print(", ");
                System.out.print(paramTypes[j].getName());
            }
            System.out.println(");");
        }
    }

    /**
     * 打印类的所有方法
     * 
     * @param cl
     *            a class
     */
    public static void printMethods(Class cl) {
        Method[] methods = cl.getDeclaredMethods();     // 获得类中的所有方法

        for (Method m : methods) {
            Class retType = m.getReturnType();          // 得到返回值的类型
            String name = m.getName();                  // 得到函数名字

            System.out.print("   ");
            // 得到修饰符的字符串形式
            String modifiers = Modifier.toString(m.getModifiers());
            if (modifiers.length() > 0)
                System.out.print(modifiers + " ");
            System.out.print(retType.getName() + " " + name + "(");

            Class[] paramTypes = m.getParameterTypes(); // 打印方法中参数的类型
            for (int j = 0; j < paramTypes.length; j++) {
                if (j > 0)
                    System.out.print(", ");
                System.out.print(paramTypes[j].getName());
            }
            System.out.println(");");
        }
    }

    /**
     * 打印类中的所有域
     * 
     * @param cl
     *            a class
     */
    public static void printFields(Class cl) {
        Field[] fields = cl.getDeclaredFields();        // 得到所有类中的所有域

        for (Field f : fields) {
            Class type = f.getType();                   // 获得域的类型
            String name = f.getName();                  // 获得域的名字
            System.out.print("   ");

            String modifiers = Modifier.toString(f.getModifiers()); // 得到修饰符的字符串形式
            if (modifiers.length() > 0)
                System.out.print(modifiers + " ");
            System.out.println(type.getName() + " " + name + ";");
        }
    }
}
