/**
 *  @author Chen Mingjian  creat on 2012-10-8
 *
 */
package corejava.corejava2.ch01;

import java.io.*;
import java.util.*;

/**
 * 将一个Employee保存记录数组存储成一个文本文件，其中每个记录都保存在单独的一行中，而实例的域彼此之间
 * 使用分隔符分离开（本例中使用竖线‘|’作为分隔符，这里假设‘｜’不会出现在数据项中）
 * 
 * 以文本方式写出数据要用PrintWriter
 * 以文本方式读入数据要用Scanner
 * 
 * @version 1.12 2007-06-22
 * @author Cay Horstmann
 */
public class CH01_01_TextFileTest {
    public static void main(String[] args) {
        Employee1[] staff = new Employee1[3];

        staff[0] = new Employee1("Carl Cracker", 75000, 1987, 12, 15);
        staff[1] = new Employee1("Harry Hacker", 50000, 1989, 10, 1);
        staff[2] = new Employee1("Tony Tester", 40000, 1990, 3, 15);

        try {
            // save all Employee1 records to the file Employee1.dat
            PrintWriter out = new PrintWriter("Employee1.dat");
            writeData(staff, out);
            out.close();

            // retrieve all records into a new array
            Scanner in = new Scanner(new FileReader("Employee1.dat"));
            Employee1[] newStaff = readData(in);
            in.close();

            // print the newly read Employee1 records
            for (Employee1 e : newStaff)
                System.out.println(e);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Writes all Employee1s in an array to a print writer
     * 
     * @param Employee1s
     *            an array of Employee1s
     * @param out
     *            a print writer
     */
    private static void writeData(Employee1[] Employee1s, PrintWriter out) throws IOException {
        // write number of Employee1s
        out.println(Employee1s.length);

        for (Employee1 e : Employee1s)
            e.writeData(out);
    }

    /**
     * Reads an array of Employee1s from a scanner
     * 
     * @param in
     *            the scanner
     * @return the array of Employee1s
     */
    private static Employee1[] readData(Scanner in) {
        // retrieve the array size
        int n = in.nextInt();
        in.nextLine(); // consume newline

        Employee1[] Employee1s = new Employee1[n];
        for (int i = 0; i < n; i++) {
            Employee1s[i] = new Employee1();
            Employee1s[i].readData(in);
        }
        return Employee1s;
    }
}

class Employee1 {
    public Employee1() {}

    public Employee1(String n, double s, int year, int month, int day) {
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

    /**
     * Writes Employee1 data to a print writer
     * 
     * @param out
     *            the print writer
     */
    public void writeData(PrintWriter out) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(hireDay);
        out.println(name + "|" + salary + "|" + calendar.get(Calendar.YEAR) + "|" + (calendar.get(Calendar.MONTH) + 1) + "|"
                + calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Reads Employee1 data from a buffered reader
     * 
     * @param in
     *            the scanner
     */
    public void readData(Scanner in) {
        String line = in.nextLine();
        String[] tokens = line.split("\\|");
        name = tokens[0];
        salary = Double.parseDouble(tokens[1]);
        int y = Integer.parseInt(tokens[2]);
        int m = Integer.parseInt(tokens[3]);
        int d = Integer.parseInt(tokens[4]);
        GregorianCalendar calendar = new GregorianCalendar(y, m - 1, d);
        hireDay = calendar.getTime();
    }

    private String name;
    private double salary;
    private Date   hireDay;
}
