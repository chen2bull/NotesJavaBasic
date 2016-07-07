package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.

 You may assume that each input would have exactly one solution.

 Example:
 Given nums = [2, 7, 11, 15], target = 9,

 Because nums[0] + nums[1] = 2 + 7 = 9,
 return [0, 1].
 UPDATE (2016/2/13):
 The return format had been changed to zero-based indices. Please read the above updated description carefully.

 Subscribe to see which companies asked this question
 */
public class TwoSum {
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] == target - nums[i]) {
                    return new int[] { i, j };
                }
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    public static void main(String args[]) {
        test1();
    }
    public static void test1() {
        int []input = {-1,-2,-3,-4,-5};
        int []result = new TwoSum().twoSum(input, -8);
        for (int num : result) {
            System.out.println(num);
        }
    }
    // 时间复杂度为 O(N) 的版本,洗呢能更好
    public int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            Integer prIndex = map.get(nums[i]);
            if (prIndex != null) {
                return new int[]{prIndex, i};
            } else {
                map.put(target - nums[i], i);
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }

}