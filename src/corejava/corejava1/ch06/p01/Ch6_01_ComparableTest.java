/**
 *  @author Chen Mingjian  creat on 2012-4-13
 *
 */
package corejava.corejava1.ch06.p01;

import java.util.*;

/**
 * 这个程序说明Comparable接口的使用.
 */
public class Ch6_01_ComparableTest {
    public static void main(String[] args) {
        Employee[] staff = new Employee[3];

        staff[0] = new Employee("Harry Hacker", 35000);
        staff[1] = new Employee("Carl Cracker", 75000);
        staff[2] = new Employee("Tony Tester", 38000);

        // 如果希望使用Arrays类的静态方法sort对Employee对象进行排序，Employee类就必须实现Comparable接口
        Arrays.sort(staff);

        // print out information about all Employee objects
        for (Employee e : staff)
            System.out.println("name=" + e.getName() + ",salary=" + e.getSalary());
    }
}

class Employee implements Comparable<Employee> // 实现Comparable接口
{
    public Employee(String n, double s) {
        name = n;
        salary = s;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public void raiseSalary(double byPercent) {
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    /**
     * Compares employees by salary
     * 
     * @param other
     *            another Employee object
     * @return a negative value if this employee has a lower salary than
     *         otherObject, 0 if the salaries are the same, a positive value
     *         otherwise
     */
    public int compareTo(Employee other) {
        if (salary < other.salary)
            return -1;
        if (salary > other.salary)
            return 1;
        return 0;
    }

    private String name;
    private double salary;
}

/*
 * 在JDK中，Comparable接口的定义如下。 接口的所有方法自动地属于public。因此，在接口中声明方法时，不必提供关键字public。
 * 不过，在实现接口时，必须把方法声明为public。 public interface Comparable<T> { int compareTo(T
 * other); }
 */
