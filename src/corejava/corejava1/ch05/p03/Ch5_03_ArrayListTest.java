/**
 *  @author Chen Mingjian  creat on 2012-3-26
 *
 */
package corejava.corejava1.ch05.p03;

import java.util.*;

/**
 * This program demonstrates the ArrayList class.
 * 
 * @version 1.1 2004-02-21
 * @author Cay Horstmann
 */
public class Ch5_03_ArrayListTest {
    public static void main(String[] args) {
        // fill the staff array list with three Employee objects
        ArrayList<Employee> staff = new ArrayList<Employee>();

        // 在数组列表的尾端加入一个空数组列表
        staff.add(new Employee("Carl Cracker", 75000, 1987, 12, 15));
        staff.add(new Employee("Harry Hacker", 50000, 1989, 10, 1));
        staff.add(new Employee("Tony Tester", 40000, 1990, 3, 15));

        // 使用set函数来设置下标为2的元素
        staff.set(2, new Employee("Chen mingjian", 60000, 1987, 12, 21));

        // 使用get函数来获取数组列表中的元素
        staff.get(0).raiseSalary(0.2);

        // 比起用size函数返回当前元素个数，使用for-each循环能较方便地遍历数组列表
        // raise everyone's salary by 5%
        for (Employee e : staff)
            e.raiseSalary(5);
        // print out information about all Employee objects
        for (Employee e : staff)
            System.out.println("name=" + e.getName() + ",salary=" + e.getSalary() + ",hireDay=" + e.getHireDay());

        // !!!典型技巧：数组列表(ArrayList)构造一个数组
        Employee[] emArray = new Employee[staff.size()];
        staff.toArray(emArray);

        // 设置数组列表的容量不少于100
        staff.ensureCapacity(100);

        // 将数组列表的存储容量削减到当前尺寸
        staff.trimToSize();
    }
}

class Employee {
    public Employee(String n, double s, int year, int month, int day) {
        name = n;
        salary = s;
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
        hireDay = calendar.getTime();
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public Date getHireDay() {
        return hireDay;
    }

    public void raiseSalary(double byPercent) {
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    private String name;
    private double salary;
    private Date   hireDay;
}
