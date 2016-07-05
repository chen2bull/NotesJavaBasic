/**Given two numbers represented as strings, return multiplication of the numbers as a string.

 Note:
 The numbers can be arbitrarily large and are non-negative.
 Converting the input string to integer is NOT allowed.
 You should NOT use internal library such as BigInteger.
 * Created by 陈明键 on 2016/7/5.
 */
public class MultiplyStrings {
    public String multiply(String num1, String num2) {
        int m = num1.length(), n = num2.length();
        int[] result = new int[m + n];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int temp = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                int pos1 = i + j;
                int pos2 = i + j + 1;
                result[pos2] += temp;
                result[pos1] += result[pos2] / 10;
                result[pos2] = result[pos2] % 10;
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int p : result){
            if(!(sb.length() == 0 && p == 0)) {
                sb.append(p);
            }
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }
    public static void main(String[] args) {
        System.out.println(new MultiplyStrings().multiply("0", "0"));
        System.out.println(new MultiplyStrings().multiply("7", "7"));
        System.out.println(new MultiplyStrings().multiply("65567", "765565"));
    }
}

/*
    97858|
     8837|
    *****|
   ***** |
  *****  |
 *****   |
---------|--
*********|
m = num1.length(), n = num2.length();
结果放在长度为m+n的数组中的话,
那么第i和j个元素相乘,改变
运算的结果位:result[1 + i + j]
进位:result[1 + i + j - 1]即是result[i+j]

 */