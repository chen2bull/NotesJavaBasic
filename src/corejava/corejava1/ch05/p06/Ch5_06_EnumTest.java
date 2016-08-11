/**
 *  @author Chen Mingjian  creat on 2012-3-26
 *
 */
package corejava.corejava1.ch05.p06;

import java.util.*;

/**
 * This program demonstrates enumerated types.
 * @version 1.0 2004-05-24
 * @author Cay Horstmann
 */
/**
 * 枚举类的使用
 */
public class Ch5_06_EnumTest {
    public static void main(String[] args) {
        // 定义完了Color之后，就可以像下面这样给Color类型的变量赋值了
        Color co1 = Color.RED;      // 同Enum<Color> co1 = Color.RED;Enum是一个类模板
        System.out.println(co1);    // 可以认为，Color类有一个返回枚举值字符串的toString方法

        Scanner in = new Scanner(System.in);
        System.out.print("Enter a size: (SMALL, MEDIUM, LARGE, EXTRA_LARGE) ");
        String input = in.next().toUpperCase();

        // toString的逆方法是Enum.valueOf(Size.class, aString);
        Size size = Enum.valueOf(Size.class, input);
        System.out.println("size=" + size);
        System.out.println("abbreviation=" + size.getAbbreviation());
        if (size == Size.EXTRA_LARGE) {
            System.out.println("Good job--you paid attention to the _.");
        }
    }
}

/* 定义枚举类型 */
enum Color {
    RED, BLACK, WHITE, YELLOW, GREEN
}

enum Size {
    SMALL("S"), MEDIUM("M"), LARGE("L"), EXTRA_LARGE("XL");

    private Size(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    private String abbreviation;
}
