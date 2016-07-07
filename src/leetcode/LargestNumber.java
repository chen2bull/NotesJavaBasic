package leetcode;

import java.util.*;

/**Given a list of non negative integers, arrange them such that they form the largest number.

 For example, given [3, 30, 34, 5, 9], the largest formed number is 9534330.

 Note: The result may be very large, so you need to return a string instead of an integer.
 * Created by 陈明键 on 2016/7/4.
 */
public class LargestNumber {
    // 没有什么黑魔法啦,就是这样照拷贝
    public String largestNumber(int[] nums) {
        String[] strs = new String[nums.length];
        for(int i = 0; i < nums.length; i++){
            strs[i] = String.valueOf(nums[i]);
        }
        Arrays.sort(strs, new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                return (str2 + str1).compareTo(str1 + str2);
            }
        });
        if(strs[0].equals("0")) return "0";
        StringBuilder sb = new StringBuilder();
        for(String str : strs) sb.append(str);
        return sb.toString();
    }
}
