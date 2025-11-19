962. Maximum Width Ramp - Medium

A ramp in an integer array nums is a pair (i, j) for which i < j and nums[i] <= nums[j]. 
The width of such a ramp is j - i.

Given an integer array nums, return the maximum width of a ramp in nums. If there is no 
ramp in nums, return 0.

 

Example 1:

Input: nums = [6,0,8,2,1,5]
Output: 4
Explanation: The maximum width ramp is achieved at (i, j) = (1, 5): nums[1] = 0 and nums[5] = 5.
Example 2:

Input: nums = [9,8,1,0,1,9,4,0,4,1]
Output: 7
Explanation: The maximum width ramp is achieved at (i, j) = (2, 9): nums[2] = 1 and nums[9] = 1.
 

Constraints:

2 <= nums.length <= 5 * 104
0 <= nums[i] <= 5 * 104


******************************************************
key:
	- DP: keep a max array for max value starting from right
	- monotonic stack
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1: stack

Method: find next largest -- decreasing array -- if cur>array.peek(), pop

Stats:

	- Time: O(n)
	- 

class Solution {

    public int maxWidthRamp(int[] nums) {
        int n = nums.length;
        Stack<Integer> indicesStack = new Stack<>();

        // Fill the stack with indices in increasing order of their values
        for (int i = 0; i < n; i++) {
            if (indicesStack.isEmpty() || nums[indicesStack.peek()] > nums[i]) {
                indicesStack.push(i);
            }
        }

        int maxWidth = 0;

        // Traverse the array from the end to the start
        for (int j = n - 1; j >= 0; j--) {
            while (!indicesStack.isEmpty() && nums[indicesStack.peek()] <= nums[j]) {
                maxWidth = Math.max(maxWidth, j - indicesStack.peek());
                // Pop the index since it's already processed
                indicesStack.pop();
            }
        }

        return maxWidth;
    }
}



two pointers:
先构造一个从右往左的 “后缀最小值数组”，保持单调递减。
然后用两个指针从左往右和从右往左同时移动，寻找最宽的 j − i。

也就是：
找最大 j - i，使得 A[i] ≤ A[j]



class Solution {

    public int maxWidthRamp(int[] nums) {
        int n = nums.length;
        int[] rightMax = new int[n];

        // Fill rightMax array with the maximum values from the right
        rightMax[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], nums[i]);
        }

        int left = 0, right = 0;
        int maxWidth = 0;

        // Traverse the array using left and right pointers
        while (right < n) {
            // Move left pointer forward if current left exceeds rightMax
            while (left < right && nums[left] > rightMax[right]) {
                left++;
            }
            maxWidth = Math.max(maxWidth, right - left);
            right++;
        }

        return maxWidth;
    }
}



