package corejava.corejava2.ch01;

/**
 @version 1.20 17 Aug 1998
 @author Cay Horstmann
 */

import java.io.*;
import java.util.*;

public class CH01_06_SerialCloneTest {
    public static void main(String[] args) {
        Employee3 harry = new Employee3("Harry Hacker", 35000, 1989, 10, 1);
        // clone harry
        Employee3 harry2 = (Employee3) harry.clone();

        // mutate harry
        harry.raiseSalary(10);

        // now harry and the clone are different
        System.out.println(harry);
        System.out.println(harry2);
    }
}

/**
 * 一个在clone方法中使用序列化的类，
 * 扩展于这个类的类（或者拷贝这个clone方法），则clone方法产生的对象是对现有对象的深拷贝
 */
class SerialCloneable implements Cloneable, Serializable {
    public Object clone() {
        try {
            // save the object to a byte array
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bout);
            out.writeObject(this);
            out.close();

            // read a clone of the object from the byte array
            ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
            ObjectInputStream in = new ObjectInputStream(bin);
            Object ret = in.readObject();
            in.close();

            return ret;
        } catch (Exception e) {
            return null;
        }
    }
}

/**
 * The familiar Employee3 class, redefined to extend the
 * SerialCloneable class.
 */
class Employee3 extends SerialCloneable {
    public Employee3(String n, double s, int year, int month, int day) {
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

    public String toString() {
        return getClass().getName() + "[name=" + name + ",salary=" + salary + ",hireDay=" + hireDay + "]";
    }

    private String name;
    private double salary;
    private Date   hireDay;
}
