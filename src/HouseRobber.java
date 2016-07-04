/**You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed,
 * the only constraint stopping you from robbing each of them is that adjacent houses have security system connected and
 * it will automatically contact the police if two adjacent houses were broken into on the same night.
 *
 * Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount of
 * money you can rob tonight without alerting the police.
 * Created by 陈明键 on 2016/7/4.
 */
public class HouseRobber {
    public int rob(int[] nums) {
        if (nums.length == 0 ) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        int notPickLastSum = 0, pickLastSum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int temp = pickLastSum;
            pickLastSum = Math.max(notPickLastSum + nums[i], pickLastSum);
            notPickLastSum = temp;
        }

        return pickLastSum;
    }
    public static void main(String[] args) {
        int[] arr = {85, 64, 59, 75, 84, 20, 53, 68, 42, 58, 99, 57, 0, 566, 57, 48, 2};
        System.out.println(new HouseRobber().rob(arr));
    }
}
