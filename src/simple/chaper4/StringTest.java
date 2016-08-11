import java.awt.Point;
import java.io.InputStream;
import java.util.Arrays;

/**第四课 Java 的常用包与数组的复制与排序
 * Java_chaper4
 * 
 * StringTest.java
 * 2011-11-19 上午1:31:55
 * 
 * 
 */
/**
 * @author Chen Mingjian 2011-11-19
 *
 */
public class StringTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				System.out.println(args[i]); //不包括“java”和程序名							
			}
		}
		
		//String
		String str1 = new String("abc");
		String str2 = new String("abc");
/*		if( str1 == str2 ) {
			System.out.println("str1 == str2");
		} else {
			System.out.println("str1 != str2");			
		}*/
		if( str1.equals(str2) ) {
			System.out.println("str1 equals to str2");
		} else {
			System.out.println("str1 not equals to str2");			
		}
		
		/* StringBuffer */
		StringBuffer strb1 = new StringBuffer();
		strb1.append("ABC").append("EFG");
		System.out.println(strb1);
		strb1.delete(1, 3);
		System.out.println(strb1);
		
		/* 数组 */
		int[] num = new int[3];
		for(int i = 0; i < num.length; i++) {
			System.out.println(num[i]);
		}
		Student[] students;
		students = new Student[3];
		for (int i = 0; i < students.length; i++) {
			System.out.println(students[i]); //students[i]为null;
		}
		
		//函数的形参和实参 ，传参都是以传值的方式进行的
		int x = 3;
		int y = 4;
		change(x,y);
		System.out.println("x="+x+"\ty="+y);
		
		//数组类型作形参的情况(类似于C++中的数组名)
		int[] num2 = new int[]{3,4};
		change(num2);
		System.out.println("x="+num2[0]+"\ty="+num2[1]);
		
		//引用类型作为形参的情况(类似于C++中的指针)
		Point ptPoint = new Point();
		ptPoint.x = 3;
		ptPoint.y = 4;
		change(ptPoint);
		System.out.println("x="+ptPoint.x+"\ty="+ptPoint.y);
		
		//使用克隆
//		Student s1 = new Student("ChenMjingjian", 23);
//		Student s2 = (Student)s1.clone();
//		s2.name = "QuMingzhi";
//		s2.age = 22;
//		System.out.println("name="+s1.name+",age="+s1.age); //修改学生s2不会影响学生s1的内容
		
		//使用克隆的深拷贝
		Professor p1 = new Professor("Wang", 50);
		Student s1 = new Student("ChenMjingjian", 23,p1);
		Student s2 = (Student)s1.clone();
		s2.name = "QuMingzhi";
		s2.age = 22;
		s2.p.age = 35;
		s2.p.name = "Li";
		System.out.println("name="+s1.name+",age="+s1.age); 	//修改学生s2不会影响学生s1的内容
		System.out.println("name="+s1.p.name+",age="+s1.p.age); //如果没有实现深拷贝：修改学生s2的Professor会影响学生s1中Professor的内容
																//如果实现了深拷贝：则不会影响
		
		//测试数组的复制
		ArrayTest.test();
		
		//测试数组的排序
		TestSort.test();
		
		//测试对象数组的排序
		TestObjectSort.test();
		
		//测试基本类型对应的封装类
		Test.test();
		
		//测试Runtime类 
		TestRunTime.test();
		
		//测试Process类 
		TestProcess.test();
	}

	
	public static void change(int x, int y) {	//函数的形参和实参 
		x = x +y;
		y = x - y;
		x = x - y;
	}
	public static void change(int[] num ) {		//数组类型作形参的情况(类似于C++中的数组名)
		num[0] = num[0] + num[1];
		num[1] = num[0] - num[1];
		num[0] = num[0] - num[1];
	}
	public static void change(Point pt) {		//引用类型作为形参的情况(类似于C++中的指针)
		pt.x=pt.x+pt.y;
		pt.y=pt.x-pt.y;
		pt.x=pt.x-pt.y;
	}
}

class Student implements Cloneable{
	String name;
	int age;
	Professor p;
	Student(String name,int age,Professor p) {
		this.name = name;
		this.age = age;
		this.p = p;
	}
	//增加克隆的能力
	public Object clone() {
		Student object = null;
		try {
			object = (Student)super.clone();	
			object.p = (Professor)p.clone(); 	//深拷贝
		} catch (CloneNotSupportedException e) {
			System.out.println(e.toString());
		}
		return object;
	}
}

//克隆时要注意以下内容,Student类中有Professor对象，为了实现Student的深拷贝，Professor需要实现Clonable接口并覆盖clone方法
class Professor implements Cloneable {
	String name;
	int age;
	public Professor(String name,int age) {
		this.name = name;
		this.age = age;
	}
	//增加克隆的能力
	public Object clone() {
		Professor object = null;
		try {
			object = (Professor)super.clone();			
		} catch (CloneNotSupportedException e) {
			System.out.println(e.toString());
		}
		return object;
	}
}

