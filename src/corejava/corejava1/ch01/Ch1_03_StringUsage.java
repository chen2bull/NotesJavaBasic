/**
 *  @author Chen Mingjian  creat on 2012-3-26
 *
 */
package corejava.corejava1.ch01;

/**
 * 这是一个用于测试字符串使用的类
 */
public class Ch1_03_StringUsage {
    public static void main(String[] args) {

        TestString();
        /** 测试String使用的代码 */

        TestStringBuilder();
        /** 测试StringBuilder使用的代码 */

    }

    /** 测试String使用的代码 start */
    public static void TestString() {
        /* 求子串 */
        String greeting = "Hello";
        System.out.println(greeting.substring(0, 3));

        /* 拼接 */
        /* 将一个字符串与一个非字符串相加时，后者被转换为成字符串（调用toString方法） */
        String rating = "No.";
        int age = 13;
        System.out.println(rating + age);

        /* 不可以改变字符串 */
        /* String类没有提供用于修改字符串的方法，但可以修改String变量，让它引用另一个字符串 */
        String str1 = greeting.substring(0, 3) + "p!";
        System.out.println(str1);

        /* 检测字符串是否相等 */
        System.out.println("Hello".equals(greeting));

        /* 如果要遍历一个字符串，可以使用以下语句 */
        String sentence = "ÓÂ¸ÒµÄÐÄ  €★○◎◇◆¤♂♀请遍历这个字符串\n";
        int cp;
        for (int i = 0; i < sentence.length(); i++) {
            cp = sentence.codePointAt(i);
            if (Character.isSupplementaryCodePoint(cp)) {
                i++;
                cp = sentence.codePointAt(i);
            }
            System.out.print((char) cp);
        }

        /* 不建议使用以下语句 */
        for (int i = 0; i < sentence.length(); i++) {
            System.out.print(sentence.charAt(i));
        }

        /*
         * 代码点与代码单元的对应关系 Unicode 代码点 U+0041 U+00DF U+6771 U+10400 UTF-32 代码单元
         * 00000041 000000DF 00006771 00010400 UTF-16 代码单元 0041 00DF 6771 D801
         * DC00 UTF-8 代码单元 41 C3 9F E6 9D B1 F0 90 90 80
         */

        /* String 类的使用法参见java.lang.string，以下的为常用的方法 */
        /*
         * char charAt(int index) 返回指定索引处的 代码单元值，除非对底层代码单元感兴趣，否则不需要调用这个方法。 int
         * codePointAt(int index) 返回指定索引处的字符（Unicode 代码点）。 int
         * codePointCount(0,test.length()); 0-length单元之间，返回代码点的数量 int
         * compareTo(String anotherString) 按字典顺序比较两个字符串。 boolean endsWith(String
         * suffix) 测试此字符串是否以指定的后缀结束。 boolean equals(Object anObject)
         * 将此字符串是否与指定的对象的toString方法的返回值相等。 boolean equalsIgnoreCase(String
         * anotherString) 将此 String 与另一个 String 比较，不考虑大小写。 int indexOf(int ch)
         * int indexOf(int ch, int fromIndex) int indexOf(String str) int
         * indexOf(String str, int fromIndex)
         * 返回与字符串str或代码点cp匹配的第一个子串的开始位置。这个位置从索引0或fromIndex开始计算。如果在原始串中不在在，返回-1。
         * int lastIndexOf(int ch) int lastIndexOf(int ch, int fromIndex) int
         * lastIndexOf(String str) int lastIndexOf(String str, int fromIndex)
         * 返回与字符串str或代码点cp匹配的第一个子串的开始位置。这个位置从索引0或fromIndex开始计算。如果在原始串中不在在，返回-1。
         * int length() 返回此字符串的长度。 String replace(CharSequence target,
         * CharSequence replacement) 使用指定的字面值替换序列替换此字符串所有匹配字面值目标序列的子字符串。 boolean
         * startsWith(String prefix) 测试此字符串是否以指定的前缀开始。 boolean startsWith(String
         * prefix, int toffset) 测试此字符串从指定索引开始的子字符串是否以指定前缀开始。 String
         * substring(int beginIndex) 返回一个新的字符串，它是此字符串的一个子字符串。 String
         * substring(int beginIndex, int endIndex) 返回一个新字符串，它是此字符串的一个子字符串。
         * String toLowerCase() 使用默认语言环境的规则将此 String 中的所有字符都转换为小写。 String
         * toUpperCase() 使用默认语言环境的规则将此 String 中的所有字符都转换为大写。 String trim()
         * 返回字符串的副本，忽略前导空白和尾部空白。 static String format(String format, Object...
         * args) 使用指定的格式字符串和参数返回一个格式化字符串。 static String valueOf(boolean b)
         * static String valueOf(char c) static String valueOf(char[] data)
         * static String valueOf(char[] data, int offset, int count) static
         * String valueOf(double d) static String valueOf(float f) static String
         * valueOf(int i) static String valueOf(long l) static String
         * valueOf(Object obj) 返回 float 参数的字符串表示形式。
         */
    }

    /** 测试String使用的代码 end */

    /** 测试StringBuilder使用的代码 start */
    public static void TestStringBuilder() {
        StringBuilder builder = new StringBuilder("Test ");
        builder.append(15);
        builder.appendCodePoint(65);    // 'A'的Unicode码为65
        System.out.println(builder);
    }
    /* StringBuilder 类的使用法参见java.lang.StringBuilder，以下的为常用的方法 */
}
/*
 * StringBuilder(String str) 构造一个字符串生成器，并初始化为指定的字符串内容。 StringBuilder(String str)
 * 构造一个字符串生成器，并初始化为指定的字符串内容。 StringBuilder append(Object obj) 追加 Object
 * 参数的字符串表示形式。 StringBuilder append(String str) 将指定的字符串追加到此字符序列。 StringBuilder
 * appendCodePoint(int codePoint) 将 codePoint 参数的字符串表示形式追加到此序列。 StringBuilder
 * delete(int start, int end) 移除此序列的子字符串中的字符。 StringBuilder insert(int offset,
 * Object obj) 将 Object 参数的字符串表示形式插入此字符序列中。 StringBuilder insert(int offset,
 * String str) 将字符串插入此字符序列中。 int length() 返回长度（字符数）。 StringBuilder replace(int
 * start, int end, String str) 使用给定 String 中的字符替换此序列的子字符串中的字符。 StringBuilder
 * reverse() 将此字符序列用其反转形式取代。 void setCharAt(int index, char ch) 将给定索引处的字符设置为 ch。
 * StringBuilder delete(int start, int end) 移除此序列的子字符串中的字符。 String toString()
 * 返回此序列中数据的字符串表示形式。
 */
/** 测试StringBuilder使用的代码 end */

