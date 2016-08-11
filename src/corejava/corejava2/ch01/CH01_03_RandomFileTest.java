package corejava.corejava2.ch01;

import java.io.*;
import java.util.*;

/**
 * 程序将三条记录写到一上数据文件中，然后以逆序将它们从文件中读回。
 * 为了高效地读回，这里使用了随机访问。
 * @version 1.11 2004-05-11
 * @author Cay Horstmann
 */
public class CH01_03_RandomFileTest {
    public static void main(String[] args) {
        Employee2[] staff = new Employee2[3];

        staff[0] = new Employee2("Carl Cracker", 75000, 1987, 12, 15);
        staff[1] = new Employee2("Harry Hacker", 50000, 1989, 10, 1);
        staff[2] = new Employee2("Tony Tester", 40000, 1990, 3, 15);

        try {
            // save all Employee2 records to the file Employee2.dat
            DataOutputStream out = new DataOutputStream(new FileOutputStream("Employee2.dat"));
            for (Employee2 e : staff)
                e.writeData(out);
            out.close();

            // retrieve all records into a new array
            RandomAccessFile in = new RandomAccessFile("Employee2.dat", "r");
            // compute the array size
            int n = (int) (in.length() / Employee2.RECORD_SIZE);
            Employee2[] newStaff = new Employee2[n];

            // read Employee2s in reverse order
            for (int i = n - 1; i >= 0; i--) {
                newStaff[i] = new Employee2();
                in.seek(i * Employee2.RECORD_SIZE);
                newStaff[i].readData(in);
            }
            in.close();

            // print the newly read Employee2 records
            for (Employee2 e : newStaff)
                System.out.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Employee2 {
    public Employee2() {}

    public Employee2(String n, double s, int year, int month, int day) {
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

    /**
     * Raises the salary of this Employee2.
     * 
     * @byPercent the percentage of the raise
     */
    public void raiseSalary(double byPercent) {
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    public String toString() {
        return getClass().getName() + "[name=" + name + ",salary=" + salary + ",hireDay=" + hireDay + "]";
    }

    /**
     * Writes Employee2 data to a data output
     * 
     * @param out
     *            the data output
     */
    public void writeData(DataOutput out) throws IOException {
        DataIO.writeFixedString(name, NAME_SIZE, out);
        out.writeDouble(salary);

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(hireDay);
        out.writeInt(calendar.get(Calendar.YEAR));
        out.writeInt(calendar.get(Calendar.MONTH) + 1);
        out.writeInt(calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Reads Employee2 data from a data input
     * 
     * @param in
     *            the data input
     */
    public void readData(DataInput in) throws IOException {
        name = DataIO.readFixedString(NAME_SIZE, in);
        salary = in.readDouble();   //读取各类型的数据
        int y = in.readInt();
        int m = in.readInt();
        int d = in.readInt();
        GregorianCalendar calendar = new GregorianCalendar(y, m - 1, d);
        hireDay = calendar.getTime();
    }

    public static final int NAME_SIZE   = 40;
    public static final int RECORD_SIZE = 2 * NAME_SIZE + 8 + 4 + 4 + 4;

    private String          name;
    private double          salary;
    private Date            hireDay;
}

/**
 * 自定义的类，提供读和写固定长度字符串的方法
 */
class DataIO {
    public static String readFixedString(int size, DataInput in) throws IOException {
        StringBuilder b = new StringBuilder(size);
        int i = 0;
        boolean more = true;
        while (more && i < size) {
            char ch = in.readChar();
            i++;
            if (ch == 0)
                more = false;
            else
                b.append(ch);
        }
        in.skipBytes(2 * (size - i));
        return b.toString();
    }

    public static void writeFixedString(String s, int size, DataOutput out) throws IOException {
        for (int i = 0; i < size; i++) {
            char ch = 0;
            if (i < s.length())
                ch = s.charAt(i);
            out.writeChar(ch);
        }
    }
}
