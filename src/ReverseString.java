import java.util.Collections;

/**
 *
 * Created by Administrator on 2016/7/3.
 */
public class ReverseString {
    public static void main(String args[]) {
        String string = "这是一些中文啦";
        System.out.println(new ReverseString().reverseString(string));
    }

    // 没有用codePoint, 不适用于增补字符,但是速度更快的版本
    public String reverseString(String s) {
        char charArray[] = s.toCharArray();
        int mid = charArray.length / 2;
        char tempChar;
        for (int i = 0; i < mid; i++) {
            tempChar = charArray[i];
            charArray[i] = charArray[charArray.length - 1 -i];
            charArray[charArray.length - 1 -i] = tempChar;
        }
        return new String(charArray, 0, charArray.length);
    }
}