//测试数组的复制 start
class ArrayTest {
	public static void test() {
		int[] num1=new int[]{5,4,3,2,1};
		int[] num2=new int[10];
		System.arraycopy(num1, 2, num2, 6, num1.length-2);
		System.out.println("数组num2的内容如下：");
		for (int i = 0; i < num2.length; i++) {
			System.out.print( num2[i] +", ");
		}
		
		MyPoint[] pts1=new MyPoint[]{new MyPoint(1, 1), new MyPoint(2, 2), new MyPoint(3, 3) };
		MyPoint[] pts2=new MyPoint[3];
		System.arraycopy(pts1, 0, pts2, 0, pts1.length);
		System.out.println("\n数组pts2的内容如下：");
		for (int i = 0; i < pts2.length; i++) {
			System.out.print( pts2[i] +", ");
		}
		pts2[1].x = 5;
		pts2[1].y = 5;
		
		System.out.println("\npts1[1]的内容如下："); //pts2拷贝了pts1中的引用类型MyPoint,因此为浅拷贝
		System.out.print( pts1[1] +", ");
		
		
	}
}

class MyPoint {
	int x,y;
	public MyPoint(int x,int y) {
		this.x = x;
		this.y = y;
	}
	public String toString() {
		StringBuffer bs = new StringBuffer();
		bs.append("[x="+x+",y="+y+"]");
		return bs.toString();
	}
}
//测试数组的复制 end

//测试数组的排序 start
class TestSort {
	public static void test() {
		int[] SortNum = new int[]{4,2,3,1,5};
		Arrays.sort(SortNum);
		System.out.println("\nSortNum的内容如下："); 
		for (int i = 0; i < SortNum.length; i++) {
			System.out.print(SortNum[i]+", ");
		}
		
		int index=Arrays.binarySearch(SortNum, 2);
		System.out.println("\nindex="+index);
		System.out.println("element="+SortNum[index]);
	}
}
//测试数组的排序 end

//测试对象数组的排序 start
class TestObjectSort {
	static void test() {
		Employee[] employees = new Employee[]{
				new Employee(24, "Chen Mjingjian"),
				new Employee(22, "Shi Feibao"),
				new Employee(23, "Qu Mingzhi"),
				new Employee(23, "Ao Lao"),
		};
		Arrays.sort(employees);
		System.out.println("\nemployees的内容如下：");
		for (int i = 0; i < employees.length; i++) {
			System.out.print(employees[i]+", ");
		}
	}
}
class Employee implements java.lang.Comparable { 		//注意，要排序一个对象数组，则这种对象要实现Compareable并定义compareTo
	int age;
	String name;
	public Employee(int age,String name) {
		this.age = age;
		this.name = name;
	}
	
	public int compareTo(Object o) {
		Employee e = (Employee)o;
		int re_value;
		if(age > e.age) {
			re_value = 1;
		} else if(age < e.age) {
			re_value = 0;
		} else {
			re_value = name.compareTo(e.name);
		}
		return re_value;
	}
	
	@Override
	public String toString() {
		
		return "[age="+age+",name="+name+"]";
	}
}
//测试对象数组的排序 end

//测试基本类型对应的封装类start
//注意：封装类中的数据成员是不能被改变的
class Test {
	public static void test() {
		int i=3;
		Integer integer=new Integer(i);
		int j=integer.intValue();
		System.out.println("\n\nj="+j);
		
		String string="324";
		System.out.println(Integer.valueOf(string));   //字符串转Integer
		System.out.println(Integer.parseInt(string));  //字符串转整型
	}
}
//测试基本类型对应的封装类 end 


//测试Runtime类 start
/*每个 Java 应用程序都有一个 Runtime 类实例，使应用程序能够与其运行的环境相连接。
 * 可以通过 getRuntime 方法获取当前运行时。 应用程序不能创建自己的 Runtime 类实例。 
*/
class TestRunTime {
    public static void test() {
        Runtime rt = Runtime.getRuntime();
        System.out.println("Runtime test Start");
        
        System.out.println(rt.freeMemory());
        System.out.println(rt.totalMemory());
/*        try {
            rt.exec("notepad"); //执行一个外部程序，作为当前进程的子进程
            //当然可以：编译各种源文件，执行编译产生的程序
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        
        System.out.println("Runtime test End");
        
    }
}
//测试Runtime类 end

//测试Process类 start
//通过Runtime对象的方法得到Process类
class TestProcess {
    public static void test() {
        System.out.println("Process test Start");
        Runtime rt = Runtime.getRuntime();
        try {
//            rt.exec("javac simple.chaper1.simple.chaper4.hello.java"); //执行一个外部程序，作为当前进程的子进程
            Process pro = rt.exec("java simple.chaper1.simple.chaper4.hello");
            InputStream inStream = pro.getInputStream();
            int data;
            System.out.println("Start");  //以下内容不可以在Eclipse中直接执行，需要到命令行中才可以
            while( (data= inStream.read()) != -1) {
                System.out.print((char)data);
            }
            System.out.println("End");
            //当然可以：编译各种源文件，执行编译产生的程序
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Process test End");
    }
}

//测试Process类 end

//单例类的实现 start
class Singleton {
    private static final Singleton st = new Singleton();
    private Singleton() {
        
    }
    public static Singleton getSingleton() {
        return st;
    }
}
//单例类的实现 end