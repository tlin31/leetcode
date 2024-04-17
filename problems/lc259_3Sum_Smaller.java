259. 3Sum Smaller
Medium

Given an array of n integers nums and an integer target, find the number of index 
triplets i, j, k with 0 <= i < j < k < n that satisfy the condition 
nums[i] + nums[j] + nums[k] < target.

 
Example 1:

Input: nums = [-2,0,1,3], target = 2
Output: 2
Explanation: Because there are two triplets which sums are less than 2:
[-2,0,1]
[-2,0,3]

Example 2:
Input: nums = [], target = 0
Output: 0

Example 3:
Input: nums = [0], target = 0
Output: 0
 

Constraints:

n == nums.length
0 <= n <= 3500
-100 <= nums[i] <= 100
-100 <= target <= 100


******************************************************
key:
	- is the array sorted?
	- don't return the equal triplets

	
	- edge case:
		1) array contains less than 3 elements, return 0
		2) empty array, return 0
		3) can store in set, and return set size?

******************************************************



=======================================================================================================
Method 1: fix 1 element, then 2 pointers


Stats:

	- time: 0(n^2)
	- space:O(1)


Method:
	target = 7
	-	[1, 2, 3, 5, 8]
 		 ↑        ↑
		left    right
Now the pair sum is 1+5=6, which is less than target. How many pairs 
with one of the index=left that satisfy the condition? You can tell by the 
difference between right and left which is 3, namely (1,2), (1,3),
and (1,5). Therefore, we move left one step to its right.




class Solution {
    public int threeSumSmaller(int[] nums, int target) {
        Arrays.sort(nums);
        int sum = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            sum += twoSumSmaller(nums, i + 1, target - nums[i]);
        }
        return sum;
    }

    private int twoSumSmaller(int[] nums, int startIndex, int target) {
        int sum = 0;
        int left = startIndex;
        int right = nums.length - 1;
        while (left < right) {
            if (nums[left] + nums[right] < target) {
                sum += right - left;
                left++;
            } else {
                right--;
            }
        }
        return sum;
    }
}