2012. Sum of Beauty in the Array - Medium

You are given a 0-indexed integer array nums. For each index i (1 <= i <= nums.length - 2) the 
beauty of nums[i] equals:

2, if nums[j] < nums[i] < nums[k], for all 0 <= j < i and for all i < k <= nums.length - 1.
1, if nums[i - 1] < nums[i] < nums[i + 1], and the previous condition is not satisfied.
0, if none of the previous conditions holds.
Return the sum of beauty of all nums[i] where 1 <= i <= nums.length - 2.

 

Example 1:

Input: nums = [1,2,3]
Output: 2
Explanation: For each index i in the range 1 <= i <= 1:
- The beauty of nums[1] equals 2.

Example 2:

Input: nums = [2,4,6,4]
Output: 1
Explanation: For each index i in the range 1 <= i <= 2:
- The beauty of nums[1] equals 1.
- The beauty of nums[2] equals 0.

Example 3:

Input: nums = [3,2,1]
Output: 0
Explanation: For each index i in the range 1 <= i <= 1:
- The beauty of nums[1] equals 0.
 

Constraints:

3 <= nums.length <= 105
1 <= nums[i] <= 105


******************************************************
key:
	- use 2 boolean array
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- O(n)
	- 


Method:
	-  All the numbers left to current should be less and all the numbers right to current 
		should be high.
	So First we loop from left to right by maintaining the maxLeft and if current is greater than 
		maxLeft then all nums to the left are less.
	Similarly we loop from right to left by maintaining the minRight and if current is less than 
		minRight then all nums to the right are high.

when beauty=2: all left nums <current & all right nums >cur

class Solution {
    public int sumOfBeauties(int[] nums) {
        boolean[] left = new boolean[nums.length];
        boolean[] right = new boolean[nums.length];
        
        left[0] = true;
        int leftMax = nums[0];
        for(int i = 1; i < nums.length; i++) {
            if(nums[i] > leftMax) {
                left[i] = true;
                leftMax = nums[i];
            }
        }
        
        right[nums.length-1] = true;
        int rightMin = nums[nums.length-1];
        for(int i = nums.length-2; i >= 0; i--) {
            if(nums[i] < rightMin) {
                right[i] = true;
                rightMin = nums[i];
            }
        }
        
        int beautyCount = 0;
        for(int i = 1; i < nums.length-1; i++) {

            if(left[i] && right[i]) {
                beautyCount += 2;
            }
            
            else if(nums[i-1] < nums[i] && nums[i] < nums[i+1]) {
                beautyCount += 1;
            }
            
        }
        return beautyCount;
    }
}


=======================================================================================================

use 2 array to store min and max

    public int sumOfBeauties(int[] nums) {
        int sum = 0, n = nums.length;
        int[] mi = new int[n + 1], mx = new int[n + 1];
        mi[n] = Integer.MAX_VALUE;
        for (int i = 0, j = n - 1; i < n; ++i, --j) {
            mx[i + 1] = Math.max(nums[i], mx[i]);
            mi[j] = Math.min(nums[j], mi[j + 1]);
        }
        for (int i = 1; i < nums.length - 1; ++i) {
            if (mx[i] < nums[i] && nums[i] < mi[i + 1]) {
                sum += 2;
            }else if (nums[i - 1] < nums[i] && nums[i] < nums[i + 1]) {
                ++sum;
            }
        }
        return sum;
    }

