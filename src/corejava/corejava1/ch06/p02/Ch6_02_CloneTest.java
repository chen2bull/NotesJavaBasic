/**
 *  @author Chen Mingjian  creat on 2012-4-13
 *
 */
package corejava.corejava1.ch06.p02;

import java.util.*;

/**
 * This program demonstrates cloning.
 */
public class Ch6_02_CloneTest {
    public static void main(String[] args) {
        try {
            Employee original = new Employee("John Q. Public", 50000);
            original.setHireDay(2000, 1, 1);
            Employee copy = original.clone();
            copy.raiseSalary(10);
            copy.setHireDay(2002, 12, 31);
            System.out.println("original=" + original);
            System.out.println("copy=" + copy);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}

class Employee implements Cloneable {
    public Employee(String n, double s) {
        name = n;
        salary = s;
        hireDay = new Date();
    }

    public Employee clone() throws CloneNotSupportedException {
        /*
         * 只要在clone中含有没有实现Cloneable接口的对象，Object类的clone方法就会抛出
         * CloneNotSupportedException异常。虽然Employee类和Date类都实现了Cloneable接口，但是为
         * 了使Employee类的子类具有是否抛出CloneNotSupportedException异常的选择权，应该在clone方
         * 法中抛出这个异常。(如果是final类，则可以在clone方法不抛出异常，并且在方法内部处理这个异常。)
         */
        /*
         * 即使colne的实现（浅拷贝）能满足需求，也应该实现Cloneable接口！将clone重定义为public，
         * 并调用super.clone。（默认的clone方法是Object类的一个protected方法，）
         */
        Employee cloned = (Employee) super.clone();

        /*
         * 类中有可变的域，则应该实现深拷贝。 （如果不实现深拷贝，拷贝的结果会使得两个域引用同一个子对象。）
         */
        cloned.hireDay = (Date) hireDay.clone();

        return cloned;
    }

    /**
     * Set the hire day to a given date.
     * 
     * @param year
     *            the year of the hire day
     * @param month
     *            the month of the hire day
     * @param day
     *            the day of the hire day
     */
    public void setHireDay(int year, int month, int day) {
        Date newHireDay = new GregorianCalendar(year, month - 1, day).getTime();

        // Example of instance field mutation
        hireDay.setTime(newHireDay.getTime());
    }

    public void raiseSalary(double byPercent) {
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    public String toString() {
        return "Employee[name=" + name + ",salary=" + salary + ",hireDay=" + hireDay + "]";
    }

    private String name;
    private double salary;
    private Date   hireDay;
}
