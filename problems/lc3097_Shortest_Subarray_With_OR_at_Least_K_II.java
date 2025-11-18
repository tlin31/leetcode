3097. Shortest Subarray With OR at Least K II - Medium

You are given an array nums of non-negative integers and an integer k.

An array is called special if the bitwise OR of all of its elements is at least k.

Return the length of the shortest special non-empty subarray of nums, or return -1 if no 
special subarray exists.

 

Example 1:

Input: nums = [1,2,3], k = 2

Output: 1

Explanation:

The subarray [3] has OR value of 3. Hence, we return 1.

Example 2:

Input: nums = [2,1,8], k = 10

Output: 3

Explanation:

The subarray [2,1,8] has OR value of 11. Hence, we return 3.

Example 3:

Input: nums = [1,2], k = 0

Output: 1

Explanation:

The subarray [1] has OR value of 1. Hence, we return 1.

 

Constraints:

1 <= nums.length <= 2 * 105
0 <= nums[i] <= 109
0 <= k <= 109

******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1: 

Method:

	-	sliding window



Stats:

	- 
	- 


class Solution {

    public int minimumSubarrayLength(int[] nums, int k) {
        int n = nums.length, result = n + 1;
        int[] bits = new int[32];
        for (int start = 0, end = 0; end < n; end++) {
            update(bits, nums[end], 1); // insert nums[end] into window
            //缩小window，移动左指针
            while (start <= end && bitsToNum(bits) >= k) {
                result = Math.min(result, end - start + 1);
                update(bits, nums[start++], -1); // remove nums[start] from window
            }
        }
        return result != n + 1 ? result : -1;
    }

    private void update(int[] bits, int x, int change) {
        // insert or remove element from window, time: O(32)
        for (int i = 0; i < 32; i++) {
            if (((x >> i) & 1) != 0) {
                bits[i] += change;
            }
        }
    }

    private int bitsToNum(int[] bits) {
        // convert 32-size bits array to integer, time: O(32)
        int result = 0;
        for (int i = 0; i < 32; i++) {
            if (bits[i] != 0) {
                result |= 1 << i;
            }
        }
        return result;
    }
}




===================================================================================================


binary search:

class Solution {
    private void update(int[] bits, int x, int change) {
        // insert or remove element from window, time: O(32)
        for (int i = 0; i < 32; i++) {
            if (((x >> i) & 1) != 0) {
                bits[i] += change;
            }
        }
    }

    private int bitsToNum(int[] bits) {
        // convert 32-size bits array to integer, time: O(32)
        int result = 0;
        for (int i = 0; i < 32; i++) {
            if (bits[i] != 0) {
                result |= 1 << i;
            }
        }
        return result;
    }

    private boolean isSpecial(int[] nums, int k, int len) {
        // checks if special subarray exists for length len, time: O(n)
        int n = nums.length;
        int[] bits = new int[32];
        for (int i = 0; i < n; i++) {
            update(bits, nums[i], 1); // insert nums[i] into window
            if (i >= len) {
                update(bits, nums[i - len], -1); // remove nums[i - len] from window
            }
            if (i >= len - 1 && bitsToNum(bits) >= k) {
                // special subarray found
                return true;
            }
        }
        return false;
    }

    public int minimumSubarrayLength(int[] nums, int k) {
        int n = nums.length, start = 1, end = n + 1, mid;
        while (start < end) {
            mid = (start + end) / 2;
            if (!isSpecial(nums, k, mid)) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        return start != n + 1 ? start : -1;
    }
}


