/**
 *
 * Created by Administrator on 2016/7/3.
 */
public class StringToInteger {
    public int myAtoi(String str) {
        try {
            return Integer.valueOf(str.trim());
        } catch (NumberFormatException e) {

            return 0;
        }
    }
    public static void main(String args[]) {

        System.out.println(Integer.valueOf("  010".trim()));
    }
}
