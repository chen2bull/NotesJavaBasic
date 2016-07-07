package leetcode;

import java.util.*;

/**
 * Given a string, find the length of the longest substring without repeating characters.
 * <p/>
 * Examples:
 * <p/>
 * Given "abcabcbb", the answer is "abc", which the length is 3.
 * <p/>
 * Given "bbbbb", the answer is "b", with the length of 1.
 * <p/>
 * Given "pwwkew", the answer is "wke", with the length of 3. Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
 * <p/>
 * Subscribe to see which companies asked this question
 * Created by Administrator on 2016/7/4.
 */
public class LongestSubstringWithoutRepeatingCharacters {
    public int lengthOfLongestSubstring(String s) {
        char[] cArray = s.toCharArray();
        int maxLen = 0;
        int curLen;
        int startPos = 0;
        int curPos;

        Map<Character, Integer> map = new HashMap<Character, Integer>();

        for (curPos = 0; curPos < cArray.length; curPos++) {
            char curChar = cArray[curPos];
            // 如果当前字符在starPos以后出现过,表示要更新这个区间
            if (map.containsKey(curChar) && map.get(curChar) >= startPos) {
                startPos = map.get(curChar) + 1;    // [startPos, curPos]是闭区间,所以都要加1
            } else {
                curLen = curPos - startPos + 1;
                if (curLen > maxLen) {
                    maxLen = curLen;
                }
            }
            map.put(curChar, curPos);
        }
        return maxLen;
    }

    public static void main(String args[]) {
        System.out.println(new LongestSubstringWithoutRepeatingCharacters().lengthOfLongestSubstring("abcdebbbfg"));
        System.out.println(new LongestSubstringWithoutRepeatingCharacters().lengthOfLongestSubstring("abcabcbb"));
        System.out.println(new LongestSubstringWithoutRepeatingCharacters().lengthOfLongestSubstring("bbbbb"));
        System.out.println(new LongestSubstringWithoutRepeatingCharacters().lengthOfLongestSubstring("tmmzuxt"));
        System.out.println(new LongestSubstringWithoutRepeatingCharacters().lengthOfLongestSubstring("abcdef"));
    }

    public int lengthOfLongestSubstring2(String s) {
        // 这里的思路是,使用两个指针,指定一个窗口区间,区间内的都是判断子字符串长度需要的
        // 注意:A sliding window is an abstract concept commonly used in array/string problems.
        // 比如在每步循环开始的时候,[i,j) 是xxx
        int n = s.length();
        Set<Character> set = new HashSet<Character>();
        int ans = 0, i = 0, j = 0;
        while (i < n && j < n) {
            // try to extend the range [i, j]
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j++));
                ans = Math.max(ans, j - i);
            } else {
                set.remove(s.charAt(i++));
            }
        }
        return ans;
    }
}
