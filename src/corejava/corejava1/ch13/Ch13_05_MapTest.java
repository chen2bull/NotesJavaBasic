package corejava.corejava1.ch13;

import java.util.*;

/**
 * This program demonstrates the use of a map with key type String and value
 * type Employee.
 * 
 * @version 1.10 2004-08-02
 * @author Cay Horstmann
 */
public class Ch13_05_MapTest {
    public static void main(String[] args) {
        Map<String, Employee> staff = new HashMap<String, Employee>();
        staff.put("144-25-5464", new Employee("Amy Lee"));
        staff.put("567-24-2546", new Employee("Harry Hacker"));
        staff.put("157-62-7935", new Employee("Gary Cooper"));
        staff.put("456-62-5527", new Employee("Francesca Cruz"));

        // print all entries
        System.out.println(staff);

        // remove an entry
        staff.remove("567-24-2546");

        // replace an entry
        staff.put("456-62-5527", new Employee("Francesca Miller"));

        // look up a value
        System.out.println(staff.get("157-62-7935"));

        // iterate through all entries
        for (Map.Entry<String, Employee> entry : staff.entrySet()) {
            String key = entry.getKey();    // 获得Map集的键
            Employee value = entry.getValue();  // 获得Map集的值
            System.out.println("key=" + key + ", value=" + value);
        }
        // 还可以用以下函数(得到的集都不可以增加元素,但能删除元素)
        // Set<K> keySet() 返回映射表中所有键的集视图
        // Collection<T> valuesSet() 返回映射表中所有值的集视图
        // Set<Map.Entry<K, V>> entrySet() 返回映射表中的键值对
    }
}

/**
 * A minimalist employee class for testing purposes.
 */
class Employee {
    /**
     * Constructs an employee with $0 salary.
     * 
     * @param n
     *            the employee name
     */
    public Employee(String n) {
        name = n;
        salary = 0;
    }

    public String toString() {
        return "[name=" + name + ", salary=" + salary + "]";
    }

    private String name;
    private double salary;
}
