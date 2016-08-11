/**
 *  @author Chen Mingjian  creat on 2012-3-26
 *
 */
package corejava.corejava1.ch04;

/**
 *  @author Chen Mingjian  creat on 2012-3-20
 *  静态域、静态方法、包的静态导入
 */
import static java.lang.System.*; /* 静态导入，可以直接使用静态成员out,而不必使用类名 */
/* 如果没有static 是不可以直接使用类中的成员的 */
import static java.lang.Math.*; /* 这应该是静态导入的典型用法 */

public class Ch4_02_StaticTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        double a;
        a = pow(5, 2); /* 如果没有静态导入，就必须使用Math.pow和Math.sin这样的形式 */
        out.println(a);

        Employee[] es = new Employee[3];

        es[0] = new Employee("Chen Mingjian", 5500);
        es[1] = new Employee("Qu Ming zhi", 0);
        es[2] = new Employee("Chen Mingzhuang", 3000);
        for (Employee e : es) {
            e.ShowEmployee();
        }
    }
}

class Employee {
    public Employee(String n, double s) {
        name = n;
        salary = s;
        id = nextId;
        nextId++;
    }

    public static int getNextId() { /* 静态函数 */
        return nextId;
    }

    public void ShowEmployee() {
        out.println("Name:" + name + "\tSalary:" + salary + "\tID:" + id);
    }

    private String              name;
    private double              salary;
    private int                 id;
    private static int          nextId = 1;        /*
                                                     * 静态量，属于类,可以用于记录与类相关的信息，如类实例的个数
                                                     */
    private static final double PI     = 3.1415926; /*
                                                     * 静态常量，属于类，有final关键字（
                                                     * 类似于c中的const
                                                     * ,注：java中没有const）
                                                     */
}
