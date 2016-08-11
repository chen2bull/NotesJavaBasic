/**
 *  @author Chen Mingjian  creat on 2012-3-26
 *
 */
package corejava.corejava1.ch05.p02;

import java.util.*;

/**
 * @author Chen Mingjian creat on 2012-3-21
 *         Object类的几个重要的方法(equals,hashCode,toString)
 */

public class Ch5_02_EqualsTest {
    public static void main(String[] args) {
        Employee alice1 = new Employee("Alice Adams", 75000, 1987, 12, 15);
        Employee alice2 = alice1;
        Employee alice3 = new Employee("Alice Adams", 75000, 1987, 12, 15);
        Employee bob = new Employee("Bob Brandson", 50000, 1989, 10, 1);

        System.out.println("alice1 == alice2: " + (alice1 == alice2));

        System.out.println("alice1 == alice3: " + (alice1 == alice3));

        System.out.println("alice1.equals(alice3): " + alice1.equals(alice3));

        System.out.println("alice1.equals(bob): " + alice1.equals(bob));

        System.out.println("bob.toString(): " + bob);

        Manager carl = new Manager("Carl Cracker", 80000, 1987, 12, 15);
        Manager boss = new Manager("Carl Cracker", 80000, 1987, 12, 15);
        boss.setBonus(5000);
        System.out.println("boss.toString(): " + boss);
        System.out.println("carl.equals(boss): " + carl.equals(boss));
        System.out.println("alice1.hashCode(): " + alice1.hashCode());
        System.out.println("alice3.hashCode(): " + alice3.hashCode());
        System.out.println("bob.hashCode(): " + bob.hashCode());
        System.out.println("carl.hashCode(): " + carl.hashCode());
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

    public boolean equals(Object otherObject) {
        // 下面是一种比较好的实现Equals方法的步骤
        // 快速检测otherObject与this是否引用同一个对象
        // a quick test to see if the objects are identical
        if (this == otherObject)
            return true;

        // 检测otherObject是否为NULL，如果为NULL，返回false。这项检测是很必要的
        // must return false if the explicit parameter is null
        if (otherObject == null)
            return false;

        // 比较this与otherObject是否属于同一个类，如果equals的语义在每个子类中有所改变，就使用getClass检测
        // if the classes don't match, they can't be equal
        if (getClass() != otherObject.getClass())
            return false;

        // now we know otherObject is a non-null Employee
        Employee other = (Employee) otherObject;

        // test whether the fields have identical values
        return name.equals(other.name) && salary == other.salary && hireDay.equals(other.hireDay);
    }

    public int hashCode() {
        return 7 * name.hashCode() + 11 * new Double(salary).hashCode() + 13 * hireDay.hashCode();
        // 散列码是由对象导出的一个整型值。散列码是没有规律的。不同的对象，散列码基本不会相同
        // 以上是一种典型的定义散列码的方法，用不同的质数乘于各元素的散裂码
    }

    public String toString() {
        return getClass().getName() + "[name=" + name + ",salary=" + salary + ",hireDay=" + hireDay + "]";
        // toString方法的典型写法，用getClass().getName()要比直接写上类名要好（改类名时，不用改这行代码）
        // 强烈建议为每一个类定义一个toString方法格式
    }

    private String name;
    private double salary;
    private Date   hireDay;
}

class Manager extends Employee {
    public Manager(String n, double s, int year, int month, int day) {
        super(n, s, year, month, day);
        bonus = 0;
    }

    public double getSalary() {
        double baseSalary = super.getSalary();
        return baseSalary + bonus;
    }

    public void setBonus(double b) {
        bonus = b;
    }

    public boolean equals(Object otherObject) {
        if (!super.equals(otherObject))
            return false;
        Manager other = (Manager) otherObject;
        // super.equals checked that this and other belong to the same class
        return bonus == other.bonus;
    }

    public int hashCode() {
        return super.hashCode() + 17 * new Double(bonus).hashCode();
        // 子类hashCode方法的典型写法！！注意17与父类散列码公式中的系数均不相等。
    }

    public String toString() {
        return super.toString() + "[bonus=" + bonus + "]";
        // 子类toString方法的典型写法！！
    }

    private double bonus;
}
